package imagen.emm.cibo;

//import android.support.v7.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Perfil_Usuario extends Activity{

    Button btn_TomaFoto;
    Button btn_VerAlmacen;
    private TextView text_Nombre_Usuario;
    Button btn_logout;
    DatabaseReference databaseUsuarios;
    DatabaseReference databaseAlmacen;
    private ArrayList<String> notificaciones = new ArrayList<>();
    ListView listaNotificaciones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        databaseUsuarios = FirebaseDatabase.getInstance().getReference("Usuarios");

        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_perfil__usuario);

            btn_TomaFoto = (Button) findViewById(R.id.btn_Toma_Foto);
            btn_VerAlmacen = (Button) findViewById(R.id.btn_Visualizar_Almacen);
            text_Nombre_Usuario = ( TextView ) findViewById(R.id.text_Nombre_Usuario);
            listaNotificaciones = (ListView) findViewById(R.id.ListaNotificaciones);
            listaNotificaciones.setAdapter(null);


            btn_logout = (Button) findViewById(R.id.btn_Cancelar_menu);

            btn_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.exit(0);
                }
            });

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
               // String id = databaseUsuarios.push().getKey();

                UsuarioFirebase usuarioFirebase = new UsuarioFirebase(personId,personName,personEmail);
                databaseUsuarios.child(personId).setValue(usuarioFirebase);

                //si esto jala con esto podríamos meter estos datos a la base de datos.
                text_Nombre_Usuario.setText(personName);


                notificaciones = new ArrayList<String>();
                databaseAlmacen = FirebaseDatabase.getInstance().getReference("Almacen").child(personId);
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                // Obtenemos la fecha de hoy
                Date hoy = calendar.getTime();

                databaseAlmacen.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot almacenSnapshot : dataSnapshot.getChildren()){

                            String productoNombre = almacenSnapshot.child("nombreAlimento").getValue().toString();
                            String productoCad = almacenSnapshot.child("caducidad").getValue().toString();



                            try {
                                listaNotificaciones.setAdapter(null);
                                Date fechaCaducidad = sdf.parse(productoCad);
                                int dias = (int)((fechaCaducidad.getTime()-hoy.getTime())/86400000)+1;
                                // Comparamos si falta una semana o menos para que llegue la fecha de caducidad, y si es así creamos una notificación
                                if(dias <= 6){
                                    String contenido = "Faltan " + dias + " dias para que " + productoNombre + " caduque. ¡Úsalo cuanto antes!";
                                    notificaciones.add(contenido);
                                }
                            } catch (Exception e) {
                                System.out.println("Error en fecha");
                            }

                            //   Toast.makeText(Filtro_Productos.this, productoNombre , Toast.LENGTH_LONG).show();

                        }

                        ArrayAdapter notsAdapter = new ArrayAdapter(Perfil_Usuario.this,android.R.layout.simple_list_item_1,notificaciones);
                        listaNotificaciones.setAdapter(notsAdapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                //si esto jala con esto podríamos meter estos datos a la base de datos.
//            text_producto.setText(personName);
            }

            btn_TomaFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), tomaFotografia.class));

                }
            });

            btn_VerAlmacen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), Filtro_Productos.class));

                }
            });

        }
    }

    // Método para generar las notificaciones del día
//    public void generarNotificaciones(){
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
//        // Obtenemos la fecha de hoy
//        Date hoy = calendar.getTime();
//        for(Producto producto : this.productosAlmacenados){
//            try {
//                Date fechaCaducidad = sdf.parse(producto.getCaducidad());
//                int dias = fechaCaducidad.compareTo(hoy);
//                // Comparamos si falta una semana o menos para que llegue la fecha de caducidad, y si es así creamos una notificación
//                if(dias <= 7){
//                    String contenido = "Faltan " + dias + " para que " + producto.getNombre() + " caduque. ¡Úsalo cuanto antes!";
//                    String estado = "Pendiente";
//                    Notificacion notificacion = new Notificacion(contenido);
//                    this.notificaciones.add(notificacion);
//                }
//            } catch (Exception e) {
//                System.out.println("Error en fecha");
//            }
//        }
//    }

    // Método para enviar una notificación general que pida al usuario revisar su almacén
//    private void enviarNotificacion(){
//        Almacen almacen = new Almacen();
//        int tam = almacen.getNotificaciones().size();
//        if (tam > 0){
//            NotificationCompat.Builder mBuilder;
//            NotificationManager mNotifyMgr =(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
//
//            int icono = R.mipmap.ic_launcher;
//            Intent i=new Intent(Perfil_Usuario.this, Perfil_Usuario.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(Perfil_Usuario.this, 0, i, 0);
//
//            mBuilder =new NotificationCompat.Builder(getApplicationContext())
//                    .setContentIntent(pendingIntent)
//                    .setSmallIcon(icono)
//                    .setContentTitle("¡Tienes artículos por caducar!")
//                    .setContentText("Hemos encontrado " + tam + " productos cercanos a su vencimiento")
//                    .setVibrate(new long[] {100, 250, 100, 500})
//                    .setAutoCancel(true);
//
//            mNotifyMgr.notify(1, mBuilder.build());
//        }
//    }

//    private void signOut() {
//        FirebaseAuth.getInstance().signOut();
//        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                System.exit(0);
//            }
//        });
//    }
}

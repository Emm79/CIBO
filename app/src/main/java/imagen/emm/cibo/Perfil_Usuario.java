package imagen.emm.cibo;

//import android.support.v7.app.AppCompatActivity;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Perfil_Usuario extends Activity {

    Button btn_TomaFoto;
    Button btn_VerAlmacen;
    private TextView text_Nombre_Usuario;
    Button btn_logout;
    DatabaseReference databaseUsuarios;
    DatabaseReference databaseAlmacen;
    String productoNombre;
    String productoCad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        databaseUsuarios = FirebaseDatabase.getInstance().getReference("Usuarios");

        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_perfil__usuario);

            btn_TomaFoto = (Button) findViewById(R.id.btn_Toma_Foto);
            btn_VerAlmacen = (Button) findViewById(R.id.btn_Visualizar_Almacen);
            text_Nombre_Usuario = ( TextView ) findViewById(R.id.text_Nombre_Usuario);

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



                databaseAlmacen = FirebaseDatabase.getInstance().getReference("Almacen").child(personId);
                databaseAlmacen.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot almacenSnapshot : dataSnapshot.getChildren()){
                            String productoNombre = almacenSnapshot.getValue().toString();
                            String productoCad = almacenSnapshot.getValue().toString();
                            Toast.makeText(Perfil_Usuario.this, productoNombre , Toast.LENGTH_SHORT).show();

                        }
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

    // Método para enviar una notificación general que pida al usuario revisar su almacén
    private void enviarNotificacion(){
        Almacen almacen = new Almacen();
        int tam = almacen.getNotificaciones().size();
        if (tam > 0){
            NotificationCompat.Builder mBuilder;
            NotificationManager mNotifyMgr =(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

            int icono = R.mipmap.ic_launcher;
            Intent i=new Intent(Perfil_Usuario.this, Perfil_Usuario.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(Perfil_Usuario.this, 0, i, 0);

            mBuilder =new NotificationCompat.Builder(getApplicationContext())
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(icono)
                    .setContentTitle("¡Tienes artículos por caducar!")
                    .setContentText("Hemos encontrado " + tam + " productos cercanos a su vencimiento")
                    .setVibrate(new long[] {100, 250, 100, 500})
                    .setAutoCancel(true);

            mNotifyMgr.notify(1, mBuilder.build());
        }
    }

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

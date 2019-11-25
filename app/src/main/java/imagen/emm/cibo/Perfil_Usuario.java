package imagen.emm.cibo;

//import android.support.v7.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Perfil_Usuario extends Activity {

    Button btn_TomaFoto;
    Button btn_VerAlmacen;
    private TextView text_Nombre_Usuario;
    Button btn_logout;
    GoogleSignInClient mGoogleSignInClient;
    DatabaseReference databaseUsuarios;

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

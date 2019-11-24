package imagen.emm.cibo;

//import android.support.v7.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Perfil_Usuario extends Activity {

    private TextView text_Nombre_Usuario;

    Button btn_TomaFoto;
    Button btn_VerAlmacen;
    Button btn_ModifCuenta;
    Button btn_logout;
    GoogleSignInClient mGoogleSignInClient;

    @Override

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil__usuario);

        text_Nombre_Usuario = ( TextView ) findViewById(R.id.text_Nombre_Usuario);
        btn_TomaFoto = (Button) findViewById(R.id.btn_Toma_Foto);
        btn_VerAlmacen = (Button) findViewById(R.id.btn_Visualizar_Almacen);
        btn_ModifCuenta = (Button) findViewById(R.id.btn_Modificar_Cuenta);
        btn_logout = (Button) findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    // ...
                    case R.id.btn_logout:
                        signOut();
                        break;
                    // ...
                }
            }
        });


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
           // String personEmail = acct.getEmail();
           // String personId = acct.getId(); si esto jala con esto podríamos meter estos datos a la base de datos.
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



//        btn_ModifCuenta.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), Modificar_Cuenta.class));
//
//            }
//        });
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       finish();// ...
                    }
                });
    }
}

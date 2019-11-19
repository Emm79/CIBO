package imagen.emm.cibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;

public class Registrarse extends Activity {

    EditText correo;
    EditText pwd;
    EditText nombre;
    Button btn_aceptar;
    Button btn_cancelar;
    String correo_T;
    String pwd_T;
    String nombre_T;
    FirebaseAuth mAuth;
    ProgressBar proceso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        correo = (EditText) findViewById(R.id.edit_CorreoN);
        nombre = (EditText) findViewById(R.id.edit_NombreN);
        pwd = (EditText) findViewById(R.id.edit_Contrasena);
        btn_aceptar = (Button) findViewById(R.id.btn_Aceptar);
        btn_cancelar = (Button) findViewById(R.id.btn_Cancelar);
        proceso = (ProgressBar) findViewById(R.id.proceso);

        correo_T = correo.getText().toString();
        pwd_T = pwd.getText().toString();
        nombre_T = nombre.getText().toString();

        mAuth = FirebaseAuth.getInstance();
        proceso.setVisibility(View.GONE);


       // FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                proceso.setVisibility(View.VISIBLE);

                if(TextUtils.isEmpty(correo_T)||TextUtils.isEmpty(nombre_T)){
                    Toast.makeText(Registrarse.this,"Ingresa un correo", Toast.LENGTH_LONG).show();
                }

                if(TextUtils.isEmpty(pwd_T)){
                    Toast.makeText(Registrarse.this,"Ingresa una contraseña", Toast.LENGTH_LONG).show();
                }

                if(pwd_T.length()<4){
                    Toast.makeText(Registrarse.this, "Ingresa una contraseña con mas de 4 digitos", Toast.LENGTH_LONG).show();
                }

                else {
                    mAuth.createUserWithEmailAndPassword(correo_T, correo_T)
                            .addOnCompleteListener(Registrarse.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    proceso.setVisibility(View.GONE);

                                    if (task.isSuccessful()) {
                                        Toast.makeText(Registrarse.this, "Registro exitoso", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    } else {
                                        Toast.makeText(Registrarse.this, "Error de registro", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }

            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });
    }

}

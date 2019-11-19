package imagen.emm.cibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//import android.support.v7.app.AppCompatActivity;

public class MainActivity extends Activity {

    EditText correo;
    EditText pwd;
    Button btn_ingresar;
    Button btn_registro;
    private FirebaseAuth mAuth;
    String correo_T;
    String pwd_T;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correo = (EditText) findViewById(R.id.edit_Correo);
        pwd = (EditText) findViewById(R.id.edit_Contrasena);
        btn_ingresar = (Button) findViewById(R.id.btn_Iniciar);
        btn_registro = (Button) findViewById(R.id.btn_registrarse);

        correo_T = correo.getText().toString();
        pwd_T = pwd.getText().toString();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();



        //        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //        DatabaseReference ref = database.getReference();

//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//        final DatabaseReference mensajeRef = ref.child("mensaje");
//        mensajeRef.setValue("Hola mundo");


        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),Registrarse.class));

            }
        });


        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(correo_T)){
                    Toast.makeText(MainActivity.this,"Ingresa un correo", Toast.LENGTH_LONG).show();
                }

                if(TextUtils.isEmpty(pwd_T)){
                    Toast.makeText(MainActivity.this,"Ingresa una contraseña", Toast.LENGTH_LONG).show();
                }

                if(pwd_T.length()<4){
                    Toast.makeText(MainActivity.this, "Ingresa una contraseña con mas de 4 digitos", Toast.LENGTH_LONG).show();
                }

                else {

                    mAuth.signInWithEmailAndPassword(correo_T, pwd_T)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        startActivity(new Intent(getApplicationContext(), Perfil_Usuario.class));
                                    } else {

                                        Toast.makeText(MainActivity.this, "Correo o contraseña incorrectos", Toast.LENGTH_LONG).show();

                                    }

                                }
                            });
                }

            }
        });


    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//       // updateUI(currentUser);
//
//    }





}



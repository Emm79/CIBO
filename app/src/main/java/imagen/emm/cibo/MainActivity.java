package imagen.emm.cibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

//import android.support.v7.app.AppCompatActivity;

public class MainActivity extends Activity {


    Button btn_ingresar;
    Button btn_entrar;
    static final int GOOGLE_SIGN = 123;
    FirebaseAuth mAuth;
    ProgressBar proceso;
    GoogleSignInClient mGoogleSignInClient;
    TextView text;
    ImageView image;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_ingresar = (Button) findViewById(R.id.btn_Iniciar);
        proceso = (ProgressBar) findViewById(R.id.proceso_main);
        mAuth = FirebaseAuth.getInstance();
        text = (TextView) findViewById(R.id.text_CIBO);
        image = (ImageView) findViewById(R.id.image_Logo);
        btn_entrar = (Button) findViewById(R.id.btn_Entrar);

        // Configure Google Sign In
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        //        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //        DatabaseReference ref = database.getReference();
        //        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        //        final DatabaseReference mensajeRef = ref.child("mensaje");
        //        mensajeRef.setValue("Hola mundo");

        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SignInGoogle();

            }
        });

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), Perfil_Usuario.class));

            }
        });


        if(mAuth.getCurrentUser() != null){
            FirebaseUser user = mAuth.getCurrentUser();
            UpdateUI(user);
        }

    }

    void SignInGoogle(){
        proceso.setVisibility(View.VISIBLE);
        Intent signInternet = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInternet, GOOGLE_SIGN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== GOOGLE_SIGN){

            Task<GoogleSignInAccount> task = GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if(account != null){

                    firebaseAuthWithGoogle(account);
                }

            } catch (ApiException e){
                Toast.makeText(MainActivity.this, "Prueba", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }


    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        Log.d("TAG", "firebaseAuthWithGoogle: " + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                proceso.setVisibility(View.INVISIBLE);

                                Log.d("TAG", "signin success");

                                FirebaseUser user = mAuth.getCurrentUser();
                                UpdateUI(user);
                            }
                            else {
                                proceso.setVisibility(View.INVISIBLE);

                                Log.d("TAG", "signin failure", task.getException());

                                Toast.makeText(MainActivity.this, "Error de registro", Toast.LENGTH_SHORT).show();
                                UpdateUI(null);


                            }
                    }
                });
    }

    private void UpdateUI(FirebaseUser user){
        if(user != null){

            String nombre_us = user.getDisplayName();
            String correo_us = user.getEmail();
            //String foto_us = String.valueOf(user.getPhotoUrl());

            text.append("Informacion: \n");
            text.append(nombre_us + "\n");
            text.append(correo_us);

            //Picasso.get().load(foto_us).into(image);

            btn_ingresar.setVisibility(View.INVISIBLE);
            btn_entrar.setVisibility(View.VISIBLE);

        } else {

            text.setText(getString(R.string.text_cibo));
           // Picasso.get().load(R.drawable.comida).into(image);
            btn_ingresar.setVisibility(View.VISIBLE);
            btn_entrar.setVisibility(View.INVISIBLE);
        }
    }

    void LogOut(){
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                UpdateUI(null);
            }
        });
    }

}



package imagen.emm.cibo;

import android.app.Activity;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends Activity {

    EditText correo;
    EditText pwd;
    Button btn_ingresar;
    Button btn_registro;
    TextView textPrueba;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correo = (EditText) findViewById(R.id.edit_Correo);
        pwd = (EditText) findViewById(R.id.edit_Contrasena);
        btn_ingresar = (Button) findViewById(R.id.btn_Iniciar);
        btn_registro = (Button) findViewById(R.id.btn_registrarse);
        textPrueba = (TextView) findViewById(R.id.text_CIBO);

        //        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //        DatabaseReference ref = database.getReference();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference mensajeRef = ref.child("mensaje");
        mensajeRef.setValue("Hola mundo");

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Registro = new Intent(MainActivity.this, Registrarse.class);

                String correo_t = correo.getText().toString();
                mensajeRef.setValue(correo_t); //Modificando el mensaje de la BD

                correo.setText("");


                //Actualizacion en tiempo real de la base:
                mensajeRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    //Metodo interno que se ejecuta cuando el valor cambia
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);

                        textPrueba.setText(value); //PRUEBA DE ACTUALIZACION DE TEXTO
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                            databaseError.toException();
                    }
                });
            }
        });

        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Ingreso = new Intent(MainActivity.this, Perfil_Usuario.class);

            }
        });



    }






}



package imagen.emm.cibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Registrarse extends Activity {

    EditText correo;
    EditText pwd;
    EditText nombre;
    Button btn_aceptar;
    Button btn_cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        correo = (EditText) findViewById(R.id.edit_CorreoN);
        nombre = (EditText) findViewById(R.id.edit_NombreN);
        pwd = (EditText) findViewById(R.id.edit_Contrasena);
        btn_aceptar = (Button) findViewById(R.id.btn_Aceptar);
        btn_cancelar = (Button) findViewById(R.id.btn_Cancelar);

       // Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

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
                Intent Aceptar_cuenta = new Intent(Registrarse.this, MainActivity.class);

            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Cancelar_creacion = new Intent(Registrarse.this, Perfil_Usuario.class);

            }
        });
    }

}

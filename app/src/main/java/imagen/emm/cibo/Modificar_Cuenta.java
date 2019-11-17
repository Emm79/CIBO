package imagen.emm.cibo;

import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Modificar_Cuenta extends Activity {

    Button btn_Aceptar;
    Button btn_Cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar__cuenta);

        btn_Aceptar = (Button) findViewById(R.id.btn_Aceptar);
        btn_Cancelar = (Button) findViewById(R.id.btn_Cancelar);

        btn_Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Aceptar_Cambios_C = new Intent(Modificar_Cuenta.this, MainActivity.class);

            }
        });

        btn_Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Eliminar_C = new Intent(Modificar_Cuenta.this, MainActivity.class);

            }
        });
    }
}

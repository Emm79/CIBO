package imagen.emm.cibo;

//import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Perfil_Usuario extends Activity {

    Button btn_TomaFoto;
    Button btn_VerAlmacen;
    Button btn_ModifCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil__usuario);

        btn_TomaFoto = (Button) findViewById(R.id.btn_Toma_Foto);
        btn_VerAlmacen = (Button) findViewById(R.id.btn_Visualizar_Almacen);
        btn_ModifCuenta = (Button) findViewById(R.id.btn_Modificar_Cuenta);

        btn_TomaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Toma_F = new Intent(Perfil_Usuario.this, Tomar_Fotografia.class);

            }
        });

        btn_VerAlmacen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Ve_Almacen = new Intent(Perfil_Usuario.this, Almacen_Actual.class);

            }
        });

        btn_ModifCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Modifica_C = new Intent(Perfil_Usuario.this, Modificar_Cuenta.class);

            }
        });
    }
}

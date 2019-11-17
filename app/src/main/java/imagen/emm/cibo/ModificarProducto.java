package imagen.emm.cibo;

//import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ModificarProducto extends Activity {

    Button btn_Cancelar;
    Button btn_Aceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_producto);

        btn_Cancelar = (Button) findViewById(R.id.btn_Cancelar);
        btn_Aceptar = (Button) findViewById(R.id.btn_Aceptar);

        btn_Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Cancelar_Cambios_P = new Intent(ModificarProducto.this, Filtro_Productos.class);

            }
        });

        btn_Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Aceptar_Cambios_P = new Intent(ModificarProducto.this, Filtro_Productos.class);

            }
        });
    }
}

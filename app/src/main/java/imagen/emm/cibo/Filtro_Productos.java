package imagen.emm.cibo;

//import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Filtro_Productos extends Activity {

    Button btn_ModifProd;
    Button btn_Cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro__productos);

        btn_ModifProd = (Button) findViewById(R.id.btn_ModificarP);
        btn_Cancelar = (Button) findViewById(R.id.btn_Cancelar);


        btn_ModifProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Filtro_Productos.this, ModificarProducto.class));

            }
        });

        btn_Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }
}

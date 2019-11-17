package imagen.emm.cibo;

//import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Almacen_Actual extends Activity {

    Button btn_Cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almacen__actual);

        btn_Cancelar = (Button) findViewById(R.id.btn_Cancelar);

        btn_Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Regresar_Perfil = new Intent(Almacen_Actual.this, Perfil_Usuario.class);

            }
        });
    }
}

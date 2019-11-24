package imagen.emm.cibo;

//import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.core.app.NotificationCompat;

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

    // Método para enviar una notificación general que pida al usuario revisar su almacén
    private void enviarNotificacion(){
        Almacen almacen = new Almacen();
        int tam = almacen.getNotificaciones().size();
        if (tam > 0){
            NotificationCompat.Builder mBuilder;
            NotificationManager mNotifyMgr =(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

            int icono = R.mipmap.ic_launcher;
            Intent i=new Intent(Almacen_Actual.this, Almacen_Actual.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(Almacen_Actual.this, 0, i, 0);

            mBuilder =new NotificationCompat.Builder(getApplicationContext())
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(icono)
                    .setContentTitle("¡Tienes artículos por caducar!")
                    .setContentText("Hemos encontrado " + tam + " productos cercanos a su vencimiento")
                    .setVibrate(new long[] {100, 250, 100, 500})
                    .setAutoCancel(true);

            mNotifyMgr.notify(1, mBuilder.build());
        }
    }


}

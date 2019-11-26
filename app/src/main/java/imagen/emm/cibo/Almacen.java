package imagen.emm.cibo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Almacen {
    private ArrayList<Producto> productosAlmacenados = new ArrayList<>();
    private ArrayList<Notificacion> notificaciones = new ArrayList<>();

    public Almacen(){

    }

    public void setProductosAlmacenados(ArrayList<Producto> productos){
        this.productosAlmacenados = productos;
    }

    public void setNotificaciones(ArrayList<Notificacion> notificaciones){
        this.notificaciones = notificaciones;
    }

    public ArrayList<Producto> getProductosAlmacenados(){
        return this.productosAlmacenados;
    }

    public ArrayList<Notificacion> getNotificaciones(){
        return this.notificaciones;
    }

    // Método para convertir el JSON que nos caiga de Firebase en información operable para nosotros
    // Debe mandarse como json el nodo del almacen del usuario en firebase
    public void convertirJSONaArrayProductos(String json){
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Producto>>(){}.getType();
        this.productosAlmacenados = gson.fromJson(json, listType);
    }

    // Método para generar las notificaciones del día
    public void generarNotificaciones(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        // Obtenemos la fecha de hoy
        Date hoy = calendar.getTime();
        for(Producto producto : this.productosAlmacenados){
            try {
                Date fechaCaducidad = sdf.parse(producto.getCaducidad());
                int dias = fechaCaducidad.compareTo(hoy);
                // Comparamos si falta una semana o menos para que llegue la fecha de caducidad, y si es así creamos una notificación
                if(dias <= 7){
                    String contenido = "Faltan " + dias + " para que " + producto.getNombre() + " caduque. ¡Úsalo cuanto antes!";
                    String estado = "Pendiente";
                    Notificacion notificacion = new Notificacion(contenido);
                    this.notificaciones.add(notificacion);
                }
            } catch (Exception e) {
                System.out.println("Error en fecha");
            }
        }
    }
}

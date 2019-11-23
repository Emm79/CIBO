package imagen.emm.cibo;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Date;
import java.text.SimpleDateFormat;

public class GestorProductos {
    private Dictionary<String,Integer> productosConocidos = new Hashtable<String,Integer>();

    public GestorProductos(){}

    public void generarListaProductos(){
        this.productosConocidos.put("Leche",15);
        this.productosConocidos.put("arroz",180);
        this.productosConocidos.put("atun",365);
        this.productosConocidos.put("Manzana",7);
        this.productosConocidos.put("Zanahorias",8);
        this.productosConocidos.put("Uvas",6);
        this.productosConocidos.put("Tomate",7);
        this.productosConocidos.put("Platano",5);
        this.productosConocidos.put("cafe",200);
        this.productosConocidos.put("sal",200);
        this.productosConocidos.put("Chicharos",7);
        this.productosConocidos.put("chile lata",180);
        this.productosConocidos.put("Pollo",15);
        this.productosConocidos.put("Carne",15);
        this.productosConocidos.put("Naranja",7);
        this.productosConocidos.put("Limón",9);
        this.productosConocidos.put("azucar",90);
        this.productosConocidos.put("Papa",9);
        this.productosConocidos.put("pasta",90);
        this.productosConocidos.put("frijol",60);
    }

    public ArrayList<Producto> calcularCaducidades(ArrayList<String> productos){
        Calendar calendario = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        String fechaCaducidad;
        ArrayList<Producto> prodCompletos = new ArrayList<>();
        Producto p;
        for (String producto : productos){
            int aumento = this.productosConocidos.get(producto);
            calendario.add(Calendar.DAY_OF_YEAR,aumento);
            Date hoy = calendario.getTime();
            fechaCaducidad = formatter.format(hoy);
            p = new Producto(producto,fechaCaducidad,1);
            prodCompletos.add(p);
        }
        return prodCompletos;
    }

    public String convertirAJSON(Producto producto){
        Gson gson = new Gson();
        String JSON = gson.toJson(producto);
        return JSON;
    }
}

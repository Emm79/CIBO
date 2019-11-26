package imagen.emm.cibo;

import android.app.Activity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;

public class GestorProductos extends Activity {
    String personId;
    String idP;
    DatabaseReference databaseAlmacen;
    private Dictionary<String,Integer> productosConocidos = new Hashtable<String,Integer>();

    public GestorProductos(){}

    public void generarListaProductos(){
        this.productosConocidos.put("Leche",15);
        this.productosConocidos.put("arroz",180);
        this.productosConocidos.put("atún",365);
        this.productosConocidos.put("Manzana",7);
        this.productosConocidos.put("Zanahorias",8);
        this.productosConocidos.put("Uvas",6);
        this.productosConocidos.put("Tomate",7);
        this.productosConocidos.put("Platano",5);
        this.productosConocidos.put("café",200);
        this.productosConocidos.put("sal",200);
        this.productosConocidos.put("Chicharos",7);
        this.productosConocidos.put("chile lata",180);
        this.productosConocidos.put("Pollo",15);
        this.productosConocidos.put("Carne",15);
        this.productosConocidos.put("Naranja",7);
        this.productosConocidos.put("Limon",9);
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

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            //comentario
            personId = acct.getId();

                databaseAlmacen = FirebaseDatabase.getInstance().getReference("Almacen").child(personId);
                idP = databaseAlmacen.push().getKey();
        }



        for (String producto : productos){
            int aumento = this.productosConocidos.get(producto);
            calendario.add(Calendar.DAY_OF_YEAR,aumento);
            Date hoy = calendario.getTime();
            fechaCaducidad = formatter.format(hoy);
            p = new Producto(idP,producto,fechaCaducidad);
            prodCompletos.add(p);
        }
        return prodCompletos;
    }

    public String convertirAJSON(Producto producto){
        Gson gson = new Gson();
        String JSON = gson.toJson(producto);
        return JSON;
    }


    // Aquí va el método de subida de información a Firebase
}

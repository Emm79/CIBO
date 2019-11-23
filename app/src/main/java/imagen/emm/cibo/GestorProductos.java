package imagen.emm.cibo;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

public class GestorProductos {
    private Dictionary productos = new Hashtable();

    public GestorProductos(){}

    public void generarListaProductos(){
        this.productos.put(R.string.leche,15);
        this.productos.put(R.string.arroz,180);
        this.productos.put(R.string.atun,365);
        this.productos.put(R.string.manzana,7);
        this.productos.put(R.string.zanahorias,8);
        this.productos.put(R.string.uvas,6);
        this.productos.put(R.string.tomate,7);
        this.productos.put(R.string.platano,5);
        this.productos.put(R.string.cafe,200);
        this.productos.put(R.string.sal,200);
        this.productos.put(R.string.chicharos,7);
        this.productos.put(R.string.chile,180);
        this.productos.put(R.string.pollo,15);
        this.productos.put(R.string.carne,15);
        this.productos.put(R.string.naranja,7);
        this.productos.put(R.string.limon,9);
        this.productos.put(R.string.azucar,90);
        this.productos.put(R.string.papa,9);
        this.productos.put(R.string.pasta,90);
        this.productos.put(R.string.frijol,60);
    }
}

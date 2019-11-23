package imagen.emm.cibo;

import com.google.gson.Gson;

public class Producto {
    private String caducidad;
    private String nombre;
    private int cantidad;

    public Producto(String nombre, String caducidad, int cantidad){
        this.nombre = nombre;
        this.caducidad = caducidad;
        this.cantidad = cantidad;
    }

    public void setCaducidad(String caducidad){
        this.caducidad = caducidad;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }

    public String getNombre(){
        return this.nombre;
    }

    public String getCaducidad (){
        return this.caducidad;
    }

    public int getCantidad(){
        return this.cantidad;
    }
}

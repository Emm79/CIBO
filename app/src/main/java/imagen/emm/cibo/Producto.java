package imagen.emm.cibo;

public class Producto {
    private String caducidad;
    private String nombre;

    public Producto(String nombre, String caducidad){
        this.nombre = nombre;
        this.caducidad = caducidad;
    }


    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getCaducidad (){
        return this.caducidad;
    }

    public void setCaducidad(String caducidad){
        this.caducidad = caducidad;
    }


}
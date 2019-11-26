package imagen.emm.cibo;

public class Producto {
    private String id;
    private String caducidad;
    private String nombre;

    public Producto(String id, String nombre, String caducidad){
        this.id = id;
        this.nombre = nombre;
        this.caducidad = caducidad;
    }

    public String getID(){
        return this.id;
    }

    public void setID(String id){
        this.id = id;
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
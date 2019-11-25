package imagen.emm.cibo;

public class UsuarioFirebase {
    String id;
    String nombre;
    String correo;

    public UsuarioFirebase(){

    }

    public UsuarioFirebase(String id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }

    public String getCorreo() {
        return correo;
    }
}

package imagen.emm.cibo;

import java.util.Date;

public class Notificacion {
    private String contenido;
    private Date fechaCaducidad;
    private String estado;

    public Notificacion(String contenido, Date fechaCaducidad, String estado){
        this.contenido = contenido;
        this.fechaCaducidad = fechaCaducidad;
        this.estado = estado;
    }

    public String getContenido(){
        return this.contenido;
    }

    public Date getFechaCaducidad(){
        return this.fechaCaducidad;
    }

    public String getEstado(){
        return this.estado;
    }

    public void setContenido(String contenido){
        this.contenido = contenido;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }

    public void setFechaCaducidad(Date fechaCaducidad){
        this.fechaCaducidad = fechaCaducidad;
    }
}

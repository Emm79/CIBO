package imagen.emm.cibo;

public class AlmacenFirebase {
    private String alimentoId;
    private String nombreAlimento;
    private String Caducidad;

    public AlmacenFirebase(){

    }

    public AlmacenFirebase(String alimentoId, String nombreAlimento, String caducidad) {
        this.alimentoId = alimentoId;
        this.nombreAlimento = nombreAlimento;
        Caducidad = caducidad;
    }

    public String getAlimentoId() {
        return alimentoId;
    }

    public String getNombreAlimento() {
        return nombreAlimento;
    }

    public String getCaducidad() {
        return Caducidad;
    }
}

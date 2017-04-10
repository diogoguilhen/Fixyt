package fixyt.fixyt;



public class PartnersProximos extends Auxilio{
    //Variáveis do objeto
    private String codigoPartner;
    private String statusPartner;
    private String latitudePartner;
    private String longitudePartner;
    private int distanciaAteMotorista;
    private int tempoAteMotorista;

    //Construtor em Lista



    //Getters e Setters

    public String getCodigoPartner() {
        return codigoPartner;
    }

    public void setCodigoPartner(String codigoPartner) {
        this.codigoPartner = codigoPartner;
    }

    public String getStatusPartner() {
        return statusPartner;
    }

    public void setStatusPartner(String statusPartner) {
        this.statusPartner = statusPartner;
    }

    public String getLatitudePartner() {
        return latitudePartner;
    }

    public void setLatitudePartner(String latitudePartner) {
        this.latitudePartner = latitudePartner;
    }

    public String getLongitudePartner() {
        return longitudePartner;
    }

    public void setLongitudePartner(String longitudePartner) {
        this.longitudePartner = longitudePartner;
    }

    public int getDistanciaAteMotorista() {
        return distanciaAteMotorista;
    }

    public void setDistanciaAteMotorista(int distanciaAteMotorista) {
        this.distanciaAteMotorista = distanciaAteMotorista;
    }

    public int getTempoAteMotorista() {
        return tempoAteMotorista;
    }

    public void setTempoAteMotorista(int tempoAteMotorista) {
        this.tempoAteMotorista = tempoAteMotorista;
    }
}

package fixyt.fixyt;


import android.icu.text.MessagePattern;

public class PartnersProximos extends Auxilio{
    //Variáveis do objeto
    private String codigoPartner;
    private String statusPartner;
    private String latitudePartner;
    private String longitudePartner;
    private String servicoPartner;
    private int distanciaAteMotorista;
    private int tempoAteMotorista;

    //Construtor em Lista

    public PartnersProximos(){

    }

    public PartnersProximos(String codigoPartner, String statusPartner, String latitudePartner, String longitudePartner, int tempoAteMotorista) {
        this.codigoPartner = codigoPartner;
        this.statusPartner = statusPartner;
        this.latitudePartner = latitudePartner;
        this.longitudePartner = longitudePartner;
        this.tempoAteMotorista = tempoAteMotorista;
    }



    //Getters e Setters


    public String getServicoPartner() {
        return servicoPartner;
    }

    public void setServicoPartner(String servicoPartner) {
        this.servicoPartner = servicoPartner;
    }

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

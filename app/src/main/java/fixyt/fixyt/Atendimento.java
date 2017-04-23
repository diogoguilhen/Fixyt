package fixyt.fixyt;


public class Atendimento {
    //Variaveis do Objeto
    public String horarioAtendimento;
    public String statusAceitacao;
    public String latitudeMotorista;
    public String longitudeMotorista;
    public String tempoEstimado;
    public String pontoDeReferencia;


    // construtor

    public Atendimento() {
        super();
    }

    public Atendimento(String horarioAtendimento, String statusAceitacao, String latitudeMotorista, String longitudeMotorista, String tempoEstimado, String pontoDeReferencia) {
        this.horarioAtendimento = horarioAtendimento;
        this.statusAceitacao = statusAceitacao;
        this.latitudeMotorista = latitudeMotorista;
        this.longitudeMotorista = longitudeMotorista;
        this.tempoEstimado = tempoEstimado;
        this.pontoDeReferencia = pontoDeReferencia;
    }


    //get set

    public String getHorarioAtendimento() {
        return horarioAtendimento;
    }

    public void setHorarioAtendimento(String horarioAtendimento) {
        this.horarioAtendimento = horarioAtendimento;
    }

    public String getStatusAceitacao() {
        return statusAceitacao;
    }

    public void setStatusAceitacao(String statusAceitacao) {
        this.statusAceitacao = statusAceitacao;
    }
}


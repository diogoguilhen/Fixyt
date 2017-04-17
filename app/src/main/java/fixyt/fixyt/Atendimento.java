package fixyt.fixyt;


public class Atendimento {
    //Variaveis do Objeto
    public String horarioAtendimento;
    public String statusAceitacao;

    // construtor

    public Atendimento() {
        super();
    }

    public Atendimento(String horarioAtendimento, String statusAceitacao) {

        this.horarioAtendimento = horarioAtendimento;
        this.statusAceitacao = statusAceitacao;
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


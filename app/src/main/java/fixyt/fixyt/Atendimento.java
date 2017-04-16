package fixyt.fixyt;


public class Atendimento {
    //Variaveis do Objeto
    public String horarioAtendimento;

    // construtor

    public Atendimento() {
        super();
    }

    public Atendimento(String horarioAtendimento) {

        this.horarioAtendimento = horarioAtendimento;
    }


    //get set

    public String getHorarioAtendimento() {
        return horarioAtendimento;
    }

    public void setHorarioAtendimento(String horarioAtendimento) {
        this.horarioAtendimento = horarioAtendimento;
    }
}


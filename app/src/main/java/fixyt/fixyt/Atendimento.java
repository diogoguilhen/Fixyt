package fixyt.fixyt;


public class Atendimento extends Auxilio{
    //Variaveis do Objeto
    private String horarioAtendimento;

    // construtor

    public Atendimento() {

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


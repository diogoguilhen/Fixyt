package fixyt.fixyt;

public class CadastroMotorista {

    private String nome;
    private String sobrenome;
    private String pais;
    private String telefone;

    public CadastroMotorista(String nome, String sobrenome, String pais, String telefone) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.pais = pais;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}

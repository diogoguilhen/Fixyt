package fixyt.fixyt;

import java.util.Date;

public class Cadastro {

    // Tipo String
    private String nome;
    private String sobrenome;
    private String telefone;
    private String email;
    private String senha;
    private String cpf;
    private String rg;
    private String sexo;
    private String enderecoCompleto;
    private String cep;
    private String bairro;
    private String uf;
    private String cidade;

    // Precisa criar um tipo de cadastro de Veiculo.
    private String veiculoTipo;
    private String veiculoMarca;
    private String veiculoAnoFabricacao;
    private String veiculoAnoModelo;
    private String veiculoPlaca;
    private String veiculoRenavam;
    private String veiculoKilometragem;
    private String veiculoCor;

    // Tipo Date
    private Date dataNascimento;

    public Cadastro(String nome, String sobrenome, String pais, String telefone) {
        this.nome = nome;
        this.sobrenome = sobrenome;
       // this.pais = pais;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEnderecoCompleto() {
        return enderecoCompleto;
    }

    public void setEnderecoCompleto(String enderecoCompleto) {
        this.enderecoCompleto = enderecoCompleto;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getVeiculoTipo() {
        return veiculoTipo;
    }

    public void setVeiculoTipo(String veiculoTipo) {
        this.veiculoTipo = veiculoTipo;
    }

    public String getVeiculoMarca() {
        return veiculoMarca;
    }

    public void setVeiculoMarca(String veiculoMarca) {
        this.veiculoMarca = veiculoMarca;
    }

    public String getVeiculoAnoFabricacao() {
        return veiculoAnoFabricacao;
    }

    public void setVeiculoAnoFabricacao(String veiculoAnoFabricacao) {
        this.veiculoAnoFabricacao = veiculoAnoFabricacao;
    }

    public String getVeiculoAnoModelo() {
        return veiculoAnoModelo;
    }

    public void setVeiculoAnoModelo(String veiculoAnoModelo) {
        this.veiculoAnoModelo = veiculoAnoModelo;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }


}

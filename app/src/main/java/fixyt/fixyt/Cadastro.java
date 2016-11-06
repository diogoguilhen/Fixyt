package fixyt.fixyt;

import android.widget.EditText;

import java.util.Date;

public class Cadastro {

    // Tipo String
    private EditText nome;
    private EditText sobrenome;
    private EditText telefone;
    private EditText email;
    private EditText senha;
    private EditText cpf;
    private EditText rg;
    private EditText sexo;
    private EditText enderecoCompleto;
    private EditText cep;
    private EditText bairro;
    private EditText uf;
    private EditText cidade;
    private EditText pais;

    // Precisa criar um tipo de cadastro de Veiculo.
    private EditText veiculoTipo;
    private EditText veiculoMarca;
    private EditText veiculoAnoFabricacao;
    private EditText veiculoAnoModelo;
    private EditText veiculoPlaca;
    private EditText veiculoRenavam;
    private EditText veiculoKilometragem;
    private EditText veiculoCor;
    private EditText dataNascimento;

    public Cadastro(EditText nome, EditText sobrenome, EditText pais, EditText telefone) {
        this.nome = nome;
        this.sobrenome = sobrenome;
       // this.pais = pais;
        this.telefone = telefone;
    }

    public Cadastro () {

    }

    public EditText getNome() {
        return nome;
    }

    public void setNome(EditText nome) {
        this.nome = nome;
    }

    public EditText getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(EditText sobrenome) {
        this.sobrenome = sobrenome;
    }

    public EditText getTelefone() {
        return telefone;
    }

    public void setTelefone(EditText telefone) {
        this.telefone = telefone;
    }

    public EditText getEmail() {
        return email;
    }

    public void setEmail(EditText email) {
        this.email = email;
    }

    public EditText getSenha() {
        return senha;
    }

    public void setSenha(EditText senha) {
        this.senha = senha;
    }

    public EditText getCpf() {
        return cpf;
    }

    public void setCpf(EditText cpf) {
        this.cpf = cpf;
    }

    public EditText getRg() {
        return rg;
    }

    public void setRg(EditText rg) {
        this.rg = rg;
    }

    public EditText getSexo() {
        return sexo;
    }

    public void setSexo(EditText sexo) {
        this.sexo = sexo;
    }

    public EditText getEnderecoCompleto() {
        return enderecoCompleto;
    }

    public void setEnderecoCompleto(EditText enderecoCompleto) {
        this.enderecoCompleto = enderecoCompleto;
    }

    public EditText getCep() {
        return cep;
    }

    public void setCep(EditText cep) {
        this.cep = cep;
    }

    public EditText getBairro() {
        return bairro;
    }

    public void setBairro(EditText bairro) {
        this.bairro = bairro;
    }

    public EditText getUf() {
        return uf;
    }

    public void setUf(EditText uf) {
        this.uf = uf;
    }

    public EditText getCidade() {
        return cidade;
    }

    public void setCidade(EditText cidade) {
        this.cidade = cidade;
    }

    public EditText getVeiculoTipo() {
        return veiculoTipo;
    }

    public void setVeiculoTipo(EditText veiculoTipo) {
        this.veiculoTipo = veiculoTipo;
    }

    public EditText getVeiculoMarca() {
        return veiculoMarca;
    }

    public void setVeiculoMarca(EditText veiculoMarca) {
        this.veiculoMarca = veiculoMarca;
    }

    public EditText getVeiculoAnoFabricacao() {
        return veiculoAnoFabricacao;
    }

    public void setVeiculoAnoFabricacao(EditText veiculoAnoFabricacao) {
        this.veiculoAnoFabricacao = veiculoAnoFabricacao;
    }

    public EditText getVeiculoAnoModelo() {
        return veiculoAnoModelo;
    }

    public void setVeiculoAnoModelo(EditText veiculoAnoModelo) {
        this.veiculoAnoModelo = veiculoAnoModelo;
    }

    public EditText getVeiculoPlaca() {
        return veiculoPlaca;
    }

    public void setVeiculoPlaca(EditText veiculoPlaca) {
        this.veiculoPlaca = veiculoPlaca;
    }

    public EditText getVeiculoRenavam() {
        return veiculoRenavam;
    }

    public void setVeiculoRenavam(EditText veiculoRenavam) {
        this.veiculoRenavam = veiculoRenavam;
    }

    public EditText getVeiculoKilometragem() {
        return veiculoKilometragem;
    }

    public void setVeiculoKilometragem(EditText veiculoKilometragem) {
        this.veiculoKilometragem = veiculoKilometragem;
    }

    public EditText getVeiculoCor() {
        return veiculoCor;
    }

    public void setVeiculoCor(EditText veiculoCor) {
        this.veiculoCor = veiculoCor;
    }

    public EditText getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(EditText dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public EditText getPais() {
        return pais;
    }

    public void setPais(EditText pais) {
        this.pais = pais;
    }


}

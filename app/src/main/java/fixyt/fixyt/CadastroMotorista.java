package fixyt.fixyt;

import android.os.Parcel;
import android.os.Parcelable;


public class CadastroMotorista implements Parcelable{

    // Tipo String
    private String nome;
    private String sobrenome;
    private String telefone;
    private String email;
    private String senha;
    private String cpf;
    private String rg;
    private String sexo;
    private String tpLogradouro;
    private String endereco;
    private String cep;
    private String bairro;
    private String uf;
    private String cidade;
    private String dataNascimento;
    private String id;

    // Precisa criar um tipo de cadastro de Veiculo.
    private String veiculoTipo;
    private String veiculoMarca;
    private String veiculoModelo;
    private String veiculoAnoFabricacao;
    private String veiculoAnoModelo;
    private String veiculoPlaca;
    private String veiculoRenavam;
    private String veiculoKilometragem;
    private String veiculoCor;

    public CadastroMotorista() {
        super();
    }
//craido o construtor para que seja enviado para o databse.


    public CadastroMotorista(String nome, String sobrenome, String telefone, String email, String senha, String cpf, String rg, String sexo, String tpLogradouro, String endereco, String cep, String bairro, String uf, String cidade, String dataNascimento, String id, String veiculoTipo, String veiculoMarca, String veiculoModelo, String veiculoAnoFabricacao, String veiculoAnoModelo, String veiculoPlaca, String veiculoRenavam, String veiculoKilometragem, String veiculoCor) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.rg = rg;
        this.sexo = sexo;
        this.tpLogradouro = tpLogradouro;
        this.endereco = endereco;
        this.cep = cep;
        this.bairro = bairro;
        this.uf = uf;
        this.cidade = cidade;
        this.dataNascimento = dataNascimento;
        this.id = id;
        this.veiculoTipo = veiculoTipo;
        this.veiculoMarca = veiculoMarca;
        this.veiculoModelo = veiculoModelo;
        this.veiculoAnoFabricacao = veiculoAnoFabricacao;
        this.veiculoAnoModelo = veiculoAnoModelo;
        this.veiculoPlaca = veiculoPlaca;
        this.veiculoRenavam = veiculoRenavam;
        this.veiculoKilometragem = veiculoKilometragem;
        this.veiculoCor = veiculoCor;
    }


    // Utilizando objetos como parcelável
    public CadastroMotorista(Parcel parcel){
        //Sobre o motorista
        this.nome=parcel.readString();
        this.sobrenome=parcel.readString();
        this.telefone=parcel.readString();
        this.email=parcel.readString();
        this.senha=parcel.readString();
        this.cpf=parcel.readString();
        this.rg=parcel.readString();
        this.sexo=parcel.readString();
        this.tpLogradouro=parcel.readString();
        this.endereco=parcel.readString();
        this.cep=parcel.readString();
        this.bairro=parcel.readString();
        this.uf=parcel.readString();
        this.cidade=parcel.readString();
        this.dataNascimento=parcel.readString();

        //Sobre o veiculo do motorista
        this.veiculoTipo=parcel.readString();
        this.veiculoMarca=parcel.readString();
        this.veiculoModelo=parcel.readString();
        this.veiculoAnoFabricacao=parcel.readString();
        this.veiculoAnoModelo=parcel.readString();
        this.veiculoPlaca=parcel.readString();
        this.veiculoRenavam=parcel.readString();
        this.veiculoKilometragem=parcel.readString();
        this.veiculoCor=parcel.readString();
    }



    //Metodo de descrição de conteudo do Parcelable
    @Override
    public int describeContents(){
        return 0;
    }

    //Metodo de escrita para os campos da "parcela"
    @Override
    public void writeToParcel(Parcel parcel, int i){
        //Sobre o Motorista
        parcel.writeString(this.nome);
        parcel.writeString(this.sobrenome);
        parcel.writeString(this.telefone);
        parcel.writeString(this.email);
        parcel.writeString(this.senha);
        parcel.writeString(this.cpf);
        parcel.writeString(this.rg);
        parcel.writeString(this.sexo);
        parcel.writeString(this.tpLogradouro);
        parcel.writeString(this.endereco);
        parcel.writeString(this.cep);
        parcel.writeString(this.bairro);
        parcel.writeString(this.uf);
        parcel.writeString(this.cidade);
        parcel.writeString(this.dataNascimento);

        //Sobre o veiculo do motorista
        parcel.writeString(this.veiculoTipo);
        parcel.writeString(this.veiculoMarca);
        parcel.writeString(this.veiculoModelo);
        parcel.writeString(this.veiculoAnoFabricacao);
        parcel.writeString(this.veiculoAnoModelo);
        parcel.writeString(this.veiculoPlaca);
        parcel.writeString(this.veiculoRenavam);
        parcel.writeString(this.veiculoKilometragem);
        parcel.writeString(this.veiculoCor);
    }


    public static final Creator<CadastroMotorista> CREATOR=new Creator<CadastroMotorista>() {
        @Override
        public CadastroMotorista createFromParcel(Parcel source) {
            return new CadastroMotorista(source);
        }

        @Override
        public CadastroMotorista[] newArray(int size) {
            return new CadastroMotorista[size];
        }
    };

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

    public String getTpLogradouro() {
        return tpLogradouro;
    }

    public void setTpLogradouro(String tpLogradouro) {
        this.tpLogradouro = tpLogradouro;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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

    public String getVeiculoModelo() {
        return veiculoModelo;
    }

    public void setVeiculoModelo(String veiculoModelo) {
        this.veiculoModelo = veiculoModelo;
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

    public String getVeiculoPlaca() {
        return veiculoPlaca;
    }

    public void setVeiculoPlaca(String veiculoPlaca) {
        this.veiculoPlaca = veiculoPlaca;
    }

    public String getVeiculoRenavam() {
        return veiculoRenavam;
    }

    public void setVeiculoRenavam(String veiculoRenavam) {
        this.veiculoRenavam = veiculoRenavam;
    }

    public String getVeiculoKilometragem() {
        return veiculoKilometragem;
    }

    public void setVeiculoKilometragem(String veiculoKilometragem) {
        this.veiculoKilometragem = veiculoKilometragem;
    }

    public String getVeiculoCor() {
        return veiculoCor;
    }

    public void setVeiculoCor(String veiculoCor) {
        this.veiculoCor = veiculoCor;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}


package fixyt.fixyt;

import android.widget.EditText;

public class AddVeiculo {

    private EditText veiculoTipo;
    private EditText veiculoMarca;
    private EditText veiculoAnoFabricacao;
    private EditText veiculoAnoModelo;
    private EditText veiculoPlaca;
    private EditText veiculoRenavam;
    private EditText veiculoKilometragem;
    private EditText veiculoCor;

    public AddVeiculo(EditText veiculoTipo, EditText veiculoMarca, EditText veiculoAnoFabricacao, EditText veiculoAnoModelo, EditText veiculoPlaca, EditText veiculoRenavam, EditText veiculoKilometragem, EditText veiculoCor) {
        this.veiculoTipo = veiculoTipo;
        this.veiculoMarca = veiculoMarca;
        this.veiculoAnoFabricacao = veiculoAnoFabricacao;
        this.veiculoAnoModelo = veiculoAnoModelo;
        this.veiculoPlaca = veiculoPlaca;
        this.veiculoRenavam = veiculoRenavam;
        this.veiculoKilometragem = veiculoKilometragem;
        this.veiculoCor = veiculoCor;
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
}

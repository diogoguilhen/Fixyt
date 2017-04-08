package fixyt.fixyt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrocarCarro extends AppCompatActivity implements View.OnClickListener {

    private Button botaoAtualizar;
    private EditText campoModeloVeiculo;
    private EditText campoPlacaVeiculo;
    private EditText campoKmVeiculo;
    private EditText campoRenavam;
    private EditText campoCorVeiculo;
    private Spinner campoTpVeiculo;
    private Spinner campoMarcaVeiculo;
    private Spinner campoAnoFabVeiculo;
    private Spinner campoAnoModVeiculo;

    private ArrayAdapter adaptadorTpVeiculo;
    private ArrayAdapter adaptadorMarcaVeiculo;
    private ArrayAdapter adaptadorAnoFabVeiculo;
    private ArrayAdapter adaptadorAnoModVeiculo;

    private ProgressDialog dialogoProgresso;



    // Declarar API Firabase Auth
    private FirebaseAuth firebasAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trocar_carro);

    //  //Chamando FIrebase Auth
      firebasAuth = FirebaseAuth.getInstance();
    //  if(firebasAuth.getCurrentUser() != null){
    //      //ir para tela main ou perfil
    //      finish();
    //      //inicializar tela principal
    //      startActivity(new Intent(getApplicationContext(), Main.class));
    //  }

        dialogoProgresso = new ProgressDialog(this);

        // Spinner de Tipo de Veiculo
        campoTpVeiculo = (Spinner) findViewById(R.id.spinnerTpCarro);
        adaptadorTpVeiculo = ArrayAdapter.createFromResource(this,R.array.TipoVeiculo, android.R.layout.simple_spinner_item);
        adaptadorTpVeiculo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campoTpVeiculo.setAdapter(adaptadorTpVeiculo);

        // Spinner de Marca do Veiculo
        campoMarcaVeiculo = (Spinner) findViewById(R.id.spinnerMarcaCarro);
        adaptadorMarcaVeiculo = ArrayAdapter.createFromResource(this,R.array.MarcaVeiculo, android.R.layout.simple_spinner_item);
        adaptadorMarcaVeiculo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campoMarcaVeiculo.setAdapter(adaptadorMarcaVeiculo);

        // Spinner de Ano Fabricacao do veiculo
        campoAnoFabVeiculo = (Spinner) findViewById(R.id.spinnerAnoFabricacao);
        adaptadorAnoFabVeiculo = ArrayAdapter.createFromResource(this,R.array.AnoVeiculo, android.R.layout.simple_spinner_item);
        adaptadorAnoFabVeiculo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campoAnoFabVeiculo.setAdapter(adaptadorAnoFabVeiculo);

        // Spinner de Ano Modelo do veiculo
        campoAnoModVeiculo = (Spinner) findViewById(R.id.spinnerAnoModelo);
        adaptadorAnoModVeiculo = ArrayAdapter.createFromResource(this,R.array.AnoVeiculo, android.R.layout.simple_spinner_item);
        adaptadorAnoModVeiculo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campoAnoModVeiculo.setAdapter(adaptadorAnoModVeiculo);


        botaoAtualizar = (Button) findViewById(R.id.botAtualizar);
        campoModeloVeiculo = (EditText) findViewById(R.id.campoModeloVeiculo);
        campoPlacaVeiculo = (EditText) findViewById(R.id.campoPlacaVeiculo);
        campoKmVeiculo = (EditText) findViewById(R.id.campoKmVeiculo);
        campoRenavam = (EditText) findViewById(R.id.campoRenavam);
        campoCorVeiculo = (EditText) findViewById(R.id.campoCorVeiculo);

        //Preparando os botões e menus para receber clicks
        botaoAtualizar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == botaoAtualizar){
            //Finalizar Cadastro, salvar no banco de dados associando o User aos dados e ir para Main
            atualizarVeiculo();
        }

    }

    private void atualizarVeiculo(){

        //Recebendo cadastro de Veiculo atual
        CadastroMotorista cadastroMotorista= getIntent().getParcelableExtra("cadastro");

        //Apropriando os valores aos campos seguintes.
        cadastroMotorista.setVeiculoTipo(campoTpVeiculo.getSelectedItem().toString().trim());
        cadastroMotorista.setVeiculoMarca(campoMarcaVeiculo.getSelectedItem().toString().trim());
        cadastroMotorista.setVeiculoModelo(campoModeloVeiculo.getText().toString().trim());
        cadastroMotorista.setVeiculoAnoFabricacao(campoAnoFabVeiculo.getSelectedItem().toString().trim());
        cadastroMotorista.setVeiculoAnoModelo(campoAnoModVeiculo.getSelectedItem().toString().trim());
        cadastroMotorista.setVeiculoPlaca(campoPlacaVeiculo.getText().toString().trim());
        cadastroMotorista.setVeiculoRenavam(campoRenavam.getText().toString().trim());
        cadastroMotorista.setVeiculoKilometragem(campoKmVeiculo.getText().toString().trim());
        cadastroMotorista.setVeiculoCor(campoCorVeiculo.getText().toString().trim());

        String tipoVeiculo = campoTpVeiculo.getSelectedItem().toString().trim();
        String marcaVeiculo = campoMarcaVeiculo.getSelectedItem().toString().trim();
        String modeloVeiculo = campoModeloVeiculo.getText().toString().trim();
        String anoFabVeiculo = campoAnoFabVeiculo.getSelectedItem().toString().trim();
        String anoModVeiculo = campoAnoModVeiculo.getSelectedItem().toString().trim();
        String placaVeiculo = campoPlacaVeiculo.getText().toString().trim();
        String renavamVeiculo = campoRenavam.getText().toString().trim();
        String kmVeiculo = campoKmVeiculo.getText().toString().trim();
        String corVeiculo = campoCorVeiculo.getText().toString().trim();

        if(TextUtils.isEmpty(modeloVeiculo)){
            //Modelo de Veiculo vazio
            Toast.makeText(this, "Ingresse um Modelo de Carro!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }




        // Após validar que cadastro está OK um dialogo de progresso é mostrada
        dialogoProgresso.setMessage("Registrando Usuário...Aguarde...");
        dialogoProgresso.show();


        // PARTE ONDE SALVA OS DADOS DO CLIENTE

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference criacaoMotorista = database.getReference("Motorista");

        String key = FirebaseAuth.getInstance().getCurrentUser().getUid();

        CadastroMotorista VeiculoMotorista = new CadastroMotorista(
                                                            cadastroMotorista.getVeiculoTipo(),
                                                            cadastroMotorista.getVeiculoMarca(),
                                                            cadastroMotorista.getVeiculoModelo(),
                                                            cadastroMotorista.getVeiculoAnoFabricacao(),
                                                            cadastroMotorista.getVeiculoAnoModelo(),
                                                            cadastroMotorista.getVeiculoPlaca(),
                                                            cadastroMotorista.getVeiculoRenavam(),
                                                            cadastroMotorista.getVeiculoKilometragem(),
                                                            cadastroMotorista.getVeiculoCor()
                                                                );



        criacaoMotorista.child(key).child("Veiculos").child(criacaoMotorista.push().getKey()).setValue(VeiculoMotorista);



        //Intent intentMain = new Intent(TrocarCarro.this, Main.class);
        Toast.makeText(this, "Dados de Veículo Salvos com Sucesso!", Toast.LENGTH_SHORT).show();
        finish();
        dialogoProgresso.dismiss();
        //startActivity(intentMain);



    }

}

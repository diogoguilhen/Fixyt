package fixyt.fixyt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Registrar_3 extends AppCompatActivity implements View.OnClickListener {

    private Button botaoRegistrar;
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
        setContentView(R.layout.activity_registrar_3);

        //Chamando FIrebase Auth
        firebasAuth = FirebaseAuth.getInstance();
        if(firebasAuth.getCurrentUser() != null){
            //ir para tela main ou perfil
            finish();
            //inicializar tela principal
            startActivity(new Intent(getApplicationContext(), Main.class));
        }

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


        botaoRegistrar = (Button) findViewById(R.id.botFinalizar);
        campoModeloVeiculo = (EditText) findViewById(R.id.campoModeloVeiculo);
        campoPlacaVeiculo = (EditText) findViewById(R.id.campoPlacaVeiculo);
        campoKmVeiculo = (EditText) findViewById(R.id.campoKmVeiculo);
        campoRenavam = (EditText) findViewById(R.id.campoRenavam);
        campoCorVeiculo = (EditText) findViewById(R.id.campoCorVeiculo);

        //Preparando os botões e menus para receber clicks
        botaoRegistrar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == botaoRegistrar){
            //Finalizar Cadastro, salvar no banco de dados associando o User aos dados e ir para Main
            registrar3();
        }

    }

    private void registrar3(){

        //Recebendo cadastro da tela Registrar_2
        CadastroMotorista cadastroMotorista=(CadastroMotorista)getIntent().getParcelableExtra("cadastro");

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


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference criacao = database.getReference("Motorista");
        CadastroMotorista user = new CadastroMotorista(     cadastroMotorista.getNome(),
                                                            cadastroMotorista.getSobrenome(),
                                                            cadastroMotorista.getTelefone(),
                                                            cadastroMotorista.getEmail(),
                                                            cadastroMotorista.getSenha(),
                                                            cadastroMotorista.getCpf(),
                                                            cadastroMotorista.getRg(),
                                                            cadastroMotorista.getSexo(),
                                                            cadastroMotorista.getTpLogradouro(),
                                                            cadastroMotorista.getEndereco(),
                                                            cadastroMotorista.getCep(),
                                                            cadastroMotorista.getBairro(),
                                                            cadastroMotorista.getUf(),
                                                            cadastroMotorista.getCidade(),
                                                            cadastroMotorista.getDataNascimento(),
                                                            //cadastroMotorista.getid ??
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
        DatabaseReference raiz = FirebaseDatabase.getInstance().getReference(); //Esta variavel indica a raiz da nossa árvore JSON
        raiz.child("usuarios/2").setValue(user);//Colocar o usuario com o id 2
        criacao.child(criacao.push().getKey()).setValue(user);


        // Após validar que cadastro está OK um dialogo de progresso é mostrada
            dialogoProgresso.setMessage("Registrando Usuário...Aguarde...");
            dialogoProgresso.show();

    //    dialogoProgresso.setMessage("Tela 1: " +  cadastroMotorista.getNome() + " " + cadastroMotorista.getSobrenome() + " " + cadastroMotorista.getTelefone() + " "
    //            + cadastroMotorista.getEmail() + " " + cadastroMotorista.getSenha() + " Tela 2: " + cadastroMotorista.getCpf() + " " + cadastroMotorista.getRg() + " " + cadastroMotorista.getDataNascimento() + " " +
    //            cadastroMotorista.getSexo() + " " + cadastroMotorista.getTpLogradouro() + " " + cadastroMotorista.getEndereco() + " " + cadastroMotorista.getCep() + " " + cadastroMotorista.getBairro() + " " + cadastroMotorista.getUf() + " " + cadastroMotorista.getCidade() +
    //            " Tela 3: " + tipoVeiculo + " " + marcaVeiculo + " " + modeloVeiculo + " " + anoFabVeiculo + " " + anoModVeiculo + " " + placaVeiculo + " " +
    //            renavamVeiculo + " " + kmVeiculo + " " + corVeiculo);
    //    dialogoProgresso.show();


    }

}

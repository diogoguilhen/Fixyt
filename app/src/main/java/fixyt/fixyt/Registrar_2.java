package fixyt.fixyt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class Registrar_2 extends AppCompatActivity implements View.OnClickListener  {

    private Button botaoProximo2;
    private EditText campoCpf;
    private EditText campoRg;
    private EditText campoDataNascimento;
    private Spinner campoSexo;
    private Spinner campoTpLogradouro;
    private EditText campoEndereco;
    private EditText campoCep;
    private EditText campoBairro;
    private Spinner menuEstado;
    private EditText campoCidade;

    private ArrayAdapter adaptadorEstado;
    private ArrayAdapter adaptadorSexo;
    private ArrayAdapter adaptadorTpLogradouro;
    private ProgressDialog dialogoProgresso;

    // Declarar API Firabase Auth
    private FirebaseAuth firebasAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_2);



        dialogoProgresso = new ProgressDialog(this);

        // Spinner de Estado
        menuEstado = (Spinner) findViewById(R.id.spinnerEstado);
        adaptadorEstado = ArrayAdapter.createFromResource(this,R.array.Estados, android.R.layout.simple_spinner_item);
        adaptadorEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menuEstado.setAdapter(adaptadorEstado);

        //Spinner do Sexo
        campoSexo = (Spinner) findViewById(R.id.spinnerSexo);
        adaptadorSexo = ArrayAdapter.createFromResource(this,R.array.Sexo, android.R.layout.simple_spinner_item);
        adaptadorSexo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campoSexo.setAdapter(adaptadorSexo);

        //Spinner do tipo de logradouro
        campoTpLogradouro = (Spinner) findViewById(R.id.spinnerTpLogradouro);
        adaptadorTpLogradouro = ArrayAdapter.createFromResource(this,R.array.TipoLogradouro, android.R.layout.simple_spinner_item);
        adaptadorTpLogradouro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campoTpLogradouro.setAdapter(adaptadorTpLogradouro);


        botaoProximo2 = (Button) findViewById(R.id.botProximo2);
        campoCpf = (EditText) findViewById(R.id.campoCpf);
        campoRg = (EditText) findViewById(R.id.campoRg);
        campoDataNascimento = (EditText) findViewById(R.id.campoDataNascimento);
        campoEndereco = (EditText) findViewById(R.id.campoEndereco);
        campoCep = (EditText) findViewById(R.id.campoCep);
        campoBairro = (EditText) findViewById(R.id.campoBairro);
        campoCidade = (EditText) findViewById(R.id.campoCidade);

        //Preparando os botões e menus para receber clicks
        botaoProximo2.setOnClickListener(this);

        //Mascarando Campos de CPF, RG, CEP e Data Nascimento,
        MaskEditTextChangedListener maskCpf = new MaskEditTextChangedListener("###.###.###-##", campoCpf);
        campoCpf.addTextChangedListener(maskCpf);

        MaskEditTextChangedListener maskRg = new MaskEditTextChangedListener("##.###.###-#", campoRg);
        campoRg.addTextChangedListener(maskRg);

        MaskEditTextChangedListener maskDataNasc = new MaskEditTextChangedListener("##/##/####", campoDataNascimento);
        campoDataNascimento.addTextChangedListener(maskDataNasc);

        MaskEditTextChangedListener maskCep = new MaskEditTextChangedListener("#####-###", campoCep);
        campoCep.addTextChangedListener(maskCep);



    }

    @Override
    public void onClick(View v) {
        if (v == botaoProximo2){
            //completa o segundo passo do cadastro.
            registrar2();
        }

    }

    private void registrar2() {

        //Recebendo cadastro da tela Registrar_1
        CadastroMotorista cadastroMotorista= getIntent().getParcelableExtra("cadastro");

        //Apropriando os valores aos campos seguintes.
        cadastroMotorista.setCpf(campoCpf.getText().toString().trim());
        cadastroMotorista.setRg(campoRg.getText().toString().trim());
        cadastroMotorista.setDataNascimento(campoDataNascimento.getText().toString().trim());
        cadastroMotorista.setSexo(campoSexo.getSelectedItem().toString().trim());
        cadastroMotorista.setTpLogradouro(campoTpLogradouro.getSelectedItem().toString().trim());
        cadastroMotorista.setEndereco(campoEndereco.getText().toString().trim());
        cadastroMotorista.setCep(campoCep.getText().toString().trim());
        cadastroMotorista.setBairro(campoBairro.getText().toString().trim());
        cadastroMotorista.setUf(menuEstado.getSelectedItem().toString().trim());
        cadastroMotorista.setCidade(campoCidade.getText().toString().trim());

        //Retirar mascaras para salvamento no banco de dados (incluindo Cpf para validação no frontend)
        String CPF = campoCpf.getText().toString().trim().replaceAll("[.-]", "");
        String RG = campoRg.getText().toString().trim().replaceAll("[.-]", "");
        String dataNasc = campoDataNascimento.getText().toString().trim();
        String sexo = campoSexo.getSelectedItem().toString();
        String tipoLogradouro = campoTpLogradouro.getSelectedItem().toString();
        String endereco = campoEndereco.getText().toString().trim();
        String CEP = campoCep.getText().toString().trim().replaceAll("[.-]", "");
        String bairro = campoBairro.getText().toString().trim();
        String UF = menuEstado.getSelectedItem().toString();
        String Cidade = campoCidade.getText().toString().trim();


        if(TextUtils.isEmpty(CPF)){
            //CPF vazio
            Toast.makeText(this, "Insira seu CPF!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        //Verificação de CPF válido pelo frontend para posteriormente verificar na Receita a existencia do CPF.
        if (ValidaCPF.isCPF(CPF)) {
            Toast.makeText(this, "CPF " + ValidaCPF.imprimeCPF(CPF) + " Validado!", Toast.LENGTH_SHORT).show();
            System.out.printf("%s\n", ValidaCPF.imprimeCPF(CPF));
        }
        else {
            Toast.makeText(this, "CPF " + ValidaCPF.imprimeCPF(CPF) + " Invalido! Digite um CPF valido!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(RG)){
            //RG vazio
            Toast.makeText(this, "Insira seu RG!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        if(TextUtils.isEmpty(dataNasc)){
            //data de nascimento vazia
            Toast.makeText(this, "Insira sua data de nascimento!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        if(TextUtils.isEmpty(endereco)){
            //endereço vazio
            Toast.makeText(this, "Insira um endereço!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        if(TextUtils.isEmpty(CEP)){
            //CEP vazio
            Toast.makeText(this, "Insira um CEP!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        if(TextUtils.isEmpty(bairro)){
            //Bairro vazio
            Toast.makeText(this, "Insira um bairro!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        if(TextUtils.isEmpty(Cidade)){
            //Cidade vazia
            Toast.makeText(this, "Insira sua cidade!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }



        // CADASTRO NO DATABASE

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference criacaoMotorista = database.getReference("Motorista");



        String key = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //metodo antigo que acaba substituindo o banco inteiro  nao viavel.
        //criacaoMotorista.child(key).setValue(user);


        /// ATUALIZANDO CAMPO POR CAMPO

        //CPF
        HashMap<String, Object> cpfmap = new HashMap<>();
        cpfmap.put("cpf", cadastroMotorista.getCpf());
        criacaoMotorista.child(key).updateChildren(cpfmap);
        //RG
        HashMap<String, Object> rgmap = new HashMap<>();
        rgmap.put("rg", cadastroMotorista.getRg());
        criacaoMotorista.child(key).updateChildren(rgmap);
        //DATANASCIMENTO
        HashMap<String, Object> datanascimentomap = new HashMap<>();
        datanascimentomap.put("dataNascimento", cadastroMotorista.getDataNascimento());
        criacaoMotorista.child(key).updateChildren(datanascimentomap);
        //SEXO
        HashMap<String, Object> sexomap = new HashMap<>();
        sexomap.put("sexo", cadastroMotorista.getSexo());
        criacaoMotorista.child(key).updateChildren(sexomap);
        //TP LOGRADOURO
        HashMap<String, Object> tplogradouromap = new HashMap<>();
        tplogradouromap.put("tpLogradouro", cadastroMotorista.getTpLogradouro());
        criacaoMotorista.child(key).updateChildren(tplogradouromap);
        //ENDERECO
        HashMap<String, Object> enderecomap = new HashMap<>();
        enderecomap.put("endereco", cadastroMotorista.getEndereco());
        criacaoMotorista.child(key).updateChildren(enderecomap);
        //CEP
        HashMap<String, Object> cepmap = new HashMap<>();
        cepmap.put("cep", cadastroMotorista.getCep());
        criacaoMotorista.child(key).updateChildren(cepmap);
        //BAIRRO
        HashMap<String, Object> bairromap = new HashMap<>();
        bairromap.put("bairro", cadastroMotorista.getBairro());
        criacaoMotorista.child(key).updateChildren(bairromap);
        //UF
        HashMap<String, Object> ufmap = new HashMap<>();
        ufmap.put("uf", cadastroMotorista.getUf());
        criacaoMotorista.child(key).updateChildren(ufmap);
        //CIDADE
        HashMap<String, Object> cidademap = new HashMap<>();
        cidademap.put("cidade", cadastroMotorista.getCidade());
        criacaoMotorista.child(key).updateChildren(cidademap);


        //Passando dados para a tela REGISTRAR 3
        Intent intentReg2 = new Intent(Registrar_2.this, Registrar_3.class);
        intentReg2.putExtra("cadastro", cadastroMotorista);
        startActivity(intentReg2);
        dialogoProgresso.dismiss();

    }
}

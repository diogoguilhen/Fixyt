package fixyt.fixyt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.Objects;

public class Registrar_1 extends AppCompatActivity implements View.OnClickListener{

    private Button botaoProximo1;
    private EditText nome;
    private EditText sobrenome;
    private EditText pais;
    private EditText telefone;
    private EditText email;
    private EditText confirmaSenha;
    private EditText digSenha;
    private ProgressDialog dialogoProgresso;
    private CadastroMotorista cadastroMotorista;

    private Spinner spinnerPais;
    private ArrayAdapter adaptador;

    // Declarar API Firabase Auth
    private FirebaseAuth firebasAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_1);

        //Chamando Firebase Auth
        firebasAuth = FirebaseAuth.getInstance();

        dialogoProgresso = new ProgressDialog(this);
        cadastroMotorista = new CadastroMotorista();
        //Teste do SPINNER PAIS
        spinnerPais = (Spinner) findViewById(R.id.spinnerPais);
        adaptador = ArrayAdapter.createFromResource(this, R.array.Paises, android.R.layout.simple_spinner_item);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPais.setAdapter(adaptador);

        botaoProximo1 = (Button) findViewById(R.id.botaoProximo1);
        nome = (EditText) findViewById(R.id.campoNome);
        sobrenome = (EditText) findViewById(R.id.campoSobrenome);
        //pais = (EditText) findViewById(R.id.campoPais);
        telefone = (EditText) findViewById(R.id.campoTelefone);
        email = (EditText) findViewById(R.id.campoEmail);

        //Apropriando os valores dos EditText para os STRINGS do objeto CADASTRO.

        confirmaSenha = (EditText) findViewById(R.id.confirmaSenha);
        digSenha = (EditText) findViewById(R.id.campoSenha);

        //Utilizando mascaras para os campos devidos
        MaskEditTextChangedListener maskTEL = new MaskEditTextChangedListener("(##)#####-####", telefone);
        telefone.addTextChangedListener(maskTEL);

        botaoProximo1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == botaoProximo1){
            //completa o primeiro passo do cadastro
            registrar1();
        }
    }

    private void registrar1(){
        cadastroMotorista.setNome(nome.getText().toString().trim());
        cadastroMotorista.setSobrenome(sobrenome.getText().toString().trim());
        //cadastroMotorista.setPais(pais.getText().toString().trim());
        cadastroMotorista.setTelefone(telefone.getText().toString().trim());
        cadastroMotorista.setEmail(email.getText().toString().trim());
        cadastroMotorista.setSenha(digSenha.getText().toString().trim());

        String email = cadastroMotorista.getEmail().trim();
        String senha = cadastroMotorista.getSenha().trim();
        String nome = cadastroMotorista.getNome().trim();
        String sobrenome = cadastroMotorista.getSobrenome().trim();
        String pais = cadastroMotorista.getPais().trim();
        String telefone = cadastroMotorista.getTelefone().trim();
        String ConfSen = confirmaSenha.getText().toString().trim();
        String tSenha = digSenha.getText().toString().trim();

        if(TextUtils.isEmpty(nome)){
            //email vazio
            Toast.makeText(this, "Ingresse um nome!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        if(TextUtils.isEmpty(sobrenome)){
            //email vazio
            Toast.makeText(this, "Ingresse um sobrenome!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        /*if(TextUtils.isEmpty(pais)){
            //email vazio
            Toast.makeText(this, "Ingresse um pais!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }*/
        if(TextUtils.isEmpty(telefone)){
            //email vazio
            Toast.makeText(this, "Ingresse um telefone!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        if(TextUtils.isEmpty(email)){
            //email vazio
            Toast.makeText(this, "Ingresse um Email Válido!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        if(TextUtils.isEmpty(senha)){
            //senha vazia
            Toast.makeText(this, "Ingresse uma Senha!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        if(TextUtils.isEmpty(senha)){
            //senha vazia
            Toast.makeText(this, "Ingresse uma Senha!" , Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        if(!ConfSen.equals(tSenha)){
            //senha não está igual em ambos os campos
            Toast.makeText(this, "Verifique a senha e a confirmação novamente!" + tSenha + "+" + ConfSen, Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        // Após validar que cadastro está OK um dialogo de progresso é mostrada
        dialogoProgresso.setMessage("Aguarde...");
        dialogoProgresso.show();
        startActivity(new Intent(this, Registrar_2.class));
        dialogoProgresso.dismiss();

        // Enviando objeto CadastroMotorista preenchido para a próxima tela



    }
}

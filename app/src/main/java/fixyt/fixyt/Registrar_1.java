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
import android.widget.Button;
import android.widget.EditText;
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
    private EditText confirmaSenha;
    private ProgressDialog dialogoProgresso;
    private Cadastro cadastroMotorista;

    private static final String TAG = "Registrar_1";
    // Declarar API Firabase Auth
    private FirebaseAuth firebasAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_1);

        //Chamando FIrebase Auth
        firebasAuth = FirebaseAuth.getInstance();

        dialogoProgresso = new ProgressDialog(this);

        botaoProximo1 = (Button) findViewById(R.id.botaoProximo1);
        cadastroMotorista.setNome((EditText) findViewById(R.id.campoNome));
        cadastroMotorista.setSobrenome((EditText) findViewById(R.id.campoSobrenome));
        cadastroMotorista.setTelefone((EditText) findViewById(R.id.campoTelefone));
        cadastroMotorista.setEmail((EditText) findViewById(R.id.campoEmail));
        cadastroMotorista.setSenha((EditText) findViewById(R.id.campoSenha));
        confirmaSenha = (EditText) findViewById(R.id.confirmaSenha);

        botaoProximo1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == botaoProximo1){
            //completa o primeiro passo do cadastro
            registrar1();
            startActivity(new Intent(getApplicationContext(), Registrar_2.class));
            dialogoProgresso.dismiss();
        }
    }

    private void registrar1(){
        String email = cadastroMotorista.getEmail().toString().trim();
        String senha = cadastroMotorista.getSenha().toString().trim();
        String ConfSen = confirmaSenha.getText().toString().trim();

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
        if(!ConfSen.equals(senha)){
            //senha não está igual em ambos os campos
            Toast.makeText(this, "Verifique a senha e a confirmação novamente!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        // Após validar que cadastro está OK um dialogo de progresso é mostrada
        dialogoProgresso.setMessage("Aguarde...");
        dialogoProgresso.show();
    }
}

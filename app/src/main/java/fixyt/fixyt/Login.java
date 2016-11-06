package fixyt.fixyt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity implements View.OnClickListener{


    private Button botaoLogar;
    private TextView textoRegistrar;
    private EditText campoEmail;
    private EditText campoSenha;
    private ProgressDialog dialogoProgresso;

    private static final String TAG = "Login";
    // Declarar API Firabase Auth
    private FirebaseAuth firebasAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Chamando FIrebase Auth
        firebasAuth = FirebaseAuth.getInstance();
        if(firebasAuth.getCurrentUser() != null){
            //ir para tela main ou perfil
            finish();
            //inicializar tela principal
            startActivity(new Intent(getApplicationContext(), Main.class));
        }

        dialogoProgresso = new ProgressDialog(this);

        botaoLogar = (Button) findViewById(R.id.botEntrar);
        textoRegistrar = (TextView) findViewById(R.id.textoRegistrar);
        campoEmail = (EditText) findViewById(R.id.campoEmail);
        campoSenha = (EditText) findViewById(R.id.campoSenha);

        botaoLogar.setOnClickListener(this);
        textoRegistrar.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == botaoLogar){
            //logar
            usuarioLogin();
        }
        if (v == textoRegistrar){
            //Ir para tela de registro;
            finish();
            startActivity(new Intent(this, Registrar_1.class));
        }
    }

    private void usuarioLogin() {
        String email = campoEmail.getText().toString().trim();
        String senha = campoSenha.getText().toString().trim();

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
        // Após validar que cadastro está OK um dialogo de progresso é mostrada
        dialogoProgresso.setMessage("Fazendo Login...");
        dialogoProgresso.show();

        // Chamando Signin
        firebasAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Se tarefa é completada
                        if(task.isSuccessful()){
                            //usuario logou corretamente
                            finish();
                            //inicializar tela principal
                            startActivity(new Intent(getApplicationContext(), Main.class));
                            //mostrar mensagem para usuario indicando sucesso
                            Toast.makeText(Login.this, "Logado com sucesso!", Toast.LENGTH_SHORT).show();
                            dialogoProgresso.dismiss();
                        }
                        else{
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthInvalidUserException e) {
                                Toast.makeText(Login.this, "O usuario digitado não existe ou foi bloqueado.", Toast.LENGTH_LONG).show();
                                dialogoProgresso.dismiss();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(Login.this, "Senha incorreta! Digite novamente.", Toast.LENGTH_LONG).show();
                                dialogoProgresso.dismiss();
                            } catch(Exception e) {
                                Log.e(TAG, e.getMessage());
                            }
                        }
                    }
                });
    }
}

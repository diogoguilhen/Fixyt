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

import java.util.ArrayList;
import java.util.List;

public class Registrar_3 extends AppCompatActivity implements View.OnClickListener {

    private Button botaoRegistrar;
    private EditText campoDataNascimento;
    private EditText campoCPF;
    private EditText campoCidade;
    private Spinner menuEstado;
    private ProgressDialog dialogoProgresso;

    private static final String TAG = "Registrar_2";

    // Declarar API Firabase Auth
    private FirebaseAuth firebasAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_2);

        //Chamando FIrebase Auth
        firebasAuth = FirebaseAuth.getInstance();
        if(firebasAuth.getCurrentUser() != null){
            //ir para tela main ou perfil
            finish();
            //inicializar tela principal
            startActivity(new Intent(getApplicationContext(), Main.class));
        }

        dialogoProgresso = new ProgressDialog(this);

        menuEstado = (Spinner) findViewById(R.id.campoEstado);
        botaoRegistrar = (Button) findViewById(R.id.botFinalizar);
        campoCPF = (EditText) findViewById(R.id.campoCpfCnpj);
        campoDataNascimento = (EditText) findViewById(R.id.campoDataNascimento);
        campoCidade = (EditText) findViewById(R.id.campoCidade);

        //Preparando os botões e menus para receber clicks
        botaoRegistrar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == botaoRegistrar){
            //Finalizar Cadastro, salvar no banco de dados associando o User aos dados e ir para Main

        }

    }

    /*private void registrar3(){
        String email = Cadastro;
        String senha = Cadastro;
        String ConfSen = confirmaSenha;

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
        dialogoProgresso.setMessage("Registrando Usuário...");
        dialogoProgresso.show();

        firebasAuth.createUserWithEmailAndPassword(email,senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Se tarefa é completada
                        if(task.isSuccessful()){
                            //usuario registrou corretamente
                            finish();
                            //inicializar cadastro de perfil
                            startActivity(new Intent(getApplicationContext(), Perfil.class));
                            //mostrar mensagem para usuario indicando sucesso
                            Toast.makeText(Registrar_1.this, "Registrado com Sucesso.", Toast.LENGTH_SHORT).show();
                            dialogoProgresso.dismiss();
                        }
                        else{
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(Registrar_1.this, "A senha utilizada deve ter no mínimo 6 caracteres.", Toast.LENGTH_LONG).show();
                                dialogoProgresso.dismiss();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(Registrar_1.this, "As credenciais utilizadas expiraram. Contate o administrador", Toast.LENGTH_LONG).show();
                                dialogoProgresso.dismiss();
                            } catch(FirebaseAuthUserCollisionException e) {
                                Toast.makeText(Registrar_1.this, "O usuário escolhido já está cadastrado. Escolha outro!", Toast.LENGTH_LONG).show();
                                dialogoProgresso.dismiss();
                            } catch(Exception e) {
                                Log.e(TAG, e.getMessage());
                            }
                        }
                    }
                });
    }

*/
}

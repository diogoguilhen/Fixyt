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

public class Registrar_1 extends AppCompatActivity implements OnClickListener{

    private Button botaoRegistrar;
    private EditText campoEmail;
    private EditText campoSenha;
    private EditText confirmaSenha;
    private ProgressDialog dialogoProgresso;

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

        botaoRegistrar = (Button) findViewById(R.id.botRegistrar);
        campoEmail = (EditText) findViewById(R.id.campoEmail);
        campoSenha = (EditText) findViewById(R.id.campoSenha);
        confirmaSenha = (EditText) findViewById(R.id.confirmaSenha);

        botaoRegistrar.setOnClickListener(this);
    }

    private void registrarUsuario(){
        String email = campoEmail.getText().toString().trim();
        String senha = campoSenha.getText().toString().trim();
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

    @Override
    public void onClick(View v) {
        if(v == botaoRegistrar){
            registrarUsuario();
        }
    }
}

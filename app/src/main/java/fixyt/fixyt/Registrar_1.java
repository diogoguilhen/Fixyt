package fixyt.fixyt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class Registrar_1 extends AppCompatActivity implements OnClickListener{

    private Button botaoRegistrar;
    private EditText campoEmail;
    private EditText campoSenha;
    private ProgressDialog dialogoProgresso;

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

        botaoRegistrar.setOnClickListener(this);
    }

    private void registrarUsuario(){
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
        dialogoProgresso.setMessage("Registrando Usuário...");
        dialogoProgresso.show();

        firebasAuth.createUserWithEmailAndPassword(email,senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Se tarefa é completada
                        if(task.isComplete()){
                            //usuario registrou corretamente
                            finish();
                            //inicializar cadastro de perfil
                            startActivity(new Intent(getApplicationContext(), Main.class));
                            //mostrar mensagem para usuario indicando sucesso
                            Toast.makeText(Registrar_1.this, "Registrado com Sucesso.", Toast.LENGTH_SHORT).show();
                            dialogoProgresso.dismiss();
                        }
                        else{
                            Toast.makeText(Registrar_1.this, "Não foi possível registrar. Tente novamente!", Toast.LENGTH_SHORT).show();
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

package fixyt.fixyt;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Perfil extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView emailUsuario;
    private EditText campoSenha;
    private Button logOut;
    private Button redefSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, PreLogin.class));
        }

        FirebaseUser usuario = firebaseAuth.getInstance().getCurrentUser();




        emailUsuario = (TextView) findViewById(R.id.textUsuarioEmail);
        emailUsuario.setText("Bem-Vindo " + usuario.getEmail() + "!!");
        campoSenha = (EditText) findViewById(R.id.novaSenha);

        logOut = (Button) findViewById(R.id.botaoLogout);
        redefSenha = (Button) findViewById(R.id.botaoRedefinirSenha);

        logOut.setOnClickListener(this);
        redefSenha.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == logOut){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, PreLogin.class));
        }
        if(v == redefSenha){
            FirebaseUser usuario = firebaseAuth.getInstance().getCurrentUser();

            String newPassword = campoSenha.getText().toString();


            usuario.updatePassword(newPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Perfil.this, "Senha Atualizada com Sucesso!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}



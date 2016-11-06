package fixyt.fixyt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class PreLogin extends AppCompatActivity {

    //SERGIO BIXA DO MATO
    // Declarar API Firabase Auth
    private FirebaseAuth firebasAuth;
    private Button entrarUsuario;
    private Button registrarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_login);

        firebasAuth = FirebaseAuth.getInstance();
        if(firebasAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, Main.class));
        }


        entrarUsuario = (Button) findViewById(R.id.BotaoEntrar);
        registrarUsuario = (Button) findViewById(R.id.BotaoRegistrar);

        registrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goTelaRegistrar = new Intent(PreLogin.this, Registrar_1.class);
                startActivity(goTelaRegistrar);
            }
        });
        entrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goTelaLogin = new Intent(PreLogin.this, Login.class);
                startActivity(goTelaLogin);
            }
        });
    }
}

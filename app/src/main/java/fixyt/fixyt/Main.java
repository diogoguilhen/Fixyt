package fixyt.fixyt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Main extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private Button logOut;
    private Button perfilUser;
    private Button pedirAuxilio;
    private Button trocarCarro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, Login.class));
        }

        logOut = (Button) findViewById(R.id.botaoLogout);
        perfilUser = (Button) findViewById(R.id.botaoPerfil);
        pedirAuxilio = (Button) findViewById(R.id.botaoAuxilio);
        trocarCarro = (Button) findViewById(R.id.botaoCarro);

        logOut.setOnClickListener(this);
        perfilUser.setOnClickListener(this);
        pedirAuxilio.setOnClickListener(this);
        trocarCarro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == logOut){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, Login.class));
        }
        if(v == perfilUser){
            startActivity(new Intent(this, Perfil.class));
        }
        if(v == pedirAuxilio){
            startActivity(new Intent(this, Auxilio.class));
        }
        if(v == trocarCarro ){
            startActivity(new Intent(this, TrocarCarro.class));
        }
    }
}

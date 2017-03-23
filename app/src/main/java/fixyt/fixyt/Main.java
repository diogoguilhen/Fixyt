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
    private Button agendarServico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, PreLogin.class));
        }

        logOut = (Button) findViewById(R.id.botaoLogout);
        perfilUser = (Button) findViewById(R.id.botaoPerfil);
        pedirAuxilio = (Button) findViewById(R.id.botaoAuxilio);
        agendarServico = (Button) findViewById(R.id.botaoAgendar);

        logOut.setOnClickListener(this);
        perfilUser.setOnClickListener(this);
        pedirAuxilio.setOnClickListener(this);
        agendarServico.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == logOut){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, PreLogin.class));
        }
        if(v == perfilUser){
            finish();
            startActivity(new Intent(this, Perfil.class));
        }
        if(v == pedirAuxilio){
            startActivity(new Intent(this, Auxilio.class));
        }
        if(v == agendarServico){
            startActivity(new Intent(this, Schedule.class));
        }
    }
}

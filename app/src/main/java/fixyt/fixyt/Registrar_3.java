package fixyt.fixyt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Registrar_3 extends AppCompatActivity implements View.OnClickListener {

    private Button botaoProximo;
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
        botaoProximo = (Button) findViewById(R.id.botFinalizar);
        campoCPF = (EditText) findViewById(R.id.campoCpfCnpj);
        campoDataNascimento = (EditText) findViewById(R.id.campoDataNascimento);
        campoCidade = (EditText) findViewById(R.id.campoCidade);

        //Preparando os botões e menus para receber clicks
        botaoProximo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == botaoProximo){
            //Finalizar Cadastro, salvar no banco de dados associando o User aos dados e ir para Main

        }

    }


}

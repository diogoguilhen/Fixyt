package fixyt.fixyt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class Registrar_2 extends AppCompatActivity implements View.OnClickListener ,OnItemSelectedListener {

    private Button botaoProximo;
    private EditText campoDataNascimento;
    private EditText campoCPF;
    private EditText campoCidade;
    private Spinner menuEstado;
    private ProgressDialog dialogoProgresso;

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
        menuEstado.setOnItemSelectedListener((OnItemSelectedListener) Registrar_2.this);
        botaoProximo.setOnClickListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("SP");
        categories.add("RJ");
        categories.add("SC");
        categories.add("AL");
        categories.add("MG");
        categories.add("CE");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        menuEstado.setAdapter(dataAdapter);

    }

    //Spinner------------------------------------------------------------------
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    //Spinner--------------------------------------------------------------------

    @Override
    public void onClick(View v) {
        if (v == botaoProximo){
            //completar o cadastro.
            registrar2();
            dialogoProgresso.dismiss();
            startActivity(new Intent(getApplicationContext(), Registrar_3.class));
        }

    }

    private void registrar2() {
        String CPF = campoCPF.getText().toString().trim();
        String dataNasc = campoDataNascimento.getText().toString().trim();
        String UF = menuEstado.getSelectedItem().toString();
        String Cidade = campoCidade.getText().toString().trim();

        if(TextUtils.isEmpty(CPF)){
            //CPF vazio
            Toast.makeText(this, "Insira seu CPF!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        if(TextUtils.isEmpty(dataNasc)){
            //data de nascimento vazia
            Toast.makeText(this, "Insira sua data de nascimento!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        if(TextUtils.isEmpty(UF)){
            //UF vazia
            Toast.makeText(this, "Insira o estado!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        if(TextUtils.isEmpty(Cidade)){
            //Cidade vazia
            Toast.makeText(this, "Insira sua cidade!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        // Apos validar que os campos de cadastro2 estão OK um dialogo de progresso é mostrado
        dialogoProgresso.setMessage("Aguarde...");
        dialogoProgresso.show();
    }
}

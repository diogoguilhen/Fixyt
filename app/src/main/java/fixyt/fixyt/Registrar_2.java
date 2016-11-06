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

public class Registrar_2 extends AppCompatActivity implements View.OnClickListener{

    private Button botaoFinalizar;
    private EditText campoDataNascimento;
    private EditText campoCPF;
    private EditText campoCidade;
    private Spinner campoEstado;
    private ProgressDialog dialogoProgresso;

// SPINNER ----------------------------------
    // Spinner element
    Spinner UF = (Spinner) findViewById(R.id.UF);

    // Spinner click listener
    UF.setOnItemSelectedListener(this);

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
    UF.setAdapter(dataAdapter);

// SPINNER ----------------------------------
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

        botaoFinalizar = (Button) findViewById(R.id.botFinalizar);
        campoCPF = (EditText) findViewById(R.id.campoCPF);
        campoDataNascimento = (EditText) findViewById(R.id.campoDataNascimento);
        UF = (EditText) findViewById(R.id.campoUF);
        campoCidade = (EditText) findViewById(R.id.campoCidade);


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



        botaoFinalizar.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == botaoFinalizar){
            //logar
            registrar2();
        }

    }

    private void registrar2() {
        String CPF = campoCPF.getText().toString().trim();
        String dataNasc = campoDataNascimento.getText().toString().trim();
        String UF = UF.getText().toString().trim();
        String Cidade = campoCidade.getText().toString().trim();







        if(TextUtils.isEmpty(CPF)){
            //CPF vazio
            Toast.makeText(this, "Insira seu CPF!", Toast.LENGTH_SHORT).show();
            //parar a execu��o do c�digo
            return;
        }
        if(TextUtils.isEmpty(dataNasc)){
            //data de nascimento vazia
            Toast.makeText(this, "Insira sua data de nascimento!", Toast.LENGTH_SHORT).show();
            //parar a execu��o do c�digo
            return;
        }
        if(TextUtils.isEmpty(UF)){
            //UF vazia
            Toast.makeText(this, "Insira o estado!", Toast.LENGTH_SHORT).show();
            //parar a execu��o do c�digo
            return;
        }
        if(TextUtils.isEmpty(Cidade)){
            //cidade vazia
            Toast.makeText(this, "Insira sua cidade!", Toast.LENGTH_SHORT).show();
            //parar a execu��o do c�digo
            return;
        }
        // Ap�s validar que cadastro2 est� OK um dialogo de progresso � mostrada
        dialogoProgresso.setMessage("Registrando...");
        dialogoProgresso.show();

        // Chamando Signin
        /*firebasAuth.
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Se tarefa � completada
                        if(task.isSuccessful()){
                            //usuario logou corretamente
                            finish();
                            //inicializar tela principal
                            startActivity(new Intent(getApplicationContext(), Main.class));
                            //mostrar mensagem para usuario indicando sucesso
                            Toast.makeText(Registrar_2.this, "Logado com sucesso!", Toast.LENGTH_SHORT).show();
                            dialogoProgresso.dismiss();
                        }
                        else{
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthInvalidUserException e) {
                                Toast.makeText(Registrar_2.this, "O usuario digitado n�o existe ou foi bloqueado.", Toast.LENGTH_LONG).show();
                                dialogoProgresso.dismiss();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(Registrar_2.this, "Senha incorreta! Digite novamente.", Toast.LENGTH_LONG).show();
                                dialogoProgresso.dismiss();
                            } catch(Exception e) {
                                Log.e(TAG, e.getMessage());
                            }
                        }
                    }
                });*/
    }
}

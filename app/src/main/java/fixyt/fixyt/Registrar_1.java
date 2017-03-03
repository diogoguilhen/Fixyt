package fixyt.fixyt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;
import java.util.Objects;

public class Registrar_1 extends AppCompatActivity implements View.OnClickListener,DatabaseReference.CompletionListener{

    private Button botaoProximo1;
    private EditText nome;
    private EditText sobrenome;
    private EditText pais;
    private EditText telefone;
    private EditText email;
    private EditText confirmaSenha;
    private EditText digSenha;
    private ProgressDialog dialogoProgresso;
    private CadastroMotorista cadastroMotorista;

    private static final String TAG = "Registrar_1";
    private Spinner spinnerPais;
    private ArrayAdapter adaptador;

    // Declarar API Firabase Auth
    private FirebaseAuth firebasAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    // Banco de dados Firebase
    private Firebase mRef;

    //Emailbd
    private String emailBd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_1);

        //Chamando Firebase Auth
        firebasAuth = FirebaseAuth.getInstance();
        //Inicializando Base
        mRef = new Firebase("https://fixyt-20066.firebaseio.com/");

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                    if( firebaseUser == null || cadastroMotorista.getId() != null ){
                        return;
                    }

                    cadastroMotorista.setId( firebaseUser.getUid() );
                    cadastroMotorista.saveDB( Registrar_1.this );
                }
        };



        /*  Na verdade morreu essa chamada devido a verificação ser feita pelo firebase.    */
     //  //atribuindo email do banco ao emailBd.
     //  mRef.addValueEventListener(new ValueEventListener() {
     //      @Override
     //      public void onDataChange(DataSnapshot dataSnapshot) {
     //          Map<String, String> emailMap = dataSnapshot.getValue(Map.class);

     //          String emailBd = emailMap.get("email");


     //          Log.v("E_VALUE", "Email:" + emailMap);
     //      }

     //      @Override
     //      public void onCancelled(FirebaseError firebaseError) {

     //      }
     //  });


        dialogoProgresso = new ProgressDialog(this);

        //Implementação do Spinner de Pais
        spinnerPais = (Spinner) findViewById(R.id.spinnerPais);
        adaptador = ArrayAdapter.createFromResource(this, R.array.Paises, android.R.layout.simple_spinner_item);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPais.setAdapter(adaptador);

        botaoProximo1 = (Button) findViewById(R.id.botaoProximo1);
        nome = (EditText) findViewById(R.id.campoNome);
        sobrenome = (EditText) findViewById(R.id.campoSobrenome);
        telefone = (EditText) findViewById(R.id.campoTelefone);
        email = (EditText) findViewById(R.id.campoEmail);
        /*
        VERIFICAÇÃO DE EMAIL "ONLINE" DESATIVADA, PORQUE O FIREBASE IRA TRATAR ISSO QUANDO DER OK!
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    email.setError( "Digite um email!" );
                }
                if((email.getText().toString()).equals(emailBd)){
                    email.setError( "Email Já existe! Digite outro!");
                }

            }
        });*/

        //Apropriando os valores dos EditText para os STRINGS do objeto CADASTRO.

        confirmaSenha = (EditText) findViewById(R.id.confirmaSenha);
        digSenha = (EditText) findViewById(R.id.campoSenha);

        //Utilizando mascaras para os campos devidos
        MaskEditTextChangedListener maskTEL = new MaskEditTextChangedListener("(##)#####-####", telefone);
        telefone.addTextChangedListener(maskTEL);

        botaoProximo1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == botaoProximo1){
            //completa o primeiro passo do cadastro
            registrar1();
        }
    }

    private void registrar1() {
        cadastroMotorista = new CadastroMotorista();
        cadastroMotorista.setNome(nome.getText().toString().trim());
        cadastroMotorista.setSobrenome(sobrenome.getText().toString().trim());
        cadastroMotorista.setPais(spinnerPais.getSelectedItem().toString().trim());
        cadastroMotorista.setTelefone(telefone.getText().toString().trim());
        cadastroMotorista.setEmail(email.getText().toString().trim());
        cadastroMotorista.setSenha(digSenha.getText().toString().trim());

        final String email = cadastroMotorista.getEmail().trim();
        String senha = cadastroMotorista.getSenha().trim();
        String nome = cadastroMotorista.getNome().trim();
        String sobrenome = cadastroMotorista.getSobrenome().trim();
        String telefone = cadastroMotorista.getTelefone().trim();
        String ConfSen = confirmaSenha.getText().toString().trim();
        String tSenha = digSenha.getText().toString().trim();

        if (TextUtils.isEmpty(nome)) {
            //email vazio
            Toast.makeText(this, "Ingresse um nome!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        if (TextUtils.isEmpty(sobrenome)) {
            //email vazio
            Toast.makeText(this, "Ingresse um sobrenome!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        if (TextUtils.isEmpty(telefone)) {
            //email vazio
            Toast.makeText(this, "Ingresse um telefone!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        if (TextUtils.isEmpty(email)) {
            //email vazio
            Toast.makeText(this, "Ingresse um Email Válido!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        if (TextUtils.isEmpty(senha)) {
            //senha vazia
            Toast.makeText(this, "Ingresse uma Senha!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        if (TextUtils.isEmpty(senha)) {
            //senha vazia
            Toast.makeText(this, "Ingresse uma Senha!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        if (!ConfSen.equals(tSenha)) {
            //senha não está igual em ambos os campos
            Toast.makeText(this, "Verifique a confirmação de senha novamente!", Toast.LENGTH_SHORT).show();
            //parar a execução do código
            return;
        }
        // Após validar que cadastro está OK um dialogo de progresso é mostrada

        dialogoProgresso.setMessage("Aguarde...");
        dialogoProgresso.show();


        firebasAuth.createUserWithEmailAndPassword(cadastroMotorista.getEmail(), cadastroMotorista.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Se tarefa é completada
                        if (task.isSuccessful()) {
                            cadastroMotorista.setId(mRef.getKey().toString());
                            cadastroMotorista.saveDB();
                            mRef.setValue("email", mRef.getKey() );
                            //usuario registrou corretamente
                            finish();
                            //inicializar cadastro de perfil
                            startActivity(new Intent(getApplicationContext(), Main.class));
                            //mostrar mensagem para usuario indicando sucesso
                            Toast.makeText(Registrar_1.this, "Registrado com Sucesso.", Toast.LENGTH_SHORT).show();
                            dialogoProgresso.dismiss();
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(Registrar_1.this, "A senha utilizada deve ter no mínimo 6 caracteres.", Toast.LENGTH_LONG).show();
                                dialogoProgresso.dismiss();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(Registrar_1.this, "As credenciais utilizadas expiraram. Contate o administrador", Toast.LENGTH_LONG).show();
                                dialogoProgresso.dismiss();
                            } catch (FirebaseAuthUserCollisionException e) {
                                Toast.makeText(Registrar_1.this, "O usuário escolhido já está cadastrado. Escolha outro!", Toast.LENGTH_LONG).show();
                                dialogoProgresso.dismiss();
                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                            }
                        }
                    }
                });



    //    mRef.createUser(
    //            cadastroMotorista.getEmail(),
    //            cadastroMotorista.getSenha(),
    //            new Firebase.ValueResultHandler<Map<String, Object>>() {
    //                @Override
    //                public void onSuccess(Map<String, Object> stringObjectMap) {
    //                    cadastroMotorista.setId(stringObjectMap.get("uid").toString());
    //                    cadastroMotorista.saveDB();
    //                    mRef.unauth();
    //                    //mRef.getAuth();<-- ja tentei e não rolou
    //                    Toast.makeText(Registrar_1.this,"Conta criada com sucesso!", Toast.LENGTH_LONG).show();
//
    //                    finish();
    //                }
//
    //                @Override
    //                public void onError(FirebaseError firebaseError) {
    //                    Toast.makeText(Registrar_1.this,firebaseError.getMessage() + " ERROU FUCKER!!", Toast.LENGTH_LONG).show();
//
    //                    //ESTA CAINDO AQUI PRECISO VERIFICAR OQUE PODE SER
    //                }
    //            }
    //    );


        //Passando dados para a tela REGISTRAR 2
        Intent intentReg1 = new Intent(Registrar_1.this, Registrar_2.class);
        intentReg1.putExtra("cadastro", cadastroMotorista);
        startActivity(intentReg1);
        dialogoProgresso.dismiss();
    }
    @Override
    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

        Toast.makeText(Registrar_1.this, "Conta criada com sucesso!" , Toast.LENGTH_LONG).show();

        finish();
    }

}


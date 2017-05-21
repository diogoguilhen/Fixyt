package fixyt.fixyt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class Registrar_Termo extends AppCompatActivity implements View.OnClickListener{

    private Button botaoProximo;
    private CheckBox estouCiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_termo);

        botaoProximo = (Button) findViewById(R.id.botReg1);
        estouCiente = (CheckBox) findViewById(R.id.cienteMotorista);

        botaoProximo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == botaoProximo){
            //completa o primeiro passo do cadastro
            irRegistrar1();
        }
    }

    private void irRegistrar1() {
        if(estouCiente.isChecked()){
            Intent intentRegNext = new Intent(Registrar_Termo.this, Registrar_1.class);
            startActivity(intentRegNext);
        } else {
            Toast.makeText(Registrar_Termo.this, "Você precisa concordar com os termos de uso!!", Toast.LENGTH_LONG).show();
        }
    }

}


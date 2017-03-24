package fixyt.fixyt;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;

import java.util.Calendar;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

public class Schedule extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private FirebaseAuth firebaseAuth;
    private Button logOut;
    private Button finalizarAgendamento;
    private Button perfilUser;
    private Button escolherData;
    private Button escolherHora;
    private TextView eDate;
    private TextView eHour;
    private int ano, mes, dia, hora, minuto;
    private Spinner menuTipoAgendamento;
    private ArrayAdapter adaptadorTipoAgendamento;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, PreLogin.class));
        }

        logOut = (Button) findViewById(R.id.botaoLogout);
        perfilUser = (Button) findViewById(R.id.botaoPerfil);
        finalizarAgendamento = (Button) findViewById(R.id.botFinalizarAgendamento);

        /// maps
        //setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        /// fim maps


        logOut.setOnClickListener(this);
        perfilUser.setOnClickListener(this);
        finalizarAgendamento.setOnClickListener(this);

        //Botoes e campos Texto da data e hora de agendamento
        escolherData = (Button) findViewById(R.id.bData);
        escolherHora = (Button) findViewById(R.id.bHora);
        eDate = (TextView) findViewById(R.id.eData);
        eHour = (TextView) findViewById(R.id.eHora);

        escolherData.setOnClickListener(this);
        escolherHora.setOnClickListener(this);

        //Spinner do tipo de agendamento
        menuTipoAgendamento = (Spinner) findViewById(R.id.tipoAgendamento);
        adaptadorTipoAgendamento = ArrayAdapter.createFromResource(this, R.array.KitsAgendamento, android.R.layout.simple_spinner_item);
        adaptadorTipoAgendamento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menuTipoAgendamento.setAdapter(adaptadorTipoAgendamento);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if (v == logOut) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, PreLogin.class));
        }
        if (v == perfilUser) {
            finish();
            startActivity(new Intent(this, Perfil.class));
        }
        if (v == escolherData) {
            //Inicializar Calendario para escolha de dia,mes e ano
            final Calendar c = Calendar.getInstance();
            ano = c.get(Calendar.YEAR);
            mes = c.get(Calendar.MONTH);
            dia = c.get(Calendar.DAY_OF_MONTH);
            //Abrir caixa de dialogo para escolha do usuario
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    eDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                }
            }
                    , dia, mes, ano);
            datePickerDialog.show();

        }
        if (v == escolherHora) {
            //Inicializar Calendario para escolha de hora e minuto
            final Calendar c = Calendar.getInstance();
            hora = c.get(Calendar.HOUR_OF_DAY);
            minuto = c.get(Calendar.MINUTE);
            //Abrir caixa de dialogo para escolha do usuario
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    eHour.setText(hourOfDay + ":" + minute);
                }
            }, hora, minuto, false);
            timePickerDialog.show();
        }
        if (v == finalizarAgendamento) {
            //Finalizar o agendamento, enviar os dados para o FixyT e notificar usuário do agendamento

        }

    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap = googleMap;


        mMap.setMyLocationEnabled(true);

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}

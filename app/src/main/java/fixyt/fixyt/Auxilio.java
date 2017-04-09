package fixyt.fixyt;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class Auxilio extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView textCoords;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private GoogleMap gMap;
    private EditText textoEndereco;
    private EditText pontoReferencia;
    public String userKey;
    private Location localizacao;
    private Spinner spinnerReparo;
    private Spinner spinnerCarros;
    private String[] servicosArray;
    private String[] placaCarros;
    private Button solicitarAuxilio;
    private String servicoString;
    private String placaString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auxilio);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, PreLogin.class));
        }

        //Inicio Codigo
        textCoords = (TextView) findViewById(R.id.coordinates);
        textoEndereco = (EditText) findViewById(R.id.enderecoAtual);
        pontoReferencia = (EditText) findViewById(R.id.pontoRef);
        solicitarAuxilio = (Button) findViewById(R.id.botSolicitar);
        spinnerReparo = (Spinner) findViewById(R.id.spinnerServico);
        spinnerCarros = (Spinner) findViewById(R.id.spinnerVeiculo);

        solicitarAuxilio.setOnClickListener(this);

        //

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.gMapView);
        mapFragment.getMapAsync(Auxilio.this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        localizacao = locationManager.getLastKnownLocation("gps");




        // INICIO DE PEGAR OS SERVICOS DO BANCO
       FirebaseDatabase database = FirebaseDatabase.getInstance();
       DatabaseReference servicos = database.getReference();
        // QUERY para captar SPINNER de Serviços
       Query query1 = servicos.child("Servicos/Partner/Guincho");

       query1.addListenerForSingleValueEvent(new ValueEventListener() {

           public void onDataChange(DataSnapshot dataSnapshot) {
                //Passar os dados para a interface grafica
               servicoString = (String) dataSnapshot.getValue();
               servicoString = servicoString.replace("[","").replace("]","");
               servicosArray = servicoString.split(",");
               ArrayAdapter<String> adaptadorServico = new ArrayAdapter<String>(Auxilio.this, android.R.layout.simple_spinner_item, servicosArray);
               adaptadorServico.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               spinnerReparo.setAdapter(adaptadorServico);

           }
           public void onCancelled(DatabaseError databaseError) {
               //Se ocorrer um erro
               databaseError.getMessage();
           }

       });
        // QUERY para captar SPINNER de Carros PRECISA VER COMO CAPTURAR AS PLACAS DOS CARROS (Não sei como fazer o query por codigo de veiculo)
        Query query2 = servicos.child("Motorista/"+ firebaseAuth.getInstance().getCurrentUser().getUid()+"/Veiculos");


        //Aqui NAO TEM CARALHADEASA!!
        query2.addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                placaCarros = new String[]{""};
                placaString = "";
                int i = 0;
                for(DataSnapshot alert: dataSnapshot.getChildren()){
                    //System.out.println(alert.child("veiculoPlaca").getValue());
                    placaString = placaString.concat("," + alert.child("veiculoPlaca").getValue().toString());
                }
                placaString = placaString.substring(1);
                placaCarros = placaString.split(",");

                ArrayAdapter<String> adaptadorPlacas = new ArrayAdapter<String>(Auxilio.this, android.R.layout.simple_spinner_item, placaCarros);
                adaptadorPlacas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCarros.setAdapter(adaptadorPlacas);
                Toast.makeText(Auxilio.this, placaString, Toast.LENGTH_SHORT).show();
                //Passar os dados para a interface grafica
            }
            public void onCancelled(DatabaseError databaseError) {
                //Se ocorrer um erro
                databaseError.getMessage();
            }

        });
    //   /// FIM DO GET DO BANCO

        //Transformação da LatLang para Endereço e mostrar no TextView da tela.
        try{
            Geocoder geo = new Geocoder(Auxilio.this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(localizacao.getLatitude(), localizacao.getLongitude(), 1);
            if (addresses.isEmpty()) {
                textoEndereco.setText("Não encontramos sua localização!");
            }
            else {
                textoEndereco.setText(addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getLocality());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


                textCoords.append("\n " + location.getLatitude() + ", " + location.getLongitude());
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference localizacao = database.getReference("Localizacoes/Motoristas");

                userKey =  FirebaseAuth.getInstance().getCurrentUser().getUid();
                String key = userKey;

                String  vLatitude =    String.valueOf(location.getLatitude());
                String  vLongitude =   String.valueOf(location.getLongitude());

                CadastroMotorista diogoLindo = new CadastroMotorista(vLatitude,vLongitude, "null");


                localizacao.child(key).setValue(diogoLindo);

                //  FIM DE SALVAR A LOCALIZAÇÃO ATUAL NO BANCO

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }
            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(Auxilio.this, "Ative seu GPS para utilizar o serviço!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        // OUTRA COISA
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
                return;
            }
        }else{
            locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
        }

        //locationManager.requestLocationUpdates("gps", 0, 0, locationListener);




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
                return;
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng atual = new LatLng(localizacao.getLatitude(), localizacao.getLongitude());

        gMap.setMyLocationEnabled(true);
        //gMap.animateCamera(CameraUpdateFactory.newLatLng(atual));

      //  gMap.addMarker(new MarkerOptions().position(atual).title("Marker in Sydney"));

    }

    @Override
    public void onClick(View v) {
        if(v == solicitarAuxilio){
            //Execução do programa para achar o mecanico mais próximo e mostrar na tela.

        }
        /*if(v == teste){
            /*Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("google.navigation:q=R.Aimbere,668,SP"));
            //final String url = String.format("waze://?ll=-23.536052, -46.680724&navigate=yes");
            final String url = String.format("waze://?ll=" + localizacao.getLatitude()+", " + localizacao.getLongitude()+ "&navigate=yes");
            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }*/
    }
}




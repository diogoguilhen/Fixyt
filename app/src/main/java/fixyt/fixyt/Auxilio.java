package fixyt.fixyt;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
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
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class Auxilio extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private static String TAG;
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
    public  Double fromLatitude ;
    public  Double fromLongitude ;
    public  Double toLatitude       =    -23.0;
    public  Double toLongitude      =   -46.66;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auxilio);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, PreLogin.class));
        }
        //POLITICA DE FUNCIONAMENTO NÃO PODE TIRAR ESSA MERDA DE CODIGO DE MERDA FILHA DA PUTA
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
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
        Query query2 = servicos.child("Motorista/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/Veiculos");


        //Aqui NAO TEM CARALHADEASA!!
        query2.addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                placaCarros = new String[]{""};
                placaString = "";
                int i = 0;
                for(DataSnapshot alert: dataSnapshot.getChildren()){
                    //System.out.println(alert.child("veiculoPlaca").getValue());
                    placaString = placaString.concat("," + alert.child("veiculoPlaca").getValue().toString() + " - " + alert.child("veiculoMarca").getValue().toString() + " " + alert.child("veiculoModelo").getValue().toString());
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

                        fromLatitude = location.getLatitude();
                        fromLongitude = location.getLongitude();

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
          //  CalculadorETA diogoCuzudo= new CalculadorETA();
            //diogoCuzudo.obterETA(-23.62517109155844, -46.63068254729331, localizacao.getLatitude(), localizacao.getLongitude());



            try {
                Toast.makeText(Auxilio.this, RetornaJson() , Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }



        }

    }



    public String makeURL (double sourcelat, double sourcelog, double destlat, double destlog ){
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(String.valueOf((sourcelat)));
                urlString.append(",");
        urlString.append(String.valueOf( sourcelog));
        urlString.append("&destination=");// to
        urlString.append(String.valueOf( destlat));
        urlString.append(",");
        urlString.append(String.valueOf(destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("&key=AIzaSyDe-yV7hy223L4I7O8f2qprbpNQd9IBEqQ");
        return urlString.toString();
    }

    public String RetornaJson() throws IOException {
    String UrlBolada = makeURL( -23.625234244329558,-46.6763037, -23.5360516,-46.6807242);


    URL url = null;
            try {
        url = new URL(UrlBolada);
    } catch (MalformedURLException e) {
        e.printStackTrace();
    }

    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    connection.setRequestMethod("GET");

    InputStream is = connection.getInputStream();
    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
    String pirocaDeTempo;
        String porraToda ="";
    while ((pirocaDeTempo = rd.readLine()) != null) {
     // Toast.makeText(Auxilio.this, pirocaDeTempo , Toast.LENGTH_SHORT).show();
        porraToda =  porraToda + pirocaDeTempo;
       // System.out.println(pirocaDeTempo); so para imprimir no log
    }
    rd.close();

        // SERGIO AGORA TEM Q PEGAR A STRING CONVERTER EM JSON E LER DO JEITO Q ESTA SENDO FEITO AQUI EM BAIXO! E JA ERA MANO...

      // precisa importar esse fdp  JSONParser parser = new JSONParser();
       // JSONObject json = (JSONObject) parser.parse(stringToParse);

   //  final JSONObject json = new JSONObject(porraToda);
  //  JSONArray routeArray = json.getJSONArray("routes");
  //  JSONObject routes = routeArray.getJSONObject(0);
  //  JSONObject overviewPolylines = routes.getJSONObject("legs");
  //  JSONObject duration = overviewPolylines.getJSONObject("duration");

  //  String pirocaDeTempo = overviewPolylines.getString("value");

   return porraToda;

}

}




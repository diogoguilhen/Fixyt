package fixyt.fixyt;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class Auxilio extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{

    private static String TAG;
    private FirebaseAuth firebaseAuth;

    private LocationManager locationManager;
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
    public Double fromLatitude;
    public Double fromLongitude;
    private TextView partnerName, partnerETA;
    public String nomePartner;
    private ProgressDialog progresso;
    private String latMec = "";
    private String lngMec = "";
    private Marker mecanicoPosition;
    private int tempoAtualizado = 0;
    private PartnersProximos atendente;
    private int contadorLeituraMapa = 0;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    Context context;
    boolean GpsStatus ;




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
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            checkLocationPermission();
        }
        //Inicio Codigo

        textoEndereco = (EditText) findViewById(R.id.enderecoAtual);
        pontoReferencia = (EditText) findViewById(R.id.pontoRef);
        solicitarAuxilio = (Button) findViewById(R.id.botSolicitar);
        spinnerReparo = (Spinner) findViewById(R.id.spinnerServico);
        spinnerCarros = (Spinner) findViewById(R.id.spinnerVeiculo);
        partnerName = (TextView) findViewById(R.id.namePartner);
        partnerETA = (TextView) findViewById(R.id.tempoETA);


        solicitarAuxilio.setTag(0);
        solicitarAuxilio.setText("Solicitar Auxilio");
        solicitarAuxilio.setOnClickListener(this);
        //


        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.gMapView);
        supportMapFragment.getMapAsync(this);

        context = getApplicationContext();

        CheckGpsStatus();
        
        if(GpsStatus == true)
        {
            //
        }else {
            //
            Toast.makeText(Auxilio.this, "Ative seu GPS para utilizar o serviço!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);

        }


        /*locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        localizacao = locationManager.getLastKnownLocation("gps");*/


        // INICIO DE PEGAR OS SERVICOS DO BANCO
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference servicos = database.getReference();
        // QUERY para captar SPINNER de Serviços
        Query query1 = servicos.child("Servicos/Partner/Guincho");

        query1.addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                //Passar os dados para a interface grafica
                servicoString = (String) dataSnapshot.getValue();
                servicoString = servicoString.replace("[", "").replace("]", "");
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
        Query query2 = servicos.child("Motorista/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Veiculos");


        query2.addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                placaCarros = new String[]{""};
                placaString = "";

                for (DataSnapshot alert : dataSnapshot.getChildren()) {
                    //System.out.println(alert.child("veiculoPlaca").getValue());
                    placaString = placaString.concat("," + alert.child("veiculoPlaca").getValue().toString() + " - " + alert.child("veiculoMarca").getValue().toString() + " " + alert.child("veiculoModelo").getValue().toString());
                }
                placaString = placaString.substring(1);
                placaCarros = placaString.split(",");

                ArrayAdapter<String> adaptadorPlacas = new ArrayAdapter<String>(Auxilio.this, android.R.layout.simple_spinner_item, placaCarros);
                adaptadorPlacas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCarros.setAdapter(adaptadorPlacas);

                //Passar os dados para a interface grafica
            }

            public void onCancelled(DatabaseError databaseError) {
                //Se ocorrer um erro
                databaseError.getMessage();
            }

        });
        //   /// FIM DO GET DO BANCO

        //Transformação da LatLang para Endereço e mostrar no TextView da tela.



    }

    private void CheckGpsStatus() {
        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                gMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            gMap.setMyLocationEnabled(true);
        }

    }


    @Override
    public void onClick(View v) {
        if (v == solicitarAuxilio) {
            final int status = (int) v.getTag();
            if(status == 1){
                solicitarAuxilio.setText("Solicitar Auxilio");
                partnerName.setText("");
                partnerETA.setText("");
                gMap.clear();
                onMapReady(gMap);
                Toast.makeText(Auxilio.this, "Solicitação cancelada com Sucesso!", Toast.LENGTH_SHORT).show();
                v.setTag(0);
            } else{
                //Execução do programa para achar o mecanico mais próximo e mostrar na tela.
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                localizacao = locationManager.getLastKnownLocation("gps");

                progresso = new ProgressDialog(this);
                progresso.setMessage("Procurando o Mecanico mais próximo... Aguarde...");
                progresso.show();
                capturarPartners(localizacao);
                atualizarPosMecanico();

                v.setTag(1);
                solicitarAuxilio.setText("Cancelar Solicitação");

            }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void aguardarAceitarMecanico(PartnersProximos atendente) {
        //Inicio de Query para capturar os dados de localização do Partner em atendimento
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference emAtendimento = database.getReference();
        String codAt = (atendente.getCodigoPartner() + "AND" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        Query queryAtendimento = emAtendimento.child("EmAtendimento/" + codAt );

        queryAtendimento.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //Chamado foi aceito pelo motorista
                progresso.dismiss();
                Toast.makeText(Auxilio.this, "O Mecanico aceitou seu pedido!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                solicitarAuxilio.setText("Solicitar Auxilio");
                partnerName.setText("");
                partnerETA.setText("");
                gMap.clear();
                //onMapReady(gMap);
                Toast.makeText(Auxilio.this, "Solicitação cancelada ou não aceita pelo mecanico!", Toast.LENGTH_SHORT).show();
                progresso.dismiss();
                solicitarAuxilio.setTag(0);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void atualizarPosMecanico() {
        //Inicio de Query para capturar os dados de localização do Partner em atendimento
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference posicaoPartner = database.getReference();

        Query queryPosPartner = posicaoPartner.child("Localizacoes/Partner");

        queryPosPartner.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //Atualizar posição do mecanico no mapa.

                latMec = dataSnapshot.child("vLatitude").getValue().toString();
                lngMec = dataSnapshot.child("vLongitude").getValue().toString();
                LatLng posMec = new LatLng(Double.parseDouble(latMec), Double.parseDouble(lngMec));
                mecanicoPosition.setPosition(posMec);
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(posMec)      // Sets the center of the map to Mountain View
                        .zoom(14)                   // Sets the zoom
                        .tilt(45)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                try {
                    tempoAtualizado = RetornaTempoJson(localizacao.getLatitude(), localizacao.getLongitude(), latMec, lngMec);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                partnerETA.setText("Tempo estimado de chegada é: " + (tempoAtualizado/60) + " Minutos.");

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void capturarPartners(final Location location) {
        //Inicio de Query para capturar os dados do banco de partners
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference partners = database.getReference();

        Query queryPartners = partners.child("Localizacoes/Partner");

        queryPartners.addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                //Passar os dados para o objeto

                ArrayList<PartnersProximos> listagemPartnersProximos = new ArrayList<>();
                PartnersProximos partner = new PartnersProximos();
                for (DataSnapshot alert : dataSnapshot.getChildren()) {
                    partner.setLatitudePartner(alert.child("vLatitude").getValue().toString());
                    partner.setLongitudePartner(alert.child("vLongitude").getValue().toString());
                    System.out.println("key: " + alert.getKey().toString());
                    partner.setStatusPartner(alert.child("vOnline").getValue().toString());
                    partner.setCodigoPartner(alert.getKey().toString());
                    partner.setServicoPartner(alert.child("vServico").getValue().toString());
                    partner.setEmAtendimento(alert.child("vEmAtendimento").getValue().toString());
                    try {
                        partner.setTempoAteMotorista(RetornaTempoJson(mLastLocation.getLatitude(), mLastLocation.getLongitude(), partner.getLatitudePartner(), partner.getLongitudePartner()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if(Integer.parseInt(partner.getStatusPartner()) == 1 && partner.getTempoAteMotorista() != 0 && partner.getServicoPartner().contains(spinnerReparo.getSelectedItem().toString()) && Integer.parseInt(partner.getEmAtendimento()) != 1 ){
                        PartnersProximos partnerfinal = new PartnersProximos(partner.getCodigoPartner(), partner.getStatusPartner(), partner.getLatitudePartner(), partner.getLongitudePartner(), partner.getTempoAteMotorista(), partner.getEmAtendimento());
                        listagemPartnersProximos.add(partnerfinal);
                    }


                }
                int indicePartnerMenorTempo = RetornaIndicePartnerMenorTempo(listagemPartnersProximos);
                final PartnersProximos atendente = new PartnersProximos();
                atendente.setCodigoPartner(listagemPartnersProximos.get(indicePartnerMenorTempo).getCodigoPartner());
                atendente.setTempoAteMotorista(listagemPartnersProximos.get(indicePartnerMenorTempo).getTempoAteMotorista());
                atendente.setLatitudePartner(listagemPartnersProximos.get(indicePartnerMenorTempo).getLatitudePartner());
                atendente.setLongitudePartner(listagemPartnersProximos.get(indicePartnerMenorTempo).getLongitudePartner());
                atendente.setEmAtendimento(listagemPartnersProximos.get(indicePartnerMenorTempo).getEmAtendimento());
                final int minutagem = (atendente.getTempoAteMotorista()/60);

                FirebaseDatabase databaseName = FirebaseDatabase.getInstance();
                DatabaseReference partnerNomePlaca = databaseName.getReference();

                String codAt = (atendente.getCodigoPartner() + "AND" + FirebaseAuth.getInstance().getCurrentUser().getUid());
                DatabaseReference noAtendimento = databaseName.getReference("EmAtendimento/");
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy H:mm:ss");
                aguardarAceitarMecanico(atendente);

                Atendimento atendimento = new Atendimento(String.valueOf(mdformat.format(calendar.getTime())), "-1", String.valueOf(mLastLocation.getLatitude()), String.valueOf(mLastLocation.getLongitude()), String.valueOf(minutagem), pontoReferencia.getText().toString()) ;

                noAtendimento.child(codAt).setValue(atendimento);

                Query queryNamePartnerFinal = partnerNomePlaca.child("Partner/" + atendente.getCodigoPartner());

                queryNamePartnerFinal.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        double lat = Double.parseDouble(atendente.getLatitudePartner());
                        double lng = Double.parseDouble(atendente.getLongitudePartner());
                        LatLng posicaoPartner = new LatLng(lat, lng);
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(posicaoPartner)      // Sets the center of the map to Mountain View
                                .zoom(14)                   // Sets the zoom
                                .tilt(45)                   // Sets the tilt of the camera to 30 degrees
                                .build();                   // Creates a CameraPosition from the builder
                        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        mecanicoPosition = gMap.addMarker(new MarkerOptions().position(posicaoPartner).title("Seu Mecanico"));
                        //Verificar bounds entre motorista e mecanico para fazer zoom na camera.
                        /*
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        for (Marker marker : markers) {
                            builder.include(marker.getPosition());
                        }
                        LatLngBounds bounds = builder.build();*/

                        nomePartner = (dataSnapshot.child("nome").getValue() + " " + dataSnapshot.child("sobrenome").getValue());
                        partnerName.setText("O nome do mecanico é : " + nomePartner);
                        partnerETA.setText("Tempo estimado de chegada é: " + minutagem + " Minutos.");
                        progresso.dismiss();
                        Toast.makeText(Auxilio.this, "Encontramos o seu mecanico!", Toast.LENGTH_SHORT).show();

                        progresso.setMessage("Aguardando Aceitação do Mecanico...");
                        progresso.show();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



                //Toast.makeText(Auxilio.this, String.valueOf(location.getLatitude()) + String.valueOf(location.getLongitude()), Toast.LENGTH_SHORT).show();

            }

            public void onCancelled(DatabaseError databaseError) {
                //Se ocorrer um erro
                databaseError.getMessage();
            }

        });

    }

    public String makeURL(double sourcelat, double sourcelog, String destlat, String destlog) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// Localização Origem
        urlString.append(String.valueOf(sourcelat));
        urlString.append(",");
        urlString.append(String.valueOf(sourcelog));
        urlString.append("&destination=");// Localização Destino
        urlString.append(destlat);
        urlString.append(",");
        urlString.append(destlog);
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("&key=AIzaSyDe-yV7hy223L4I7O8f2qprbpNQd9IBEqQ");
        return urlString.toString();
    }

    public int RetornaTempoJson(double origemLat, double origemLong, String destLat, String destLong) throws IOException, JSONException {
        String UrlFinal = makeURL(origemLat, origemLong, destLat, destLong);


        URL url = null;
        try {
            url = new URL(UrlFinal);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        // String temporario onde guardar os dados que vem do URL.
        String directionMaps;
        String objetoMaps = "";
        while ((directionMaps = rd.readLine()) != null) {

            objetoMaps = objetoMaps + directionMaps;

        }
        rd.close();

        //Parse (leitura) do JSON (que vem do Directions API)

        JSONObject raiz = new JSONObject(objetoMaps);
        JSONArray routeArray = raiz.getJSONArray("routes");
        JSONObject routes = routeArray.getJSONObject(0);
        JSONArray overPernas = routes.getJSONArray("legs");
        JSONObject perna = overPernas.getJSONObject(0);
        JSONObject duracao = perna.getJSONObject("duration");
        int tempoDeViagem = duracao.getInt("value");

        return tempoDeViagem;
    }
    public int RetornaIndicePartnerMenorTempo(ArrayList<PartnersProximos> lista){
        int minTempo = lista.get(0).getTempoAteMotorista();
        int i = 0;
        int indice = 0;
        for(i=0; i < lista.size();i++){
            if(lista.get(i).getTempoAteMotorista() < minTempo && lista.get(i).getTempoAteMotorista() != 0){
                minTempo = lista.get(i).getTempoAteMotorista();
                indice = i;
            }
        }
        return indice;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        gMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            //You can add here other case statements according to your requirement.
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        //stop location updates


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference localizacao = database.getReference("Localizacoes/Motoristas");

        userKey = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String key = userKey;

        String vLatitude = String.valueOf(location.getLatitude());
        String vLongitude = String.valueOf(location.getLongitude());

        fromLatitude = location.getLatitude();
        fromLongitude = location.getLongitude();

        LatLng latLng = new LatLng(fromLatitude, fromLongitude);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)      // Sets the center of the map to Mountain View
                .zoom(14)                   // Sets the zoom
                .tilt(45)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        if(contadorLeituraMapa == 0) {

            try {
                Geocoder geo = new Geocoder(Auxilio.this.getApplicationContext(), Locale.getDefault());
                List<Address> addresses = geo.getFromLocation(fromLatitude, fromLongitude, 1);
                if (addresses.isEmpty()) {
                    textoEndereco.setText("Não encontramos sua localização!");
                } else {
                    textoEndereco.setText(addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getLocality());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            contadorLeituraMapa++;
        }



        CadastroMotorista diogoLindo = new CadastroMotorista(vLatitude, vLongitude, pontoReferencia.getText().toString());


        localizacao.child(key).setValue(diogoLindo);

        /*if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }*/

        //  FIM DE SALVAR A LOCALIZAÇÃO ATUAL NO BANCO

    }


    /*@Override
    public void onProviderDisabled(String provider) {

    }*/
}








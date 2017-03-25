package fixyt.fixyt;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Auxilio extends FragmentActivity implements OnMapReadyCallback {

    private FirebaseAuth firebaseAuth;
    private TextView textCoords;
    private Button botao;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private GoogleMap gMap;
    public String userKey;
    String  vLatitude;
    String  vLongitude;


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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.gMapView);
        mapFragment.getMapAsync(Auxilio.this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                textCoords.append("\n " + location.getLatitude() + ", " + location.getLongitude());
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference localizacao = database.getReference("Localizacoes/Motoristas");

                userKey =  FirebaseAuth.getInstance().getCurrentUser().getUid();
                String key = userKey;

                vLatitude =    String.valueOf(location.getLatitude());
                vLongitude =   String.valueOf(location.getLongitude());

                CadastroMotorista diogoLindo = new CadastroMotorista(vLatitude,vLongitude);


                localizacao.child(key).setValue(diogoLindo);

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

        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);

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
    //     LatLng atual = new LatLng(Double.parseDouble(vLatitude), Double.parseDouble(vLongitude));

        gMap.setMyLocationEnabled(true);
        gMap.getCameraPosition();


     //   gMap.addMarker(new MarkerOptions().position(atual).title("Marker in Home"));
       // gMap.moveCamera(CameraUpdateFactory.newLatLng(atual));
    }
}



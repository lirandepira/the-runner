package enseirb.fr.therunner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

import java.util.Date;

public class Running extends AppCompatActivity {

    private RunsHandler runsHandler;
    private UsersHandler usersHandler;
    private Chronometer chronometer;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double currentLongitude;
    private double currentLatitude;
    private double distance = 0D;
    private final double radians = Math.PI / 180D;
    private boolean firstCoordinates = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        usersHandler = new UsersHandler(this);
        runsHandler = new RunsHandler(this);
        Toast.makeText(getBaseContext(), "User " + this.getIntent().getExtras().getString("username") + " is connected", Toast.LENGTH_SHORT).show();
        chronometer = (Chronometer) findViewById(R.id.chrono);
        chronometer.start();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(firstCoordinates){
                    firstCoordinates = false;
                }
                else {
                    double dlong = (location.getLongitude() - currentLongitude) * radians;
                    double dlat = (location.getLatitude() - currentLatitude) * radians;
                    double a = Math.pow(Math.sin(dlat / 2D), 2D) + Math.cos(currentLatitude * radians)
                            * Math.cos(location.getLatitude() * radians) * Math.pow(Math.sin(dlong / 2D), 2D);
                    distance += 1000D * 63780.1370D * 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1D - a));
                    currentLatitude = location.getLatitude();
                    currentLongitude = location.getLongitude();
                }
                //Toast.makeText(getBaseContext(), currentLatitude + " : " +currentLongitude, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
                }, 10);

                return;
            }
        } else {
            configureLocation();
        }
    }

    private void configureLocation() {
        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);

    }

    @Override
    public void onRequestPermissionsResult (int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureLocation();
                    return;
        }
    }

    public void finish (View view){
        chronometer.stop();
        long elapsed = SystemClock.elapsedRealtime() - chronometer.getBase();
        //int distance = 12;//fixme find how to calculate me
        long chrono = elapsed/1000;
        String username = this.getIntent().getExtras().getString("username");
        usersHandler.open();
        runsHandler.open();
        RunController runner = new RunController(elapsed, distance, usersHandler.getUserByName(username).getId());
        runsHandler.insertRun(runner);
        runsHandler.close();
        usersHandler.close();
        launchSummary(view, username, distance, chrono);
    }

    public void launchSummary(View view, String username, double distance, long chrono){
        Intent intent = new Intent(this, Summary.class);
        intent.putExtra("username", username);
        intent.putExtra("distance", distance);
        intent.putExtra("chrono", chrono);
        startActivity(intent);
    }

    }
package enseirb.fr.therunner;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import enseirb.fr.therunner.model.Coordinates;

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
    private Coordinates coordinates = new Coordinates();
    private double maxSpeed = 0D;
    private long timeElapsed = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        usersHandler = new UsersHandler(this);
        runsHandler = new RunsHandler(this);
        Toast.makeText(getBaseContext(), "User " + this.getIntent().getExtras().getString("username") + " is connected", Toast.LENGTH_SHORT).show();
        chronometer = (Chronometer) findViewById(R.id.chrono);
        chronometer.start();
        TextView distanceDisplay = findViewById(R.id.kilometers);
        distanceDisplay.setText(Double.toString(distance));

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //time Elapsed between two coordinates
                long lastElapsed = SystemClock.elapsedRealtime() - chronometer.getBase() - timeElapsed;
                //update total elapsed time.
                timeElapsed = SystemClock.elapsedRealtime() - chronometer.getBase();
                if(firstCoordinates){
                    firstCoordinates = false;
                }
                else {
                    double dlong = (location.getLongitude() - currentLongitude) * radians;
                    double dlat = (location.getLatitude() - currentLatitude) * radians;
                    double a = Math.pow(Math.sin(dlat / 2D), 2D) + Math.cos(currentLatitude * radians)
                            * Math.cos(location.getLatitude() * radians) * Math.pow(Math.sin(dlong / 2D), 2D);
                    double runDistance = 63780.1370D * 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1D - a));
                    double instantSpeed = runDistance / lastElapsed * 3.6;//km/h
                    if(instantSpeed > maxSpeed){
                        maxSpeed = instantSpeed;
                    }
                    distance += runDistance;
                }
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();
                coordinates.addCoordinate(currentLatitude, currentLongitude);
                TextView distanceDisplay = findViewById(R.id.kilometers);
                DecimalFormat df = new DecimalFormat("#.##");
                df.setRoundingMode(RoundingMode.CEILING);
                distanceDisplay.setText(df.format(distance));
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

        configureLocation();

    }

    private void configureLocation() {
        locationManager.requestLocationUpdates("gps", 0, 100, locationListener);

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
        Bundle bundleCoordinates = new Bundle();
        bundleCoordinates.putSerializable("coordinates", coordinates);
        intent.putExtras(bundleCoordinates);
        startActivity(intent);
    }

    }
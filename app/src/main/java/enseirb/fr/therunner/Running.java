package enseirb.fr.therunner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import enseirb.fr.therunner.db.RunController;
import enseirb.fr.therunner.db.RunsHandler;
import enseirb.fr.therunner.db.UsersHandler;
import enseirb.fr.therunner.model.Coordinates;
import enseirb.fr.therunner.util.FormatUtil;

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
    // initial max speed is 0
    private String maxSpeedString = FormatUtil.formatSpeed(0.0, 1);
    private long timeElapsed = 0L;

    // used for instant speed
    private long lastTimeElapsed = 0L;
    private double lastRunDistance = 0L;

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
                double runDistance = 0D;
                //time Elapsed between two coordinates
                long lastElapsed = SystemClock.elapsedRealtime() - chronometer.getBase() - timeElapsed;
                //update total elapsed time.
                timeElapsed = SystemClock.elapsedRealtime() - chronometer.getBase();
                if (firstCoordinates) {
                    firstCoordinates = false;
                } else {
                    double dlong = (location.getLongitude() - currentLongitude) * radians;
                    double dlat = (location.getLatitude() - currentLatitude) * radians;
                    double a = Math.pow(Math.sin(dlat / 2D), 2D) + Math.cos(currentLatitude * radians)
                            * Math.cos(location.getLatitude() * radians) * Math.pow(Math.sin(dlong / 2D), 2D);
                    runDistance = 63780.1370D * 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1D - a));

                    // compute speed since the last point taken by GPS
                    double instantDistance = runDistance - lastRunDistance;
                    long instantTimeElapsed = timeElapsed - lastTimeElapsed;

                    double instantSpeed = instantDistance / instantTimeElapsed;

                    if (instantSpeed > maxSpeed) {
                        maxSpeed = instantSpeed;
                        maxSpeedString = FormatUtil.formatSpeed(instantDistance, instantTimeElapsed);
                    }
                    distance += runDistance;
                }
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();
                coordinates.addCoordinate(currentLatitude, currentLongitude);
                TextView distanceDisplay = findViewById(R.id.kilometers);
                DecimalFormat df = new DecimalFormat("#.##");
                df.setRoundingMode(RoundingMode.CEILING);
                distanceDisplay.setText(FormatUtil.formatDistance(distance));

                // save last time elapsed and last distance so that we can recompute instant speed
                lastTimeElapsed = timeElapsed;
                lastRunDistance = runDistance;
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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        locationManager.requestLocationUpdates("gps", 0, 0, locationListener);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureLocation();
                return;
        }
    }

    public void finish(View view) {
        chronometer.stop();
        long elapsed = SystemClock.elapsedRealtime() - chronometer.getBase();
        String username = this.getIntent().getExtras().getString("username");
        usersHandler.open();
        runsHandler.open();
        RunController runner = new RunController(elapsed, distance, usersHandler.getUserByName(username).getId());
        runsHandler.insertRun(runner);
        runsHandler.close();
        usersHandler.close();
        launchSummary(view, username, distance, elapsed);
    }

    public void launchSummary(View view, String username, double distance, long chrono) {
        Intent intent = new Intent(this, Summary.class);
        intent.putExtra("username", username);
        intent.putExtra("distance", distance);
        intent.putExtra("chrono", chrono);
        intent.putExtra("maxspeed", maxSpeedString);
        Bundle bundleCoordinates = new Bundle();
        bundleCoordinates.putSerializable("coordinates", coordinates);
        intent.putExtras(bundleCoordinates);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        Toast.makeText(getBaseContext(), "You can't go back. Click on Finish if you want to finish the run", Toast.LENGTH_SHORT).show();
    }

}
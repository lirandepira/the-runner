package enseirb.fr.therunner;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Main extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

    }

    public void logout(View view){
        launchLogin(view);
    }

    public void launchLogin(View view){
        startActivity(new Intent(this, Login.class));
    }

    public void start(View view){
        launchRunning(view);
    }

    public void launchRunning(View view){
        Intent receivedIntent = this.getIntent();
        Intent deliveredIntent = new Intent(this, Running.class);
        deliveredIntent.putExtra("username", receivedIntent.getExtras().getString("username"));
        startActivity(deliveredIntent);
    }

    public void previousRuns(View view){
        launchPrevious(view);
    }

    public void launchPrevious(View view){
        startActivity(new Intent(this, PreviousRuns.class));
    }
}

package enseirb.fr.therunner;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Summary extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private MapView         mMapView;
    private MapController   mMapController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
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

        double distance = this.getIntent().getExtras().getDouble("distance");
        long chrono = this.getIntent().getExtras().getLong("chrono");
        long minutes = chrono / 60;
        long seconds = chrono - 60 * minutes;
        String displayChrono = minutes + " : " + seconds;
        TextView chronoView = findViewById(R.id.chronoresult);
        chronoView.setText(displayChrono);
        TextView distanceView = findViewById(R.id.distance);
        //formatting distance display
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        //end of formatting
        distanceView.setText(df.format(distance));
        TextView avgSpeedView = findViewById(R.id.avgspeed);
        avgSpeedView.setText(Double.toString(averageSpeed(distance, chrono)));

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        mMapView = findViewById(R.id.mapview2);
        mMapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mMapView.setBuiltInZoomControls(true);
        mMapController = (MapController) mMapView.getController();
        mMapController.setZoom(13);
        GeoPoint gPt = new GeoPoint(44.836151, -0.580816);
        mMapController.setCenter(gPt);
    }

    private double averageSpeed(double distance, long chrono){
        return distance / chrono;
    }

    public void goToHomepage (View view){

        launchMain(view);
    }

    public void launchMain(View view){
        Intent intent = new Intent(this, Main.class);
        intent.putExtra("username", this.getIntent().getExtras().getString("username"));
        startActivity(intent);
    }
    }
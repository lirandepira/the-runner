package enseirb.fr.therunner;

import android.content.Context;
import android.content.Intent;
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

public class Summary extends AppCompatActivity {

    private MapView         mMapView;
    private MapController   mMapController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        int distance = this.getIntent().getExtras().getInt("distance");
        long chrono = this.getIntent().getExtras().getLong("chrono");
        long minutes = chrono / 60;
        long seconds = chrono - 60 * minutes;
        String displayChrono = minutes + " : " + seconds;
        TextView chronoView = findViewById(R.id.chronoresult);
        chronoView.setText(displayChrono);
        TextView distanceView = findViewById(R.id.distance);
        distanceView.setText(Integer.toString(distance));
        TextView avgSpeedView = findViewById(R.id.avgspeed);
        avgSpeedView.setText(Long.toString(averageSpeed(distance, chrono)));

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        mMapView = (MapView) findViewById(R.id.mapview);
        mMapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mMapView.setBuiltInZoomControls(true);
        mMapController = (MapController) mMapView.getController();
        mMapController.setZoom(13);
        GeoPoint gPt = new GeoPoint(51500000, -150000);
        mMapController.setCenter(gPt);
    }

    public long averageSpeed(int distance, long chrono){
        return distance * 60 / chrono;
    }

    public void goToHomepage (View view){
        launchMain(view);
    }

    public void launchMain(View view){
        startActivity(new Intent(this, Main.class));
    }
    }
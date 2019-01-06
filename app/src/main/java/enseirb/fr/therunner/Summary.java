package enseirb.fr.therunner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.views.overlay.PathOverlay;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.overlay.Polyline;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import enseirb.fr.therunner.model.Coordinate;
import enseirb.fr.therunner.model.Coordinates;
import enseirb.fr.therunner.util.FiguresUtil;

public class Summary extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private MapView         mMapView;
    private MapController   mMapController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        double distance = this.getIntent().getExtras().getDouble("distance");
        long chrono = this.getIntent().getExtras().getLong("chrono");
        TextView chronoView = findViewById(R.id.chronoresult);
        chronoView.setText(FiguresUtil.formatTime(chrono));
        TextView distanceView = findViewById(R.id.distance);

        //end of formatting
        distanceView.setText(FiguresUtil.formatDistance(distance));
        TextView avgSpeedView = findViewById(R.id.avgspeed);
        avgSpeedView.setText(FiguresUtil.formatAverageSpeed(distance, chrono));

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        mMapView = findViewById(R.id.mapview2);
        mMapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mMapView.setBuiltInZoomControls(true);
        mMapController = (MapController) mMapView.getController();

        // deal with coordinates
        Polyline line = new Polyline();
        line.setWidth(20f);
        line.setColor(Color.RED);
        List<GeoPoint> pts = new ArrayList<>();


        Coordinates coordinates = (Coordinates) this.getIntent().getExtras().getSerializable("coordinates");
        GeoPoint currentPoint = null;
        for (Coordinate coordinate : coordinates.getCoordinates()) {
            currentPoint = new GeoPoint(coordinate.getLatitude(), coordinate.getLongitude());
            pts.add(currentPoint);
        }
        line.setPoints(pts);
        line.setGeodesic(true);
        mMapView.getOverlayManager().add(line);
        mMapController.setZoom(13);
        mMapController.setCenter(currentPoint);

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
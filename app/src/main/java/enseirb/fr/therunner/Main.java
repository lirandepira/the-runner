package enseirb.fr.therunner;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.OverlayManager;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.LinkedList;

public class Main extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private MapView         mMapView;
    private MapController   mMapController;
    // private DefaultResourceProxyImpl resourceProxyImp = new DefaultResourceProxyImpl;

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

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        mMapView = findViewById(R.id.mapview);
        mMapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mMapView.setBuiltInZoomControls(true);
        mMapController = (MapController) mMapView.getController();
        mMapController.setZoom(13);
        // GeoPoint gPt = new GeoPoint(44.836151, -0.580816);
        // mMapController.setCenter(gPt);

        MyLocationNewOverlay myLocationoverlay = new MyLocationNewOverlay(mMapView);
        myLocationoverlay.enableFollowLocation();
        myLocationoverlay.enableMyLocation();
        mMapView.getOverlays().add(myLocationoverlay);

        /*ItemizedIconOverlay itemizedIconOverlay = new ItemizedIconOverlay<OverlayItem> (new LinkedList<OverlayItem>(),
                myMarker, null, resProxyImp);
        mMapView.getOverlays().add(markersOverlay);

        OverlayItem ovm = new OverlayItem("title", "description", new GeoPoint(s.LatitudeE6(), s.LongitudeE6()));
        ovm.setMarker(myMarker);
        markersOverlay.addItem(ovm);*/

    }

    public void onResume(){
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        mMapView.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void onPause(){
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        mMapView.onPause();  //needed for compass, my location overlays, v6.0.0 and up
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

        Intent intent = (new Intent(this, PreviousRuns.class));
        intent.putExtra("username", this.getIntent().getExtras().getString("username"));
        startActivity(intent);
    }
}

package edu.bridgewatercs.jscott.eagleassistant;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.util.ArrayList;

public class Maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        Location Bridgewater_College = new Location("Bridgewater College");
        Bridgewater_College.setLatitude(38.378849);
        Bridgewater_College.setLongitude(-78.969369);
        LatLng bridgewaterCollegeLatLng = new LatLng(Bridgewater_College.getLatitude(), Bridgewater_College.getLongitude());

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        boolean showAcademicBuildingMapMarkers = settings.getBoolean("appSettingsScreenSwitchShowAcademicBuildingMapMarkers", true);
        boolean showResidentHallMapMarkers = settings.getBoolean("appSettingsScreenSwitchShowResidentHallMapMarkers", true);
        boolean showOtherBuildingMapMarkers = settings.getBoolean("appSettingsScreenSwitchShowOtherBuildingMapMarkers", true);

        //makes a usable database and array list
        Log.d("Creation", "Creating a database and array list...");
        DBHandler db = new DBHandler(this);
        ArrayList<MapMarker> mapMarkers = db.getAllMapMarkers();
        Log.d("ArrayList mapMarkers", "Length = " + mapMarkers.size());

        //inserts map markers into database
        if(mapMarkers.size() < 18) {
            Log.d("Inserting", "Inserting all map markers...");
            db.addMapMarker(new MapMarker(1, 38.380626 , -78.968113, "McKinney Hall"));
            db.addMapMarker(new MapMarker(2, 38.379658 , -78.969881, "Bowman Hall"));
            db.addMapMarker(new MapMarker(3, 38.378244 , -78.971603, "Flory Hall"));
            db.addMapMarker(new MapMarker(4, 38.377463 , -78.970080, "Nininger Hall"));
            db.addMapMarker(new MapMarker(5, 38.378281, -78.968668, "Moomaw Hall"));
            db.addMapMarker(new MapMarker(6, 38.378066, -78.971262, "Memorial Hall"));

            db.addMapMarker(new MapMarker(101, 38.378496, -78.967549, "Dillon Hall"));
            db.addMapMarker(new MapMarker(102, 38.379006, -78.968334, "Daleville Hall"));
            db.addMapMarker(new MapMarker(103, 38.379126, -78.969039, "Blue Ridge Hall"));
            db.addMapMarker(new MapMarker(104, 38.380237, -78.970261, "Heritage Hall"));
            db.addMapMarker(new MapMarker(105, 38.379842, -78.970677, "Wright Hall"));
            db.addMapMarker(new MapMarker(106, 38.380719, -78.969649, "Geisert Hall"));
            db.addMapMarker(new MapMarker(107, 38.379304, -78.966832, "Wakeman Hall"));

            db.addMapMarker(new MapMarker(201, 38.378341, -78.969128, "Kline Campus Center"));
            db.addMapMarker(new MapMarker(202, 38.378028, -78.969592, "Cole Hall"));
            db.addMapMarker(new MapMarker(203, 38.377262, -78.966549, "Funkhouser Center"));
            db.addMapMarker(new MapMarker(204, 38.379080, -78.971443, "Carter Center"));
            db.addMapMarker(new MapMarker(205, 38.378791, -78.970670, "Alexander Mack Library"));

            mapMarkers = db.getAllMapMarkers();
        }
        else {
            Log.d("Inserted", "Map Markers have already been inserted!");
            mapMarkers = db.getAllMapMarkers();
        }
        Log.d("ArrayList mapMarkers", "Length = " + mapMarkers.size());

        //db.deleteMapMarker(mapMarkers.get(0));
        //mapMarkers.remove(0);


        if(showAcademicBuildingMapMarkers)
        {
            for(MapMarker mapMarker : mapMarkers) {
                LatLng dbLocation = new LatLng(mapMarker.getLat(), mapMarker.getLng());
                if(mapMarker.getID() < 7)
                    mMap.addMarker(new MarkerOptions().position(dbLocation).title(mapMarker.getTitle()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
        }

        if(showResidentHallMapMarkers) {
            for(MapMarker mapMarker : mapMarkers) {
                LatLng dbLocation = new LatLng(mapMarker.getLat(), mapMarker.getLng());
                if(mapMarker.getID() > 6 & mapMarker.getID() < 14)
                    mMap.addMarker(new MarkerOptions().position(dbLocation).title(mapMarker.getTitle()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            }
        }

        if(showOtherBuildingMapMarkers) {
            for(MapMarker mapMarker : mapMarkers) {
                LatLng dbLocation = new LatLng(mapMarker.getLat(), mapMarker.getLng());
                if(mapMarker.getID() > 13 & mapMarker.getID() < 19)
                    mMap.addMarker(new MarkerOptions().position(dbLocation).title(mapMarker.getTitle()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
            }
        }
        CameraPosition cameraPosition = new CameraPosition.Builder().target(bridgewaterCollegeLatLng).zoom(16).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        for(MapMarker mapMarker : mapMarkers) {
            String log = mapMarker.getID() + " ----- Lat: " + mapMarker.getLat() + " -----  Long: " + mapMarker.getLng();
            Log.d(mapMarker.getTitle(), log);
        }
    }
}

package com.example.inclass12;


// Submitted by Gowtham Bharadwaj and Rajath Anand
// Group 18

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    List<LatLng> coordinates = null;
    Double FinalLatitude;
    Double FinalLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        String json = null;
        try {
            InputStream inputStream = getApplicationContext().getAssets().open("trip.json");
            int size = inputStream.available();

            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            // return null;
        }
        // return json;
        Gson gson = new Gson();
        Location loc = gson.fromJson(json, Location.class);
        ArrayList<Coordinates> locations = null;
        locations = loc.getPoints();

        if (locations != null) for (Coordinates point : locations) {
            FinalLatitude = Double.valueOf(point.getLatitude());
            FinalLongitude = Double.valueOf(point.getLongitude());
        }
        coordinates = new ArrayList<>();
        if (locations != null) for (Coordinates point : locations) {
            coordinates.add(new LatLng(Double.parseDouble(point.getLatitude()), Double.parseDouble(point.getLongitude())));
        }
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
        PolylineOptions polylineOptions;
        Polyline polyline = googleMap.addPolyline(new PolylineOptions().addAll(coordinates));

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LatLngBounds.Builder latlngboundBuilder = new LatLngBounds.Builder();

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(FinalLatitude, FinalLongitude))
                        .title("end"));
                mMap.addMarker(new MarkerOptions()
                        .position(coordinates.get(0))
                        .title("Start"));
                for (LatLng p : coordinates) {
                    latlngboundBuilder.include(p);
                }
                LatLngBounds bounds = latlngboundBuilder.build();
                mMap.setLatLngBoundsForCameraTarget(bounds);
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latlngboundBuilder.build(), 1));
            }
        });
    }
}

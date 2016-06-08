package com.example.calla.heyhome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by yuan on 6/6/16.
 */
public class ViewMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng pictureLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_map);

        LLHolder llHolder = LLHolder.getInstance();
        // todo set latitude from getLatitude()
        /*String latitude = llHolder.getLatitude();
        String longitude = llHolder.getLongitude();*/

        String latitude = "0";
        String longitude = "0";

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // set interesting locations
        pictureLocation = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        mMap.addMarker(new MarkerOptions().position(pictureLocation).title("Picture Location").snippet("Picture Location"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pictureLocation, 17.0f));

        // set up UI control
        UiSettings ui = mMap.getUiSettings();
        ui.setAllGesturesEnabled(true);
        ui.setCompassEnabled(true);
        ui.setZoomControlsEnabled(true);
    }

}

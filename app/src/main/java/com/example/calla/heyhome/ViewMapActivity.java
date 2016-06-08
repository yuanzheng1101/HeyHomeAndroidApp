package com.example.calla.heyhome;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by yuan on 6/6/16.
 */
public class ViewMapActivity extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng pictureLocation;
    private FragmentActivity myContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_map, container, false);

       /* LLHolder llHolder = LLHolder.getInstance();
        String latitude = llHolder.getLatitude();
        String longitude = llHolder.getLongitude();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        FragmentManager fragManager = myContext.getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fragManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // set interesting locations
        pictureLocation = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));*/

        return rootView;
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

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

}

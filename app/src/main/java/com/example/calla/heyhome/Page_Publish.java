package com.example.calla.heyhome;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class Page_Publish extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private DBFirebase dbFirebase;
    SessionManager sessionManager;

    ImageView imageView;
    EditText caption;

    // for geo location
    GoogleApiClient mGoogleApiClient = null;
    Location mLastLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_page_publish, container, false);


        // instantiate databse
        dbFirebase = new DBFirebase(getActivity().getApplicationContext());

        // instantiate session
        sessionManager = new SessionManager(getActivity().getApplicationContext());


        // instantiate components
        imageView = (ImageView) rootView.findViewById(R.id.image);
        caption = (EditText) rootView.findViewById(R.id.caption);


        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        // set preview photo from camera
        if (MainActivity.imageUri != null) {
            imageView.setImageURI(MainActivity.imageUri);
        }


//        if (MainActivity.imageFromCamera != null) {
//            Uri uri = Uri.parse(MainActivity.imageFromCamera);
//            System.out.println(MainActivity.imageFromCamera);
//            System.out.println(uri.toString());
//            imageView.setImageURI(uri);
//            MainActivity.imageFromCamera = null;
//        }
//        // photo from library
//        if (MainActivity.imageFromLibrary != null) {
////            imageView.setImageURI(null);
//            imageView.setImageBitmap(MainActivity.imageFromLibrary);
////            MainActivity.imageFromLibrary = null;
//        }


        // publish button
        Button buttom = (Button) rootView.findViewById(R.id.save);
        buttom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get image
                String picString = imageToString();

                // get caption
                EditText tv = (EditText) getActivity().findViewById(R.id.caption);
                String caption = tv.getText().toString();

                // get location
                startTrackLocation();
                stopTrackLocation();
                String location =
                        String.format("%.4f", mLastLocation.getLatitude())
                                + ","
                                + String.format("%.4f", mLastLocation.getLongitude());
                System.out.println("location: " + location);

                // get time
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());


                Record record = new Record("", picString, caption, location, timeStamp,
                        sessionManager.getCurrentUserId(), sessionManager.getCurrentUserName(), sessionManager.getCurrentUserImage());
                dbFirebase.addRecord(record);


                // jump to homepage
                Bundle bundle = new Bundle();
                Page_Homepage page = new Page_Homepage();
                page.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.mainFragment, page)
                        .commit();
                MainActivity.mBottomBar.selectTabAtPosition(0, true);
            }
        });

        return rootView;
    }


    private String imageToString(){
        String fileName = MainActivity.imageUri.toString();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8; // Experiment with different sizes
        Bitmap pic_bitmap = BitmapFactory.decodeFile(fileName.substring(7), options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        pic_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        String picString = Base64.encodeToString(bytes, Base64.DEFAULT);
        return picString;
    }

    // for geo location
    private void startTrackLocation() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(2000);
        mLocationRequest.setFastestInterval(500);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    private void stopTrackLocation() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
    }




}

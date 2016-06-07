package com.example.calla.heyhome;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class Page_Gallery extends Fragment implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    private static String[] brands = {"Brand", "Bassett", "Ethan Allen", "IKEA", "Pottery Barn"};
    private static String[] rooms = {"Room", "Living", "Bedroom", "Kitchen", "Dining", "Bathroom"};
    private static String[] styles = {"Style", "Contemporary", "Transitional", "Traditional"};

    private GridView gridview;
    private final List<Bitmap> bitmapList = new ArrayList<>();
    private ImageAdapter imageAdapter;

    private Context context;
    private ArrayList<Bitmap> list;

    public String selectBrand;
    public String selectRoom;
    public String selectStyle;

    // create Firebase
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage storage;
    StorageReference storageRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_page_gallery, container, false);
        context = this.getActivity().getApplicationContext();

        // initialize database and storage
        firebaseDatabase = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://intense-inferno-3371.appspot.com");

        // part1: three spinners
        Spinner spinner1 = (Spinner) rootView.findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this.getActivity(),
                android.R.layout.simple_spinner_item,
                brands);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);

        Spinner spinner2 = (Spinner) rootView.findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this.getActivity(),
                android.R.layout.simple_spinner_item,
                rooms);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        Spinner spinner3 = (Spinner) rootView.findViewById(R.id.spinner3);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(
                this.getActivity(),
                android.R.layout.simple_spinner_item,
                styles);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(this);


        // part2: GridView for images
        list = new ArrayList<>();
        imageAdapter = new ImageAdapter(context, bitmapList);
        gridview = (GridView) rootView.findViewById(R.id.gridview);
        gridview.setOnItemClickListener(this);
        gridview.setAdapter(imageAdapter);

        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner1:
                selectBrand = brands[position];
                break;
            case R.id.spinner2:
                selectRoom = rooms[position];
                break;
            case R.id.spinner3:
                selectStyle = styles[position];
                break;
        }

        // avoid execute three times when first launching this fragment
        if (selectRoom != null && selectStyle != null && selectBrand != null) {
            getGalleries();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getActivity().getApplicationContext(), "Nothing selected!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        // TODO: 5/26/16 show solo picture
    }



    public void getGalleries() {
        Log.d("position", "in getGalleries()");
        imageAdapter.clear();
        DatabaseReference userRef = firebaseDatabase.getReference("GalleryList");

        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                Gallery gallery = snapshot.getValue(Gallery.class);

                Boolean brandIsSelected = selectBrand == "Brand" ? false : true;
                Boolean roomIsSelected = selectRoom == "Room" ? false : true;
                Boolean styleIsSelected = selectStyle == "Style" ? false : true;

                if ((!brandIsSelected || gallery.getBrand().equals(selectBrand))
                        && (!roomIsSelected || gallery.getRoom().equals(selectRoom))
                        && (!styleIsSelected || gallery.getStyle().equals(selectStyle))) {
                    addBitmapToList(gallery.getImage());
                } else {
                    Log.d("position", "fail to add to list");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


            public void addBitmapToList(String fileName) {
                StorageReference fileRef = storageRef.child("galleries/" + fileName);

                final long ONE_MEGABYTE = 1024 * 1024;
                fileRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // image return as byte[] and convert to bitmap
                        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        // dynamically add to adapter and shown in gridview
                        imageAdapter.addBitmap(bm);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        exception.printStackTrace();
                        Log.d("error", "Failed to load image from Firebase storage!!!");
                    }
                });

            }
        });

    }

}

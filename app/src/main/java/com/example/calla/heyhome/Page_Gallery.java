package com.example.calla.heyhome;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class Page_Gallery extends Fragment implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    String[] brands = {"Brand", "Bassett", "RH", "Crate & Barrel", "Ethan Allen", "IKEA", "Pottery Barn", "West elm"};
    String[] rooms = {"Room", "Living", "Bedroom", "Kitchen", "Dining", "Bath"};
    String[] styles = {"Style", "Contemporary", "Modern", "Transitional", "Traditional", "Farmhouse", "Rustic"};
    String[] selections = new String[3];

    GridView gridview;
    ImageView soloPhoto;
    Button btnBack;

    Context context;
    ArrayList<Bitmap> list;

    public String selectBrand;
    public String selectRoom;
    public String selectStyle;

    // initialize array of smallImages (100x75 thumbnails)
    Integer[] smallImages = { R.drawable.pic01_small,
            R.drawable.pic02_small, R.drawable.pic03_small,
            R.drawable.pic04_small, R.drawable.pic05_small,
            R.drawable.pic06_small, R.drawable.pic07_small,
            R.drawable.pic08_small, R.drawable.pic09_small,
            R.drawable.pic10_small, R.drawable.homedec_5,
            R.drawable.homedec_4, R.drawable.homedec_3,
            R.drawable.homedec_2, R.drawable.homedec_1 };

    List<Bitmap> bitmaps = new ArrayList<>();

    //in case you want to use-save state values
    Bundle myOriginalMemoryBundle;

    // create Firebase
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage storage;
    StorageReference storageRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_page_gallery, container, false);
        context = this.getActivity().getApplicationContext();
        list = new ArrayList<>();

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
        myOriginalMemoryBundle = savedInstanceState;
        gridview = (GridView) rootView.findViewById(R.id.gridview);
        gridview.setOnItemClickListener(this);
//        getGalleries("");

        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner1:
                selections[0] = brands[position];
                selectBrand = brands[position];
                break;
            case R.id.spinner2:
                selections[1] = rooms[position];
                selectRoom = rooms[position];
                break;
            case R.id.spinner3:
                selections[2] = styles[position];
                selectStyle = styles[position];
                break;
        }
        Toast.makeText(getActivity().getApplicationContext(),
                selectBrand + " " + selectRoom + " " + selectStyle + " are selected!", Toast.LENGTH_SHORT).show();

        getGalleries();
//        getGalleries("Brand", "Room", "Style");
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
        Log.d("here", "in getGalleries()");
        DatabaseReference userRef = firebaseDatabase.getReference("GalleryList");

        ChildEventListener listener = new ChildEventListener() {
            List<Bitmap> list = new ArrayList<>();

            int i = 0;
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                Log.d("here", "in onChildAdded()");
//                String key = snapshot.getKey();
                Gallery gallery = snapshot.getValue(Gallery.class);

                Boolean brandIsSelected = selectBrand == "Brand" ? false : true;
                Boolean roomIsSelected = selectRoom == "Room" ? false : true;
                Boolean styleIsSelected = selectStyle == "Style" ? false : true;

//                Log.d("parameter", "" + selectBrand + "-" + selectRoom + "-" + selectStyle);
//                Log.d("parameter", "" + brandIsSelected + "-" + roomIsSelected + "-" + styleIsSelected);
//                Log.d("parameter", "" + gallery.getBrand() + "-" + gallery.getRoom() + "-" + gallery.getStyle());
//                System.out.println(!brandIsSelected);
//                System.out.println(gallery.getBrand().equals(selectBrand));

                if ((!brandIsSelected || gallery.getBrand().equals(selectBrand))
                        && (!roomIsSelected || gallery.getRoom().equals(selectRoom))
                        && (!styleIsSelected || gallery.getStyle().equals(selectStyle))) {
                    addBitmapToList(gallery.image);
                    Log.d("addToList Number: ", "" + i);
                    i++;
                } else {
                    Log.d("here", "fail to add to list");
                }

//                if (brandIsSelected != null) {
//
//                }
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


            public void addBitmapToList(String fileNmae) {
                String s = "galleries/" + fileNmae;
                StorageReference fileRef = storageRef.child(s);

                final long ONE_MEGABYTE = 1024 * 1024;
                fileRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // image return as byte[]
                        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        list.add(bm);
                        gridview.setAdapter(new ImageAdapter(context, list.toArray(new Bitmap[list.size()])));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        exception.printStackTrace();
                        System.out.println("Failed to load image from Firebase storage!!!");
                    }
                });

                Bitmap[] bitmaps = list.toArray(new Bitmap[list.size()]);
//                System.out.println(bitmaps.toString());
//                gridview.setAdapter(new ImageAdapter(context, bitmaps));
            }
        };


        userRef.addChildEventListener(listener);

//            ValueEventListener postListener = new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    // Get Post object and use the values to update the UI
//                    for (DataSnapshot movieSnapshot : dataSnapshot.getChildren()) {
//
//                        if (movieSnapshot.get.equals('Jack Nicholson')) {
//                            console.log(movieSnapshot.getKey());
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    // Getting Post failed, log a message
//                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//                    // ...
//                }
//            };
//            mPostReference.addValueEventListener(postListener);


//        userRef.addChildEventListener(listener);

//        Query query = userRef.orderByChild("room").equalTo(s);
//        query.addChildEventListener(listener);
    }

}

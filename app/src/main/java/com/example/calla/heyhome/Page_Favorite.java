package com.example.calla.heyhome;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Page_Favorite extends Fragment {
    private DBFirebase dbFirebase;
    ImageView image;
    FirebaseDatabase firebaseDatabase;

    TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_page_favorite, container, false);
        //show the whole page here

        // create Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();


        dbFirebase = new DBFirebase();


        Button button = (Button) rootView.findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                User user1 = new User("John", "johnpass", "john@scu", "jjjjjj", "johnImage", 0, 0);
//                User user2 = new User("Green", "greenpass", "green@scu", "ggggg", "greenImage", 4, 5);
//                dbFirebase.addUser(user1);
//                dbFirebase.addUser(user2);
//
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                Gallery gallery = new Gallery("Ethan Allen", "Dining", "Transitional", "ea11.jpg", timeStamp);
                dbFirebase.addGallery(gallery);

//                Record record = new Record(101, "caption", "image", "time");
//                dbFirebase.addRecord(record);
            }
        });

        Button button2 = (Button) rootView.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = "kate";
                dbFirebase.searchUser(name);
            }
        });


        // load image from firebase
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://intense-inferno-3371.appspot.com");
        StorageReference fileRef = storageRef.child("images/image4.jpg");


        image = (ImageView) rootView.findViewById(R.id.imageView);
        final long ONE_MEGABYTE = 1024 * 1024;
        fileRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/image1.png" is returns, use this as needed
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                image.setImageBitmap(bm);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                exception.printStackTrace();
                System.out.println("Failed to load image from Firebase storage!!!");
            }
        });


        return rootView;
    }



    // for database
    public void getUser(String uid) {
        DatabaseReference userRef = firebaseDatabase.getReference("UserList");

        Query query = userRef.orderByKey().equalTo(uid);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                System.out.println(snapshot.getKey());
                System.out.println(snapshot.getValue());
                User user = snapshot.getValue(User.class);

//                // store uid into seesion
//                sessionManager.createLoginSession(user);
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

        });
    }



}

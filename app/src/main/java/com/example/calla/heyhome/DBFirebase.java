package com.example.calla.heyhome;

import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Calla on 5/25/16.
 */
public class DBFirebase {

    // create Firebase
    Firebase firebase = new Firebase("https://intense-inferno-3371.firebaseio.com/");
//    DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();

    public DBFirebase() {

    }

    // functions for activities
    public void addUser(User user) {
        firebase.child("UserList").push().setValue(user);
    }

    public void updateUser() {
        // TODO: 5/25/16

    }

    public void searchUser(String name) {
        // TODO: 5/25/16

        String rst = "";
        Firebase f = firebase.child("UserList");
        Query query = f.orderByChild("name").equalTo(name);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                String key = snapshot.getKey();
                System.out.println(key);
                System.out.println(snapshot.getValue());
                User user = snapshot.getValue(User.class);
                System.out.println(user.getEmail());
                System.out.println(user.description);
                String photoPath = user.photo;
                System.out.println(photoPath);


                // pic

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }
            //

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
    }

    public void addRecord(Record record) {
        firebase.child("RecordList").push().setValue(record);
    }

    public void addGallery(Gallery gallery) {
        firebase.child("GalleryList").push().setValue(gallery);
    }
}

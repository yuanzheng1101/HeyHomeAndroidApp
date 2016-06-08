package com.example.calla.heyhome;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class Page_Favorite extends Fragment {
    private DBFirebase dbFirebase;
    ImageView image;
    FirebaseDatabase firebaseDatabase;

    TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_page_favorite, container, false);

        ImageView addPeople = (ImageView) rootView.findViewById(R.id.menu_add_people);
        addPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPageAddPeople();
            }
        });
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
                dbFirebase.getUser(dbFirebase.getCurrentUid());


            }
        });

        Button button2 = (Button) rootView.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = "radio";
                dbFirebase.searchUser(name);

                // add favorite
//                Favorite favorite = new Favorite("image05.jpg");
//                DatabaseReference userRef = firebaseDatabase.getReference("UserList");
//                userRef.child(dbFirebase.getCurrentUid()).child("favorites").push().setValue(favorite);

                // add comment
                String rid = "-KJfqpyIzZmjLyfLvww0";
                Comment comment = new Comment(rid, dbFirebase.getCurrentUid(), "good!");
                DatabaseReference commentRef = firebaseDatabase.getReference("CommentList");
                commentRef.push().setValue(comment);

            }
        });


        Button button3 = (Button) rootView.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Log.d("sign out", "successfully signed out!");
                Intent intent = new Intent(getActivity(), SignIn.class);
                startActivity(intent);
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

    private void openPageAddPeople() {
        Bundle bundle = new Bundle();
        AddPeopleFragment page = new AddPeopleFragment();
        page.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.mainFragment, page).commit();

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

    public Bitmap convertStringToBitmap(String imageString) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8; // Experiment with different sizes

        byte[] decodedImage = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap decodedImageByte = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
        return decodedImageByte;
    }



}

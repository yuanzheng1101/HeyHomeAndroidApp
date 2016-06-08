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
    FirebaseDatabase firebaseDatabase;

    SessionManager sessionManager;

    ImageView image;
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

        // instantiate Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        dbFirebase = new DBFirebase(getActivity().getApplicationContext());

        // instantiate session
        sessionManager = new SessionManager(getActivity().getApplicationContext());


        Button button = (Button) rootView.findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                addFollowings("8MCULOFoXRWIDyK8XApP4w1EBuH3");
//                getFollowingAndFollowerCount();
//                unFollowing("8MCULOFoXRWIDyK8XApP4w1EBuH3");
                checkFollowing("8MCULOFoXRWIDyK8XApP4w1EBuH3");
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
                Comment comment = new Comment(rid, sessionManager.getCurrentUserName(), sessionManager.getCurrentUserImage(), "good!");
                DatabaseReference commentRef = firebaseDatabase.getReference("CommentList");
                commentRef.push().setValue(comment);

            }
        });


        Button button3 = (Button) rootView.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                SessionManager sessionManager = new SessionManager(getActivity().getApplicationContext());
                sessionManager.clearSessionForSignOut();
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


    public void addFollowings(String id) {
        final String uid = sessionManager.getCurrentUserId();
        final String followingId = id;

        final DatabaseReference userRef = firebaseDatabase.getReference("UserList");

        Query query1 = userRef.orderByKey().equalTo(uid);
        query1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                User user = snapshot.getValue(User.class);
                userRef.child(uid).child("followings").setValue(user.getFollowings() + followingId  + ",");
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

        Query query2 = userRef.orderByKey().equalTo(followingId);
        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                User user = snapshot.getValue(User.class);
                userRef.child(followingId).child("followers").setValue(user.getFollowers() + uid  + ",");
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

    public void getFollowingAndFollowerCount() {
        final String uid = sessionManager.getCurrentUserId();

        final DatabaseReference userRef = firebaseDatabase.getReference("UserList");

        Query query = userRef.orderByKey().equalTo(uid);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                User user = snapshot.getValue(User.class);
                String[] followings = user.getFollowings().split(",");
                String[] followers = user.getFollowers().split(",");

                int followingCount = 0;
                int followerCount = 0;
                if (followings.length == 1 && followings[0].equals("")) {
                    followingCount = 0;
                } else {
                    followingCount = followings.length;
                }

                if (followers.length == 1 && followers[0].equals("")) {
                    followerCount = 0;
                } else {
                    followerCount = followers.length;
                }

                System.out.println("getFollowingAndFollowerCount" + followingCount + " & " + followerCount);

                // set Page_Me UI with followingCount and followerCount

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

    public void checkFollowing(String id) {
        final String uid = sessionManager.getCurrentUserId();
        final String followingId = id;
        final DatabaseReference userRef = firebaseDatabase.getReference("UserList");

        Query query = userRef.orderByKey().equalTo(uid);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                User user = snapshot.getValue(User.class);
                String[] strs = user.getFollowings().split(",");
                for (String s : strs) {
                    if (followingId.equals(s)) {
                        // current user is following this record user
                        System.out.println("already followed!");

                    } else {
                        // current user is following this record user
                    }
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
        });
    }

    public void unFollowing(String id) {
        final String uid = sessionManager.getCurrentUserId();
        final String followingId = id;

        final DatabaseReference userRef = firebaseDatabase.getReference("UserList");

        Query query1 = userRef.orderByKey().equalTo(uid);
        query1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                User user = snapshot.getValue(User.class);
                StringBuilder newFollowings = new StringBuilder();
                String[] strs = user.getFollowings().split(",");
                for (String s : strs) {
                    if (!s.equals(followingId)) {
                        newFollowings.append(s + ",");
                    }
                }
                userRef.child(uid).child("followings").setValue(newFollowings.toString());
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

        Query query2 = userRef.orderByKey().equalTo(followingId);
        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                User user = snapshot.getValue(User.class);
                StringBuilder newFollowers = new StringBuilder();
                String[] strs = user.getFollowers().split(",");
                for (String s : strs) {
                    if (!s.equals(uid)) {
                        newFollowers.append(s + ",");
                    }
                }
                userRef.child(followingId).child("followers").setValue(newFollowers.toString());
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

package com.example.calla.heyhome;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Calla on 5/25/16.
 */
public class DBFirebase {

    // create Firebase
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    // create auth
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    Context context;

    // instantiate session
//    SessionManager sessionManager = new SessionManager();

    public DBFirebase(Context context) {
        this.context = context;
    }

    public void initializeComment(String rid) {
        firebaseDatabase.getReference("CommentList").child(rid).setValue("empty");
    }
    public void addComment(Comment comment) {
        firebaseDatabase.getReference("CommentList").push().setValue(comment);
    }


    public void addRecord(Record record) {
        firebaseDatabase.getReference("RecordList").push().setValue(record);
    }

    public void addUser(String uid, User user) {
        firebaseDatabase.getReference("UserList").child(uid).setValue(user);
    }



    public void updateUser() {
        // TODO: 5/25/16

    }

    public void getUser(String uid) {
        Log.d("position", "in getUser");
        Log.d("position", uid);
        DatabaseReference userRef = firebaseDatabase.getReference("UserList");

        Query query = userRef.orderByChild("name").equalTo("radio");
        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                Log.d("position", "in getUser - onChildAdded");
                System.out.println(snapshot.getKey());
                System.out.println(snapshot.getValue());
                User user = snapshot.getValue(User.class);


                // store uid into seesion
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


    public void searchUser(String name) {
        DatabaseReference userRef = firebaseDatabase.getReference("UserList");
        Query query = userRef.orderByChild("name").equalTo(name);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                String key = snapshot.getKey();
                System.out.println(key);
                System.out.println(snapshot.getValue());
                User user = snapshot.getValue(User.class);
                System.out.println(user.getName());
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

    public void getCurrentUserInfo() {
        if (mAuth.getCurrentUser() != null) {
            System.out.println();
            String currentUid = mAuth.getCurrentUser().getUid();

            DatabaseReference userRef = firebaseDatabase.getReference("UserList");
            Query query = userRef.orderByKey().equalTo(currentUid);
            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                    User user = snapshot.getValue(User.class);


                    SessionManager sessionManager = new SessionManager(context);
                    sessionManager.createSignInSession(mAuth.getCurrentUser().getUid(), user.getName(), user.getPhoto());
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


}

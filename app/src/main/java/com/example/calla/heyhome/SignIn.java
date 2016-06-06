package com.example.calla.heyhome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class SignIn extends AppCompatActivity {
    private DBFirebase dbFirebase;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText email;
    private EditText password;


    // current user information
    public String name;
    public String description;
    public String followings;
    public String followers;
    public int followingCount;
    public int followerCount;
    public List<String> followingList;
    public List<String> followerList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        dbFirebase = new DBFirebase();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // get user from system built-in db
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d("login", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("login", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        Button button = (Button) findViewById(R.id.signIn);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


        Button button2 = (Button) findViewById(R.id.signOut);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Log.d("sign out", "successfully signed out!");
            }
        });

        Button button3 = (Button) findViewById(R.id.home);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, MainActivity.class);
                startActivity(intent);
                Log.d("back to main", "successfully turn to homepage!");
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void signIn() {
        String emailS = email.getText().toString();
        String passwordS = password.getText().toString();
        mAuth.signInWithEmailAndPassword(emailS, passwordS)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("sign in", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("sign in", "signInWithEmail", task.getException());
                            Toast.makeText(SignIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

//    public void loadUserProfile(String uid) {
//        System.out.println(uid);
//        User user = dbFirebase.getUser(uid);
//        name = user.getName();
//        description = user.getDescription();
//        followingCount = user.getFollowingCount();
//        followerCount = user.getFollowerCount();
//        followings = user.getFollowings();
//        followers = user.getFollowers();
//
//        System.out.println(name);
//        System.out.println(description);
//        System.out.println(followingCount);
//        System.out.println(followerCount);
//    }
}

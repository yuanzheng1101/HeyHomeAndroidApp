package com.example.calla.heyhome;

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

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText email;
    private EditText password;
    private DBFirebase dbFirebase;

    // current user information
    public String uid;
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
        setContentView(R.layout.activity_sign_up);

        dbFirebase = new DBFirebase();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // get user from system built-in db
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d("sign up", "onAuthStateChanged:signed_in:" + user.getUid());
                    uid = user.getUid();
                    User userInfo = new User("new user", "", "", "", "", 0, 0);
        dbFirebase.addUser(uid, userInfo);
                } else {
                    // User is signed out
                    Log.d("sign up", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        Button button = (Button) findViewById(R.id.signUp);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signUp();
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

    private void signUp() {
        String emailS = email.getText().toString();
        String passwordS = password.getText().toString();
        mAuth.createUserWithEmailAndPassword(emailS, passwordS)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("SignUp", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // inset a new user profile into database
//        User userInfo = new User("new user", "", "", "", "", 0, 0);
//        dbFirebase.addUser(uid, userInfo);
    }
}

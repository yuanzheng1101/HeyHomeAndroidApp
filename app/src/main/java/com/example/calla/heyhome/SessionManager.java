package com.example.calla.heyhome;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Calla on 6/8/16.
 */
public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "AndroidHivePref";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_IMAGE = "image";

    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createSignInSession(String uid, String name, String image) {
        editor.putString(KEY_ID, uid);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_IMAGE, image);
        editor.commit();
    }

    public void clearSessionForSignOut() {
        editor.clear();
        editor.commit();
    }


    public String getCurrentUserId() {
        return pref.getString(KEY_ID, null);
    }

    public String getCurrentUserName() {
        return pref.getString(KEY_NAME, null);
    }

    public String getCurrentUserImage() {
        return pref.getString(KEY_IMAGE, null);
    }

}

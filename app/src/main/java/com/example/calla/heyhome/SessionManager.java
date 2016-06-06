package com.example.calla.heyhome;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Calla on 6/4/16.
 */
public class SessionManager {
    Context context;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "MyRef";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_FOLLOWINGS = "followings";
    public static final String KEY_FOLLOWERS = "followers";

    // Constructor
    public SessionManager() {
//        this.context = context;
//        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(User user) {
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_DESCRIPTION, user.getDescription());
        editor.putString(KEY_FOLLOWINGS, user.getFollowings());
        editor.putString(KEY_FOLLOWERS, user.getFollowers());

        editor.commit();
    }
}

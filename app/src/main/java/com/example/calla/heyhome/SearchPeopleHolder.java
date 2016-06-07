package com.example.calla.heyhome;

/**
 * Created by yuan on 6/6/16.
 */
public class SearchPeopleHolder {
    private static SearchPeopleHolder dataObject = null;

    private SearchPeopleHolder() {
    }

    public static SearchPeopleHolder getInstance() {
        if (dataObject == null) {
            dataObject = new SearchPeopleHolder();
        }
        return dataObject;
    }

    protected int ProfilePic; // R.id.photo todo need change this to string
    protected String userName;


    public void setProfilePic(int photo) {
        this.ProfilePic = photo;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

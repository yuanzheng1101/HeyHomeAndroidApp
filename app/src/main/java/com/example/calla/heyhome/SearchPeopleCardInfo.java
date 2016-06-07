package com.example.calla.heyhome;

/**
 * Created by yuan on 6/6/16.
 */
public class SearchPeopleCardInfo {

    // todo change image type to String
    protected int profileImg;
    protected String userName;

    public SearchPeopleCardInfo(int userPostedPhoto, String userPostedCaption) {
        this.profileImg = userPostedPhoto;
        this.userName = userPostedCaption;
    }

    public int getProfileImg() {
        return profileImg;
    }
    public String getUserName() {
        return userName;
    }

}

package com.example.calla.heyhome;

/**
 * Created by yuan on 5/24/16.
 */
public class CardInfo {

    protected String userProfileImgPath;
    protected String userName;
    protected String location;
    protected String userPostedCaption;
    protected int userPostedPhoto; // R.id.photo
    protected String favIcon;
    protected String userPostedTime;
    protected String friendName;
    protected String friendComment;

    public CardInfo(int userPostedPhoto, String userPostedCaption) {
        this.userPostedPhoto = userPostedPhoto;
        this.userPostedCaption = userPostedCaption;
    }

    public String getUserProfileImgPath() {
        return userProfileImgPath;
    }
    public String getUserName() {
        return userName;
    }
    public String getLocation() {
        return location;
    }
    public String getUserPostedCaption() {
        return userPostedCaption;
    }
    public int getUserPostedPhoto() {
        return userPostedPhoto;
    }
    public String getFavIcon() {
        return favIcon;
    }
    public String getUserPostedTime() {
        return userPostedTime;
    }
    public String getFriendName() {
        return friendName;
    }
    public String getFriendComment() {
        return friendComment;
    }

}
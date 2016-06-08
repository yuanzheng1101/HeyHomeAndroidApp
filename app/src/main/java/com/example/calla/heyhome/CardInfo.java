package com.example.calla.heyhome;

/**
 * Created by yuan on 5/24/16.
 */
public class CardInfo {

    protected String userProfileImgPath;
    protected String userName;
    protected String location;
    protected String userPostedCaption;
    protected String userPostedPhoto; // R.id.photo todo need change this to string
    protected boolean favIcon;
    protected String userPostedTime;
    protected String recordId;

    public CardInfo(String userPostedPhoto, String userPostedCaption) {
        this.userPostedPhoto = userPostedPhoto;
        this.userPostedCaption = userPostedCaption;
    }

    public CardInfo(String userProfileImgPath, String userName, String location,
                    String userPostedCaption, String userPostedPhoto, boolean favIcon,
                    String userPostedTime, String recordId) {
        this.userProfileImgPath = userProfileImgPath;
        this.userName = userName;
        this.location = location;
        this.userPostedCaption = userPostedCaption;
        this.userPostedPhoto = userPostedPhoto;
        this.favIcon = favIcon;
        this.userPostedTime = userPostedTime;
        this.recordId = recordId;
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
    public String getUserPostedPhoto() {
        return userPostedPhoto;
    }
    public boolean getFavIcon() {
        return favIcon;
    }
    public String getUserPostedTime() {
        return userPostedTime;
    }
    public String getRecordId() {
        return recordId;
    }

}
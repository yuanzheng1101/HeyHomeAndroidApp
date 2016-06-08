package com.example.calla.heyhome;

/**
 * Created by yuan on 6/4/16.
 */
public class CardInfoHolder {
    private static CardInfoHolder dataObject = null;
    private CardInfoHolder(){}

    public static CardInfoHolder getInstance() {
        if (dataObject == null) {
            dataObject = new CardInfoHolder();
        }
        return dataObject;
    }

    private String userPostedCaption;
    protected String userPostedPhoto; // R.id.photo todo need change this to string
    protected String userProfileImgPath;
    protected String userName;
    protected String location;
    protected boolean favIcon;
    protected String userPostedTime;



    public void setCaption(String caption) {
        this.userPostedCaption = caption;
    }
    public void setPhoto(String photo) {
        this.userPostedPhoto = photo;
    }
    public void setUserProfileImgPath(String profileImg) {
        this.userProfileImgPath = profileImg;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setFavIcon(boolean favMark) {
        this.favIcon = favMark;
    }
    public void setUserPostedTime (String time) {
        this.userPostedTime = time;
    }



    public String getCaption() {
        return userPostedCaption;
    }
    public String getPhoto() {
        return userPostedPhoto;
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
    public boolean getFavIcon() {
        return favIcon;
    }
    public String getUserPostedTime() {
        return userPostedTime;
    }
}

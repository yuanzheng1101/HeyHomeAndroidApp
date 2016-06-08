package com.example.calla.heyhome;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Calla on 5/17/16.
 */
public class Record {

    private String rid;
    private String image;
    private String caption;
    private String location;
    private String time;
    private String userId;
    private String userName;
    private String userImage;

    public Record (String rid, String image, String caption, String location, String time, String userId, String userName, String userImage) {
        this.rid = rid;
        this.image = image;
        this.caption = caption;
        this.location = location;
        this.time = time;
        this.userId = userId;
        this.userName = userName;
        this.userImage = userImage;
    }

    public Record(){};

    public String getImage() {
        return image;
    }

    public String getCaption() {
        return caption;
    }

    public String getLocation() {
        return location;
    }

    public String getTime() {
        return time;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserImage() {
        return userImage;
    }
}

package com.example.calla.heyhome;

/**
 * Created by Calla on 5/17/16.
 */
public class Record {

    private String user;
    private String image;
    private String caption;
    private String comments;
    private String location;
    private String time;

    public Record (String user, String image, String caption, String comments, String location, String time) {
        this.user = user;
        this.image = image;
        this.caption = caption;
        this.comments = comments;
        this.location = location;
        this.time = time;
    }

    public Record(){};

    public String getUser() {
        return user;
    }

    public String getImage() {
        return image;
    }

    public String getCaption() {
        return caption;
    }

    public String getComments() {
        return comments;
    }

    public String getLocation() {
        return location;
    }

    public String getTime() {
        return time;
    }


}

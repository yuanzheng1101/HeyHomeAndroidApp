package com.example.calla.heyhome;

/**
 * Created by Calla on 6/7/16.
 */
public class Comment {

    private String rid;
    private String userName;
    private String userImage;
    private String content;

    public Comment(String rid, String userName, String userImage, String content) {
        this.rid = rid;
        this.userName = userName;
        this.userImage = userImage;
        this.content = content;
    }

    public Comment() {

    }

    public String getRid() {
        return rid;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getContent() {
        return content;
    }

}

package com.example.calla.heyhome;

/**
 * Created by Calla on 6/7/16.
 */
public class Comment {

    private String uid;
    private String content;

    public Comment(String uid, String content) {
        this.uid = uid;
        this.content = content;
    }

    public Comment() {

    }

    public String getUid() {
        return uid;
    }

    public String getContent() {
        return content;
    }
}

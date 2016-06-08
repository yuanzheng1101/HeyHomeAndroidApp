package com.example.calla.heyhome;

/**
 * Created by Calla on 6/7/16.
 */
public class Comment {

    private String rid;
    private String uid;
    private String content;

    public Comment(String rid, String uid, String content) {
        this.rid = rid;
        this.uid = uid;
        this.content = content;
    }

    public Comment() {

    }

    public String getRid() {
        return rid;
    }

    public String getUid() {
        return uid;
    }

    public String getContent() {
        return content;
    }

}

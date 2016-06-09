package com.example.calla.heyhome;

/**
 * Created by yuan on 6/8/16.
 */
public class RecordUserIdHolder {
    private static RecordUserIdHolder dataObject = null;
    private RecordUserIdHolder(){}

    public static RecordUserIdHolder getInstance() {
        if (dataObject == null) {
            dataObject = new RecordUserIdHolder();
        }
        return dataObject;
    }

    private String userId;
    public void setUserId (String userId) {
        this.userId = userId;
    }
    public String getUserId () {
        return userId;
    }
}

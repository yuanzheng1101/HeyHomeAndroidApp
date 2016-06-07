package com.example.calla.heyhome;

/**
 * Created by yuan on 6/6/16.
 */
public class LLHolder {
    private static LLHolder dataObject = null;
    private LLHolder(){}

    public static LLHolder getInstance() {
        if (dataObject == null) {
            dataObject = new LLHolder();
        }
        return dataObject;
    }

    private String latitude;
    protected String longitude;



    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    public String getLatitude() {
        return latitude;
    }
    public String getLongitude() {
        return longitude;
    }
}

package com.example.calla.heyhome;

/**
 * Created by yuan on 6/8/16.
 */
public class FFHolder {
    private static FFHolder dataObject = null;
    private FFHolder(){}

    public static FFHolder getInstance() {
        if (dataObject == null) {
            dataObject = new FFHolder();
        }
        return dataObject;
    }

    private int following;
    private int follower;
    private String whetherFollowing = "false";



    public void setFollowing(int following) {
        this.following = following;
    }
    public void setFollower(int follower) {
        this.follower = follower;
    }
    public void setWhetherFollowing(String whetherFollowing) {
        this.whetherFollowing = whetherFollowing;
    }


    public int getFollowing() {
        return following;
    }
    public int getFollower() {
        return follower;
    }
    public String getWhetherFollowing() {
        return whetherFollowing;
    }
}

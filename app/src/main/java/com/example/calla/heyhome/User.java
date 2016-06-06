package com.example.calla.heyhome;

import java.util.List;

/**
 * Created by Calla on 5/17/16.
 */
public class User {

    String name;
    String description;
    String photo;
    String followings;
    String followers;
    int followingCount;
    int followerCount;

    public User(String name, String description, String photo, String followings, String followers, int followingCount, int followerCount) {
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.followings = followings;
        this.followers = followers;
        this.followingCount = followingCount;
        this.followerCount = followerCount;
    }

    public User() {
        // empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoto() {
        return photo;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public String getFollowings() {
        return followings;
    }

    public String getFollowers() {
        return followers;
    }
}

package com.example.calla.heyhome;

import java.util.List;

/**
 * Created by Calla on 5/17/16.
 */
public class User {

    private String name;
    private String description;
    private String photo;
    private String followings;
    private String followers;
    private String favorites;

    public User(String name, String description, String photo, String followings, String followers, String favorites) {
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.followings = followings;
        this.followers = followers;
        this.favorites = favorites;
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

    public String getFollowings() {
        return followings;
    }

    public String getFollowers() {
        return followers;
    }

    public String getFavorite() {
        return favorites;
    }
}

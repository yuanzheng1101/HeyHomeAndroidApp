package com.example.calla.heyhome;

/**
 * Created by Calla on 5/17/16.
 */
public class User {

    String name;
    String passsword;
    String email;
    String description;
    String photo;
    int followingCount;
    int followerCount;

    public User(String name, String passsword, String email, String description, String photo, int followingCount, int followerCount) {
        this.name = name;
        this.passsword = passsword;
        this.email = email;
        this.description = description;
        this.photo = photo;
        this.followingCount = followingCount;
        this.followerCount = followerCount;
    }

    public User() {
        // empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }

    public String getName() {
        return name;
    }

    public String getPasssword() {
        return passsword;
    }

    public String getEmail() {
        return email;
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



}

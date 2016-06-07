package com.example.calla.heyhome;

/**
 * Created by Calla on 5/21/16.
 */
public class Gallery {

    private String brand;
    private String room;
    private String style;
    private String image;


    public Gallery(String brand, String room, String style, String image) {
        this.brand = brand;
        this.room = room;
        this.style = style;
        this.image = image;
    }

    public Gallery() {

    }

    public String getBrand() {
        return brand;
    }

    public String getRoom() {
        return room;
    }

    public String getStyle() {
        return style;
    }

    public String getImage() {
        return image;
    }
}

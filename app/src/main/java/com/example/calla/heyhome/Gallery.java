package com.example.calla.heyhome;

/**
 * Created by Calla on 5/21/16.
 */
public class Gallery {

    String brand;
    String room;
    String style;
    String image;
    String time;

    public Gallery(String brand, String room, String style, String image, String time) {
        this.brand = brand;
        this.room = room;
        this.style = style;
        this.image = image;
        this.time = time;
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
}

package com.example.spark;

public class BookTheVehicle {

    //private String spotBook;
    public String spot,time;

    public BookTheVehicle() {
    }

    public BookTheVehicle(String spot, String time) {
        this.spot = spot;
        this.time = time;
    }

    public String getSpot() {
        return spot;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

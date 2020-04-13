package com.example.spark;

public class BookTheVehicle {

    //private String spotBook;
    private String car_booking;

    public BookTheVehicle() {
    }

    public BookTheVehicle(String car_booking) {
        this.car_booking = car_booking;
    }

    public String getCar_booking() {
        return car_booking;
    }

    public void setCar_booking(String car_booking) {
        this.car_booking = car_booking;
    }
}

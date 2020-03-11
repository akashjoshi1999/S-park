package com.example.spark;

public class ParkingSpot{
    private String account,car_standing,city,cost,distance,email,name,place_name;
    private Double lat, lng;

    public ParkingSpot(String account, String car_standing, String city, String cost, String distance, String email, String name, String place_name, Double lat ,
                       Double lng) {
        this.account = account;
        this.car_standing = car_standing;
        this.city = city;
        this.cost = cost;
        this.distance = distance;
        this.email = email;
        this.name = name;
        this.place_name = place_name;
        this.lat = lat;
        this.lng = lng;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCar_standing() {
        return car_standing;
    }

    public void setCar_standing(String car_standing) {
        this.car_standing = car_standing;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
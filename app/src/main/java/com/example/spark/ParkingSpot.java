package com.example.spark;

public class ParkingSpot{
    private String account, carStanding,city,cost,distance,email,name, placeName;
    private Double lat, lng;

    public ParkingSpot(String account, String carStanding, String city, String cost, String distance, String email, String name, String placeName, Double lat ,
                       Double lng) {
        this.account = account;
        this.carStanding = carStanding;
        this.city = city;
        this.cost = cost;
        this.distance = distance;
        this.email = email;
        this.name = name;
        this.placeName = placeName;
        this.lat = lat;
        this.lng = lng;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCarStanding() {
        return carStanding;
    }

    public void setCarStanding(String carStanding) {
        this.carStanding = carStanding;
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

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
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
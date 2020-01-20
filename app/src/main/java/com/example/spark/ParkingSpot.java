package com.example.spark;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by LUCIFER on 22-03-2018.
 */

public class ParkingSpot implements Serializable {

    private int pincode;
    private String name;
    private long sensorCount;
    private HashMap<Integer, Sensor> sensor;
    private double latitiude;
    private double longitude;
    private double cost;
    private String address;

    public ParkingSpot() {
        sensor = new HashMap<>();
        cost = 1;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSensorCount() {
        return sensorCount;
    }

    public void setSensorCount(long sensorCount) {
        this.sensorCount = sensorCount;
    }

    public HashMap<Integer, Sensor> getSensor() {
        return sensor;
    }

    public void setSensor(HashMap<Integer, Sensor> sensor) { this.sensor = sensor; }

    public double getLatitiude() {
        return latitiude;
    }

    public void setLatitiude(double latitiude) {
        this.latitiude = latitiude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

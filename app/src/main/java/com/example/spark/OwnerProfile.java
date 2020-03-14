package com.example.spark;

public class OwnerProfile {
    public String name;
    public String OwnerGooglePayID;

    public OwnerProfile(String name, String ownerGooglePayID) {
        this.name = name;
        OwnerGooglePayID = ownerGooglePayID;
    }

    public String getOwnerGooglePayID() {
        return OwnerGooglePayID;
    }

    public void setOwnerGooglePayID(String ownerGooglePayID) {
        OwnerGooglePayID = ownerGooglePayID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.example.spark;

public class UserPayment {
    public String OwnerName,GoogleApiID;
    public int amount;

    public UserPayment(){

    }


    public UserPayment(String ownerName, String googleApiID, int amount) {
        OwnerName = ownerName;
        GoogleApiID = googleApiID;
        this.amount = amount;
    }
}

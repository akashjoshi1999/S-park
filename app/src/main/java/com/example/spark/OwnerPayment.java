package com.example.spark;

public class OwnerPayment {
    public String UserName,GoogleApiID;
    public int Amount;

    public OwnerPayment(){

    }

    public OwnerPayment(String userName, String googleApiID, int amount) {
        UserName = userName;
        GoogleApiID = googleApiID;
        Amount = amount;
    }
}

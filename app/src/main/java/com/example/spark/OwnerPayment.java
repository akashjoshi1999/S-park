package com.example.spark;

public class OwnerPayment {
    public String UserName,GoogleApiID,Amount;

    public OwnerPayment(){

    }

    public OwnerPayment(String ownerName, String googleApiID, String amount) {
        UserName = ownerName;
        GoogleApiID = googleApiID;
        Amount = amount;
    }
}

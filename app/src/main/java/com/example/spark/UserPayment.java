package com.example.spark;

public class UserPayment {
    public String OwnerName,GoogleApiID,Amount;

    public UserPayment(){

    }

    public UserPayment(String ownerName, String googleApiID, String amount) {
        OwnerName = ownerName;
        GoogleApiID = googleApiID;
        Amount = amount;
    }
}

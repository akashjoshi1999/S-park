package com.example.spark;

public class PaymentUser {
    public String name;
    public String UpiID;
    public int amount;

    public PaymentUser(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpiID() {
        return UpiID;
    }

    public void setUpiID(String upiID) {
        UpiID = upiID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public PaymentUser(String name, String upiID, int amount) {
        this.name = name;
        UpiID = upiID;
        this.amount = amount;
    }
}

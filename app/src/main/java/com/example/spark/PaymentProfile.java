package com.example.spark;

public class PaymentProfile {
    public String upiId;
    public String uName;

    public PaymentProfile(){

    }
    public PaymentProfile(String upiId, String uName) {
        this.upiId = upiId;
        this.uName = uName;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }
}

package com.example.spark;

public class PaymentProfile {
    public String name;
    public String Id;

    public PaymentProfile(){

    }

    public PaymentProfile(String name, String id) {
        this.name = name;
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}

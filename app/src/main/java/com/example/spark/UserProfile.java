package com.example.spark;

public class UserProfile {
    public String name;
    public String email;
    public String phone;
    public String upiId;

    public UserProfile(){

    }

    public UserProfile(String Name, String email, String phone, String upiId) {
        this.name = Name;
        this.email = email;
        this.phone = phone;
        this.upiId = upiId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }
}

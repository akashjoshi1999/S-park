package com.example.spark;

public class UserProfile {
    public String Name;
    public String email;
    public String phone;

    public UserProfile(){

    }

    public UserProfile(String Name, String email, String phone) {
        this.Name = Name;
        this.email = email;
        this.phone = phone;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}

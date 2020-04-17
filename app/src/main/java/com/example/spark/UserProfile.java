package com.example.spark;

public class UserProfile {
    public String account;
    public String name;
    public String email;
    public String phone;

    public UserProfile(){

    }

    public UserProfile(String account, String name, String email, String phone) {
        this.account = account;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

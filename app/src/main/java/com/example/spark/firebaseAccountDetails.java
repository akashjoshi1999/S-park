package com.example.spark;

public class firebaseAccountDetails {
    public String account,name,email,paasword;

    public firebaseAccountDetails(){

    }
    public firebaseAccountDetails(String acccount, String name, String email,String paasword) {
        this.account = acccount;
        this.name = name;
        this.email = email;
        this.paasword = paasword;
    }
}

package com.example.bikerx.entities;

public class User {
    //store user info in object to send to firebase
    public String fullName,email;
    public int age;

    public User(){

    }

    public User(String fullName, int age, String email){
        this.fullName=fullName;
        this.age=age;
        this.email=email;
    }
}

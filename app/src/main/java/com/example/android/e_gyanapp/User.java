package com.example.android.e_gyanapp;

public class User {
    public String id;
    public String userName;
    public String email;
    public String password;
    public String enrollment;

    public User(String id, String userName, String email, String password,String enrollment) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.enrollment=enrollment;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getEnrollment() {
        return enrollment;
    }
}

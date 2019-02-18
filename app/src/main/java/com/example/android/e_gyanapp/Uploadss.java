package com.example.android.e_gyanapp;

public class Uploadss {
    public String name;
    public String url;


    public String getAuthor() {
        return author;
    }

    public String getDescr() {
        return descr;
    }

    public String author;
    public String descr;


    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Uploadss() {
    }

    public Uploadss(String name, String author, String descr, String url) {
        this.name = name;
        this.author=author;
        this.descr=descr;

        this.url= url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
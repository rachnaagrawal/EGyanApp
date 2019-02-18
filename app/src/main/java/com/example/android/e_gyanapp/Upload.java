package com.example.android.e_gyanapp;

public class Upload {
    public String name;
    public String url;
    public String urlimage;
    public String author;
    public String shortdesc;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Upload() {
    }

    public Upload(String name, String author, String shortdesc, String urlimage, String url) {
        this.name = name;
        this.url = url;
        this.author=author;
        this.urlimage=urlimage;
        this.shortdesc=shortdesc;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthor() {
        return author;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public String getUrlimage() {
        return urlimage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlimage(String urlimage) {
        this.urlimage = urlimage;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }
}

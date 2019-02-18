package com.example.android.e_gyanapp;

public class BookEntry {
    private String book_title;
    private String book_desc;
    private int book_image;

    public BookEntry(String book_title, String book_desc, int book_image) {
        this.book_title = book_title;
        this.book_desc = book_desc;
        this.book_image = book_image;
    }

    public String getBook_title() {
        return book_title;
    }

    public String getBook_desc() {
        return book_desc;
    }

    public int getBook_image() {
        return book_image;
    }
}
package com.example.android.e_gyanapp;

public class Feedback_db {
    public static final String TABLE_NAME = "feedback";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_DESC = "description";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_NAME + " TEXT ,"
                    + COLUMN_NUMBER + " TEXT,"
                    + COLUMN_DESC + " TEXT "
                    + ")";


    private String sname;
    private String number;
    private String desc;

    public Feedback_db()
    {

    }

    public Feedback_db(String sname, String number, String desc)
    {

        this.sname=sname;
        this.number=number;
        this.desc=desc;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

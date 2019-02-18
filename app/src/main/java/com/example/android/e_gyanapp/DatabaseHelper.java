package com.example.android.e_gyanapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "stu_db";

    public DatabaseHelper(Context ctx)

    {
        super(ctx,DATABASE_NAME,null,DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Feedback_db.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP Table " +Feedback_db.TABLE_NAME);
    }


    public long insertNote(String name,String number,String desc) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Feedback_db.COLUMN_NAME, name);
        values.put(Feedback_db.COLUMN_NUMBER, number);
        values.put(Feedback_db.COLUMN_DESC, desc);

        // insert row
        long id = db.insert(Feedback_db.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
    /*public List<Feedback_db> ShowAllStudent()
    {
        List<Feedback_db> stu = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Feedback_db.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst())
        {
            do {

                Feedback_db s = new Feedback_db();

                s.setSname(cursor.getString(cursor.getColumnIndex(Feedback_db.COLUMN_NAME)));
                s.setNumber(cursor.getString(cursor.getColumnIndex(Feedback_db.COLUMN_NUMBER)));
                s.setDesc(cursor.getString(cursor.getColumnIndex(Feedback_db.COLUMN_DESC)));

                stu.add(s);
            } while (cursor.moveToNext());
        }

        return  stu;

    }*/


}

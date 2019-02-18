package com.example.android.e_gyanapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
TextView Textname,authorname,desc;
ImageView imagedoc;
    String bk;
    String name;
    private DatabaseReference mDatabase;
    final ArrayList<Upload> bookEntries = new ArrayList<Upload>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent i = this.getIntent();

        Bundle extras = i.getExtras();
        if (extras != null) {
            bk = extras.getString("booknm");
        }

        Textname = findViewById(R.id.TextName);
        authorname = findViewById(R.id.authorname);
        desc = findViewById(R.id.shortdesc);
        imagedoc = findViewById(R.id.bookpic);


         mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Upload bookEntry = postSnapshot.getValue(Upload.class);
                    bookEntries.add(bookEntry);
                }


                Upload obj =new Upload();
                obj.setName(bk);
                String namee=obj.getName();
                int n=0;
                while(n<bookEntries.size()){
                    String name=bookEntries.get(n).getName();
                    if(namee.equals(name)){
                       // Textname.setText(bookEntries.get(n).getName());
                        authorname.setText(name);
                    }
                    else{
                        n++;
                    }
                }

               /* Toast t=Toast.makeText(getApplicationContext(),bookEntries.get(3).getAuthor(),Toast.LENGTH_SHORT);
                t.show();*/
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






       // Toast t=Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_SHORT);
      //  t.show();

            //while(bk==bookEntries.get(in).getName()){

            //}
            // and get whatever type user account id is

        //Toast t=Toast.makeText(getApplicationContext(),bk,Toast.LENGTH_SHORT);
        //t.show();


    }
}

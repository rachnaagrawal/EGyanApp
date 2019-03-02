package com.example.android.e_gyanapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
TextView downloadbook,authorname,desc,bookname;
ImageView imagedoc;
    String bk;
    String name;
    private DatabaseReference mDatabase;
    final ArrayList<Upload> bookEntries = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent i = this.getIntent();

        Bundle extras = i.getExtras();
        if (extras != null) {
            bk = extras.getString("booknm");
        }

        bookname = findViewById(R.id.book_title);
        authorname = findViewById(R.id.authorname);
        desc = findViewById(R.id.shortdesc);
        imagedoc = findViewById(R.id.bookpic);
        downloadbook=findViewById(R.id.downloadbook);


         mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
        mDatabase.orderByChild("name").equalTo(bk).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    final Upload bookEntry = postSnapshot.getValue(Upload.class);
                    bookEntries.add(bookEntry);
                    authorname.setText(bookEntry.author);
                    desc.setText(bookEntry.shortdesc);
                    Glide.with(getBaseContext()).load(bookEntry.urlimage).into(imagedoc);
                   bookname.setText(bookEntry.name);
                   downloadbook.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Intent intent = new Intent(Intent.ACTION_VIEW);
                           intent.setData(Uri.parse(bookEntry.url));
                           startActivity(intent);
                       }
                   });
                }

              // Toast t=Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT);
                //t.show();
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

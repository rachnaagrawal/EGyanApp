package com.example.android.e_gyanapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import com.android.volley.toolbox.NetworkImageView;

class BookCardViewHolder extends RecyclerView.ViewHolder {
    protected ImageView book_image;
    protected TextView book_title;
    protected TextView book_desc;


    public BookCardViewHolder(@NonNull View itemView) {
        super(itemView);

        book_image=(ImageView)itemView.findViewById(R.id.book_image);
        book_title=(TextView)itemView.findViewById(R.id.book_title);
        book_desc=(TextView)itemView.findViewById(R.id.book_desc);
    }

}

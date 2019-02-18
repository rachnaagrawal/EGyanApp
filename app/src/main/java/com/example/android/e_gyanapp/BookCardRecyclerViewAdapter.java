package com.example.android.e_gyanapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.view.LayoutInflater;

import com.bumptech.glide.Glide;

class BookCardRecyclerViewAdapter extends RecyclerView.Adapter<BookCardViewHolder>
{  private List<Upload> bookEntryList;
   private Context context;



    public BookCardRecyclerViewAdapter(Context mcontext,List<Upload> bookEntryList) {
            super();
            this.context=mcontext;
            this.bookEntryList=bookEntryList;

        }


    @NonNull
    @Override
    public BookCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book_card, viewGroup, false);
        return new BookCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookCardViewHolder bookCardViewHolder, int i) {
        final Upload bi=bookEntryList.get(i);
        if(bookEntryList!=null && i<bookEntryList.size())
        {
        bookCardViewHolder.book_title.setText(bi.getName());
        bookCardViewHolder.book_desc.setText(bi.getAuthor());

        Glide.with(context).load(bi.getUrlimage()).into(bookCardViewHolder.book_image);
        }

        bookCardViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,DetailActivity.class);
                i.putExtra("booknm",bi.getName());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookEntryList.size();
    }
}

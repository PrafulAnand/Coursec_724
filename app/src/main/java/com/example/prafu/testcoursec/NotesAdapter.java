package com.example.prafu.testcoursec;

/**
 * Created by prafu on 3/28/2017.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder>{

    String[] values;
    Context context1;
    int [] icons;
    public NotesAdapter(Context context2, String[] values2, int[] icons1){

        values = values2;

        context1 = context2;
        icons=icons1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public ImageView icon_notes;
        public CardView notescardview;
        public ViewHolder(View v){

            super(v);

            textView = (TextView) v.findViewById(R.id.category_notes);
            icon_notes=(ImageView)v.findViewById(R.id.img_notes_icon);
            notescardview=(CardView)v.findViewById(R.id.cardview1);
        }
    }

    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View view1 = LayoutInflater.from(context1).inflate(R.layout.notes_recycleritems,parent,false);

        ViewHolder viewHolder1 = new ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position){
        Vholder.icon_notes.setImageResource(icons[position]);
        Vholder.textView.setText(values[position]);

        Vholder.textView.setBackgroundColor(Color.WHITE);

        Vholder.textView.setTextColor(Color.BLACK);


    }

    @Override
    public int getItemCount(){

        return values.length;
    }
}
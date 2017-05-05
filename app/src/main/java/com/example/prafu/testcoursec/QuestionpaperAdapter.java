package com.example.prafu.testcoursec;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by prafu on 3/30/2017.
 */

public class QuestionpaperAdapter extends RecyclerView.Adapter<QuestionpaperAdapter.ViewHolder>{

    String[] category_questionpapers;
    Context context_questionpapaers;
    int [] icons_questionpapers;

    public QuestionpaperAdapter(Context context2, String[] values2, int[] icons1){

        category_questionpapers = values2;

        context_questionpapaers = context2;
        icons_questionpapers=icons1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView question_paperstextView;
        public ImageView questionpapers;

        public ViewHolder(View v){

            super(v);

            question_paperstextView = (TextView) v.findViewById(R.id.category_questions);
            questionpapers=(ImageView)v.findViewById(R.id.img_questionpapers_icon);

        }
    }

    @Override
    public QuestionpaperAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View view1 = LayoutInflater.from(context_questionpapaers).inflate(R.layout.question_papers_recycleritems,parent,false);

        QuestionpaperAdapter.ViewHolder viewHolder1 = new QuestionpaperAdapter.ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(QuestionpaperAdapter.ViewHolder Vholder, int position){
        Vholder.questionpapers.setImageResource(icons_questionpapers[position]);

        Vholder.question_paperstextView.setText(category_questionpapers[position]);

        Vholder.question_paperstextView.setBackgroundColor(Color.WHITE);

        Vholder.question_paperstextView.setTextColor(Color.BLACK);


    }

    @Override
    public int getItemCount(){

        return category_questionpapers.length;
    }
}


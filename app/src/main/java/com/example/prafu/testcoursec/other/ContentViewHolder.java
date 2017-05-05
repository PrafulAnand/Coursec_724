package com.example.prafu.testcoursec.other;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.prafu.testcoursec.R;

/**
 * Created by prafu on 4/2/2017.
 */


public class ContentViewHolder extends RecyclerView.ViewHolder {

    View mView;
    TextView subName;
    TextView titleTextView;



    public ContentViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setSubject(String subject){
        subName = (TextView)mView.findViewById(R.id.subName);
        subName.setText(subject);
    }

    public  void setTitle(String title){
        titleTextView = (TextView) mView.findViewById(R.id.titleTextView);
        titleTextView.setText(title);
    }




}

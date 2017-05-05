package com.example.prafu.testcoursec.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prafu.testcoursec.R;

import java.util.ArrayList;

/**
 * Created by prafu on 4/2/2017.
 */

public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.NotesViewHolder> {

    ArrayList<String> categories;
    Context context;

    public BranchAdapter(ArrayList<String> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }




    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_recycleritems, parent, false);
        NotesViewHolder holder = new NotesViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {

        holder.notes_category.setText(categories.get(position));
        if(categories.get(position).equalsIgnoreCase("Computer Science"))
        {
            holder.notes_image.setImageResource(R.drawable.computerscience);
        }
        else
        {
            holder.notes_image.setImageResource(R.drawable.civil);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class  NotesViewHolder extends RecyclerView.ViewHolder{

        CardView notesCardView;
        TextView notes_category;
ImageView notes_image;
        public NotesViewHolder(View itemView) {
            super(itemView);

            notesCardView = (CardView) itemView.findViewById(R.id.cardview1);
            notes_category = (TextView) itemView.findViewById(R.id.category_notes);
            notes_image=(ImageView)itemView.findViewById(R.id.img_notes_icon);
        }
    }



}

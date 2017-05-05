package com.example.prafu.testcoursec;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prafu.testcoursec.MainActivities.Notes_Branches;
import com.example.prafu.testcoursec.MainActivities.Show_Notes;
import com.example.prafu.testcoursec.other.RecyclerItemClickListener;


public class notes_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private String selectedCategory;
    RecyclerView recyclerView;

    Context context;

    RecyclerView.Adapter recyclerView_Adapter;

    RecyclerView.LayoutManager recyclerViewLayoutManager;

    String[] categories = {
            "Engineering",
            "Law",
            "Commerce",
            "Foreign Language",
            "Science",
            "Competitive Exams",
            "Management",
            "Arts",
            "Medical",
            "Literature",
            "Journalism",
            "School Notes"

    };

    int[] notes_icon = {
            R.drawable.engineering,
            R.drawable.law,
            R.drawable.commerce,
            R.drawable.foreignlanguage,
            R.drawable.science,
            R.drawable.exam,
            R.drawable.management,
            R.drawable.arts,
            R.drawable.medical,
            R.drawable.literature,
            R.drawable.journalism,
            R.drawable.schoolnotes,
    };


    public notes_fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.notes_fragment, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view1);


        //Change 2 to your choice because here 2 is the number of Grid layout Columns in each row.
        recyclerViewLayoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        recyclerView_Adapter = new NotesAdapter(getContext(), categories, notes_icon);

        recyclerView.setAdapter(recyclerView_Adapter);
       recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),recyclerView,new RecyclerItemClickListener.OnItemClickListener(){
           @Override
           public void onItemClick(View view, int position) {
               selectedCategory = categories[position];
               Log.d("SelectedCateogry",selectedCategory);
               if(selectedCategory.equalsIgnoreCase("Engineering")){
                   Intent to_branch = new Intent(getContext(),Notes_Branches.class);
                   to_branch.putExtra("selectedCategory",selectedCategory);
                   startActivity(to_branch);
               }
               else{
                   Intent to_notes = new Intent(getContext(), Show_Notes.class);
                   to_notes.putExtra("category",selectedCategory);
                   startActivity(to_notes);
               }

           }

           @Override
           public void onLongItemClick(View view, int position) {

           }
       }));



        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event


}

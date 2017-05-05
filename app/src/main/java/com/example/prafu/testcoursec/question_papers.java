package com.example.prafu.testcoursec;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prafu.testcoursec.MainActivities.Show_QuestionPaper;
import com.example.prafu.testcoursec.other.RecyclerItemClickListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * to handle interaction events.
 *
 * create an instance of this fragment.
 */
public class question_papers extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    RecyclerView recyclerView_questionpapers;

    Context context;
    private String selectedBranch;

    RecyclerView.Adapter recyclerView_Adapter_questionpapers;

    RecyclerView.LayoutManager recyclerViewLayoutManager_questionpapers;

    String[] categories_questionpapers = {
            "Computer Science ",
            "Electrical & Electronics ",
            "Mechanical ",
            "Aeronautical",
            "Civil ",
            "Information Technology",
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
            "Boards"

    };

    int[] questionpapers_icon = {
            R.drawable.computerscience,
            R.drawable.electrical,
            R.drawable.mechanical,
            R.drawable.aeronautical,
            R.drawable.civil,
            R.drawable.it,
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

    public question_papers() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment question_papers.
     */
    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_question_papers, container, false);
        recyclerView_questionpapers = (RecyclerView) v.findViewById(R.id.recycler_view2);


        //Change 2 to your choice because here 2 is the number of Grid layout Columns in each row.
        recyclerViewLayoutManager_questionpapers = new GridLayoutManager(getContext(), 2);

        recyclerView_questionpapers.setLayoutManager(recyclerViewLayoutManager_questionpapers);

        recyclerView_Adapter_questionpapers = new QuestionpaperAdapter(getContext(), categories_questionpapers, questionpapers_icon);

        recyclerView_questionpapers.setAdapter(recyclerView_Adapter_questionpapers);

        recyclerView_questionpapers.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),recyclerView_questionpapers,new RecyclerItemClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                selectedBranch = categories_questionpapers[position];
                Intent show_question = new Intent(getContext(), Show_QuestionPaper.class);
                show_question.putExtra("branch",selectedBranch);
                startActivity(show_question);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        return v;
    }
    // TODO: Rename method, update argument and hook method into UI event





    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}

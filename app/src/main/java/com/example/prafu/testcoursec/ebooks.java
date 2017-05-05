package com.example.prafu.testcoursec;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.prafu.testcoursec.MainActivities.Ebook_PdfViewing;
import com.example.prafu.testcoursec.other.ContentViewHolder;
import com.example.prafu.testcoursec.other.Data;
import com.example.prafu.testcoursec.other.RecyclerItemClickListener;
import com.example.prafu.testcoursec.other.Utils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ebooks extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private RecyclerView showebookRecycler;
    private TextView tv_no_data;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    FirebaseRecyclerAdapter<Data,ContentViewHolder> firebaseRecyclerAdapter;
    DatabaseReference show_ebooks;
    private RecyclerView.AdapterDataObserver mObserver;
    private ProgressBar mprogressBar;

    //private String branch;

    private void setProgressVisible(Boolean b){
        if(b== true){
            mprogressBar.setVisibility(View.VISIBLE);
        }
        else if(b == false){
            mprogressBar.setVisibility(View.INVISIBLE);
        }
    }


    public ebooks() {
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
        View v=inflater.inflate(R.layout.fragment_ebooks, container, false);

        firebaseDatabase = Utils.getDatabase();
        databaseReference = firebaseDatabase.getReference();

        showebookRecycler = (RecyclerView) v.findViewById(R.id.showebookRecycler);
        tv_no_data = (TextView) v.findViewById(R.id.tv_no_data_question);

        //Bundle b = getIntent().getExtras();
        //branch = b.getString("branch");
        //setTitle(branch);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mprogressBar = (ProgressBar) v.findViewById(R.id.pBar5);
        setProgressVisible(true);

        show_ebooks = databaseReference.child("Temp").child("Category").child("Ebook");

        mObserver = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                tv_no_data.setVisibility(View.GONE);
                super.onItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                if(firebaseRecyclerAdapter.getItemCount()<1){
                    tv_no_data.setVisibility(View.VISIBLE);
                }
                super.onItemRangeRemoved(positionStart, itemCount);
            }
        };

        show_ebooks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setProgressVisible(false);
                if(dataSnapshot.hasChildren()== false){
                    tv_no_data.setVisibility(View.VISIBLE);
                }
                else
                    tv_no_data.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        showebookRecycler.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), showebookRecycler ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        DatabaseReference dRef = firebaseRecyclerAdapter.getRef(position);
                        String key = dRef.getKey();
                        Intent transfer = new Intent(getContext(),Ebook_PdfViewing.class);
                        transfer.putExtra("key",key);
                       // transfer.putExtra("branch",branch);
                        startActivity(transfer);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Data, ContentViewHolder>(

                Data.class,
                R.layout.recycle_items,
                ContentViewHolder.class,
                show_ebooks

        ) {
            @Override
            protected void populateViewHolder(ContentViewHolder viewHolder, Data model, int position) {

                viewHolder.setSubject(model.getSubject());
                viewHolder.setTitle(model.getTitle());
                Log.d("ContentViewHol",model.getTitle());

            }
        };

        showebookRecycler.setAdapter(firebaseRecyclerAdapter);
        showebookRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseRecyclerAdapter.registerAdapterDataObserver(mObserver);
    }


}



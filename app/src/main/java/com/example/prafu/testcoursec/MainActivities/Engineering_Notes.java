package com.example.prafu.testcoursec.MainActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.prafu.testcoursec.other.Utils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.example.prafu.testcoursec.MainActivity;
import com.example.prafu.testcoursec.R;
import com.example.prafu.testcoursec.other.ContentViewHolder;
import com.example.prafu.testcoursec.other.Data;
import com.example.prafu.testcoursec.other.RecyclerItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Engineering_Notes extends AppCompatActivity {

    private RecyclerView showNotesEnggRecycler;
    private String category;
    private TextView tv_no_data;

    private ProgressBar mprogressBar;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
   FirebaseRecyclerAdapter<Data,ContentViewHolder> firebaseRecyclerAdapter;

    DatabaseReference show_notes;
    private RecyclerView.AdapterDataObserver mObserver;

    private void setProgressVisible(Boolean b){
        if(b== true){
            mprogressBar.setVisibility(View.VISIBLE);
        }
        else if(b == false){
            mprogressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineering__notes);

        Bundle b = getIntent().getExtras();
        category = b.getString("category");
        setTitle(category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        showNotesEnggRecycler = (RecyclerView) findViewById(R.id.engineeringNotesRecycler);
        tv_no_data=(TextView) findViewById(R.id.tv_no_data_engg);

        firebaseDatabase = Utils.getDatabase();
        databaseReference = firebaseDatabase.getReference();

        mprogressBar = (ProgressBar)findViewById(R.id.pBar3);
        setProgressVisible(true);

        show_notes = databaseReference.child("Temp").child("Category").child("Notes").child("Engineering").child(category);

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

        show_notes.addListenerForSingleValueEvent(new ValueEventListener() {
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

        showNotesEnggRecycler.addOnItemTouchListener(new RecyclerItemClickListener(this, showNotesEnggRecycler ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        DatabaseReference dRef = firebaseRecyclerAdapter.getRef(position);
                        String key = dRef.getKey();
                        Intent transfer = new Intent(Engineering_Notes.this, PdfViewing.class);
                        transfer.putExtra("branch",category);
                        transfer.putExtra("key",key);
                        startActivity(transfer);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );



    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Data, ContentViewHolder>(

                Data.class,
                R.layout.recycle_items,
                ContentViewHolder.class,
                show_notes

        ) {
            @Override
            protected void populateViewHolder(ContentViewHolder viewHolder, Data model, int position) {

                viewHolder.setSubject(model.getSubject());
                viewHolder.setTitle(model.getTitle());
                Log.d("ContentViewHol",model.getTitle());

            }
        };

        showNotesEnggRecycler.setAdapter(firebaseRecyclerAdapter);
        showNotesEnggRecycler.setLayoutManager(new LinearLayoutManager(Engineering_Notes.this));
        firebaseRecyclerAdapter.registerAdapterDataObserver(mObserver);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}

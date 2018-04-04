package com.example.sara.tripplanner;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import java.util.ArrayList;

import com.daimajia.swipe.util.Attributes;

import java.lang.annotation.Annotation;
import java.util.ArrayList;


public class home extends AppCompatActivity {

    FloatingActionButton fab,fab1,fab2;
    Animation fabOpen;
    Animation fabClose;
    Animation rotateForward;
    Animation rotatebackward;
    boolean isOpen= false;
    private TextView tvEmptyTextView;
    private RecyclerView mRecyclerView;
    private ArrayList<Trip> mDataSet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);

      //  setSupportActionBar(toolbar);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab1=(FloatingActionButton)findViewById(R.id.fab1);
        fab2=(FloatingActionButton)findViewById(R.id.fab2);

        fabOpen= AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fabClose= AnimationUtils.loadAnimation(this,R.anim.fab_close);
        rotateForward= AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        rotatebackward= AnimationUtils.loadAnimation(this,R.anim.rotate_backward);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animateFab();
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(home.this,"car",Toast.LENGTH_LONG).show();
            }
        });


        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(home.this,"swip",Toast.LENGTH_LONG).show();
            }
        });

        tvEmptyTextView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataSet = new ArrayList<>();

        if(mDataSet.isEmpty()){
            mRecyclerView.setVisibility(View.GONE);
            tvEmptyTextView.setVisibility(View.VISIBLE);
        }else{
            mRecyclerView.setVisibility(View.VISIBLE);
            tvEmptyTextView.setVisibility(View.GONE);
        }

        SwipeRecyclerViewAdapter mAdapter = new SwipeRecyclerViewAdapter(this, mDataSet);

        ((SwipeRecyclerViewAdapter) mAdapter).setMode(Attributes.Mode.Single);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("RecyclerView", "onScrollStateChanged");
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });



    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    private void animateFab(){

        if(isOpen){

         fab.startAnimation(rotateForward);
         fab1.startAnimation(fabClose);
         fab2.startAnimation(fabClose);
         fab1.setClickable(false);
         fab2.setClickable(false);
         isOpen=false;


        }else {

            fab.startAnimation(rotatebackward);
            fab1.startAnimation(fabOpen);
            fab2.startAnimation(fabOpen);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isOpen=true;
        }
    }

}

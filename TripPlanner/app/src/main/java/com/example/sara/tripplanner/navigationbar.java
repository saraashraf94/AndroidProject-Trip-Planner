package com.example.sara.tripplanner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class navigationbar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab,fab1,fab2;
    Animation fabOpen;
    Animation fabClose;
    Animation rotateForward;
    Animation rotatebackward;
    FirebaseUser user;
    Intent goToDetails = new Intent();
    private Button start;
    private List<Trip> Trips;
    private RecyclerView rv;
    private Button navbutton2;
    boolean isOpen= false;
    NavigationView navView;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab= findViewById(R.id.fab);
        fab1= findViewById(R.id.fab1);
        fab2= findViewById(R.id.fab2);
        navView= findViewById(R.id.nav_view);
        fabOpen= AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fabClose= AnimationUtils.loadAnimation(this,R.anim.fab_close);
        rotateForward= AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        rotatebackward= AnimationUtils.loadAnimation(this,R.anim.rotate_backward);
        navView.setItemIconTintList(null);
        rv = findViewById(R.id.rv);
        user = FirebaseAuth.getInstance().getCurrentUser();
        LinearLayoutManager llm = new LinearLayoutManager(navigationbar.this);
        rv.setLayoutManager(llm);
        navbutton2=(Button)findViewById(R.id.past_trip);

//        navbutton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(), "Past Trip", Toast.LENGTH_SHORT).show();
//
//            }
//        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animateFab();
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(navigationbar.this,"Tow Way",Toast.LENGTH_LONG).show();

                startActivity(new Intent(getApplicationContext(),MainActivity2.class));

            }
        });


        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

                Toast.makeText(navigationbar.this,"One Way",Toast.LENGTH_LONG).show();
            }
        });

        initializeData();
        initializeAdapter();
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigationbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        }

          else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;



    }

    SwipeRecyclerViewAdapter adapter = new SwipeRecyclerViewAdapter(Trips);
    private void initializeData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        assert user != null;
        Query myMostViewedPostsQuery = firebaseDatabase.getReference().child(user.getUid()).child("Trips")
                .orderByChild("status")
                .equalTo("upComing");

        Query myMostViewedPostsQuery2 = firebaseDatabase.getReference().child(user.getUid()).child("Trip2way")
                .orderByChild("status")
                .equalTo("upComing");

        myMostViewedPostsQuery2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Trips = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Trip trip = snapshot.getValue(Trip.class);
                    assert trip != null;
                    trip.setKey(snapshot.getKey());
                    Trips.add(trip);
                }

                adapter.updateTrips(Trips);
                rv.setLayoutManager(new LinearLayoutManager(navigationbar.this));
                rv.setAdapter(adapter);
//                    Log.i("Snapshot is", dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        myMostViewedPostsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Trips = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Trip trip = snapshot.getValue(Trip.class);
                    assert trip != null;
                    trip.setKey(snapshot.getKey());
                    Trips.add(trip);
                }

                adapter.updateTrips(Trips);
                rv.setLayoutManager(new LinearLayoutManager(navigationbar.this));
                rv.setAdapter(adapter);
//                    Log.i("Snapshot is", dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

            private void initializeAdapter() {

                rv.setAdapter(adapter);
            }

    public void goToPastTrips(MenuItem item) {
        Toast.makeText(this, "Hello,Iam Past Trips", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,past_trips_activity.class));
    }

    public void showMap(MenuItem item) {
        final  String uri ="https://www.google.com/maps/dir/?api=1&map_action=pano&origin=Mansoura&destination=Cairo";
        final  Intent mapIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(uri));
        mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mapIntent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }



        }

package com.example.sara.tripplanner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class past_trips_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView pastTripsRecyclerView;
    ArrayList pastTrips = new ArrayList();
    List<Trip> Trips = new ArrayList<>();
    pastTripsAdapter adapter = new pastTripsAdapter(Trips);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.past_trips_activity);
        pastTripsRecyclerView = findViewById(R.id.pastTrips);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        pastTripsRecyclerView.setLayoutManager(llm);
        initializeData();
    }

    private void initializeData(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        assert user != null;
        Query myMostViewedPostsQuery = firebaseDatabase.getReference().child(user.getUid()).child("Trips")
                .orderByChild("status")
                .equalTo("done");
        Log.i("Query is",myMostViewedPostsQuery.toString());
        myMostViewedPostsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("Trips with status is ",dataSnapshot.toString());
                Log.i("Snapshot Count is", String.valueOf(dataSnapshot.getChildrenCount()));
                Trips = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Trip trip = snapshot.getValue(Trip.class);
                    Trips.add(trip);
                }
                adapter.updateTrips(Trips);
                pastTripsRecyclerView.setLayoutManager(new LinearLayoutManager(past_trips_activity.this));
                pastTripsRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(past_trips_activity.this, "SomeError"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}

//    Tmam Ya Sahby.
//    private void initializeData(){
//        FirebaseDatabase database =FirebaseDatabase.getInstance();
//        database.getReference().child(user.getUid()).child("Trips").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Trips = new ArrayList<>();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Trip trip = snapshot.getValue(Trip.class);
//                    Trips.add(trip);
//                }
//                adapter.updateTrips(Trips);
//                pastTripsRecyclerView.setLayoutManager(new LinearLayoutManager(past_trips_activity.this));
//                pastTripsRecyclerView.setAdapter(adapter);
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(past_trips_activity.this, "Cancel Yabny", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
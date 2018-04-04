package com.example.sara.tripplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class details_activity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        Intent tripIntent = getIntent();
        TextView tripName = findViewById(R.id.tripName);
        TextView tripDate = findViewById(R.id.tripDate);
        TextView tripTime=findViewById(R.id.tripTime);
        TextView tripFrom=findViewById(R.id.placeFrom);
        TextView tripto=findViewById(R.id.placeTo);
        TextView status=findViewById(R.id.status);

        tripName.setText(tripIntent.getStringExtra("Name"));
        tripDate.setText(tripIntent.getStringExtra("Date"));
        tripTime.setText(tripIntent.getStringExtra("Time"));
        tripFrom.setText(tripIntent.getStringExtra("Placefrom"));
        tripto.setText(tripIntent.getStringExtra("Placeto"));
        status.setText(tripIntent.getStringExtra("Status"));

    }
}

package com.example.sara.tripplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditActvity extends AppCompatActivity {
    EditText tripName,tripDate,tripTime,tripFrom;
    TextView tripto;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    String pKey;
    Button saveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_actvity);
        firebaseAuth = FirebaseAuth.getInstance();

        pKey = getIntent().getStringExtra("key");
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Intent tripIntent = getIntent();
        tripName = findViewById(R.id.tripName);
        tripDate = findViewById(R.id.tripDate);
        tripTime = findViewById(R.id.tripTime);
        tripFrom = findViewById(R.id.placeFrom);
        tripto = findViewById(R.id.placeTo);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child(user.getUid()).child("Trips").child(pKey);
        TextView status = findViewById(R.id.status);

        tripName.setText(tripIntent.getStringExtra("Name"));
        tripDate.setText(tripIntent.getStringExtra("Date"));
        tripTime.setText(tripIntent.getStringExtra("Time"));
        tripFrom.setText(tripIntent.getStringExtra("Placefrom"));
        tripto.setText(tripIntent.getStringExtra("Placeto"));
        status.setText(tripIntent.getStringExtra("Status"));
    saveBtn=findViewById(R.id.save);
    saveBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getValues();
        }
    });
    }

    public void getValues(){

        String Name=tripName.getText().toString().trim();
        String Date=tripDate.getText().toString().trim();
        String Time=tripTime.getText().toString().trim();
        String PlaceForm=tripFrom.getText().toString().trim();
        String PlaceTo=tripto.getText().toString().trim();
        String  Statuse="upComing";
        // String  key=reference.getKey();

        //aprogress.show();
        Trip trip=new Trip(Name,Date,Time,null,PlaceForm,PlaceTo,Statuse);

        reference.setValue(trip);
        //aprogress.dismiss();
        Toast.makeText(EditActvity.this,"Updated",Toast.LENGTH_LONG).show();

    }


}


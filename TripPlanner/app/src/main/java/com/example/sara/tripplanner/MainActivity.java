package com.example.sara.tripplanner;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.PlaceDetectionClient;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, View.OnClickListener
{


    private static final String TAG = "MainActivity";
    private FirebaseDatabase database;
    private DatabaseReference reference;
    public Button sentBtn;
    public TextView name;
    Trip trip;
    ProgressDialog aprogress;
    DateFormat formatdate=DateFormat.getDateInstance();
    DateFormat formattime=DateFormat.getTimeInstance();
    Calendar dateobj= Calendar.getInstance();
    Calendar timeobj= Calendar.getInstance();
    public Button dateBtn;
    public Button timeBtn;
    public  TextView dateText;
    public  TextView timeText;
    public  TextView noteText;
    public  TextView statuse;
    public  TextView key;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private static final int RC_SIGN_IN = 9001;
    private AutoCompleteTextView mAutocompleteTextView;
    private AutoCompleteTextView autoComplete2TextView;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private FirebaseAuth firebaseAuth;
    private  Button mapbtn;
    private Button logoutBtn;


    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();


        FirebaseUser user=firebaseAuth.getCurrentUser();


        sentBtn = findViewById(R.id.send);
        name = findViewById(R.id.userName);
        dateBtn= findViewById(R.id.datebtn);
        timeBtn= findViewById(R.id.timebtn);
        dateText= findViewById(R.id.datetext);
        timeText= findViewById(R.id.timeText);
        noteText= findViewById(R.id.noteText);
        statuse=findViewById(R.id.status);
        mAutocompleteTextView = findViewById(R.id.autoCompleteTextView);
        mAutocompleteTextView.setThreshold(3);
        autoComplete2TextView = findViewById(R.id.autoComplete2TextView);
        autoComplete2TextView.setThreshold(3);
        logoutBtn= findViewById(R.id.logoutbtn);
        mapbtn= findViewById(R.id.mapbtn);
        aprogress = new ProgressDialog(this);
        aprogress.setMessage("saveing..");
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child(user.getUid()).child("Trips");


        updatedateLabel();
        updatetimeLabel();

        logoutBtn.setOnClickListener(this);

        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();


        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);

        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();

            }
        });

        autoComplete2TextView.setOnItemClickListener(mAutocompleteClickListener);
        autoComplete2TextView.setAdapter(mPlaceArrayAdapter);


        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime();

            }
        });



        sentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getValues();
            }
        });


        mapbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               goToMap();
            }
        });


    }

    public void goToMap(){

        Uri gmmIntentUri = Uri.parse("google.navigation:d=Taronga+Zoo,+Sydney+Australia");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);



    }


    private void updateDate(){

        DatePickerDialog dilog=   new DatePickerDialog(this,d,dateobj.get(Calendar.YEAR),dateobj.get(Calendar.MONTH),dateobj.get(Calendar.DAY_OF_MONTH));
        dilog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        dilog.show();

    }


    private void updateTime(){

        new TimePickerDialog(this,t,timeobj.get(Calendar.HOUR_OF_DAY),timeobj.get(Calendar.MINUTE),true).show();
    }



    DatePickerDialog.OnDateSetListener d =new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


            dateobj.set(Calendar.YEAR, year);
            dateobj.set(Calendar.MONTH, monthOfYear);
            dateobj.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updatedateLabel();
        }
    };

    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            timeobj.set(Calendar.HOUR_OF_DAY, hourOfDay);
            timeobj.set(Calendar.MINUTE, minute);
            updatetimeLabel();

        }
    };



    private void updatedateLabel(){
        dateText.setText(formatdate.format(dateobj.getTime()));

    }

    private void updatetimeLabel(){
        timeText.setText(formattime.format(timeobj.getTime()));

    }



    public void getValues(){

        String Name=name.getText().toString().trim();
        String Date=dateText.getText().toString().trim();
        String Time=timeText.getText().toString().trim();
        String Note=noteText.getText().toString().trim();
        String PlaceForm=mAutocompleteTextView.getText().toString().trim();
        String PlaceTo=autoComplete2TextView.getText().toString().trim();
        String  Statuse="upComing";
       // String  key=reference.getKey();

        aprogress.show();
        Trip trip=new Trip(Name,Date,Time,Note,PlaceForm,PlaceTo,Statuse);

        reference.push().setValue(trip);
        aprogress.dismiss();
        Toast.makeText(MainActivity.this,"Inserted",Toast.LENGTH_LONG).show();

    }


    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);


            final String placeId = String.valueOf(item.placeId);
            Log.i(TAG, "place id: " + item.placeId);


            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };





    public ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);

            CharSequence attributions = places.getAttributions();

           Intent intent = new Intent(MainActivity.this, GoogleApiMap.class);
Log.i(TAG, "laaang " +place.getLatLng());

        }
    };





    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(TAG, "Google Places API connected.");
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(TAG, "Google Places API connection suspended.");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.e(TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View view) {

        if(view==logoutBtn){

            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,Login.class));
        }

    }


}


package com.example.sara.tripplanner;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, View.OnClickListener
{


    private static final String TAG = "MainActivity";
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private DatabaseReference reference2;
    public Button sentBtn;
    public TextView name;
    Trip2way trip2obj1;
    Trip2way trip2obj2;
    ProgressDialog aprogress;
    DateFormat formatdate=DateFormat.getDateInstance();
    DateFormat formattime=DateFormat.getTimeInstance();
    Calendar dateobj= Calendar.getInstance();
    Calendar timeobj= Calendar.getInstance();
    public Button dateBtn;
    public Button timeBtn;
    public TextView note1;
    public TextView note2;
    public  TextView dateText;
    public  TextView dateText2;
    public  TextView TimeText2;
    private AutoCompleteTextView autoCompleteTextViewsf;
    private AutoCompleteTextView autoCompleteTextViewst;
    public Button dateBtn2;
    public Button timeBtn2;
    public  TextView timeText;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private static final int RC_SIGN_IN = 9001;
    private AutoCompleteTextView mAutocompleteTextView;
    private AutoCompleteTextView autoComplete2TextView;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private FirebaseAuth firebaseAuth;
    private  Button mapbtn;
    public TextView States;
    public TextView States2;
    private Button logoutBtn;

    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        firebaseAuth=FirebaseAuth.getInstance();


        FirebaseUser user=firebaseAuth.getCurrentUser();


        sentBtn = (Button) findViewById(R.id.send);
        name = (TextView) findViewById(R.id.userName);
        dateBtn=(Button)findViewById(R.id.datebtn);
        timeBtn=(Button)findViewById(R.id.timebtn);
        dateText=(TextView)findViewById(R.id.datetext);
        dateText2=(TextView)findViewById(R.id.date2text);
        timeText=(TextView)findViewById(R.id.timeText);
        TimeText2=(TextView)findViewById(R.id.time2Text);
        note1=(TextView)findViewById(R.id.noteText1);
        note2=(TextView)findViewById(R.id.noteText2);



        States=(TextView)findViewById(R.id.status);
        States2=(TextView)findViewById(R.id.statuse2);



        autoCompleteTextViewsf= (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewsf);
        autoCompleteTextViewsf.setThreshold(3);
        autoCompleteTextViewst= (AutoCompleteTextView) findViewById(R.id.autoComplete2TextViewst);
        autoCompleteTextViewst.setThreshold(3);
        dateBtn2=(Button)findViewById(R.id.date2btn);
        timeBtn2=(Button)findViewById(R.id.time2btn);
       // noteText=(TextView)findViewById(R.id.noteText);
        mAutocompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        mAutocompleteTextView.setThreshold(3);
        autoComplete2TextView = (AutoCompleteTextView) findViewById(R.id.autoComplete2TextView);
        autoComplete2TextView.setThreshold(3);

        logoutBtn=(Button)findViewById(R.id.logoutbtn);
       /// UserEmail=(TextView)findViewById(R.id.userEmail);

        aprogress = new ProgressDialog(this);
        aprogress.setMessage("saveing..");
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child(user.getUid()).child("Trip2way");
        //reference2 = database.getReference("Trip2way").child(user.getUid());

        updatedateLabel();
        updatetimeLabel();

        logoutBtn.setOnClickListener(this);

        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity2.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();


        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);

        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);

        dateBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate2();

            }
        });

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

        timeBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime2();

            }


        });


        sentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getValues2();
            }
        });




    }



    private void updateDate(){

        DatePickerDialog dilog=   new DatePickerDialog(this,d,dateobj.get(Calendar.YEAR),dateobj.get(Calendar.MONTH),dateobj.get(Calendar.DAY_OF_MONTH));
        dilog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        dilog.show();

    }



    private void updateDate2(){

        DatePickerDialog dilog2=   new DatePickerDialog(this,d2,dateobj.get(Calendar.YEAR),dateobj.get(Calendar.MONTH),dateobj.get(Calendar.DAY_OF_MONTH));
        dilog2.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        dilog2.show();

    }



    private void updateTime(){

        new TimePickerDialog(this,t,timeobj.get(Calendar.HOUR_OF_DAY),timeobj.get(Calendar.MINUTE),true).show();
    }


    private void updateTime2(){

        new TimePickerDialog(this,t2,timeobj.get(Calendar.HOUR_OF_DAY),timeobj.get(Calendar.MINUTE),true).show();
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


    DatePickerDialog.OnDateSetListener d2 =new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


            dateobj.set(Calendar.YEAR, year);
            dateobj.set(Calendar.MONTH, monthOfYear);
            dateobj.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updatedateLabel2();
        }
    };

    TimePickerDialog.OnTimeSetListener t2=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            timeobj.set(Calendar.HOUR_OF_DAY, hourOfDay);
            timeobj.set(Calendar.MINUTE, minute);
            updatetimeLabel2();

        }
    };



    private void updatedateLabel(){
        dateText.setText(formatdate.format(dateobj.getTime()));

    }

    private void updatedateLabel2(){
        dateText2.setText(formatdate.format(dateobj.getTime()));

    }

    private void updatetimeLabel2(){
        TimeText2.setText(formattime.format(timeobj.getTime()));

    }

    private void updatetimeLabel(){
        timeText.setText(formattime.format(timeobj.getTime()));

    }


    public void getValues2(){

        String Name=name.getText().toString().trim();
        String Date=dateText.getText().toString().trim();
        String Date2=dateText2.getText().toString().trim();
        String Time=timeText.getText().toString().trim();
        String Time2=TimeText2.getText().toString().trim();
        String Note1=note1.getText().toString().trim();
        String Note2=note2.getText().toString().trim();
//        String States22=States2.getText().toString().trim();
//        String States11=States.getText().toString().trim();

        String PlaceForm=mAutocompleteTextView.getText().toString().trim();
        String PlaceTo=autoComplete2TextView.getText().toString().trim();
        String PlaceForm2=autoCompleteTextViewsf.getText().toString().trim();
        String PlaceTo2=autoCompleteTextViewst.getText().toString().trim();
        String  States11="upComing";
        String  States22="upComing";




//        autoCompleteTextViewsf.setText(autoComplete2TextView.getText().toString());
//        autoCompleteTextViewst.setText(mAutocompleteTextView.getText().toString());
        aprogress.show();

        Trip  trip2obj1=new Trip(Name,Date,Time,Note1,PlaceForm,PlaceTo,States11);

        reference.push().setValue(trip2obj1);

        Trip  trip2obj2=new Trip(Name,Date2,Time2,Note2,PlaceForm2,PlaceTo2,States22);
        reference.push().setValue(trip2obj2);

        aprogress.dismiss();
        Toast.makeText(MainActivity2.this,"Insetrted",Toast.LENGTH_LONG).show();

    }


    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);


            final String placeId = String.valueOf(item.placeId);
            Log.i(TAG, "place id: " + item.placeId);


           //Log.i(TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
           // Log.i(TAG, "Fetching details for ID: " + item.placeId);
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

//           Toast.makeText(MainActivity.this,"langlat"+place.getLatLng(),Toast.LENGTH_LONG).show();
           //String langlat1= mAutocompleteTextView.getText().toString();

           Intent intent = new Intent(MainActivity2.this, GoogleApiMap.class);

//           intent.putExtra("langlat",place.getLatLng());
           //intent.putExtra("langlat",place.getLatLng());
           // startActivity(intent);
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


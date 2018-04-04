package com.example.sara.tripplanner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by sara on 25/03/18.
 */

 public  class SigninGoogle  extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    private static final String TAG="SignInActvity";
    private static final int RC_SIGN_IN=9001;
    private SignInButton msignButton;
    private GoogleApiClient mgoogleApiClint;
    private FirebaseAuth mfirebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      
    }





        @Override
    public void onClick(View view) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

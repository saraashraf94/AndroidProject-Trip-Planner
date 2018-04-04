package com.example.sara.tripplanner;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity  implements View.OnClickListener{
    private Button loginBtn;
    private EditText emailEdit;
    private EditText passowrdEdit;
    private TextView signupTextview;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser mfirebaseUser;




    private String mphotoUrl;
    private SignInButton msignButton;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private String idToken;
    //public SharedPrefManager sharedPrefManager;
    private final Context mContext = this;

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    ProgressDialog aprogress;

    private GoogleSignInClient mGoogleSignInClient;
    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private TextView login;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Tripiano");
       // getSupportActionBar().setBackgroundDrawable(new ColorDrawable().getColor(R.color.colorbar));
        mStatusTextView = findViewById(R.id.status);
        mDetailTextView = findViewById(R.id.detail);

        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        loginBtn=(Button)findViewById(R.id.loginbtn);
        emailEdit=(EditText)findViewById(R.id.email);
        passowrdEdit=(EditText)findViewById(R.id.password);
        signupTextview=(TextView)findViewById(R.id.SignupText);

        loginBtn.setOnClickListener(this);
        signupTextview.setOnClickListener(this);
        login=findViewById(R.id.login);



        signupTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisert();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddTrip();
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();


    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            @SuppressLint("RestrictedApi") Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]

        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //  Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            Toast.makeText(Login.this,"Authentication Failed",Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
    }


    @SuppressLint("RestrictedApi")
    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {
            // mStatusTextView.setText(getString(R.string.google_app_id,user.getEmail()));
            // mDetailTextView.setText(getString(R.string.firebase_database_url, user.getUid()));

            findViewById(R.id.sign_in_button).setVisibility(View.GONE);

            Intent intent=new Intent(this,navigationbar.class);
            startActivity(intent);


        } else {

            mDetailTextView.setText(null);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);

        }
    }





    private void signIn() {
        @SuppressLint("RestrictedApi") Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }




    public void openRegisert(){
        Intent intent=new Intent(this,Regiser.class);
        startActivity(intent);

    }
    public void openAddTrip(){
        Intent intent=new Intent(this,navigationbar.class);
        startActivity(intent);

    }


    private  void userLogin(){

        String email=emailEdit.getText().toString().trim();
        String password=passowrdEdit.getText().toString().trim();

        if(TextUtils.isEmpty(email)){

            Toast.makeText(this,"Please Enter Email ..",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){

            Toast.makeText(this,"Please Enter Password ..",Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(),navigationbar.class));


                }else {

                    Toast.makeText(Login.this,"Could not Login...please Try Again ",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();

            }
        });

    }



    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (view == loginBtn) {
            userLogin();
        }
        if (view == signupTextview) {
            finish();

            startActivity(new Intent(this,navigationbar.class));

        }


        if (i == R.id.sign_in_button) {
            signIn();
        }


    }




}
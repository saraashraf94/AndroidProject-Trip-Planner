package com.example.sara.tripplanner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Regiser extends AppCompatActivity implements View.OnClickListener {

    private Button regsterBtn;
    private EditText registerEmail;
    private  EditText regsterPass;
    private TextView alreadeyRegst;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiser);
        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() !=null){

            finish();
            startActivity(new Intent(getApplicationContext(),navigationbar.class));
        }

        progressDialog=new ProgressDialog(this);
        regsterBtn=(Button)findViewById(R.id.rigsterbtn);
        registerEmail=(EditText)findViewById(R.id.regemail);
        regsterPass=(EditText)findViewById(R.id.regpassword);
        alreadeyRegst=(TextView) findViewById(R.id.alreadyText);

        regsterBtn.setOnClickListener(this);
        alreadeyRegst.setOnClickListener(this);

        alreadeyRegst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActvity();
            }
        });


    }


    public  void openLoginActvity(){

        Intent intent=new Intent(this,Login.class);
        startActivity(intent);
    }

    public void   registerUser(){
        String email=registerEmail.getText().toString().trim();
        String  password=regsterPass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is Empity
            Toast.makeText(this,"Please Enter Your Email..",Toast.LENGTH_LONG).show();
           //stoped this function
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is Empity
            Toast.makeText(this,"Please Enter Your Password..",Toast.LENGTH_LONG).show();
            //stoped this function
            return;
        }

        //if registertion is OK
        //first we show progressbar
        progressDialog.setMessage("Registertion User..");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    //user is succssfuly registered and loged in
                    //we will start the next actvity here
                    //now we want display a Toast Only
                    Toast.makeText(Regiser.this,"Register Successful",Toast.LENGTH_LONG).show();

                }else {

                    Toast.makeText(Regiser.this,"Could not Register...please Try Again ",Toast.LENGTH_LONG).show();

                }
            }
        });



    }

    @Override
    public void onClick(View view) {

        if(view==regsterBtn){

            registerUser();

        }

    }
}

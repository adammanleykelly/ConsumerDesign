package com.example.adam.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.*;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.tasks.*;
import android.support.annotation.*;
import android.util.*;
import android.widget.Toast;


public class LoginEmail extends AppCompatActivity {
    private FirebaseAuth mAuth;

    //Initialize later
    String email, password;
    Button loginEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        loginEmail = (Button) findViewById(R.id.buttonLoginEmail);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        /**mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginEmail.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });*/

        loginEmail.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        LoginEmail();}
                });


    }

    public void LoginEmail()
    {
        Intent act1 = new Intent(this,HomeActivity.class);
        startActivity(act1);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void updateUI(FirebaseUser currentUser){}
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


}

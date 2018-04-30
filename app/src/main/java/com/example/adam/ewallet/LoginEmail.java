package com.example.adam.ewallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.*;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.tasks.*;
import android.support.annotation.*;
import android.text.TextUtils;
import android.util.*;
import android.widget.Toast;


public class LoginEmail extends BaseActivity implements View.OnClickListener
{
    private FirebaseAuth mAuth;
    //Initialize later
    EditText emailField, passwordField;
    Button loginEmail;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        emailField = (EditText) findViewById(R.id.emailDetail);
        passwordField = (EditText) findViewById(R.id.passwordDetail);

        loginEmail = (Button) findViewById(R.id.buttonLoginEmail);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        LoginEmail(currentUser);

        /**loginEmail.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        LoginEmail();}
                });*/
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        LoginEmail(currentUser);
    }
    // [END on_start_check_user]

    //Create User Account with email and password
    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            LoginEmail(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginEmail.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            LoginEmail(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            LoginEmail(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginEmail.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            LoginEmail(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                           // mStatusTextView.setText(R.string.auth_failed);
                        }
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }


    private boolean validateForm() {
        boolean valid = true;

        String email = emailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailField.setError("Required.");
            valid = false;
        } else {
            emailField.setError(null);
        }

        String password = passwordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordField.setError("Required.");
            valid = false;
        } else {
            passwordField.setError(null);
        }
        return valid;
    }


    private void signOut() {
        mAuth.signOut();
        LoginEmail(null);
    }


    public void LoginEmail(FirebaseUser currentUser)
    {
        Intent act1 = new Intent(this,HomeActivity.class);
        startActivity(act1);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.createAccount) {
            createAccount(emailField.getText().toString(), passwordField.getText().toString());
        } else if (i == R.id.buttonLoginEmail) {
            signIn(passwordField.getText().toString(),passwordField.getText().toString());
        } /**else if (i == R.id.sign_out_button) {
            signOut();
        } else if (i == R.id.verify_email_button) {
            sendEmailVerification();
        }*/
    }


}

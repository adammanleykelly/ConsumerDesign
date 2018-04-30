package com.example.adam.ewallet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;


public class LoginActivity extends AppCompatActivity {

    Button button_Login, button_SignUp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button_Login = (Button) findViewById(R.id.login);
        button_SignUp = (Button) findViewById(R.id.buttonSignUp);

        button_Login.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        LoginActivity1();}
                });

        button_SignUp.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        SignUpEmail();}
                });

    }


    public void LoginActivity1()
    {
        Intent act1 = new Intent(this,LoginEmail.class);
        startActivity(act1);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void SignUpEmail()
    {
        Intent act1 = new Intent(this,LoginEmail.class);
        startActivity(act1);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void LoginEmail()
    {
        Intent act1 = new Intent(this,LoginEmail.class);
        startActivity(act1);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

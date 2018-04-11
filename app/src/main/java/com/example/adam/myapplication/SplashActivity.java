package com.example.adam.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;


public class SplashActivity extends AppCompatActivity {

    int timer = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, timer);

        // Start home activity
        //startActivity(new Intent(SplashActivity.this, MainActivity.class));
        // close splash activity
       // finish();
    }

}

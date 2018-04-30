package com.example.adam.ewallet;
//Dummy activity for demonstration purposes
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ScanCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

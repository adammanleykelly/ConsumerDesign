package com.example.adam.ewallet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainSettingsActivity extends AppCompatActivity {

    Button Scan, Emulate, write;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_settings);

        Scan = (Button) findViewById(R.id.scanButton);
        Emulate = (Button) findViewById(R.id.emulateButton);
        write = (Button) findViewById(R.id.writeButton);

        Scan.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        ScanCards();}
                });
        Emulate.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        EmulateCards();}
                });
        write.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        WriteCards();}
                });
    }

    public void ScanCards()
    {
        Intent act1 = new Intent(this,ScanCard.class);
        startActivity(act1);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void EmulateCards()
    {
        Intent act1 = new Intent(this,EmulateCard.class);
        startActivity(act1);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void WriteCards()
    {
        Intent act1 = new Intent(this,WriteCard.class);
        startActivity(act1);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

package com.example.adam.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class HomeActivity extends AppCompatActivity {

    Button vCards;
    Button sButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        vCards = (Button) findViewById(R.id.viewCards);
        sButton = (Button) findViewById(R.id.buttonSettings);

        vCards.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        ViewCardsActivity1();}
                });
        sButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        ViewSettingsActivity1();}
                });
    }

    public void ViewCardsActivity1()
    {
        Intent act1 = new Intent(this,ViewCards.class);
        startActivity(act1);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void ViewSettingsActivity1()
    {
        Intent act1 = new Intent(this,MainSettingsActivity.class);
        startActivity(act1);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

package com.example.adam.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class HomeActivity extends AppCompatActivity {

    Button vCards;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        vCards = (Button) findViewById(R.id.viewCards);

        vCards.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        ViewCardsActivity1();}
                });
    }

    public void ViewCardsActivity1()
    {
        Intent act1 = new Intent(this,ViewCards.class);
        startActivity(act1);
    }
}
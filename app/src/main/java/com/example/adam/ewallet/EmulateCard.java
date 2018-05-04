package com.example.adam.ewallet;

import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.adam.ewallet.view.CardsAdapter;
import com.example.adam.ewallet.database.model.Card;

import com.example.adam.ewallet.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;


public class EmulateCard extends AppCompatActivity {

    private CardsAdapter mAdapter;
    private List<Card> cardsList = new ArrayList<>();
    private DatabaseHelper db;

    NfcAdapter nfcAdapter;
    TextView nfcStatus, cardName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emulate_card);

        nfcStatus = (TextView) findViewById(R.id.nfcStatus);
        cardName = (TextView) findViewById(R.id.cardName);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
        }

        //Status update if NFC is enabled
        if (!nfcAdapter.isEnabled())
        {
            nfcStatus.setText("NFC is disabled.");
        }
        else
        {
            nfcStatus.setVisibility(View.INVISIBLE);
        }
    }

    //To do get card method

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

package com.example.adam.ewallet;

import android.content.Context;
import android.content.SharedPreferences;
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
    private List<Card> cardsList = new ArrayList<>();;
    private DatabaseHelper db;
    Context context;

    int position;// =0;
    NfcAdapter nfcAdapter;
    TextView nfcStatus, cardName, cardData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emulate_card);

        nfcStatus = (TextView) findViewById(R.id.nfcStatus);
        cardName = (TextView) findViewById(R.id.cardName);
        cardData = (TextView) findViewById(R.id.cardData);

        db = new DatabaseHelper(this);
        cardsList.addAll(db.getAllCards());
        context = this;

        SharedPreferences sp = getSharedPreferences("Position", EmulateCard.MODE_PRIVATE);
        position = sp.getInt("positionPref", 0);
        System.out.println("Emulate oncreate: " +position);
        getCard(position);

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
    public void getCard(int position)
    {
        System.out.println("getCard position value" + position);
        Card card = cardsList.get(position);
        cardName.setText(card.getCard());
        cardData.setText(card.getCardData());
        System.out.println(card.getCard());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

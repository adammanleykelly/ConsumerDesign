package com.example.adam.ewallet;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
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
import android.content.Intent;


public class EmulateCard extends AppCompatActivity {

    private CardsAdapter mAdapter;
    private List<Card> cardsList = new ArrayList<>();;
    private DatabaseHelper db;
    Context context;

    int position;// =0;
    NfcAdapter nfcAdapter;
    TextView nfcStatus, cardName, cardData;
    Button emulate;
    ImageView iView1;
    String cName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emulate_card);

        nfcStatus = (TextView) findViewById(R.id.nfcStatus);
        cardName = (TextView) findViewById(R.id.cardName);
        cardData = (TextView) findViewById(R.id.cardData);
        iView1 = (ImageView) findViewById(R.id.imageView2);

        System.out.println(cName);

        if(cName == "StudentCard" || cName == "Student Card" || cName == "Student card")
        {
            iView1.setImageResource(R.drawable.studentcard);

        }
        else if(cName != "StudentCard" || cName != "Student Card" || cName != "Student card")
        {
            iView1.setImageResource(R.drawable.rfid);
        }

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

        emulate = (Button) findViewById(R.id.emulateTest);
        emulate.setVisibility(View.INVISIBLE);
        emulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView getNdefString = (TextView) findViewById(R.id.cardData);
                String test = getNdefString.getText().toString();

                Intent intent = new Intent(view.getContext(), myHostApduService.class);
                intent.putExtra("ndefMessage", test);
                startService(intent);
            }
        });
    }

    //To do get card method
    public void getCard(int position)
    {
        System.out.println("getCard position value" + position);
        Card card = cardsList.get(position);
        cardName.setText(card.getCard());

        cName = card.getCard();

        if(cName == "StudentCard" || cName == "Student Card" || cName == "Student card")
        {
            iView1.setImageResource(R.drawable.studentcard);

        }
        else if(cName != "StudentCard" || cName != "Student Card" || cName != "Student card")
        {
            iView1.setImageResource(R.drawable.rfid);
        }


        cardData.setText(card.getCardData());
        System.out.println(card.getCard());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

package com.example.adam.ewallet;
/* Read write Code adjusted from: http://www.codexpedia.com/android/android-nfc-read-and-write-example/
* 'Card' Code taken and modified from https://github.com/ravi8x/AndroidSQLite on the 12/04/2018 by Adam Manley Kelly
*/


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import android.content.Intent;
import android.app.PendingIntent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.nfc.NdefMessage;
import android.nfc.Tag;
import android.util.Log;

import com.example.adam.ewallet.database.model.Card;
import com.example.adam.ewallet.database.DatabaseHelper;

public class ScanCard extends AppCompatActivity
{
    private List<Card> cardsList = new ArrayList<>();
    private DatabaseHelper db;
    public static final String ERROR_DETECTED = "No NFC tag detected!";
    public static final String WRITE_SUCCESS = "Text written to the NFC tag successfully!";
    public static final String WRITE_ERROR = "Error during writing, is the NFC tag close enough to your device?";
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writeTagFilters[];
    boolean writeMode;
    Tag myTag;
    Context context;

    TextView tvNFCContent;
    TextView message;
    Button button, saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        db = new DatabaseHelper(this);
        cardsList.addAll(db.getAllCards());


        context = this;
        tvNFCContent = findViewById(R.id.carddata1);
        message = findViewById(R.id.edit_message);
        saveButton= (Button) findViewById(R.id.saveButton);
        //saveButton.setVisibility(View.GONE);

        saveButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        showCardDialog(false, null, -1);}
                });

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
        }
        readFromIntent(getIntent());

        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writeTagFilters = new IntentFilter[] { tagDetected };
    }

    /******************************************************************************
     **********************************Read From NFC Tag***************************
     ******************************************************************************/
    private void readFromIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            buildTagViews(msgs);
        }
    }
    private void buildTagViews(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0) return;

        String text = "";
//        String tagId = new String(msgs[0].getRecords()[0].getType());
        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16"; // Get the Text Encoding
        int languageCodeLength = payload[0] & 0063; // Get the Language Code, e.g. "en"
        // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");

        try {
            // Get the Text
            text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.toString());
        }

        tvNFCContent.setText("NFC Content: " + text);
       // saveButton.setVisibility(View.VISIBLE);
    }

     /******************************************************************************
     **********************************Creating and Adding Card*********************
     ******************************************************************************/

    /**
     * Inserting new card in db and refreshing the list
     */
    private void createCard(String card) {
        // inserting card in db and getting
        // newly inserted card id
        long id = db.insertCard(card);

        // get the newly inserted card from db
        Card n = db.getCard(id);

        if (n != null) {
            // adding new card to array list at 0 position
            cardsList.add(0, n);

        }
    }



    /**
     * Shows alert dialog with EditText options to enter / edit
     * a card.
     * when shouldUpdate=true, it automatically displays old card and changes the
     * button text to UPDATE
     */
    public void showCardDialog(final boolean shouldUpdate, final Card card, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.card_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(ScanCard.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputCard = view.findViewById(R.id.card);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_card_title) : getString(R.string.lbl_edit_card_title));

        if (shouldUpdate && card != null) {
            inputCard.setText(card.getCard());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "update" : "save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show toast message when no text is entered
                if (TextUtils.isEmpty(inputCard.getText().toString())) {
                    Toast.makeText(ScanCard.this, "Enter card!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                // check if user updating card
                if (shouldUpdate && card != null) {
                    // update card by it's id
                    //updateCard(inputCard.getText().toString(), position);
                } else {
                    // create new card
                    createCard(inputCard.getText().toString());
                    ViewCards();
                }
            }
        });
    }



    public void ViewCards()
    {
        Intent act1 = new Intent(this,ViewCards.class);
        startActivity(act1);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        readFromIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}

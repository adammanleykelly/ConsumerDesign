package com.example.adam.ewallet;

/**
 * Created by ravi on 20/02/18.
 * 'Card' Code taken and modified from https://github.com/ravi8x/AndroidSQLite on the 12/04/2018 by Adam Manley Kelly
 **/
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.adam.ewallet.view.CardsAdapter;
import com.example.adam.ewallet.database.model.Card;

import com.example.adam.ewallet.database.DatabaseHelper;
import com.example.adam.ewallet.utils.MyDividerItemDecoration;
import com.example.adam.ewallet.utils.RecyclerTouchListener;


public class ViewCards extends AppCompatActivity {

    private CardsAdapter mAdapter;
    private List<Card> cardsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView noCardsView;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cards);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);
        noCardsView = findViewById(R.id.empty_cards_view);

        db = new DatabaseHelper(this);

        cardsList.addAll(db.getAllCards());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanCard();
                //showCardDialog(false, null, -1);
            }
        });

        mAdapter = new CardsAdapter(this, cardsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptyCards();

        /**
         * On long press on RecyclerView item, open alert dialog with options to choose Edit and Delete
         */
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position)
            {
                emulateCard();
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
    }


    /**
     * Updating card in db and updating
     * item in the list by its position
     */
    private void updateCard(String card, int position) {
        Card n = cardsList.get(position);
        // updating card text
        n.setCard(card);

        // updating card in db
        db.updateCard(n);

        // refreshing the list
        cardsList.set(position, n);
        mAdapter.notifyItemChanged(position);

        toggleEmptyCards();
    }

    /**
     * Deleting card from SQLite and removing the
     * item from the list by its position
     */
    private void deleteCard(int position) {
        // deleting the card from db
        db.deleteCard(cardsList.get(position));

        // removing the card from the list
        cardsList.remove(position);
        mAdapter.notifyItemRemoved(position);

        toggleEmptyCards();
    }

    /**
     * Opens dialog with Edit - Delete options
     * Edit - 0
     * Delete - 0
     */
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showCardDialog(true, cardsList.get(position), position);
                } else {
                    deleteCard(position);
                }
            }
        });
        builder.show();
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

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(ViewCards.this);
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
                    Toast.makeText(ViewCards.this, "Enter card!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                // check if user updating card
                if (shouldUpdate && card != null) {
                    // update card by it's id
                    updateCard(inputCard.getText().toString(), position);
                } else {
                    // create new card
                   // createCard(inputCard.getText().toString());
                }
            }
        });
    }

    /**
     * Toggling list and empty cards view
     */
    private void toggleEmptyCards() {
        // you can check cardsList.size() > 0

        if (db.getCardsCount() > 0) {
            noCardsView.setVisibility(View.GONE);
        } else {
            noCardsView.setVisibility(View.VISIBLE);
        }
    }

    public void ScanCard()
    {
        Intent act1 = new Intent(this,ScanCard.class);
        startActivity(act1);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void emulateCard()
    {
        Intent act1 = new Intent(this,EmulateCard.class);
        startActivity(act1);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent act1 = new Intent(this,HomeActivity.class);
        startActivity(act1);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

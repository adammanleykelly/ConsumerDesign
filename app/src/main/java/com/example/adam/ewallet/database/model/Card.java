package com.example.adam.ewallet.database.model;

/**
 * Created by ravi on 20/02/18.
 * Code taken and modified from https://github.com/ravi8x/AndroidSQLite on the 12/04/2018 by Adam Manley Kelly
 **/

public class Card {
    public static final String TABLE_NAME = "cards";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CARD = "card";
    public static final String COLUMN_CARDDATA = "cardData";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String card;
    private String cardData;
    private String timestamp;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CARD + " TEXT,"
                    + COLUMN_CARDDATA + "TEXTs"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Card() {
    }

    public Card(int id, String card, String cardData, String timestamp) {
        this.id = id;
        this.card = card;
        this.cardData = cardData;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCardData() {
        return cardData;
    }

    public void setCardData(String cardData) {
        this.cardData = cardData;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
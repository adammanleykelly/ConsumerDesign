package com.example.adam.ewallet.database;

/**
 * Created by ravi on 20/02/18.
 * Code taken and modified from https://github.com/ravi8x/AndroidSQLite on the 12/04/2018 by Adam Manley Kelly
 **/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import com.example.adam.ewallet.database.model.Card;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "cards_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create cards table
        db.execSQL(Card.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Card.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertCard(String card) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Card.COLUMN_CARD, card);

        // insert row
        long id = db.insert(Card.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Card getCard(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Card.TABLE_NAME,
                new String[]{Card.COLUMN_ID, Card.COLUMN_CARD, Card.COLUMN_TIMESTAMP},
                Card.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare card object
        Card card = new Card(
                cursor.getInt(cursor.getColumnIndex(Card.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Card.COLUMN_CARD)),
                cursor.getString(cursor.getColumnIndex(Card.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return card;
    }

    public List<Card> getAllCards() {
        List<Card> cards = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Card.TABLE_NAME + " ORDER BY " +
                Card.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Card card = new Card();
                card.setId(cursor.getInt(cursor.getColumnIndex(Card.COLUMN_ID)));
                card.setCard(cursor.getString(cursor.getColumnIndex(Card.COLUMN_CARD)));
                card.setTimestamp(cursor.getString(cursor.getColumnIndex(Card.COLUMN_TIMESTAMP)));

                cards.add(card);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return cards list
        return cards;
    }

    public int getCardsCount() {
        String countQuery = "SELECT  * FROM " + Card.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public int updateCard(Card card) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Card.COLUMN_CARD, card.getCard());

        // updating row
        return db.update(Card.TABLE_NAME, values, Card.COLUMN_ID + " = ?",
                new String[]{String.valueOf(card.getId())});
    }

    public void deleteCard(Card card) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Card.TABLE_NAME, Card.COLUMN_ID + " = ?",
                new String[]{String.valueOf(card.getId())});
        db.close();
    }
}

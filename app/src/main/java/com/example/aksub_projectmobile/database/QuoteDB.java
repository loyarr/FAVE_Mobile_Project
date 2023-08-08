package com.example.aksub_projectmobile.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.aksub_projectmobile.models.FavoriteQuotes;

import java.util.ArrayList;
import java.util.List;

public class QuoteDB {
    private DBHelper dbHelper;

    public QuoteDB(Context context) {
        this.dbHelper = DBHelper.getInstance(context);
    }

    //Create
    public int insertQuote(FavoriteQuotes quote){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.FIELD_QUOTE_NAME, quote.getAuthor());
        cv.put(DBHelper.FIELD_QUOTE_TEXT, quote.getQuote());
        int insertedId = (int) db.insert(DBHelper.TABLE_QUOTE, null, cv);
        db.close();
        return insertedId;
    }

    //Read
    public List<FavoriteQuotes> getAllFavoriteQuotes(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_QUOTE, null);
        List<FavoriteQuotes> favoriteQuoteList = new ArrayList<>();
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.FIELD_QUOTE_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FIELD_QUOTE_NAME));
                String quoteText = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FIELD_QUOTE_TEXT));

                FavoriteQuotes quote = new FavoriteQuotes(id, name, quoteText);
                favoriteQuoteList.add(quote);

                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return favoriteQuoteList;
    }

    //Delete
    public int deleteQuote(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows = db.delete(DBHelper.TABLE_QUOTE, DBHelper.FIELD_QUOTE_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return deletedRows;
    }
}


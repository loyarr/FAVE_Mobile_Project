package com.example.aksub_projectmobile.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "dbQuotes";
    private static final int DB_VERSION = 1;

    public static final String TABLE_QUOTE = "quotes";

    public static final String FIELD_QUOTE_ID = "id";
    public static final String FIELD_QUOTE_NAME = "name";
    public static final String FIELD_QUOTE_TEXT = "quote";

    private static final String CREATE_TABLE_QUOTE = "CREATE TABLE " + TABLE_QUOTE
            + "(" + FIELD_QUOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FIELD_QUOTE_NAME + " TEXT," +
            FIELD_QUOTE_TEXT + " TEXT);";

    private static final String DROP_TABLE_QUOTE = "DROP TABLE IF EXISTS " + TABLE_QUOTE;

    private static DBHelper dbInstance;
    public static synchronized DBHelper getInstance(Context context){
        if(dbInstance == null){
            dbInstance = new DBHelper(context);
        }
        return dbInstance;
    }

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_QUOTE);
        onCreate(db);
    }
}


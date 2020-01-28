package com.nestoras.strikewatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "StrikesDB";
    private static final String TABLE_NAME = "Strikes";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";
    private static final String KEY_HOUR = "hour";
    private static final String KEY_LOCATION = "location";

    private static final String[] COLUMNS = { KEY_ID, KEY_NAME, KEY_DATE, KEY_HOUR,
            KEY_LOCATION };

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATION_TABLE = "CREATE TABLE Strikes ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT, "
                + "date TEXT, " + "hour TEXT, "+ "location TEXT )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }


    public ArrayList<Strike> allStrikes() {

        ArrayList<Strike> strikes = new ArrayList<>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Strike strike = null;

        if (cursor.moveToFirst()) {
            do {
                strike = new Strike(cursor.getString(1),cursor.getString(2),cursor.getString(3) ,cursor.getString(4));
                strikes.add(strike);
            } while (cursor.moveToNext());
        }

        return strikes;
    }

    public void addStrike(Strike strike) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, strike.getName());
        values.put(KEY_DATE, strike.getDate());
        values.put(KEY_HOUR, strike.getHour());
        values.put(KEY_LOCATION, strike.getLocation());
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public void deleteDb(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " +TABLE_NAME); //delete all rows in a table
        db.close();
    }



}
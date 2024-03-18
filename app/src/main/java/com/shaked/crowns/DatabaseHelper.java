package com.shaked.crowns;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "wars.db";// Database Name
    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "wars_table";// Table Name

    public static final String COL_P1ID = "PLAYER1_ID";// Column I
    public static final String COL_P1SCORE = "PLAYER1_SCORE";// Column II
    public static final String COL_P2ID = "PLAYER2_ID";// Column III
    public static final String COL_P2SCORE = "PLAYER2_SCORE";// Column IV

    /*=פעולה בונה=*/
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }
       @Override
    public void onCreate(SQLiteDatabase db) {// Creating Tables
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + COL_P1ID + " TEXT," + COL_P1SCORE + " TEXT," + COL_P2ID + " TEXT," + COL_P2SCORE + " TEXT" + ")");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean insertData(War war) {// Inserting Data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_P1ID, war.getP1Name());
        contentValues.put(COL_P1SCORE, war.getP1Scor());
        contentValues.put(COL_P2ID, war.getP2Name());
        contentValues.put(COL_P2SCORE, war.getP2Scor());
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }
    public Cursor getAllData(){// Getting All Data
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }

    public boolean updateData(War war){// Updating Data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_P1ID, war.getP1Name());
        contentValues.put(COL_P1SCORE, war.getP1Scor());
        contentValues.put(COL_P2ID, war.getP2Name());
        contentValues.put(COL_P2SCORE, war.getP2Scor());

        // Use WHERE clause to update based on both P1Name and P2Name
        String whereClause = COL_P1ID + " = ? AND " + COL_P2ID + " = ?";
        String[] whereArgs = {war.getP1Name(), war.getP2Name()};

        db.update(TABLE_NAME, contentValues, whereClause, whereArgs);
        return true;
    }
    public Integer deleteData (){// Deleting Data
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, null, null);
    }

    public boolean dataExists(String player1,String player2) {// Checking if data exists
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_P1ID + " = ? AND " + COL_P2ID + " = ?", new String[]{player1, player2});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public Cursor getDataCursor(String p1Name, String p2Name) {// Getting Data by 2 names
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_P1ID + " = ? AND " + COL_P2ID + " = ?", new String[]{p1Name, p2Name});
        return cursor;
    }
}

package com.example.cidemo.other;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String LOGIN_TABLE_NAME = "login_table";
    private static final String MATCH_TABLE_NAME = "match_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "URL";
    private static final String COL3 = "UserName";
    private static final String COL4 = "Password";
    private static final String MATCH_COL2 = "MatchID";
    private static final String MATCH_COL3 = "MatchName";
    private static final String MATCH_COL4 = "MatchScore";

    public DatabaseHelper(Context context) {
        super(context, LOGIN_TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createLoginTable = "CREATE TABLE " + LOGIN_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT)";
        db.execSQL(createLoginTable);

        String createMatchTable = "CREATE TABLE " + MATCH_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MATCH_COL2 + " TEXT, " + MATCH_COL3 + " TEXT, " + MATCH_COL4 + " TEXT)";
        db.execSQL(createMatchTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MATCH_TABLE_NAME);
        onCreate(db);
    }

    public boolean addLoginData(String url, String userName, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, url);
        contentValues.put(COL3, userName);
        contentValues.put(COL4, password);

        Log.d(TAG, "addData: Adding" + "(" + url + ", " + userName + ", " + password + ") to " + LOGIN_TABLE_NAME);

        long result = db.insert(LOGIN_TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + LOGIN_TABLE_NAME, null);

        return cursor;
    }

    public boolean addMatchData(String matchID, String matchName, String matchScore) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MATCH_COL2, matchID);
        contentValues.put(MATCH_COL3, matchName);
        contentValues.put(MATCH_COL4, matchScore);

        Log.d(TAG, "addData: Adding" + "(" + matchID + ", " + matchName + ", " + matchScore + ") to " + MATCH_TABLE_NAME);

        long result = db.insert(MATCH_TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getMatchData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + MATCH_TABLE_NAME, null);

        return cursor;
    }
}

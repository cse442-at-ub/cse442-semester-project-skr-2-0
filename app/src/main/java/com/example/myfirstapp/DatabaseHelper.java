package com.example.myfirstapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.annotation.Target;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Test
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "exam_schedule";
    private static final String COL0 = "ID";
    private static final String COL1 = "NAME";
    private static final String COL2 = "MONTH";
    private static final String COL3 = "DAY";
    private static final String COL4 = "YEAR";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " NAME TEXT, MONTH TEXT, DAY TEXT, YEAR TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item, String mon, String dy, String yr){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, item);
        contentValues.put(COL2, mon);
        contentValues.put(COL3, dy);
        contentValues.put(COL4, yr);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1){
            return false;
        }
        else {
            return true;
        }

    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL0 + " FROM " + TABLE_NAME + " WHERE " + COL1 + " = '"  + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL1 + " = '" + newName + "' WHERE " + COL0 + " = '" + id + "' AND " + COL1 + " = '" + oldName + "'";
        db.execSQL(query);
    }

    public void updateYear(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL4 + " = '" + newName + "' WHERE " + COL0 + " = '" + id + "' AND " + COL4 + " = '" + oldName + "'";
        db.execSQL(query);
    }

    public void updateMonth(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 + " = '" + newName + "' WHERE " + COL0 + " = '" + id + "' AND " + COL2 + " = '" + oldName + "'";
        db.execSQL(query);
    }

    public void updateDay(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL3 + " = '" + newName + "' WHERE " + COL0 + " = '" + id + "' AND " + COL3 + " = '" + oldName + "'";
        db.execSQL(query);
    }

    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        //String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL0 + " = '" + id + "' AND " + COL1 = " = '" + name + "'";
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL0 + " = '" + id + "' AND " + COL1 + " ='" + name + "'";
        db.execSQL(query);
    }

    public void clearDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();
        String clearDBQuery = "DELETE FROM " + TABLE_NAME;
        db.execSQL(clearDBQuery);
    }
}

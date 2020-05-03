package com.example.myfirstapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.annotation.Target;

public class ClassDataBase extends SQLiteOpenHelper {
    //String className, String classRoom, int day, double begin, double over, int mwf, int tt
    //Test
    private static final String TAG = "ClassDataBase";
    private static final String TABLE_NAME = "class_schedule";
    private static final String COL0 = "ID";
    private static final String COL1 = "NAME";
    private static final String COL2 = "ROOM";
    private static final String COL3 = "DAY";
    private static final String COL4 = "BG";
    private static final String COL5 = "OVER";
    private static final String COL6 = "MWF";
    private static final String COL7 = "TT";
    private static final String COL8 = "NOTE";



    public ClassDataBase(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " NAME TEXT, ROOM TEXT, DAY TEXT, BG TEXT,OVER TEXT,MWF TEXT,TT TEXT )";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
}
}

package com.example.myfirstapp;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class ClassDataBase extends SQLiteOpenHelper {


    public ClassDataBase(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table class(" +
                "id double primary key autoincrement," +
                "class_name text," +
                "class_room text," +
                "dayinweek integer," +
                "start double," +
                "over double,"+
                "mwf integer,"+
                "tt integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
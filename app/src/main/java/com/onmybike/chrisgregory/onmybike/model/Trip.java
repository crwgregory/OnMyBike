package com.onmybike.chrisgregory.onmybike.model;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Greg Christopherson on 9/28/2015.
 */
public class Trip {
    private long _id;
    public long timeStarted;
    public long timeTaken;
    private static String CLASS_NAME;

    public Trip(){
        this.CLASS_NAME = getClass().getName();
    }

    public static void create(SQLiteDatabase database){
        Log.d("SQLite", "create() of class: Trip");
        String createTable = "CREATE TABLE IF NOT EXISTS trips (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"timeStarted INTEGER NOT NULL, "
                +"timeTaken INTEGER NOT NULL, "
                +"route_id INTEGER NOT NULL, "
                +"FOREIGN KEY(route_id) REFERENCES routes(_id));";
        database.execSQL(createTable);
    }

    public static void drop(SQLiteDatabase database){
        Log.d("SQLite", "drop() of class: Trip");
        String dropTable = "drop table if exists trips;";
        database.execSQL(dropTable);
    }
}

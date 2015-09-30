package com.onmybike.chrisgregory.onmybike.model;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Greg Christopherson on 9/28/2015.
 */
public class Route {


    private long _id;
    private String name;
    private String notes;
    private Trip trips[];
    private static String CLASS_NAME;

    public Route(){
        this.CLASS_NAME = getClass().getName();
    }

    public static void create(SQLiteDatabase database){
        Log.d("SQLite", "create() of class: Routes");
        String createTable = "CREATE TABLE IF NOT EXISTS routes "
                +"(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"name TEXT NOT NULL, "
                +"notes TEXT NOT NULL);";
        database.execSQL(createTable);
    }

    public static void drop(SQLiteDatabase database){
        Log.d("SQLite", "drop() of class: Route");
        String dropTable = "drop table if exists routes;";
        database.execSQL(dropTable);
    }

    @Override
    public String toString(){
        return name;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Trip[] getTrips() {
        return trips;
    }

    public void setTrips(Trip[] trips) {
        this.trips = trips;
    }

}

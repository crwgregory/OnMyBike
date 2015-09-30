package com.onmybike.chrisgregory.onmybike.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.onmybike.chrisgregory.onmybike.model.Route;
import com.onmybike.chrisgregory.onmybike.model.Trip;

/**
 * Created by Greg Christopherson on 9/28/2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "OnYourBike.db";
    private static final int DATABASE_VERSION = 18;
    private static String CLASS_NAME;
    private SQLiteDatabase sqLiteDatabase;

    public SQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.CLASS_NAME = getClass().getName();
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        Log.d(CLASS_NAME, "onCreate()");
        Route.create(database);
        Trip.create(database);
        Log.d(CLASS_NAME, "onCreate() called");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        Log.d(CLASS_NAME, "onUpgrade()");
        Trip.drop(database);
        Route.drop(database);
        onCreate(database);
        Log.d(CLASS_NAME, "onUpgrade() called");
    }

    @Override
    public void onOpen(SQLiteDatabase database){
        Log.d(CLASS_NAME, "onOpen()");

        Log.d(CLASS_NAME, "onOpen() called");

    }

    @Override
    public void onConfigure(SQLiteDatabase database){
        super.onConfigure(database);
        Log.d(CLASS_NAME, "onConfigure()");
        database.setForeignKeyConstraintsEnabled(true);
        Log.d(CLASS_NAME, "onConfigure() called");
    }

    public void open(){
        Log.d(CLASS_NAME, "open()");
        SQLiteDatabase database = getWritableDatabase();
        Log.d(CLASS_NAME, "open() called. Path to database: " + database.toString());
        this.sqLiteDatabase = database;
    }

    public void create(){
        Log.d(CLASS_NAME, "create()");
        open();
        Log.d(CLASS_NAME, "create() called");
    }

    public SQLiteDatabase getDatabase(){
        return sqLiteDatabase;
    }

}

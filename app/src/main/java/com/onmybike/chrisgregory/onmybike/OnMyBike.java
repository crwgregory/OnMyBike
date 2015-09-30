package com.onmybike.chrisgregory.onmybike;

import android.app.Application;
import android.util.Log;

import com.onmybike.chrisgregory.onmybike.helpers.SQLiteHelper;
import com.onmybike.chrisgregory.onmybike.model.Settings;

/**
 * Created by Greg Christopherson on 9/21/2015.
 */
public class OnMyBike extends Application {

    protected Settings settings;
    private static String CLASS_NAME;
    private SQLiteHelper helper;

    public OnMyBike(){
        this.CLASS_NAME = getClass().getName();
    }

    @Override
    public void onCreate(){
        Log.d("Extends Application", "onCreate()");
        super.onCreate();
        getHelper();
    }

    public Settings getSettings(){
        if(settings == null){
            settings = new Settings();
        }
        return settings;
    }

    public SQLiteHelper getHelper(){
        if(helper == null){
            helper = new SQLiteHelper(getApplicationContext());
            helper.create();
        }
        return helper;
    }

    public void setSettings(Settings settings){
        this.settings = settings;
    }

}

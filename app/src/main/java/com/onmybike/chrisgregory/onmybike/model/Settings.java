package com.onmybike.chrisgregory.onmybike.model;

import android.app.Activity;
import android.widget.CheckBox;

import com.onmybike.chrisgregory.onmybike.SettingsAsyncTask;

import java.util.ArrayList;

/**
 * Created by Greg Christopherson on 9/21/2015.
 */
public class Settings{

    private static String CLASS_NAME;

    public Settings(){
        CLASS_NAME = getClass().getName();
    }

    // call AsyncTask
    public void action(Activity activity, ArrayList<CheckBox> settingsArray, String action){
        for(CheckBox checkBox : settingsArray){
            new SettingsAsyncTask(activity, checkBox.isChecked(), checkBox.getId(), action).execute();
        }
    }

    public boolean isVibrateOn(Activity activity){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };

        return true;
    }
}


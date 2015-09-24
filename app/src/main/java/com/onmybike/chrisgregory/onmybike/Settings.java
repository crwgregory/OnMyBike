package com.onmybike.chrisgregory.onmybike;

import android.app.Activity;
import android.widget.CheckBox;

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
}


package com.onmybike.chrisgregory.onmybike;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.CheckBox;

import com.onmybike.chrisgregory.onmybike_chapter4.R;

/**
 * Created by Greg Christopherson on 9/22/2015.
 */
public class SettingsAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private static String CLASS_NAME;
    private static String LOAD = "load";
    private static String SAVE = "save";

    private int id;
    private String action;
    private String stringId;
    private Context context;
    private Boolean checked;
    private Activity activity;

    public SettingsAsyncTask(Activity activity, boolean checked, int id, String action){
        CLASS_NAME = getClass().getName();
        this.id = id;
        this.context = activity.getApplicationContext();
        this.checked = checked;
        this.activity = activity;
        this.stringId = Integer.toString(id);
        this.action = action;
    }

    protected Boolean doInBackground(Void... noUse){
        Log.d(CLASS_NAME, "doInBackground");

        SharedPreferences preferences = context.getSharedPreferences("userprefrences", Activity.MODE_PRIVATE);

        // load setting
        if(action.equalsIgnoreCase(LOAD)){
            if(preferences.contains(stringId)){
                checked = preferences.getBoolean(stringId, false);
            }
        }
        // save setting
        else if (action.equalsIgnoreCase(SAVE)){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(stringId, checked);
            editor.apply();
        } else {
            Log.d(CLASS_NAME, "Invalid save action String.");
        }


        return checked;
    }

    protected void onPostExecute(Boolean checked){
        Log.d(CLASS_NAME, "onPostExecute");

        // set check box only on load
        if(action.equalsIgnoreCase(LOAD)){
            CheckBox checkBox = (CheckBox) activity.findViewById(id);
            checkBox.setChecked(checked);
        }

    }
}

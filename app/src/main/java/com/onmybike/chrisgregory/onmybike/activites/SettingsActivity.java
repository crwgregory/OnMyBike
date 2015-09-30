package com.onmybike.chrisgregory.onmybike.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.onmybike.chrisgregory.onmybike.OnMyBike;
import com.onmybike.chrisgregory.onmybike.helpers.Toaster;
import com.onmybike.chrisgregory.onmybike.model.Settings;
import com.onmybike.chrisgregory.onmybike_chapter4.R;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    private static String CLASS_NAME;
    private static String LOAD = "load";
    private static String SAVE = "save";

    private CheckBox vibrate;
    private ArrayList<CheckBox> settingsArray = new ArrayList<>();

    public SettingsActivity(){
        CLASS_NAME = getClass().getName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(CLASS_NAME, "onCreate");
        setContentView(R.layout.activity_settings);

        vibrate = (CheckBox) findViewById(R.id.vibrate_button);

        //Load Settings and set Check Boxes using AsyncTask
        populateSettings();
        Settings settings = ((OnMyBike)getApplication()).getSettings();
        Log.d(CLASS_NAME, "Got settings class.");
        settings.action(this, settingsArray, LOAD);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d(CLASS_NAME, "setDisplayHomeAsUpEnabled(true);");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(CLASS_NAME, "onStop");

        //Save settings using AsyncTask
        populateSettings();
        Settings settings = ((OnMyBike)getApplication()).getSettings();
        settings.action(this, settingsArray, SAVE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void vibrateChanged(View view){
        Toaster toaster = new Toaster(getApplicationContext());
        if(vibrate.isChecked()){
            toaster.make(R.string.vibrate_on);
        } else {
            toaster.make(R.string.vibrate_off);
        }
    }

    private void populateSettings(){
        settingsArray.add(vibrate);
        Log.d(CLASS_NAME, "populateSettings");
        //add in all settings
    }
}

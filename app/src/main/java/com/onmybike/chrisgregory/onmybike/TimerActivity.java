package com.onmybike.chrisgregory.onmybike;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.onmybike.chrisgregory.onmybike_chapter4.BuildConfig;
import com.onmybike.chrisgregory.onmybike_chapter4.R;


public class TimerActivity extends AppCompatActivity {

    private static String CLASS_NAME;
    private static long UPDATE_EVERY = 200;

    protected TextView counter;
    protected Button start;
    protected Button stop;
    protected boolean timerRunning;
    protected long startedAt;
    protected long lastStopped;
    protected Handler handler;
    protected UpdateTimer updateTimer;
    protected Vibrator vibrate;
    protected long lastSeconds;

    public TimerActivity(){
        CLASS_NAME = this.getClass().getName();

        // crude hack for StrictMode activity instance count violation
//        Method m;
//        try {
//            m = StrictMode.class.getMethod("incrementExpectedActivityCount", Class.class);
//            m.invoke(null, TimerActivity.class);
//            m.invoke(null, SettingsActivity.class);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(CLASS_NAME, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);



        if(savedInstanceState != null){
            timerRunning = (boolean) savedInstanceState.getSerializable("timerRunning");
            startedAt = (long) savedInstanceState.getSerializable("startedAt");
            lastStopped = (long) savedInstanceState.getSerializable("lastStopped");
        }

        Log.d(CLASS_NAME, "Setting Counter & Buttons");
        counter = (TextView) findViewById(R.id.timer);
        start = (Button) findViewById(R.id.start_button);
        stop = (Button) findViewById(R.id.stop_button);

        enableButtons();

        //Strict Mode
        if(BuildConfig.DEBUG){
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            try {
                StrictMode.setVmPolicy(new StrictMode.VmPolicy
                        .Builder()
                        .detectLeakedClosableObjects()
                        .detectLeakedSqlLiteObjects()
                        .setClassInstanceLimit(Class.forName("com.onmybike.chrisgregory.onmybike.TimerActivity"), 1)
                        .setClassInstanceLimit(Class.forName("com.onmybike.chrisgregory.onmybike.SettingsActivity"), 1)
                        .penaltyLog()
                        .penaltyDeath()
                        .build());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(CLASS_NAME, "onStart");
        if(timerRunning){
            handler = new Handler();
            updateTimer = new UpdateTimer();
            handler.postDelayed(updateTimer, UPDATE_EVERY);
        }
        vibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if(vibrate == null){
            Log.d(CLASS_NAME, "No vibration service exists.");
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(CLASS_NAME, "onPause");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(CLASS_NAME, "onResume");
        enableButtons();
        setTimeDisplay();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(CLASS_NAME, "onStop");
        if(timerRunning){
            handler.removeCallbacks(updateTimer);
            updateTimer = null;
            handler = null;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(CLASS_NAME, "onDestroy");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.d(CLASS_NAME, "onRestart");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(CLASS_NAME, "onCreateOptionsMenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d(CLASS_NAME, "Showing menu.");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(CLASS_NAME, "onOptionsItemSelected");
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

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        Log.d(CLASS_NAME, "onSaveInstanceState");
        state.putSerializable("timerRunning", timerRunning);
        state.putSerializable("startedAt", startedAt);
        state.putSerializable("lastStopped", lastStopped);
    }

    @Override
    public void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        Log.d(CLASS_NAME, "onRestoreInstanceState");
    }

    public void clickedStart(View view){
        Log.d(CLASS_NAME, "Start button clicked.");
        timerRunning = true;
        enableButtons();

        startedAt = System.currentTimeMillis();

        Log.d(CLASS_NAME, "Setting time Display");
        setTimeDisplay();

        handler = new Handler();
        updateTimer = new UpdateTimer();
        handler.postDelayed(updateTimer, UPDATE_EVERY);

    }

    public void clickedStop(View view){
        Log.d(CLASS_NAME, "Stop button clicked.");
        timerRunning = false;
        enableButtons();

        lastStopped = System.currentTimeMillis();

        Log.d(CLASS_NAME, "Setting time Display");
        setTimeDisplay();

        handler.removeCallbacks(updateTimer);
        handler = null;
    }

    public void clickedSettings(View view){
        Log.d(CLASS_NAME, "clickedSettings");
//
//        SharedPreferences preferences = getSharedPreferences("userprefrences", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.apply();

        Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
        Log.d(CLASS_NAME, "Intent Created");
        startActivity(settingsIntent);
        Log.d(CLASS_NAME, "Intent Started");
    }

    protected void vibrateCheck(){
        long timeNow = System.currentTimeMillis();
        long diff = timeNow - startedAt;
        long seconds = diff / 1000;
        long minutes = seconds / 60;

        seconds = seconds % 60;
        minutes = minutes % 60;

        if(vibrate != null && seconds == 0 && seconds != lastSeconds){
            long[] once = {0, 100};
            long[] twice = {0, 100, 400, 100};
            long[] thrice = {0, 100, 400, 100, 400, 100};

            //every hour
            if(minutes == 0){
                Log.i(CLASS_NAME, "Vibrate 3 times");
                vibrate.vibrate(thrice, -1);
            }

            //every 15min
            else if( minutes % 15 == 0){
                Log.i(CLASS_NAME, "Vibrate 2 times");
                vibrate.vibrate(twice, -1);
            }

            //every 5min
            else if(minutes % 5 == 0){
                Log.i(CLASS_NAME, "Vibrate once");
                vibrate.vibrate(once, -1);
            }
        }

        lastSeconds = seconds;

    }

    protected void setTimeDisplay(){
        String display;
        long timeNow;
        long diff;
        long seconds;
        long minutes;
        long hours;

        if(timerRunning){
            timeNow = System.currentTimeMillis();
        } else {
            timeNow = lastStopped;
        }

        diff = timeNow - startedAt;

        // no negative time
        if(diff < 0){
            diff = 0;
        }

        seconds = diff / 1000;
        minutes = seconds / 60;
        hours = minutes / 60;
        seconds = seconds % 60;
        minutes = minutes % 60;

        display = String.format("%d", hours) + ":"
                + String.format("%02d", minutes) + ":"
                + String.format("%02d", seconds);

        counter.setText(display);
    }

    protected void enableButtons(){
        start.setEnabled(!timerRunning);
        stop.setEnabled(timerRunning);
    }

    class UpdateTimer implements Runnable {

        public void run(){
            setTimeDisplay();
            if(timerRunning){
                vibrateCheck();
            }
            if(handler != null){
                handler.postDelayed(this, UPDATE_EVERY);
            }
        }

    }
}

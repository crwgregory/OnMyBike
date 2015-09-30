package com.onmybike.chrisgregory.onmybike.activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
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

import com.onmybike.chrisgregory.onmybike.helpers.Notify;
import com.onmybike.chrisgregory.onmybike.model.TimerState;
import com.onmybike.chrisgregory.onmybike_chapter4.BuildConfig;
import com.onmybike.chrisgregory.onmybike_chapter4.R;


public class TimerActivity extends AppCompatActivity {

    private static String CLASS_NAME;
    private static long UPDATE_EVERY = 200;

    private TimerState timer;
    protected TextView counter;
    protected Button start;
    protected Button stop;
    protected Handler handler;
    protected UpdateTimer updateTimer;
    protected Vibrator vibrate;
    protected long lastSeconds;
    protected Notify notify;

    public TimerActivity(){
        CLASS_NAME = this.getClass().getName();
        timer = new TimerState();
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        Log.d(CLASS_NAME, "onNewIntent");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(CLASS_NAME, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        if(savedInstanceState != null){
            Log.d(CLASS_NAME, "Saved Instance State found.");
            timer.setRunning((boolean) savedInstanceState.getSerializable("timerRunning"));
            timer.setStartedAt((long) savedInstanceState.getSerializable("startedAt"));
            timer.setLastStopped((long) savedInstanceState.getSerializable("lastStopped"));
        } else {
            Log.d(CLASS_NAME, "A Saved Instance State could not be found.");
        }

        Log.d(CLASS_NAME, "Setting Counter & Buttons");
        counter = (TextView) findViewById(R.id.timer);
        start = (Button) findViewById(R.id.start_button);
        stop = (Button) findViewById(R.id.stop_button);

        enableButtons();
        enableStrictMode();

        notify = new Notify(this);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(CLASS_NAME, "onStart");
        if(timer.isRunning()){
            handler = new Handler();
            updateTimer = new UpdateTimer(this.getApplicationContext());
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
        counter.setText(timer.display());
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(CLASS_NAME, "onStop");
        if(timer.isRunning()){
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
        switch (id){
            case R.id.action_settings:
                clickedSettings(null);
                return true;
            case R.id.menu_routes:
                clickedRoutes();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        Log.d(CLASS_NAME, "onSaveInstanceState");
        state.putSerializable("timerRunning", timer.isRunning());
        state.putSerializable("startedAt", timer.getStartedAt());
        state.putSerializable("lastStopped", timer.getLastStopped());
    }

    @Override
    public void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        Log.d(CLASS_NAME, "onRestoreInstanceState");
    }

    public void clickedStart(View view){
        Log.d(CLASS_NAME, "Start button clicked.");
        timer.start();
        enableButtons();

        Log.d(CLASS_NAME, "Setting time Display");
        counter.setText(timer.display());

        handler = new Handler();
        updateTimer = new UpdateTimer(this.getApplicationContext());
        handler.postDelayed(updateTimer, UPDATE_EVERY);

    }

    public void clickedStop(View view){
        Log.d(CLASS_NAME, "Stop button clicked.");
        timer.stop();
        enableButtons();

        Log.d(CLASS_NAME, "Setting time Display");
        counter.setText(timer.display());
        lastSeconds = 0;

        handler.removeCallbacks(updateTimer);
        handler = null;
    }

    public void clickedRoutes(){
        Log.d(CLASS_NAME, "clickedRoutes()");
        Intent routes = new Intent(getApplicationContext(), RoutesActivity.class);
        startActivity(routes);
    }

    public void clickedSettings(View view){
        Log.d(CLASS_NAME, "clickedSettings");
        Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
        Log.d(CLASS_NAME, "Intent Created");
        startActivity(settingsIntent);
        Log.d(CLASS_NAME, "Intent Started");
    }

    protected void vibrateCheck(){
        long diff = timer.elapsedTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;

        seconds = seconds % 60;
        minutes = minutes % 60;

        if(vibrate != null && seconds == 0 && seconds != lastSeconds){
            long[] once = {0, 100};
            long[] twice = {0, 100, 400, 100};
            long[] thrice = {0, 100, 400, 100, 400, 100};
            //every hour
            if(minutes % 60 == 0){
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

    protected void notifyCheck() {
        long diff = timer.elapsedTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;


        seconds = seconds % 60;
        minutes = minutes % 60;
        if (seconds == 0 && seconds != lastSeconds){
            Resources resources = getResources();
            String message;
            String title = resources.getString(R.string.time_title);
            if(hours == 0 && minutes == 0){
                message = resources.getString(R.string.time_start_message);
            } else {
                message = String.format(resources.getString(R.string.time_running_message), hours, minutes);
            }
            notify.notify(title, message);
        }
        lastSeconds = seconds;
    }

    protected void enableButtons(){
        boolean running = timer.isRunning();
        start.setEnabled(!running);
        stop.setEnabled(running);
    }

    class UpdateTimer implements Runnable {

        Context context;

        public UpdateTimer(Context context){
            this.context = context;
        }

        public void run(){
            counter.setText(timer.display());
            if(timer.isRunning()){
                String vibrateButtonID = Integer.toString(R.id.vibrate_button);
                SharedPreferences preferences = context.getSharedPreferences("userprefrences", Activity.MODE_PRIVATE);
                if(preferences.contains(vibrateButtonID)){
                    if(preferences.getBoolean(vibrateButtonID, false)){
                        vibrateCheck();
                        notifyCheck();
                    }
                }
            }
            if(handler != null){
                handler.postDelayed(this, UPDATE_EVERY);
            }
        }
    }

    private void enableStrictMode() {
        //Strict Mode
        if(BuildConfig.DEBUG){
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            try {
                StrictMode.setVmPolicy(new StrictMode.VmPolicy
                        .Builder()
                        .detectLeakedClosableObjects()
                        .detectLeakedSqlLiteObjects()
                        .setClassInstanceLimit(Class.forName("com.onmybike.chrisgregory.onmybike.activites.TimerActivity"), 100)
                        .setClassInstanceLimit(Class.forName("com.onmybike.chrisgregory.onmybike.activites.SettingsActivity"), 100)
                        .penaltyLog()
                        .penaltyDeath()
                        .build());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

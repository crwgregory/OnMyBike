package com.onmybike.chrisgregory.onmybike.model;

/**
 * Created by Greg Christopherson on 9/25/2015.
 */
public class TimerState {

    private static String CLASS_NAME;

    protected long startedAt;
    protected long lastStopped;
    protected boolean timerRunning;

    public TimerState(){
        this.CLASS_NAME = getClass().getName();
    }

    public void start(){
        timerRunning = true;
        startedAt = System.currentTimeMillis();
    }

    public void stop(){
        timerRunning = false;
        lastStopped = System.currentTimeMillis();
    }

    public void reset(){
        timerRunning = false;
        startedAt = System.currentTimeMillis();
        lastStopped = 0;
    }

    public String display(){
        String display;

        long diff;
        long seconds;
        long minutes;
        long hours;

        diff = elapsedTime();

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

        return display;
    }

    public long elapsedTime(){
        long timeNow;
        if(timerRunning){
            timeNow = System.currentTimeMillis();
        } else {
            timeNow = lastStopped;
        }
        return timeNow - startedAt;
    }

    public boolean isRunning(){
        return timerRunning;
    }

    public void setRunning(boolean timerRunning) {
        this.timerRunning = timerRunning;
    }

    public long getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(long startedAt) {
        this.startedAt = startedAt;
    }

    public long getLastStopped() {
        return lastStopped;
    }

    public void setLastStopped(long lastStopped) {
        this.lastStopped = lastStopped;
    }
}

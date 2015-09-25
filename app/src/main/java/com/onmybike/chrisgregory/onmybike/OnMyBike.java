package com.onmybike.chrisgregory.onmybike;

import android.app.Application;

/**
 * Created by Greg Christopherson on 9/21/2015.
 */
public class OnMyBike extends Application {

    protected Settings settings;

    public Settings getSettings(){
        if(settings == null){
            settings = new Settings();
        }
        return settings;
    }

    public void setSettings(Settings settings){
        this.settings = settings;
    }

}

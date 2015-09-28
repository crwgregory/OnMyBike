package com.onmybike.chrisgregory.onmybike.helpers;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Greg Christopherson on 9/25/2015.
 */
public class Toaster {

    private static String CLASS_NAME;
    private static int DURATION = Toast.LENGTH_SHORT;
    private Context context;

    public Toaster(Context context){
        this.CLASS_NAME = getClass().getName();
        this.context = context;
    }

    public void make(int resource){
        Toast toast = Toast.makeText(context, resource, DURATION);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

}

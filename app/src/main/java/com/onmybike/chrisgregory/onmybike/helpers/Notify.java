package com.onmybike.chrisgregory.onmybike.helpers;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import com.onmybike.chrisgregory.onmybike_chapter4.R;

/**
 * Created by Greg Christopherson on 9/25/2015.
 */
public class Notify {

    private static String CLASS_NAME;
    private static final int MESSAGE_ID = 1;

    private final NotificationManager notificationManager;
    private final Context context;

    public int smallIcon = R.drawable.notification_template_icon_bg;

    public Notify(Activity activity){
        CLASS_NAME = getClass().getName();

        notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        context = activity.getApplicationContext();
    }

    public void notify(String title, String message){
        Log.d(CLASS_NAME, "notify");

        Notification notification = create(title, message, System.currentTimeMillis());

        notificationManager.notify(MESSAGE_ID, notification);
    }

    public void notify(String title, String message, long whenToNotify){
        Log.d(CLASS_NAME, "notify with whenToNotify param");

        Notification notification = create(title, message, whenToNotify);

        notificationManager.notify(MESSAGE_ID, notification);
    }

    private Notification create(String title, String message, long whenToCreate){
        Log.d(CLASS_NAME, "create");

        Notification notification = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(message)
                .setWhen(whenToCreate)
                .setSmallIcon(smallIcon).build();

        return notification;
    }

}

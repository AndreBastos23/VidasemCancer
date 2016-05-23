package com.vsc.vidasemcancer;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Date;
import java.util.Random;

/**
 * Created by Eduardo on 22/05/2016.
 */
public class NotifyService extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);


        Random random = new Random();
        notificationManager.notify(random.nextInt(200), notification);

    }
}

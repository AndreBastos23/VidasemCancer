package com.vsc.vidasemcancer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.vsc.vidasemcancer.Services.NotificationService;

/**
 * Created by Eduardo on 22/05/2016.
 */
public class NotifyService extends BroadcastReceiver {

    private static final long REPEAT_TIME = 1000 * 30;
    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, NotificationService.class);
        context.startService(intent1);

    }
}

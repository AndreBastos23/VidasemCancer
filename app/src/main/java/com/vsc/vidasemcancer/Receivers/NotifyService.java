package com.vsc.vidasemcancer.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.vsc.vidasemcancer.Services.NotificationService;

/**
 * Created by Eduardo on 22/05/2016.
 */
public class NotifyService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

        } else {
            Intent intent1 = new Intent(context, NotificationService.class);
            intent1.setAction(intent.getAction());
            context.startService(intent1);
        }

    }
}

package com.vsc.vidasemcancer.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.vsc.vidasemcancer.Managers.NotificationMng;
import com.vsc.vidasemcancer.Services.NotificationService;

public class NotifyService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            NotificationMng.initiateAll(context, sharedPreferences);
        } else {
            Intent intent1 = new Intent(context, NotificationService.class);
            intent1.setAction(intent.getAction());
            context.startService(intent1);
        }

    }
}

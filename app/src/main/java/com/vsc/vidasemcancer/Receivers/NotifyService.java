package com.vsc.vidasemcancer.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.vsc.vidasemcancer.Services.NotificationService;

public class NotifyService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, NotificationService.class);
        intent1.setAction(intent.getAction());
        context.startService(intent1);

    }
}

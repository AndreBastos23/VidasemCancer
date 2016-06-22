package com.vsc.vidasemcancer.Receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;

import com.vsc.vidasemcancer.Activities.BaseActivity;
import com.vsc.vidasemcancer.Managers.WaterManager;
import com.vsc.vidasemcancer.R;


public class WaterReceiver extends BroadcastReceiver {

    public final int WATER_NOTIFICATION = 0;

    @Override
    public void onReceive(Context context, Intent intent) {

        //Obtem a quantidade a acrescentar
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Integer amount = Integer.parseInt(preferences.getString(context.getString(R.string.water_warning_qtty_key), "300"));

        //Obtem o novo nivel de agua
        WaterManager waterManager = new WaterManager();
        waterManager.changeAmount(amount);

        Notification notification = getNotification(context, waterManager.getHumanCurrentLevel(), waterManager.getDrinkPct());

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(WATER_NOTIFICATION, notification);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        notificationManager.cancel(WATER_NOTIFICATION);

    }

    private Notification getNotification(Context context, String currentLevel, int image) {

        Intent mIntent = new Intent(context, BaseActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        android.support.v4.app.NotificationCompat.Builder builder = new android.support.v7.app.NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_stat_rsz_vidasemcancer)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), image))
                .setTicker("ticker value")
                .setAutoCancel(true)
                .setPriority(8)
                .setContentTitle(context.getString(R.string.water_notification_amount))
                .setContentText(currentLevel + context.getString(R.string.water_unit));


        return builder.build();

    }


}

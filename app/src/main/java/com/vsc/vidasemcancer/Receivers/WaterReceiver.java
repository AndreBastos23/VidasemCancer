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
import com.vsc.vidasemcancer.Models.Water;
import com.vsc.vidasemcancer.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;


public class WaterReceiver extends BroadcastReceiver {

    public final int WATER_NOTIFICATION = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        String currentLevel = getCurrentLevel(context);
        Notification notification = getNotification(context, currentLevel);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(WATER_NOTIFICATION, notification);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        notificationManager.cancel(WATER_NOTIFICATION);

    }

    private String getCurrentLevel(Context context) {
        Realm realm = Realm.getDefaultInstance();
        Water waterObject;

        String today = getTodayInString(0);

        RealmResults<Water> results = realm.where(Water.class).equalTo("date", today).findAll();
        if (results.isEmpty()) {
            realm.beginTransaction();
            waterObject = realm.createObject(Water.class);
            waterObject.setCurrentLevel(0);
            waterObject.setObjective(2000);
            waterObject.setDDate(new Date());
            waterObject.setDate(today);
            realm.commitTransaction();
        } else {
            waterObject = results.first();
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Integer amount = Integer.parseInt(preferences.getString(context.getString(R.string.water_warning_qtty_key), "300"));

        realm.beginTransaction();
        int waterLevel = waterObject.getCurrentLevel() + amount;
        waterObject.setCurrentLevel(waterLevel);
        realm.commitTransaction();

        String currentLevel = (Double.parseDouble(waterObject.getCurrentLevel() + "") / 1000) + "";

        realm.close();

        return currentLevel;
    }

    private String getTodayInString(int nDays) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, nDays);
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        return formatter.format(cal.getTime());
    }

    private Notification getNotification(Context context, String currentLevel) {

        Intent mIntent = new Intent(context, BaseActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        android.support.v4.app.NotificationCompat.Builder builder = new android.support.v7.app.NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_stat_rsz_vidasemcancer)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.water_100))
                .setTicker("ticker value")
                .setAutoCancel(true)
                .setPriority(8)
                .setContentTitle(context.getString(R.string.water_notification_amount))
                .setContentText(currentLevel + context.getString(R.string.water_unit));


        return builder.build();

    }


}

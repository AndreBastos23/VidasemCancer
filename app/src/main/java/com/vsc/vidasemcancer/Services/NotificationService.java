package com.vsc.vidasemcancer.Services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.vsc.vidasemcancer.Activities.BaseActivity;
import com.vsc.vidasemcancer.Models.Water;
import com.vsc.vidasemcancer.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Eduardo on 25/05/2016.
 */
public class NotificationService extends IntentService {

    public final int WATER_NOTIFICATION = 0;
    public final int BREATHE_NOTIFICATION = 1;
    public final int BREAKFAST_NOTIFICATION = 2;
    public final int LUNCH_NOTIFICATION = 3;
    public final int DINNER_NOTIFICATION = 4;
    public final int SUN_NOTIFICATION = 5;
    Notification notification;
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    private Object sunNotification;
    private Object breatheNotification;


    public NotificationService() {
        super("not");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int notificationId = 100;

        if (intent.getAction().equals(getString(R.string.water_notification))) {
            if (isObjectiveCompleted()) {
                return;
            }
            notificationId = WATER_NOTIFICATION;
            getWaterNotification();
        } else if (intent.getAction().equals(getString(R.string.sun_notification))) {
            notificationId = SUN_NOTIFICATION;
            getSunNotification();
        } else if (intent.getAction().equals(getString(R.string.breathe_notification))) {
            notificationId = BREATHE_NOTIFICATION;
            getBreatheNotification();
        } else if (intent.getAction().equals(getString(R.string.eat_breakfast_notification))) {
            notificationId = BREAKFAST_NOTIFICATION;
            getFoodNotification(notificationId);
        } else if (intent.getAction().equals(getString(R.string.eat_lunch_notification))) {
            notificationId = LUNCH_NOTIFICATION;
            getFoodNotification(notificationId);
        } else if (intent.getAction().equals(getString(R.string.eat_dinner_notification))) {
            notificationId = DINNER_NOTIFICATION;
            getFoodNotification(notificationId);
        }

        notificationManager.notify(notificationId, notification);
        Log.i("notif", "Notifications sent.");

    }

    private boolean isObjectiveCompleted() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getBaseContext()).build();
        Realm realm = Realm.getInstance(realmConfig);
        Calendar cal = Calendar.getInstance();

        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        String str = formatter.format(cal.getTime());
        RealmResults<Water> results = realm.where(Water.class).equalTo("date", str).findAll();

        if (results.isEmpty()) {
            return false;
        } else {
            Water waterObject = results.first();
            return waterObject.getCurrentLevel() >= waterObject.getObjective();
        }
    }

    private void getWaterNotification() {
        Context context = this.getApplicationContext();
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, BaseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("text", "Tem bebido água?");
        mIntent.putExtras(bundle);
        pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Resources res = this.getResources();
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        notification = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_stat_rsz_vidasemcancer)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.water_100))
                .setTicker("ticker value")
                .setAutoCancel(true)
                .setPriority(8)
                .setSound(soundUri)
                .setContentTitle("Lembrete")
                .setContentText("Tem bebido água?").build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
        notification.defaults |= Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
        notification.ledARGB = 0xFFFFA500;
        notification.ledOnMS = 800;
        notification.ledOffMS = 1000;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public void getSunNotification() {
        Context context = this.getApplicationContext();
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, BaseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("text", "Já apanhou sol hoje?");
        mIntent.putExtras(bundle);
        pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Resources res = this.getResources();
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        notification = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.imagemfinal)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.imagemfinal))
                .setTicker("ticker value")
                .setAutoCancel(true)
                .setPriority(8)
                .setSound(soundUri)
                .setContentTitle("Lembrete")
                .setContentText("Já apanhou sol hoje?").build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
        notification.defaults |= Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
        notification.ledARGB = 0xFFFFA500;
        notification.ledOnMS = 800;
        notification.ledOffMS = 1000;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public void getBreatheNotification() {
        Context context = this.getApplicationContext();
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, BaseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("text", "Já fez os exercicios de respiração?");
        mIntent.putExtras(bundle);
        pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Resources res = this.getResources();
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        notification = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.imagemfinal)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.imagemfinal))
                .setTicker("ticker value")
                .setAutoCancel(true)
                .setPriority(8)
                .setSound(soundUri)
                .setContentTitle("Lembrete")
                .setContentText("Já fez os exercicios de respiração?").build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
        notification.defaults |= Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
        notification.ledARGB = 0xFFFFA500;
        notification.ledOnMS = 800;
        notification.ledOffMS = 1000;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private void getFoodNotification(int notificationId) {
        Context context = this.getApplicationContext();
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, BaseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("text", "Já Comeu?");
        mIntent.putExtras(bundle);
        pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Resources res = this.getResources();
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        notification = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.imagemfinal)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.imagemfinal))
                .setTicker("ticker value")
                .setAutoCancel(true)
                .setPriority(8)
                .setSound(soundUri)
                .setContentTitle("Lembrete")
                .setContentText("Já Comeu?").build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
        notification.defaults |= Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
        notification.ledARGB = 0xFFFFA500;
        notification.ledOnMS = 800;
        notification.ledOffMS = 1000;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }
}

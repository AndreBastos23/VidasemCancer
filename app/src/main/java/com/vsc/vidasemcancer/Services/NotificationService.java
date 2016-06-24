package com.vsc.vidasemcancer.Services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.vsc.vidasemcancer.Activities.BaseActivity;
import com.vsc.vidasemcancer.Models.PreferenceDate;
import com.vsc.vidasemcancer.Models.Water;
import com.vsc.vidasemcancer.R;
import com.vsc.vidasemcancer.Receivers.WaterReceiver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class NotificationService extends IntentService {

    private static final int MORNING_SNACK_NOTIFICATION = 8;
    private static final int AFTERNOON_SNACK_NOTIFICATION = 9;
    private static RealmConfiguration realmConfiguration;
    public final int WATER_NOTIFICATION = 0;
    public final int BREATHE_NOTIFICATION = 1;
    public final int BREAKFAST_NOTIFICATION = 2;
    public final int LUNCH_NOTIFICATION = 3;
    public final int DINNER_NOTIFICATION = 4;
    public final int SUN_NOTIFICATION = 5;
    public final int MEDITATION_NOTIFICATION = 6;
    public final int SPORTS_NOTIFICATION = 7;
    Notification notification;
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;


    public NotificationService() {
        super("ServiçoDeAvisos");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int notificationId = 100;

        if (nightTime()) {
            return;
        }


        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals(getString(R.string.water_notification))) {

                //TODO: REVIEW AT LATER TIME. OPENING REALM INSTANCE WITH DIFFERENT CONTEXT IS THROWING EXCEPTIONS
            /*Realm realm = Realm.getDefaultInstance();
            if (isObjectiveCompleted(realm)) {
                return;
            }*/
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
            } else if (intent.getAction().equals(getString(R.string.sports_notification))) {
                notificationId = SPORTS_NOTIFICATION;
                getSportsNotification();
            } else if (intent.getAction().equals(getString(R.string.meditation_notification))) {
                notificationId = MEDITATION_NOTIFICATION;
                getMeditationNotification();
            } else if (intent.getAction().equals(getString(R.string.eat_morningsnack_notification))) {
                notificationId = MORNING_SNACK_NOTIFICATION;
                getFoodNotification(notificationId);
            } else if (intent.getAction().equals(getString(R.string.eat_afternoonsnack_notification))) {
                notificationId = AFTERNOON_SNACK_NOTIFICATION;
                getFoodNotification(notificationId);
            }
        }

        if (notification != null) {
            notificationManager.notify(notificationId, notification);
        }
        Log.i("notif", "Notifications sent.");

    }

    private boolean nightTime() {
        Boolean night = false;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        PreferenceDate sleep = new PreferenceDate(sharedPreferences.getString(getString(R.string.notification_settings_sleep_key), "22:00"));
        PreferenceDate wakeUp = new PreferenceDate(sharedPreferences.getString(getString(R.string.notification_settings_wakeUp_key), "09:00"));

        Calendar nightTime = Calendar.getInstance();
        nightTime.set(Calendar.HOUR_OF_DAY, sleep.getHour());
        nightTime.set(Calendar.MINUTE, sleep.getMinute());

        Calendar daytime = Calendar.getInstance();
        daytime.set(Calendar.HOUR_OF_DAY, wakeUp.getHour());
        daytime.set(Calendar.MINUTE, wakeUp.getMinute());

        Calendar now = Calendar.getInstance();
        if (now.after(nightTime) || now.before(daytime)) {
            return true;
        }

        return night;
    }

    private boolean isObjectiveCompleted(Realm realm) {


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

        Intent nIntent = new Intent(this, WaterReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, nIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        android.support.v4.app.NotificationCompat.Action action = new android.support.v4.app.NotificationCompat.Action(R.drawable.ic_stat_rsz_vidasemcancer, "Já bebi!", pIntent);
        List<android.support.v4.app.NotificationCompat.Action> actionList = new LinkedList<>();
        actionList.add(action);
        buildNotification(res, soundUri, R.drawable.notiwater, R.string.water_notification_title, R.string.water_notification_text, actionList);
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
        buildNotification(res, soundUri, R.drawable.notisun, R.string.sun_notification_title, R.string.sun_notification_text, new LinkedList<android.support.v4.app.NotificationCompat.Action>());

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
        buildNotification(res, soundUri, R.drawable.notibreathing, R.string.breathe_notification_title, R.string.breathe_notification_text, new LinkedList<android.support.v4.app.NotificationCompat.Action>());

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

        switch (notificationId) {
            case BREAKFAST_NOTIFICATION:
                buildNotification(res, soundUri, R.drawable.notifood, R.string.eat_notification_title, R.string.eat_breakfast_notification_text, new LinkedList<android.support.v4.app.NotificationCompat.Action>());
                break;
            case LUNCH_NOTIFICATION:
                buildNotification(res, soundUri, R.drawable.notifood, R.string.eat_notification_title, R.string.eat_lunch_notification_text, new LinkedList<android.support.v4.app.NotificationCompat.Action>());
                break;
            case DINNER_NOTIFICATION:
                buildNotification(res, soundUri, R.drawable.notifood, R.string.eat_notification_title, R.string.eat_dinner_notification_text, new LinkedList<android.support.v4.app.NotificationCompat.Action>());
                break;
            case AFTERNOON_SNACK_NOTIFICATION:
            case MORNING_SNACK_NOTIFICATION:
                buildNotification(res, soundUri, R.drawable.notifood, R.string.eat_notification_title, R.string.eat_snack_notification_text, new LinkedList<android.support.v4.app.NotificationCompat.Action>());
                break;
            default:
                buildNotification(res, soundUri, R.drawable.notifood, R.string.eat_notification_title, R.string.eat_generic_notification_text, new LinkedList<android.support.v4.app.NotificationCompat.Action>());

        }

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public void getSportsNotification() {
        Context context = this.getApplicationContext();
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, BaseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("text", "Já praticou exercicio hoje?");
        mIntent.putExtras(bundle);
        pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Resources res = this.getResources();
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        buildNotification(res, soundUri, R.drawable.notisport, R.string.sports_notification_title, R.string.sports_notification_text, new LinkedList<android.support.v4.app.NotificationCompat.Action>());

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public void getMeditationNotification() {
        Context context = this.getApplicationContext();
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, BaseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("text", "Já meditou hoje?");
        mIntent.putExtras(bundle);
        pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Resources res = this.getResources();
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        buildNotification(res, soundUri, R.drawable.notimeditation, R.string.meditation_notification_title, R.string.meditation_notification_text, new LinkedList<android.support.v4.app.NotificationCompat.Action>());

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private void buildNotification(Resources res, Uri soundUri, int largeImage, int title, int content, List<android.support.v4.app.NotificationCompat.Action> actionList) {


        android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_stat_rsz_vidasemcancer)
                .setLargeIcon(BitmapFactory.decodeResource(res, largeImage))
                .setTicker("ticker value")
                .setAutoCancel(true)
                .setPriority(8)
                .setSound(soundUri)
                .setContentTitle(getString(title))
                .setContentText(getString(content));

        for (android.support.v4.app.NotificationCompat.Action action : actionList) {
            builder.addAction(action);
        }

        notification = builder.build();


        notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
        notification.defaults |= Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
        notification.ledARGB = 0xFFFFA500;
        notification.ledOnMS = 800;
        notification.ledOffMS = 1000;
    }
}

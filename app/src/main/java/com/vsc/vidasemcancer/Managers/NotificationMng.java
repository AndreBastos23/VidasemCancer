package com.vsc.vidasemcancer.Managers;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.vsc.vidasemcancer.R;
import com.vsc.vidasemcancer.Receivers.NotifyService;

import java.util.Calendar;

public class NotificationMng {
    private static final int WATER_NOTIFICATION = 0;
    private static final int BREATHE_NOTIFICATION = 1;
    private static final int BREAKFAST_NOTIFICATION = 2;
    private static final int LUNCH_NOTIFICATION = 3;
    private static final int DINNER_NOTIFICATION = 4;
    private static final int SUN_NOTIFICATION = 5;
    private static final int MEDITATION_NOTIFICATION = 6;
    private static final int SPORTS_NOTIFICATION = 7;


    public static void scheduleNotification(Boolean schedule, Context context, int notificationId, long interval, Calendar calendar, String action) {

        Intent myIntent = new Intent(context, NotifyService.class);
        myIntent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        if (schedule) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
        }

    }

    public static void rememberBreathe(Context context, SharedPreferences preferences) {
        Calendar calendar = Calendar.getInstance();


        Boolean breatheWarning = preferences.getBoolean(context.getString(R.string.breathe_warning_key), true);
        String interval = preferences.getString(context.getString(R.string.breathe_category_interval_key), context.getString(R.string.breathe_interval_default));
        calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(interval));

        scheduleNotification(breatheWarning, context, BREATHE_NOTIFICATION, Long.parseLong(interval) * AlarmManager.INTERVAL_HOUR, calendar, context.getString(R.string.breathe_notification));
    }

    public static void rememberSun(Context context, SharedPreferences preferences) {
        Calendar calendar = Calendar.getInstance();

        Boolean sunWarning = preferences.getBoolean(context.getString(R.string.sun_warning_key), true);
        String time = preferences.getString(context.getString(R.string.sun_time_key), "16:00");
        String[] params = time.split(":");
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(params[0]));
        calendar.set(Calendar.MINUTE, Integer.valueOf(params[1]));

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        scheduleNotification(sunWarning, context, SUN_NOTIFICATION, AlarmManager.INTERVAL_DAY, calendar, context.getString(R.string.sun_notification));

    }

    public static void rememberSports(Context context, SharedPreferences sharedPreferences) {
        Calendar calendar = Calendar.getInstance();

        Boolean sportsWarning = sharedPreferences.getBoolean(context.getString(R.string.sports_warning_key), true);
        String time = sharedPreferences.getString(context.getString(R.string.sports_time_key), "16:00");
        String[] params = time.split(":");
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(params[0]));
        calendar.set(Calendar.MINUTE, Integer.valueOf(params[1]));
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        scheduleNotification(sportsWarning, context, SPORTS_NOTIFICATION, AlarmManager.INTERVAL_DAY, calendar, context.getString(R.string.sports_notification));

    }

    public static void rememberWater(Context context, SharedPreferences preferences) {
        Calendar calendar = Calendar.getInstance();

        Boolean waterWarning = preferences.getBoolean(context.getString(R.string.water_warning_key), true);
        String interval = preferences.getString(context.getString(R.string.water_warning_interval_key), context.getString(R.string.water_interval_default));
        calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(interval));

        scheduleNotification(waterWarning, context, WATER_NOTIFICATION, Long.valueOf(interval) * AlarmManager.INTERVAL_HOUR, calendar, context.getString(R.string.water_notification));


    }

    public static void rememberMeditation(Context context, SharedPreferences sharedPreferences) {
        Calendar calendar = Calendar.getInstance();

        Boolean meditationWarning = sharedPreferences.getBoolean(context.getString(R.string.meditation_warning_key), true);
        String time = sharedPreferences.getString(context.getString(R.string.meditation_time_key), "16:00");
        String[] params = time.split(":");
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(params[0]));
        calendar.set(Calendar.MINUTE, Integer.valueOf(params[1]));
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        scheduleNotification(meditationWarning, context, MEDITATION_NOTIFICATION, AlarmManager.INTERVAL_DAY, calendar, context.getString(R.string.meditation_notification));

    }

    public static void rememberFood(Context context, SharedPreferences preferences) {
        Calendar breakfastCalendar = Calendar.getInstance();
        breakfastCalendar.set(Calendar.HOUR_OF_DAY, 9);

        Calendar lunchCalendar = Calendar.getInstance();
        lunchCalendar.set(Calendar.HOUR_OF_DAY, 13);

        Calendar dinnerCalendar = Calendar.getInstance();
        dinnerCalendar.set(Calendar.HOUR_OF_DAY, 21);


        Boolean foodWarning = preferences.getBoolean(context.getString(R.string.eat_warning_key), true);
        if (breakfastCalendar.before(Calendar.getInstance())) {
            breakfastCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        if (lunchCalendar.before(Calendar.getInstance())) {
            lunchCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        if (dinnerCalendar.before(Calendar.getInstance())) {
            dinnerCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        scheduleNotification(foodWarning, context, BREAKFAST_NOTIFICATION, AlarmManager.INTERVAL_DAY, breakfastCalendar, context.getString(R.string.eat_breakfast_notification));
        scheduleNotification(foodWarning, context, LUNCH_NOTIFICATION, AlarmManager.INTERVAL_DAY, lunchCalendar, context.getString(R.string.eat_lunch_notification));
        scheduleNotification(foodWarning, context, DINNER_NOTIFICATION, AlarmManager.INTERVAL_DAY, dinnerCalendar, context.getString(R.string.eat_dinner_notification));

    }


    public static void initiateAll(Context context, SharedPreferences preferences) {
        rememberBreathe(context, preferences);
        rememberFood(context, preferences);
        rememberMeditation(context, preferences);
        rememberSports(context, preferences);
        rememberSun(context, preferences);
        rememberWater(context, preferences);
    }
}

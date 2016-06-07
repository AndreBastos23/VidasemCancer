package com.vsc.vidasemcancer.Managers;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.vsc.vidasemcancer.Models.PreferenceDate;
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
    private static final int MORNING_SNACK_NOTIFICATION = 8;
    private static final int AFTERNOON_SNACK_NOTIFICATION = 9;


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
        PreferenceDate breakfastHour = new PreferenceDate(preferences.getString(
                context.getString(R.string.eat_breakfast_time_key), "09:00"));
        breakfastCalendar.set(Calendar.HOUR_OF_DAY, breakfastHour.getHour());
        breakfastCalendar.set(Calendar.MINUTE, breakfastHour.getMinute());

        Calendar morningSnackCalendar = Calendar.getInstance();
        PreferenceDate morningSnackHour = new PreferenceDate(preferences.getString(
                context.getString(R.string.eat_morningsnack_time_key), "10:30"));
        morningSnackCalendar.set(Calendar.HOUR_OF_DAY, morningSnackHour.getHour());
        morningSnackCalendar.set(Calendar.MINUTE, morningSnackHour.getMinute());

        Calendar lunchCalendar = Calendar.getInstance();
        PreferenceDate lunchHour = new PreferenceDate(preferences.getString(context.getString(
                R.string.eat_lunch_time_key), "13:00"));
        lunchCalendar.set(Calendar.HOUR_OF_DAY, lunchHour.getHour());
        lunchCalendar.set(Calendar.MINUTE, lunchHour.getMinute());


        Calendar afternoonSnackCalendar = Calendar.getInstance();
        PreferenceDate afternoonSnackHour = new PreferenceDate(preferences.getString(
                context.getString(R.string.eat_afternoonsnack_time_key), "16:30"));
        afternoonSnackCalendar.set(Calendar.HOUR_OF_DAY, afternoonSnackHour.getHour());
        afternoonSnackCalendar.set(Calendar.MINUTE, afternoonSnackHour.getMinute());

        Calendar dinnerCalendar = Calendar.getInstance();
        PreferenceDate dinnerHour = new PreferenceDate(preferences.getString(
                context.getString(R.string.eat_dinner_time_key), "21:00"));
        dinnerCalendar.set(Calendar.HOUR_OF_DAY, dinnerHour.getHour());
        dinnerCalendar.set(Calendar.MINUTE, dinnerHour.getMinute());


        Boolean foodWarning = preferences.getBoolean(context.getString(R.string.eat_warning_key), true);
        if (breakfastCalendar.before(Calendar.getInstance())) {
            breakfastCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        if (morningSnackCalendar.before(Calendar.getInstance())) {
            morningSnackCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        if (lunchCalendar.before(Calendar.getInstance())) {
            lunchCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        if (afternoonSnackCalendar.before(Calendar.getInstance())) {
            afternoonSnackCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        if (dinnerCalendar.before(Calendar.getInstance())) {
            dinnerCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        scheduleNotification(foodWarning, context, BREAKFAST_NOTIFICATION, AlarmManager.INTERVAL_DAY, breakfastCalendar, context.getString(R.string.eat_breakfast_notification));
        scheduleNotification(foodWarning, context, MORNING_SNACK_NOTIFICATION, AlarmManager.INTERVAL_DAY, morningSnackCalendar, context.getString(R.string.eat_morningsnack_notification));
        scheduleNotification(foodWarning, context, LUNCH_NOTIFICATION, AlarmManager.INTERVAL_DAY, lunchCalendar, context.getString(R.string.eat_lunch_notification));
        scheduleNotification(foodWarning, context, AFTERNOON_SNACK_NOTIFICATION, AlarmManager.INTERVAL_DAY, afternoonSnackCalendar, context.getString(R.string.eat_afternoonsnack_notification));
        scheduleNotification(foodWarning, context, DINNER_NOTIFICATION, AlarmManager.INTERVAL_DAY, dinnerCalendar, context.getString(R.string.eat_dinner_notification));


    }


    public static void initiateAll(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Toast toast = Toast.makeText(context, "InitiateALl VSC", Toast.LENGTH_LONG);
        toast.show();
        rememberBreathe(context, preferences);
        rememberFood(context, preferences);
        rememberMeditation(context, preferences);
        rememberSports(context, preferences);
        rememberSun(context, preferences);
        rememberWater(context, preferences);
    }
}

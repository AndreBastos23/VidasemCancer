package com.vsc.vidasemcancer.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.vsc.vidasemcancer.Models.Water;
import com.vsc.vidasemcancer.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Eduardo on 13/06/2016.
 */
public class WaterReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
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


        Toast toast = Toast.makeText(context, "Hoje já bebeu: " + waterObject.getCurrentLevel(), Toast.LENGTH_SHORT);
        toast.show();

        realm.close();

    }

    private String getTodayInString(int nDays) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, nDays);
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        return formatter.format(cal.getTime());
    }


}

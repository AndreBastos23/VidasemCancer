package com.vsc.vidasemcancer.Managers;


import com.github.mikephil.charting.data.Entry;
import com.vsc.vidasemcancer.Models.Water;
import com.vsc.vidasemcancer.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class WaterManager {

    //negative to account days in past
    private final Integer NUM_DAYS_TO_SHOW_IN_GRAPH = -7;

    private Water waterObject;
    private RealmResults<Water> graphData;
    private Realm realm;
    private String today;
    private ArrayList<Entry> ammountList;
    private ArrayList<String> keyList;

    public WaterManager() {
        today = getTodayInString(0);
        realm = realm.getDefaultInstance();
        getWaterObject();
        getGraphData();

    }


    private String getTodayInString(int nDays) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, nDays);
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        return formatter.format(cal.getTime());
    }

    private void getWaterObject() {
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
    }

    private void getGraphData() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, NUM_DAYS_TO_SHOW_IN_GRAPH);
        Date sevenDaysAgo = new Date(calendar.getTimeInMillis());
        graphData = realm.where(Water.class).between("dDate", sevenDaysAgo, waterObject.getdDate()).findAll();
        ammountList = new ArrayList<Entry>();
        keyList = new ArrayList<String>();
        Iterator<Water> it = graphData.iterator();
        int cont = 0;

        while (it.hasNext()) {
            Water water = it.next();
            ammountList.add(new Entry(((float) water.getCurrentLevel()) / 1000, cont));
            keyList.add(water.getDate().substring(0, 5));

            cont++;
        }

    }


    public void close() {
        realm.close();
    }

    public String getHumanCurrentLevel() {
        return String.valueOf((double) waterObject.getCurrentLevel() / 1000);
    }

    public float getHumanObjective() {
        return ((float) waterObject.getObjective() / 1000);
    }

    public List<Entry> getValues() {
        return ammountList;
    }

    public List<String> getKeys() {
        return keyList;
    }

    public void changeAmount(Integer amount) {
        realm.beginTransaction();
        int waterLevel = waterObject.getCurrentLevel() + amount;
        if (waterLevel > 0) {
            waterObject.setCurrentLevel(waterLevel);
        } else {
            waterObject.setCurrentLevel(0);
        }
        realm.commitTransaction();
        getGraphData();
    }

    public int getDrinkPct() {

        int objective = getObjectivePct();

        if (objective >= 100) {
            return R.drawable.water_10;
        } else if (objective >= 90) {
            return R.drawable.water_9;
        } else if (objective >= 80) {
            return R.drawable.water_8;
        } else if (objective >= 70) {
            return R.drawable.water_7;
        } else if (objective >= 60) {
            return R.drawable.water_6;
        } else if (objective >= 50) {
            return R.drawable.water_5;
        } else if (objective >= 40) {
            return R.drawable.water_4;
        } else if (objective >= 30) {
            return R.drawable.water_3;
        } else if (objective >= 20) {
            return R.drawable.water_2;
        } else if (objective >= 10) {
            return R.drawable.water_1;
        } else {
            return R.drawable.water_0;
        }

    }

    public int getObjectivePct() {
        int objective = 0;
        if (waterObject.getCurrentLevel() != 0) {
            objective = (waterObject.getCurrentLevel() * 100 / waterObject.getObjective());
        }
        return objective;
    }
}

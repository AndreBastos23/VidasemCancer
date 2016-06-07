package com.vsc.vidasemcancer.Models;

/**
 * Created by Eduardo on 07/06/2016.
 */
public class PreferenceDate {

    private int hour;
    private int minute;


    public PreferenceDate(String preferenceDate) {
        String[] dateTime = preferenceDate.split(":");
        hour = Integer.valueOf(dateTime[0]);
        minute = Integer.valueOf(dateTime[1]);

    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }


}

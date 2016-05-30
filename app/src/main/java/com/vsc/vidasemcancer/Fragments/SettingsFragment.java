package com.vsc.vidasemcancer.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.vsc.vidasemcancer.Managers.NotificationMng;
import com.vsc.vidasemcancer.R;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public SettingsFragment() {

    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager manager = getPreferenceManager();
        manager.setSharedPreferencesName("PREFS");
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences_screen);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (isAdded()) {
            if (key.equals(getString(R.string.water_warning_key)) || key.equals(getString(R.string.water_warning_interval_key))) {
                NotificationMng.rememberWater(getActivity(), sharedPreferences);
            } else if (key.equals(getString(R.string.sun_warning_key)) || key.equals(getString(R.string.sun_time_key))) {
                NotificationMng.rememberSun(getActivity(), sharedPreferences);
            } else if (key.equals(getString(R.string.breathe_warning_key)) || key.equals(getString(R.string.breathe_category_interval_key))) {
                NotificationMng.rememberBreathe(getActivity(), sharedPreferences);
            } else if (key.equals(getString(R.string.eat_warning_key))) {
                NotificationMng.rememberFood(getActivity(), sharedPreferences);
            } else if (key.equals(getString(R.string.sports_warning_key)) || key.equals(getString(R.string.sports_time_key))) {
                NotificationMng.rememberSports(getActivity(), sharedPreferences);
            } else if (key.equals(getString(R.string.meditation_warning_key)) || key.equals(getString(R.string.meditation_time_key))) {
                NotificationMng.rememberMeditation(getActivity(), sharedPreferences);
            }
        }
    }


}

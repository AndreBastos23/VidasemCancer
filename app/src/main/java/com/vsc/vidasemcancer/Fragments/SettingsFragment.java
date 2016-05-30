package com.vsc.vidasemcancer.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.vsc.vidasemcancer.Activities.BaseActivity;
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

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences_screen);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        BaseActivity baseActivity = (BaseActivity) getActivity();
        if (key.equals(getString(R.string.water_warning_key)) || key.equals(getString(R.string.water_warning_interval_key))) {
            baseActivity.rememberWater(sharedPreferences);
        } else if (key.equals(getString(R.string.sun_warning_key)) || key.equals(getString(R.string.sun_time_key))) {
            baseActivity.rememberSun(sharedPreferences);
        } else if (key.equals(getString(R.string.breathe_warning_key)) || key.equals(getString(R.string.breathe_time_key))) {
            baseActivity.rememberBreathe(sharedPreferences);
        } else if (key.equals(getString(R.string.eat_warning_key))) {
            baseActivity.rememberFood(sharedPreferences);
        } else if (key.equals(getString(R.string.sports_warning_key)) || key.equals(getString(R.string.sports_time_key))) {
            baseActivity.rememberSports(sharedPreferences);
        } else if (key.equals(getString(R.string.meditation_warning_key)) || key.equals(getString(R.string.meditation_time_key))) {
            baseActivity.rememberMeditation(sharedPreferences);
        }
    }


}

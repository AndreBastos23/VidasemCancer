package com.vsc.vidasemcancer.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.vsc.vidasemcancer.BaseActivity;
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
        if (key.equals("water_warning")) {
            Preference connectionPref = findPreference(key);

            Boolean setting = sharedPreferences.getBoolean("water_warning", true);
            BaseActivity thi = (BaseActivity) getActivity();
            thi.rememberWater(setting);


            // Set summary to be the user-description for the selected value
            connectionPref.setSummary(String.valueOf(sharedPreferences.getBoolean(key, true)));
        }
    }


}

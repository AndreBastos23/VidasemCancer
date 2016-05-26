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
        if (key.equals(getString(R.string.water_warning_key))) {
            Boolean setting = sharedPreferences.getBoolean(getString(R.string.water_warning_key), true);
            BaseActivity baseActivity = (BaseActivity) getActivity();
            baseActivity.rememberWater(setting);

        }
    }


}

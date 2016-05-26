package com.vsc.vidasemcancer;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.vsc.vidasemcancer.Fragments.PM_Fragment;
import com.vsc.vidasemcancer.Fragments.RecipeDetailsFragment;
import com.vsc.vidasemcancer.Fragments.SettingsDialogFragment;
import com.vsc.vidasemcancer.Fragments.SettingsFragment;
import com.vsc.vidasemcancer.Interface.OnRecipeSelected;

import java.util.Calendar;


public class BaseActivity extends AppCompatActivity implements OnRecipeSelected {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        if (isFirstTime()) {

            Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(myToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawerToggle = setupDrawerToggle();
            mDrawer.addDrawerListener(drawerToggle);
            nvDrawer = (NavigationView) findViewById(R.id.nvView);
            setupDrawerContent(nvDrawer);

            Fragment fragment = null;
            Class fragmentClass = SettingsFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.flContent, fragment).commit();
            showDialog();
        } else {

            Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(myToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawerToggle = setupDrawerToggle();
            mDrawer.addDrawerListener(drawerToggle);
            nvDrawer = (NavigationView) findViewById(R.id.nvView);
            setupDrawerContent(nvDrawer);
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean waterWarning = preferences.getBoolean("water_warning", true);
        rememberWater(waterWarning);
    }

    private void rememberWater(Boolean config) {
        Calendar calendar = Calendar.getInstance();

        Intent myIntent = new Intent(BaseActivity.this, NotifyService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(BaseActivity.this, 0, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (config) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 30 * 1000, pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
        }


    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView nvDrawer) {
        nvDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.recipes_item:
                fragmentClass = PM_Fragment.class;
                break;
            case R.id.sun_item:
                fragmentClass = PM_Fragment.class;
                break;
            case R.id.exercise_item:
                fragmentClass = PM_Fragment.class;
                break;
            case R.id.settings_item:
                fragmentClass = SettingsFragment.class;
                break;
            default:
                fragmentClass = PM_Fragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getFragmentManager();


        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.flContent, fragment).commit();


        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onRageComicSelected() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction().addToBackStack(null);

        RecipeDetailsFragment pm_fragment = new RecipeDetailsFragment();
        fragmentTransaction.replace(R.id.flContent, pm_fragment);

        fragmentTransaction.commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private boolean isFirstTime() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            //First time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.apply();
        }
        return !ranBefore;
    }

    void showDialog() {
        DialogFragment newFragment = SettingsDialogFragment.newInstance(R.string.alert_dialog_title, R.string.alert_dialog_message);
        newFragment.show(getSupportFragmentManager(), "dialog");
    }
}
package com.vsc.vidasemcancer.Activities;

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

import com.vsc.vidasemcancer.Fragments.RecipeDetailsFragment;
import com.vsc.vidasemcancer.Fragments.RecipesListFragment;
import com.vsc.vidasemcancer.Fragments.SettingsDialogFragment;
import com.vsc.vidasemcancer.Fragments.SettingsFragment;
import com.vsc.vidasemcancer.Fragments.WaterFragment;
import com.vsc.vidasemcancer.Interface.OnRecipeSelected;
import com.vsc.vidasemcancer.R;
import com.vsc.vidasemcancer.Receivers.NotifyService;

import java.util.Calendar;


/**
 * The type Base activity.
 */
public class BaseActivity extends AppCompatActivity implements OnRecipeSelected {

    public final int WATER_NOTIFICATION = 0;
    public final int BREATHE_NOTIFICATION = 1;
    public final int BREAKFAST_NOTIFICATION = 2;
    public final int LUNCH_NOTIFICATION = 3;
    public final int DINNER_NOTIFICATION = 4;
    public final int SUN_NOTIFICATION = 5;
    public final int MEDITATION_NOTIFICATION = 6;
    public final int SPORTS_NOTIFICATION = 7;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setupToolbar();

        //Code to execute on application's first run
        if (savedInstanceState == null) {
            if (isFirstTime()) {

                handleFirstRun();
            } else {
                handleInitialFragment();
            }
        }


    }

    private void handleInitialFragment() {
        Fragment fragment = null;
        Class fragmentClass = WaterFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }

    private void handleFirstRun() {

        handleNotifications();
        //open settings fragment
        Fragment fragment = null;
        Class fragmentClass = SettingsFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.flContent, fragment).commit();

        //show first time dialog
        showDialog();
    }

    private void handleNotifications() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        rememberWater(preferences);
        rememberBreathe(preferences);
        rememberFood(preferences);
        rememberSun(preferences);
        rememberSports(preferences);
        rememberMeditation(preferences);

    }

    public void rememberSun(SharedPreferences preferences) {
        Calendar calendar = Calendar.getInstance();
        Intent myIntent = new Intent(BaseActivity.this, NotifyService.class);
        myIntent.setAction(getString(R.string.sun_notification));
        PendingIntent sunIntent = PendingIntent.getBroadcast(BaseActivity.this, SUN_NOTIFICATION, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Boolean sunWarning = preferences.getBoolean(getString(R.string.sun_warning_key), true);
        String time = preferences.getString(getString(R.string.sun_time_key), "16:00");
        String[] params = time.split(":");
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(params[0]));
        calendar.set(Calendar.MINUTE, Integer.valueOf(params[1]));

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        if (sunWarning) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sunIntent);
        } else {
            alarmManager.cancel(sunIntent);
        }

    }


    public void rememberFood(SharedPreferences preferences) {
        Calendar breakfastCalendar = Calendar.getInstance();
        breakfastCalendar.set(Calendar.HOUR_OF_DAY, 9);

        Calendar lunchCalendar = Calendar.getInstance();
        lunchCalendar.set(Calendar.HOUR_OF_DAY, 13);

        Calendar dinnerCalendar = Calendar.getInstance();
        dinnerCalendar.set(Calendar.HOUR_OF_DAY, 21);

        Intent bfIntent = new Intent(BaseActivity.this, NotifyService.class);
        bfIntent.setAction(getString(R.string.eat_breakfast_notification));
        Intent lIntent = new Intent(BaseActivity.this, NotifyService.class);
        lIntent.setAction(getString(R.string.eat_lunch_notification));
        Intent dIntentt = new Intent(BaseActivity.this, NotifyService.class);
        dIntentt.setAction(getString(R.string.eat_dinner_notification));

        PendingIntent breakFastIntent = PendingIntent.getBroadcast(BaseActivity.this, BREAKFAST_NOTIFICATION, bfIntent, 0);
        PendingIntent lunchIntent = PendingIntent.getBroadcast(BaseActivity.this, LUNCH_NOTIFICATION, lIntent, 0);
        PendingIntent dinnerIntent = PendingIntent.getBroadcast(BaseActivity.this, DINNER_NOTIFICATION, dIntentt, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Boolean foodWarning = preferences.getBoolean(getString(R.string.eat_warning_key), true);
        if (breakfastCalendar.before(Calendar.getInstance())) {
            breakfastCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        if (lunchCalendar.before(Calendar.getInstance())) {
            lunchCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        if (dinnerCalendar.before(Calendar.getInstance())) {
            dinnerCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        if (foodWarning) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, breakfastCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, breakFastIntent);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, lunchCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, lunchIntent);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, dinnerCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, dinnerIntent);
        } else {
            alarmManager.cancel(breakFastIntent);
            alarmManager.cancel(lunchIntent);
            alarmManager.cancel(dinnerIntent);
        }

    }


    public void rememberSports(SharedPreferences sharedPreferences) {
        Calendar calendar = Calendar.getInstance();
        Intent myIntent = new Intent(BaseActivity.this, NotifyService.class);
        myIntent.setAction(getString(R.string.sports_notification));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(BaseActivity.this, SPORTS_NOTIFICATION, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Boolean sportsWarning = sharedPreferences.getBoolean(getString(R.string.sports_warning_key), true);
        String time = sharedPreferences.getString(getString(R.string.sports_time_key), "16:00");
        String[] params = time.split(":");
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(params[0]));
        calendar.set(Calendar.MINUTE, Integer.valueOf(params[1]));
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        if (sportsWarning) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
        }

    }

    public void rememberBreathe(SharedPreferences preferences) {
        Calendar calendar = Calendar.getInstance();

        Intent myIntent = new Intent(BaseActivity.this, NotifyService.class);
        myIntent.setAction(getString(R.string.breathe_notification));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(BaseActivity.this, BREATHE_NOTIFICATION, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Boolean breatheWarning = preferences.getBoolean(getString(R.string.breathe_warning_key), true);
        String time = preferences.getString(getString(R.string.breathe_time_key), "16:00");
        String[] params = time.split(":");
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(params[0]));
        calendar.set(Calendar.MINUTE, Integer.valueOf(params[1]));
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        if (breatheWarning) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
        }

    }

    private void setupToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
    }


    /**
     * Remember water.
     *
     * @param preferences the preferences
     */
    public void rememberWater(SharedPreferences preferences) {
        Calendar calendar = Calendar.getInstance();

        Intent myIntent = new Intent(BaseActivity.this, NotifyService.class);
        myIntent.setAction(getString(R.string.water_notification));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(BaseActivity.this, WATER_NOTIFICATION, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Boolean waterWarning = preferences.getBoolean(getString(R.string.water_warning_key), true);
        String interval = preferences.getString(getString(R.string.water_warning_interval_key), getString(R.string.water_interval_default));
        calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(interval));
        if (waterWarning) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), Long.valueOf(interval) * AlarmManager.INTERVAL_HOUR, pendingIntent);
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

    /**
     * Select drawer item.
     *
     * @param menuItem the menu item
     */
    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.water_item:
                fragmentClass = WaterFragment.class;
                break;
            case R.id.settings_item:
                fragmentClass = SettingsFragment.class;
                break;
            default:
                fragmentClass = RecipesListFragment.class;
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
    public void onRecipeSelected() {
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

    /**
     * Show dialog.
     */
    void showDialog() {
        DialogFragment newFragment = SettingsDialogFragment.newInstance(R.string.alert_dialog_title, R.string.alert_dialog_message);
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    public void rememberMeditation(SharedPreferences sharedPreferences) {

        Calendar calendar = Calendar.getInstance();
        Intent myIntent = new Intent(BaseActivity.this, NotifyService.class);
        myIntent.setAction(getString(R.string.meditation_notification));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(BaseActivity.this, MEDITATION_NOTIFICATION, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Boolean meditationWarning = sharedPreferences.getBoolean(getString(R.string.meditation_warning_key), true);
        String time = sharedPreferences.getString(getString(R.string.meditation_time_key), "16:00");
        String[] params = time.split(":");
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(params[0]));
        calendar.set(Calendar.MINUTE, Integer.valueOf(params[1]));
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        if (meditationWarning) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
        }

    }
}
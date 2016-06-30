package com.vsc.vidasemcancer.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.vsc.vidasemcancer.Fragments.HomeFragment;
import com.vsc.vidasemcancer.Fragments.RecipeDetailsFragment;
import com.vsc.vidasemcancer.Fragments.RecipesListFragment;
import com.vsc.vidasemcancer.Fragments.SettingsDialogFragment;
import com.vsc.vidasemcancer.Fragments.SettingsFragment;
import com.vsc.vidasemcancer.Fragments.WaterFragment;
import com.vsc.vidasemcancer.Interface.OnPostClickListener;
import com.vsc.vidasemcancer.Interface.OnRecipeSelected;
import com.vsc.vidasemcancer.Managers.NotificationMng;
import com.vsc.vidasemcancer.R;


/**
 * The type Base activity.
 */
public class BaseActivity extends AppCompatActivity implements OnRecipeSelected, OnPostClickListener {


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
        NotificationMng.initiateAll(BaseActivity.this);
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
            case R.id.home_item:
                fragmentClass = HomeFragment.class;
                break;
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


    @Override
    public void onPostClick(View view, int position) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction().addToBackStack(null);

        RecipeDetailsFragment pm_fragment = new RecipeDetailsFragment();
        fragmentTransaction.replace(R.id.flContent, pm_fragment);

        fragmentTransaction.commit();
    }
}
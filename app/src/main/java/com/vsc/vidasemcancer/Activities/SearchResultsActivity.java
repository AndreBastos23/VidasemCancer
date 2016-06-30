package com.vsc.vidasemcancer.Activities;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.vsc.vidasemcancer.Fragments.HomeFragment;
import com.vsc.vidasemcancer.R;
import com.vsc.vidasemcancer.RestOperation;

/**
 * The type Search results activity.
 */
public class SearchResultsActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private SearchView search;
    private RestOperation restOperation = new RestOperation();
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setupToolbar();
        setupSearchBar();
        restOperation = new RestOperation();

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);

        }

        FragmentManager fragmentManager = getFragmentManager();


        try {
            fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.flContent, (Fragment) HomeFragment.class.newInstance()).commit();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void setupToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupSearchBar() {
        search = (SearchView) findViewById(R.id.posts_search_view);
        search.setQueryHint("Procure por posts");

        //*** setOnQueryTextFocusChangeListener ***
        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub


            }
        });

        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(getApplicationContext(), SearchResultsActivity.class);
                intent.putExtra(SearchManager.QUERY, query);
                intent.setAction(Intent.ACTION_SEARCH);
                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub


                return false;
            }
        });
    }

    /**
     * Gets query.
     *
     * @return the query
     */
    public String getQuery() {
        return query;
    }


}

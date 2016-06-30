package com.vsc.vidasemcancer.Activities;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.widget.ImageView;
import android.widget.TextView;

import com.vsc.vidasemcancer.R;
import com.vsc.vidasemcancer.Utils.VolleyRequests;

/**
 * The type View post details activity.
 */
public class ViewPostDetailsActivity extends AppCompatActivity {

    private TextView postDetailsTV;
    private ImageView postDetailsIV;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        postDetailsTV = (TextView) findViewById(R.id.post_details_textview);
        postDetailsIV = (ImageView) findViewById(R.id.post_details_imageview);
        setupToolbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String rendered = getIntent().getStringExtra("com.vidasemcancer.post");

        VolleyRequests volleyRequests = new VolleyRequests();


        Spanned spanned = android.text.Html.fromHtml(rendered, null, null);
        postDetailsIV.setImageBitmap(volleyRequests.getImageFromSpanned(spanned));

        postDetailsTV.setText(spanned.toString().substring(1));
    }

    private void setupToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ola Ola 123");

    }
}

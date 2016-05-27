package com.vsc.vidasemcancer.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.vsc.vidasemcancer.Models.Water;
import com.vsc.vidasemcancer.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class WaterFragment extends Fragment {


    private Water waterObject;

    private RealmConfiguration realmConfig;
    private Realm realm;

    private ImageButton upArrow;
    private ImageButton downArrow;

    public WaterFragment() {

    }

    public static WaterFragment newInstance() {
        return new WaterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realmConfig = new RealmConfiguration.Builder(getActivity()).build();
        // Open the Realm for the UI thread.
        realm = Realm.getInstance(realmConfig);

        String today = getTodayInString();


        RealmResults<Water> results = realm.where(Water.class).equalTo("date", today).findAll();
        if (results.isEmpty()) {
            realm.beginTransaction();
            waterObject = realm.createObject(Water.class);
            waterObject.setCurrentLevel(0);
            waterObject.setObjective(2000);

            waterObject.setDate(today);
            realm.commitTransaction();
        } else {
            waterObject = results.first();
        }

    }

    private String getTodayInString() {
        Calendar cal = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        return formatter.format(cal.getTime());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_water, container, false);


        ImageView imageView2 = (ImageView) rootView.findViewById(R.id.water_image);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.water_up_arrow);
        ImageView imageView1 = (ImageView) rootView.findViewById(R.id.water_down_arrow);
        TextView textView = (TextView) rootView.findViewById(R.id.water_text_view);

        imageView.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
        imageView1.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        imageView2.setImageResource(setImage());
        textView.setText(String.valueOf(waterObject.getCurrentLevel()));

        addButtonListener(rootView);
        return rootView;
    }

    public void addButtonListener(View view) {
        final ImageView imageId = (ImageView) view.findViewById(R.id.water_image);
        final TextView textView = (TextView) view.findViewById(R.id.water_text_view);
        upArrow = (ImageButton) view.findViewById(R.id.water_up_arrow);
        upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waterTransaction(R.id.water_up_arrow);
                imageId.setImageResource(setImage());
                textView.setText(String.valueOf(waterObject.getCurrentLevel()));
            }
        });

        downArrow = (ImageButton) view.findViewById(R.id.water_down_arrow);
        downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waterTransaction(R.id.water_down_arrow);
                imageId.setImageResource(setImage());
                textView.setText(String.valueOf(waterObject.getCurrentLevel()));
            }
        });
    }

    public void waterTransaction(int id) {

        realm.beginTransaction();
        if (id == R.id.water_down_arrow) {
            int waterLevel = waterObject.getCurrentLevel() - 200;
            waterObject.setCurrentLevel(waterLevel);
        } else if (id == R.id.water_up_arrow) {
            int waterLevel = waterObject.getCurrentLevel() + 200;
            waterObject.setCurrentLevel(waterLevel);
        }
        realm.commitTransaction();
    }

    public int setImage() {
        int objective = 0;
        if (waterObject.getCurrentLevel() != 0) {
            objective = (waterObject.getCurrentLevel() * 100 / waterObject.getObjective());
        }

        if (objective >= 100) {
            return R.drawable.water_100;
        } else if (objective >= 80) {
            return R.drawable.water_80;
        } else if (objective >= 70) {
            return R.drawable.water_70;
        } else if (objective >= 60) {
            return R.drawable.water_60;
        } else if (objective >= 50) {
            return R.drawable.water_50;
        } else if (objective >= 40) {
            return R.drawable.water_40;
        } else if (objective >= 30) {
            return R.drawable.water_30;
        } else if (objective >= 20) {
            return R.drawable.water_20;
        } else if (objective >= 10) {
            return R.drawable.water_10;
        } else {
            return R.drawable.water_0;
        }

    }
}

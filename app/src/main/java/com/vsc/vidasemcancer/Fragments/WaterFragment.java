package com.vsc.vidasemcancer.Fragments;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.vsc.vidasemcancer.Formatters.LiterAxisFormatter;
import com.vsc.vidasemcancer.Models.Water;
import com.vsc.vidasemcancer.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

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
    private LineChart lineChart;

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

        String today = getTodayInString(0);

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

        lineChart = (LineChart) getActivity().findViewById(R.id.chart);

    }

    private String getTodayInString(int nDays) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, nDays);
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
        lineChart = (LineChart) rootView.findViewById(R.id.chart);
        imageView.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
        imageView1.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        imageView2.setImageResource(setImage());
        textView.setText(getTextViewText());

        lastWeekData();
        addButtonListener(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume(); // setup realm
        String today = getTodayInString(0);

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
        ImageView imageView2 = (ImageView) getActivity().findViewById(R.id.water_image);
        imageView2.setImageResource(setImage());
        TextView textView = (TextView) getActivity().findViewById(R.id.water_text_view);
        textView.setText(getTextViewText());


        // add data to the chart
        lastWeekData();
    }

    @NonNull
    private String getTextViewText() {
        return getString(R.string.water_value_textview) + " " + String.valueOf(((double) waterObject.getCurrentLevel()) / 1000) + getString(R.string.water_unit);
    }

    private void lastWeekData() {


        RealmResults<Water> results = realm.where(Water.class).contains("date", getTodayInString(-7).substring(2, 10)).findAll();



        ArrayList<Entry> vals = new ArrayList<Entry>();
        ArrayList<String> vals2 = new ArrayList<String>();
        Iterator<Water> it = results.iterator();
        int cont = 0;

        while (it.hasNext()) {
            Water water = it.next();
            vals.add(new Entry(((float) water.getCurrentLevel()) / 1000, cont));
            vals2.add(water.getDate().substring(0, 5));

            cont++;
        }
        LineDataSet setComp1 = new LineDataSet(vals, getString(R.string.graph_label));
        setComp1.setDrawFilled(true);
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineData data = new LineData(vals2, setComp1);
        data.setValueFormatter(new LiterAxisFormatter());

        Legend legend = lineChart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        YAxis yAxis = lineChart.getAxisRight();
        yAxis.setEnabled(false);
        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setDrawAxisLine(false);
        yAxisLeft.setDrawLabels(false);
        yAxisLeft.setAxisMinValue(0);

        yAxisLeft.removeAllLimitLines();

        LimitLine limitLine = new LimitLine((float) waterObject.getObjective() / 1000);
        limitLine.setLineColor(Color.GREEN);
        limitLine.setLineWidth(2f);
        yAxisLeft.addLimitLine(limitLine);
        yAxisLeft.setDrawLimitLinesBehindData(true);

        lineChart.setDescription("");
        lineChart.setData(data);
        lineChart.invalidate(); // refresh


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
                textView.setText(getTextViewText());
                lastWeekData();
            }
        });

        downArrow = (ImageButton) view.findViewById(R.id.water_down_arrow);
        downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waterTransaction(R.id.water_down_arrow);
                imageId.setImageResource(setImage());
                textView.setText(getTextViewText());
                lastWeekData();
            }
        });


    }

    public void waterTransaction(int id) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Integer amount = Integer.parseInt(preferences.getString(getString(R.string.water_warning_qtty_key), "300"));
        realm.beginTransaction();
        if (id == R.id.water_down_arrow) {
            int waterLevel = waterObject.getCurrentLevel() - amount;
            if (waterLevel > 0) {
                waterObject.setCurrentLevel(waterLevel);
            } else {
                waterObject.setCurrentLevel(0);
            }
        } else if (id == R.id.water_up_arrow) {
            int waterLevel = waterObject.getCurrentLevel() + amount;
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
            return R.drawable.water_over100;
        } else if (objective >= 90) {
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

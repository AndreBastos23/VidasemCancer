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
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.vsc.vidasemcancer.Formatters.LiterAxisFormatter;
import com.vsc.vidasemcancer.Managers.WaterManager;
import com.vsc.vidasemcancer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WaterFragment extends Fragment {

    private ImageButton upArrow;
    private ImageButton downArrow;
    private LineChart lineChart;
    private WaterManager waterManager;

    public WaterFragment() {

    }

    public static WaterFragment newInstance() {
        return new WaterFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        waterManager = new WaterManager();
    }

    @Override
    public void onStop() {
        super.onStop();
        waterManager.close();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lineChart = (LineChart) getActivity().findViewById(R.id.chart);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_water, container, false);


        ImageView imageView = (ImageView) rootView.findViewById(R.id.water_up_arrow);
        ImageView imageView1 = (ImageView) rootView.findViewById(R.id.water_down_arrow);
        lineChart = (LineChart) rootView.findViewById(R.id.chart);

        imageView.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
        imageView1.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);

        addButtonListener(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume(); // setup realm

        ImageView imageView2 = (ImageView) getActivity().findViewById(R.id.water_image);
        imageView2.setImageResource(waterManager.getDrinkPct());
        TextView textView = (TextView) getActivity().findViewById(R.id.water_text_view);
        textView.setText(getTextViewText());

        // add data to the chart
        lastWeekData();
    }


    @NonNull
    private String getTextViewText() {
        return getString(R.string.water_value_textview) + " " + waterManager.getHumanCurrentLevel() + getString(R.string.water_unit);
    }

    private void lastWeekData() {

        //Set data in graph
        LineDataSet setComp1 = new LineDataSet(waterManager.getValues(), getString(R.string.graph_label));
        setComp1.setDrawFilled(true);
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineData data = new LineData(waterManager.getKeys(), setComp1);
        data.setValueFormatter(new LiterAxisFormatter());

        //Customize Graph
        //legend
        Legend legend = lineChart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
        //xAxis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        //yAxis
        YAxis yAxis = lineChart.getAxisRight();
        yAxis.setEnabled(false);
        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setDrawAxisLine(false);
        yAxisLeft.setDrawLabels(false);
        yAxisLeft.setAxisMaxValue(waterManager.getHumanObjective() + 1);
        yAxisLeft.setAxisMinValue(0);
        yAxisLeft.removeAllLimitLines();
        yAxisLeft.setDrawLimitLinesBehindData(true);

        //LimitLine
        LimitLine limitLine = new LimitLine(waterManager.getHumanObjective());
        limitLine.setLineColor(Color.GREEN);
        limitLine.setLineWidth(2f);
        yAxisLeft.addLimitLine(limitLine);

        //lineChart properties
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setTouchEnabled(false);
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
                imageId.setImageResource(waterManager.getDrinkPct());
                textView.setText(getTextViewText());
                lastWeekData();
            }
        });

        downArrow = (ImageButton) view.findViewById(R.id.water_down_arrow);
        downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waterTransaction(R.id.water_down_arrow);
                imageId.setImageResource(waterManager.getDrinkPct());
                textView.setText(getTextViewText());
                lastWeekData();
            }
        });


    }

    public void waterTransaction(int id) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Integer amount = Integer.parseInt(preferences.getString(getString(R.string.water_warning_qtty_key), "300"));

        if (id == R.id.water_down_arrow) {
            amount = 0 - amount;
        }
        waterManager.changeAmount(amount);

    }

}

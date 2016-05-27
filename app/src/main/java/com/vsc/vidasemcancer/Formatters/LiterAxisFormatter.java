package com.vsc.vidasemcancer.Formatters;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Created by Eduardo on 27/05/2016.
 */
public class LiterAxisFormatter implements ValueFormatter {


    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return value + " L";
    }
}

package com.vsc.vidasemcancer.Fragments;

/**
 * Created by andre on 21-05-2016.
 */

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.vsc.vidasemcancer.Data.Recipes;
import com.vsc.vidasemcancer.R;


public class LM_Fragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pm, container,
                false);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.mylist, R.id.Itemname, Recipes.Nome);
        setListAdapter(adapter);
        return rootView;
    }
}
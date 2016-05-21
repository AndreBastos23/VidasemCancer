package com.vsc.vidasemcancer.Fragments;


import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.vsc.vidasemcancer.Data.Recipes;
import com.vsc.vidasemcancer.R;


public class LM_Fragment extends ListFragment {


    public static LM_Fragment newInstance(){
        return new LM_Fragment();
    }

    public LM_Fragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pm, container,
                false);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.mylist, R.id.Itemname, Recipes.Nome);
        setListAdapter(adapter);
        return rootView;
    }
}
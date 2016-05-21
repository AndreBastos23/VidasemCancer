package com.vsc.vidasemcancer.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vsc.vidasemcancer.R;

public class RecipeDetailsFragment extends Fragment {

    public static RecipeDetailsFragment newInstance(){
        return new RecipeDetailsFragment();
    }

    public RecipeDetailsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container,
                false);
        return rootView;
    }
}

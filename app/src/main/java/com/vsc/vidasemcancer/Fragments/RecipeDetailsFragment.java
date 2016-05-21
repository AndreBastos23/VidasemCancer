package com.vsc.vidasemcancer.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.vsc.vidasemcancer.Data.Recipes;
import com.vsc.vidasemcancer.R;

import java.util.List;

public class RecipeDetailsFragment extends Fragment {

    public static RecipeDetailsFragment newInstance() {
        return new RecipeDetailsFragment();
    }

    public RecipeDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container,
                false);

        TextView textView = (TextView) rootView.findViewById(R.id.recipe_header);
        textView.setText(Recipes.jajao);


        ListView listView = (ListView) rootView.findViewById(R.id.list_items);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                Recipes.Descricao);
        listView.setAdapter(adapter);

        return rootView;
    }

}

package com.vsc.vidasemcancer.Fragments;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vsc.vidasemcancer.Interface.OnRecipeSelected;
import com.vsc.vidasemcancer.R;
import com.vsc.vidasemcancer.entities.Recipes;


public class RecipesListFragment extends ListFragment {

    private OnRecipeSelected mListener;

    public RecipesListFragment() {

    }

    public static RecipesListFragment newInstance() {
        return new RecipesListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (OnRecipeSelected) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_recipes_list, container,
                false);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.mylist, R.id.Itemname, Recipes.Nome);
        setListAdapter(adapter);
        return rootView;


    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mListener.onRecipeSelected();
    }
}

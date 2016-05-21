package com.vsc.vidasemcancer;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.vsc.vidasemcancer.Fragments.PM_Fragment;
import com.vsc.vidasemcancer.Fragments.RecipeDetailsFragment;
import com.vsc.vidasemcancer.Interface.OnRecipeSelected;

public class TesteActivity extends BaseActivity implements OnRecipeSelected {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        PM_Fragment pm_fragment = new PM_Fragment();
        fragmentTransaction.replace(android.R.id.content, pm_fragment);

        fragmentTransaction.commit();
    }


    @Override
    public void onRageComicSelected() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        RecipeDetailsFragment pm_fragment = new RecipeDetailsFragment();
        fragmentTransaction.replace(android.R.id.content, pm_fragment);

        fragmentTransaction.commit();
    }
}

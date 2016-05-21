package com.vsc.vidasemcancer;

/**
 * Created by andre on 21-05-2016.
 */

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;

import com.vsc.vidasemcancer.Fragments.LM_Fragment;
import com.vsc.vidasemcancer.Fragments.PM_Fragment;
import com.vsc.vidasemcancer.Fragments.RecipeDetailsFragment;
import com.vsc.vidasemcancer.Interface.OnRecipeSelected;

public class TesteActivity extends Activity implements OnRecipeSelected {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration config = getResources().getConfiguration();

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

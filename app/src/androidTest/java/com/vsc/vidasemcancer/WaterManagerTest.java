package com.vsc.vidasemcancer;


import android.support.test.runner.AndroidJUnit4;

import com.vsc.vidasemcancer.Managers.WaterManager;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;

@RunWith(AndroidJUnit4.class)
public class WaterManagerTest extends TestCase {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private double initialLevel;
    private WaterManager waterManager;

    public void setUp() throws java.io.IOException {
        testFolder.create();
        File tempFolder = testFolder.newFolder("realmTestData");
        RealmConfiguration config = new RealmConfiguration.Builder(tempFolder).build();
        Realm.setDefaultConfiguration(config);
        waterManager = new WaterManager();
    }


    @Test
    public void testChangeInAmount() throws java.io.IOException {

        initialLevel = Double.valueOf(waterManager.getHumanCurrentLevel());

        int amount = 300;
        double increaseValue = initialLevel + ((double) amount / 1000);

        waterManager.changeAmount(amount);

        Assert.assertEquals(increaseValue, Double.valueOf(waterManager.getHumanCurrentLevel()));

        Integer currentLevel = (int) (Double.valueOf(waterManager.getHumanCurrentLevel()) * 1000.0);

        waterManager.changeAmount(0 - currentLevel);

        Assert.assertEquals((double) 0, Double.valueOf(waterManager.getHumanCurrentLevel()));

        waterManager.changeAmount(0 - amount);

        Assert.assertEquals((double) 0, Double.valueOf(waterManager.getHumanCurrentLevel()));

    }

    @Test
    public void testCorrectImage() {

        Assert.assertEquals(R.drawable.water_0, waterManager.getDrinkPct());

    }

    public void tearDown() {
        testFolder.delete();
    }

}

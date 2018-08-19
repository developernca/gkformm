package com.developernca.gkformm.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created on 8/13/2018.
 *
 * @author Nyein Chan Aung
 */

public class GKMMActivity extends AppCompatActivity {

    /**
     * Show Ad View.
     *
     * @param adViewId current activity adview id
     */
    protected void loadAdView(int adViewId) {
        AdView adView = findViewById(adViewId);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("56D0CA8C93C80DAFC432C4C69C02B3AA").build();
        if (adRequest.isTestDevice(this)) {
            adView.loadAd(adRequest);
        }
    }

    /**
     * Get shared preference with given name.
     *
     * @param name preference name
     * @return SharedPreferences
     */
    public SharedPreferences getSharedPref(String name) {
        return getSharedPreferences(name, MODE_PRIVATE);
    }

}

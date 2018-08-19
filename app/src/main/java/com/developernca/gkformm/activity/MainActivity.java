package com.developernca.gkformm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.developernca.gkformm.R;
import com.developernca.gkformm.fragment.DetailFragment;
import com.developernca.gkformm.fragment.MainFragment;
import com.developernca.gkformm.utils.CategoryAdapter;
import com.developernca.gkformm.utils.ConstantsAndUtils;
import com.facebook.FacebookSdk;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends GKMMActivity implements MainFragment.OnFragmentInteractionListener, DetailFragment.OnFragmentInteractionListener {

    private FragmentManager fragmentManager;
    private int currentFont;
    private ActionBar actionBar;
    private JSONObject uiJSON;

    private void showMsg(String s, int gravity) {
        ConstantsAndUtils.getCustomAlertToast(this, getLayoutInflater(), s, Toast.LENGTH_LONG, gravity).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // init MobileAds
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        loadAdView(R.id.idAdvMainActivity);
        // initialize ui json string and set name to action bar
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            try {
                uiJSON = new JSONObject(getIntent().getStringExtra(ConstantsAndUtils.EXTRA_UIJSON));
                actionBar.setTitle(uiJSON.getString("activity_name_main"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // init current font
        currentFont = getIntent().getIntExtra(ConstantsAndUtils.EXTRA_CURRENT_FNT, 99);
        // initialize fragment
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.idContainerMainFragment, MainFragment.newInstance(currentFont));
        fragmentTransaction.commit();
        showMsg("MATV : onCreate", Gravity.CENTER);
    }

    @Override
    public void onDetailBtnClick(String detailName) {
        FrameLayout container = findViewById(R.id.idContainerDetailFragment);
        String detailFileName = ConstantsAndUtils.getJSONFileName("details/" + detailName, currentFont);
        if (container == null) {// phone
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(ConstantsAndUtils.EXTRA_UIJSON, uiJSON.toString());
            intent.putExtra(ConstantsAndUtils.EXTRA_DTLFILE_NAME, detailFileName);
            startActivity(intent);
        } else {// tablet
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.idContainerDetailFragment, DetailFragment.newInstance(detailFileName));
            transaction.commit();
        }
    }

    @Override
    public JSONObject getUIJSONObject() {
        return uiJSON;
    }
}

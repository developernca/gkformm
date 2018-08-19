package com.developernca.gkformm.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.developernca.gkformm.R;
import com.developernca.gkformm.fragment.DetailFragment;
import com.developernca.gkformm.utils.ConstantsAndUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends GKMMActivity implements DetailFragment.OnFragmentInteractionListener {

    private JSONObject uiJSON;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // load ad
        loadAdView(R.id.idAdvDetailActivity);
        // get intent extra data
        intent = getIntent();
        String detailJSONFileName = intent.getStringExtra(ConstantsAndUtils.EXTRA_DTLFILE_NAME);
        // initialize detail fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.idContainerDetailFragment, DetailFragment.newInstance(detailJSONFileName));
        fragmentTransaction.commit();
    }

    @Override
    public JSONObject getUIJSONObject() {
        if (uiJSON == null) {
            try {
                uiJSON = new JSONObject(intent.getStringExtra(ConstantsAndUtils.EXTRA_UIJSON));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return uiJSON;
    }
}

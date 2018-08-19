package com.developernca.gkformm.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.developernca.gkformm.R;
import com.developernca.gkformm.dialog.FontChooserDialog;
import com.developernca.gkformm.utils.ConstantsAndUtils;

import org.json.JSONObject;

/**
 * Splash screen activity. Activity flow is as following
 * 1).If app launch for first time, show font chooser dialog. When user select font, put it in shared preference
 * and load json string file for ui.
 * 2)If app launch not first time, check font type from shared preference and load json string file for ui.
 */
public class SplashActivity extends GKMMActivity {

    /**
     * Current font use in shared preferences.
     */
    private int currentFont;
    /**
     * Shared Preferences.
     */
    private SharedPreferences sharedPreferences;
    /**
     * Font chooser dialog for first time app launch.
     */
    private FontChooserDialog fontChooserDialog;
    /**
     * JSON object for (zawgyi or unicode) ui strings.
     */
    private JSONObject uiStringJSON;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPref(ConstantsAndUtils.SHARED_PREF.PREF_NAME.val());
        currentFont = sharedPreferences.getInt(ConstantsAndUtils.SHARED_PREF.USE_FNT.val(), -1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentFont < 0 && fontChooserDialog == null) {
            // no font selected yet and app is assumed as new. Show font chooser dialog
            fontChooserDialog = new FontChooserDialog(this, this::onFontChooserClose);
            fontChooserDialog.show();
        } else if (uiStringJSON == null && currentFont > 0) {
            // The font is already selected and initialize json string file for ui
            setUpUiStringJSON();
        }
    }

    /**
     * Callback method for font chooser dialog.
     *
     * @param selectedFont user selected font from dialog.
     */
    private void onFontChooserClose(ConstantsAndUtils.FONTS selectedFont) {
        if (selectedFont != null) {
            currentFont = selectedFont.val();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(ConstantsAndUtils.SHARED_PREF.USE_FNT.val(), currentFont);
            editor.apply();
            setUpUiStringJSON();
        }
    }

    /**
     * Setting up json object from ui string json file.
     */
    private void setUpUiStringJSON() {
        String assetFileName = ConstantsAndUtils.getJSONFileName(ConstantsAndUtils.UI_STRING_JSON_FILENAME, currentFont);
        uiStringJSON = ConstantsAndUtils.getJSONObject(this, assetFileName);
        new Handler().postDelayed(this::gotoNextActivity, 1500);
    }

    /**
     * As its name imply, it will navigate to next activity.
     */
    private void gotoNextActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(ConstantsAndUtils.EXTRA_UIJSON, uiStringJSON.toString());
        intent.putExtra(ConstantsAndUtils.EXTRA_CURRENT_FNT, currentFont);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

package com.developernca.gkformm.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.developernca.gkformm.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * ConstantsAndUtils values.
 * <p>
 * Created on 8/13/2018.
 *
 * @author Nyein Chan Aung
 */

public class ConstantsAndUtils {

    // String constants
    /**
     * UI String file name.
     */
    public static final String UI_STRING_JSON_FILENAME = "strings";
    /**
     * Category string file name.
     */
    public static final String CATEGORY_JSON_FILENAME = "category";
    /**
     * Extra data key name for json string.
     */
    public static final String EXTRA_UIJSON = "uijsonString";
    /**
     * Extra data key name for current font.
     */
    public static final String EXTRA_CURRENT_FNT = "extra_cur_fnt";
    /**
     * Extra data for detail file name that is used in loading detail json string data.
     */
    public static final String EXTRA_DTLFILE_NAME = "extra_detail_name";

    /**
     * Return json string resource file name base on current font.
     *
     * @param filename    json string resource file name
     * @param currentFont current font type (zawgyi or unicode)
     * @return Json string resource file name
     */
    public static String getJSONFileName(String filename, int currentFont) {
        return (currentFont == FONTS.USE_UC.val()) ? filename + "-uc.json" : filename + "-zg.json";
    }

    /**
     * Enum for facebook share options.
     */
    public enum FBSHARE {
        /**
         * Share both Q&A
         */
        OPT1(1),
        /**
         * Share only Q
         */
        OPT2(2),
        /**
         * Share only application
         */
        OPT3(3);

        private int opt;

        FBSHARE(int opt) {
            this.opt = opt;
        }

        public int val() {
            return opt;
        }
    }

    /**
     * Enum for feed back send options.
     */
    public enum FEEDBACK {
        /**
         * Feedback for wrong Q&A
         */
        OPT1(1),
        /**
         * Feedback for suggestion.
         */
        OPT2(2),
        /**
         * Feedback for others.
         */
        OPT3(3);

        private int opt;

        FEEDBACK(int opt) {
            this.opt = opt;
        }

        public int val() {
            return opt;
        }
    }

    /**
     * Enum for fonts.
     */
    public enum FONTS {
        /**
         * Currently using ZAWGYI.
         */
        USE_ZG(99),
        /**
         * Currently using UNICODE.
         */
        USE_UC(100);

        private int val;

        FONTS(int val) {
            this.val = val;
        }

        /**
         * Get the value of current enum in number.
         *
         * @return int value.
         */
        public int val() {
            return this.val;
        }

    }

    /**
     * Enum for preference names.
     */
    public enum SHARED_PREF {
        /**
         * Preference name for the app.
         */
        PREF_NAME("gkmm_pref"),
        /**
         * Preference name for current using font.
         */
        USE_FNT("use_fnt");

        private String val;

        SHARED_PREF(String val) {
            this.val = val;
        }

        public String val() {
            return this.val;
        }
    }


    /**
     * Get {@link JSONObject} object by using given InputStream object.
     *
     * @param context  Context to get assets
     * @param filename JSON file name
     * @return {@link JSONObject} or null on error
     */
    public static JSONObject getJSONObject(Context context, String filename) {
        JSONObject jsonObject = null;
        try {
            InputStream is = context.getAssets().open(filename);
            byte[] byteArr = new byte[is.available()];
            // noinspection ResultOfMethodCallIgnored (the result of read method is ignored)
            is.read(byteArr);
            is.close();
            jsonObject = new JSONObject(new String(byteArr));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Make a custom one line toast at the top of screen.
     *
     * @param context        Context to be used to make Toast.
     * @param layoutInflater To inflate view from layout.
     * @param txt            Text to show in toast.
     * @param duration       Toast time.
     * @param gravity        {@link android.view.Gravity}
     * @return A toast.
     */
    public static Toast getCustomAlertToast(Context context, LayoutInflater layoutInflater, String txt, int duration, int gravity) {
        // inflate layout
        View v = layoutInflater.inflate(R.layout.layout_custom_alert_toast, null);
        // set toast text to TextView
        TextView tv = v.findViewById(R.id.idTvAlertToastText);
        tv.setText(txt);
        // Create and return toast
        Toast t = new Toast(context);
        t.setView(v);
        t.setDuration(duration);
        t.setGravity(gravity, 0, 0);
        return t;
    }

    /**
     * Check internet connection.
     *
     * @param context Context
     * @return true if internet connected, false otherwise
     */
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}

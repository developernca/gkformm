package com.developernca.gkformm.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.developernca.gkformm.R;
import com.developernca.gkformm.bean.Details;
import com.developernca.gkformm.dialog.FbShareOptChooserDialog;
import com.developernca.gkformm.dialog.FeedbackOptChooserDialog;
import com.developernca.gkformm.utils.ConstantsAndUtils;
import com.developernca.gkformm.utils.DetailAdapter;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} that hold all the question and answers (aka GK)
 * of selected category from {@link MainFragment} category list.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment implements DetailAdapter.DetailAdapterInteractionListener {

    private CallbackManager callbackManager;
    private JSONObject detailJSONObj;
    private JSONObject uiStringJSONObj;
    private Context mContext;
    private RecyclerView rclDetailList;
    private OnFragmentInteractionListener mListener;
    private String userCurrentWorkingQuestion;
    private String userCurrentWorkingAnswer;

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        JSONObject getUIJSONObject();
    }

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param dtlFileName Detail json file name
     * @return A new instance of fragment DetailFragment.
     */
    public static DetailFragment newInstance(String dtlFileName) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ConstantsAndUtils.EXTRA_DTLFILE_NAME, dtlFileName);
        fragment.setArguments(args);
        return fragment;
    }


    private void initRecyclerAdapter() {
        ArrayList<Details> dtlList = new ArrayList<>();
        try {
            JSONArray jsonArray = detailJSONObj.getJSONArray("data");
            int len = jsonArray.length();
            for (int i = 0; i < len; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Details dtl = new Details(jsonObject.getString("q"), jsonObject.getString("a"));
                dtlList.add(dtl);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        rclDetailList.setLayoutManager(new LinearLayoutManager(mContext));
        DetailAdapter dtlAdapter = new DetailAdapter(dtlList, this);
        rclDetailList.setAdapter(dtlAdapter);
    }

    private void onFBShareOptChoose(int opt) {
        if (!ConstantsAndUtils.isConnectedToInternet(mContext)) {
            ConstantsAndUtils.getCustomAlertToast(mContext, getLayoutInflater(), "Please connect to the internet..", Toast.LENGTH_SHORT, Gravity.CENTER).show();
        } else {
            ShareDialog shareDialog = new ShareDialog(this);
            shareDialog.registerCallback(callbackManager, new MFbShareCallback());
            ShareLinkContent.Builder builder = new ShareLinkContent.Builder();
            if (opt == ConstantsAndUtils.FBSHARE.OPT1.val()) {
                builder.setQuote(userCurrentWorkingQuestion + "\n" + userCurrentWorkingAnswer);
            } else if (opt == ConstantsAndUtils.FBSHARE.OPT2.val()) {
                builder.setQuote(userCurrentWorkingQuestion);
            }
            builder.setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.ncadeveloper.bead.view"));
            shareDialog.show(builder.build());
        }
    }

    private void onFeedbackOptChoose(int opt) {
        Intent intent = null;
        try {
            // to field in mail app
            String mailto = "mailto:developernca@gmail.com";
            // subject field in mail app
            String subject = uiStringJSONObj.getString("feedback_mail_subject_template");
            // To text
            String toTxt = uiStringJSONObj.getString("feedback_mail_to_template");
            // Opening text
            String body = uiStringJSONObj.getString("feedback_mail_body_template_opt" + String.valueOf(opt));
            // body, it is depend on option
            if (opt == ConstantsAndUtils.FEEDBACK.OPT1.val()) {
                body += userCurrentWorkingQuestion + "\n" + userCurrentWorkingAnswer;
            }
            String uirData = mailto + "?subject=" + subject + "&body=" + toTxt + body;
            intent = new Intent();
            intent.setAction(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse(uirData));
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            uiStringJSONObj = mListener.getUIJSONObject();
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            detailJSONObj = ConstantsAndUtils.getJSONObject(mContext, getArguments().getString(ConstantsAndUtils.EXTRA_DTLFILE_NAME));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        rclDetailList = v.findViewById(R.id.idRclViewDetails);
        initRecyclerAdapter();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        // facebook call back manager
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onFbShareBtnClick(String q, String a) {
        try {
            FbShareOptChooserDialog dlg = new FbShareOptChooserDialog(
                    mContext
                    , this::onFBShareOptChoose
                    , uiStringJSONObj.getString("fb_shareopt_dlg_title")
                    , uiStringJSONObj.getString("fb_shareopt_dlg_opt1")
                    , uiStringJSONObj.getString("fb_shareopt_dlg_opt2")
                    , uiStringJSONObj.getString("fb_shareopt_dlg_opt3"));
            dlg.setCanceledOnTouchOutside(false);
            dlg.show();
            userCurrentWorkingQuestion = q;
            userCurrentWorkingAnswer = a;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFeedbackBtnClick(String q, String a) {
        try {
            FeedbackOptChooserDialog dlg = new FeedbackOptChooserDialog(
                    mContext
                    , this::onFeedbackOptChoose
                    , uiStringJSONObj.getString("feedback_dlog_title")
                    , uiStringJSONObj.getString("feedback_dlg_opt1")
                    , uiStringJSONObj.getString("feedback_dlg_opt2")
                    , uiStringJSONObj.getString("feedback_dlg_opt3"));
            dlg.setCanceledOnTouchOutside(false);
            dlg.show();
            userCurrentWorkingQuestion = q;
            userCurrentWorkingAnswer = a;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class MFbShareCallback implements FacebookCallback<Sharer.Result> {

        @Override
        public void onSuccess(Sharer.Result result) {

        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {
            ConstantsAndUtils.getCustomAlertToast(mContext, getLayoutInflater(), getMessage(), Toast.LENGTH_SHORT, Gravity.CENTER);
        }

        private String getMessage() {
            String res = null;
            try {
                res = uiStringJSONObj.getString("fb_shareresult_error_alert_txt");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return res;
        }
    }

}

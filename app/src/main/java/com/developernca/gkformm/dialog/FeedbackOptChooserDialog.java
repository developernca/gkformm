package com.developernca.gkformm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;

import com.developernca.gkformm.R;
import com.developernca.gkformm.utils.ConstantsAndUtils;

/**
 * Created on 8/19/2018.
 *
 * @author Nyein Chan Aung
 */

public class FeedbackOptChooserDialog extends Dialog {

    private String title;
    private String opt1Txt;
    private String opt2Txt;
    private String opt3Txt;
    private FeedbackOptChoose feedbackOptChoose;

    public interface FeedbackOptChoose {
        void onChoose(int option);
    }

    public FeedbackOptChooserDialog(@NonNull Context context, FeedbackOptChoose feedbackOptChoose, String title, String opt1Txt, String opt2Txt, String opt3Txt) {
        super(context);
        this.feedbackOptChoose = feedbackOptChoose;
        this.title = title;
        this.opt1Txt = opt1Txt;
        this.opt2Txt = opt2Txt;
        this.opt3Txt = opt3Txt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_feedback_optchooser);
        TextView tvTitle = findViewById(R.id.idTvFeedbackOptDlgTitle);
        TextView tvOpt1 = findViewById(R.id.idTvFeedbackOptDlgOpt1);
        TextView tvOpt2 = findViewById(R.id.idTvFeedbackOptDlgOpt2);
        TextView tvOpt3 = findViewById(R.id.idTvFeedbackOptDlgOpt3);
        Button closeBtn = findViewById(R.id.idBtnSimpleDlgCloseX);
        tvTitle.setText(title);
        tvOpt1.setText(opt1Txt);
        tvOpt2.setText(opt2Txt);
        tvOpt3.setText(opt3Txt);
        tvOpt1.setOnClickListener(v -> {
            feedbackOptChoose.onChoose(ConstantsAndUtils.FEEDBACK.OPT1.val());
            dismiss();
        });
        tvOpt2.setOnClickListener(v -> {
            feedbackOptChoose.onChoose(ConstantsAndUtils.FEEDBACK.OPT2.val());
            dismiss();
        });
        tvOpt3.setOnClickListener(v -> {
            feedbackOptChoose.onChoose(ConstantsAndUtils.FEEDBACK.OPT3.val());
            dismiss();
        });
        closeBtn.setOnClickListener(v -> {
            dismiss();
        });
    }
}

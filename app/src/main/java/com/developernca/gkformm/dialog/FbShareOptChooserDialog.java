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
 * Created on 8/17/2018.
 *
 * @author Nyein Chan Aung
 */

public class FbShareOptChooserDialog extends Dialog {

    private String title;
    private String opt1Txt;
    private String opt2Txt;
    private String opt3Txt;
    private FbShareOptChoose fbShareOptChoose;

    public interface FbShareOptChoose {
        void onChoose(int option);
    }

    /**
     * @param context          Context
     * @param fbShareOptChoose {@link FbShareOptChoose} for callback
     * @param title            Dialog title
     * @param opt1Txt          Option 1 text (Q&A)
     * @param opt2Txt          Option 2 text (A)
     * @param opt3Txt          Option 3 text (App only)
     */
    public FbShareOptChooserDialog(@NonNull Context context, FbShareOptChoose fbShareOptChoose, String title, String opt1Txt, String opt2Txt, String opt3Txt) {
        super(context);
        this.fbShareOptChoose = fbShareOptChoose;
        this.title = title;
        this.opt1Txt = opt1Txt;
        this.opt2Txt = opt2Txt;
        this.opt3Txt = opt3Txt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_fbshare_optchooser);
        TextView tvTitle = findViewById(R.id.idTvFbShareOptDlgTitle);
        TextView tvOpt1 = findViewById(R.id.idTvFbShareOptDlgOpt1);
        TextView tvOpt2 = findViewById(R.id.idTvFbShareOptDlgOpt2);
        TextView tvOpt3 = findViewById(R.id.idTvFbShareOptDlgOpt3);
        Button closeBtn = findViewById(R.id.idBtnSimpleDlgCloseX);
        tvTitle.setText(title);
        tvOpt1.setText(opt1Txt);
        tvOpt2.setText(opt2Txt);
        tvOpt3.setText(opt3Txt);
        tvOpt1.setOnClickListener(v -> {
            fbShareOptChoose.onChoose(ConstantsAndUtils.FBSHARE.OPT1.val());
            dismiss();
        });
        tvOpt2.setOnClickListener(v -> {
            fbShareOptChoose.onChoose(ConstantsAndUtils.FBSHARE.OPT2.val());
            dismiss();
        });
        tvOpt3.setOnClickListener(v -> {
            fbShareOptChoose.onChoose(ConstantsAndUtils.FBSHARE.OPT3.val());
            dismiss();
        });
        closeBtn.setOnClickListener(v -> {
            dismiss();
        });
    }
}

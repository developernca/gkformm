package com.developernca.gkformm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.developernca.gkformm.R;
import com.developernca.gkformm.utils.ConstantsAndUtils;

/**
 * Font select dialog.
 * <p>
 * Created on 8/13/2018.
 *
 * @author Nyein Chan Aung
 */

public class FontChooserDialog extends Dialog {

    private ConstantsAndUtils.FONTS selectedFont;
    private OnDialogCloseListener dialogCloseListener;

    private RadioGroup radioGroup;

    public interface OnDialogCloseListener {
        void onClose(ConstantsAndUtils.FONTS selectedFontOnClose);
    }

    public FontChooserDialog(@NonNull Context context, OnDialogCloseListener dialogCloseListener) {
        super(context);
        this.dialogCloseListener = dialogCloseListener;
        this.selectedFont = null;
    }

    private void radioClick() {
        int checkedId = radioGroup.getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.idRdoBtnUc:
                // unicode
                selectedFont = ConstantsAndUtils.FONTS.USE_UC;
                break;
            case R.id.idRdoBtnZg:
                // zawgyi
                selectedFont = ConstantsAndUtils.FONTS.USE_ZG;
                break;
            default:
                selectedFont = null;
                break;
        }
        dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_font_chooser);
        radioGroup = findViewById(R.id.idRgFontChoose);
        RadioButton rdoBtnZg = findViewById(R.id.idRdoBtnZg);
        RadioButton rdoBtnUc = findViewById(R.id.idRdoBtnUc);
        rdoBtnZg.setOnClickListener(v -> radioClick());
        rdoBtnUc.setOnClickListener(v -> radioClick());
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        dialogCloseListener.onClose(selectedFont);
    }
}

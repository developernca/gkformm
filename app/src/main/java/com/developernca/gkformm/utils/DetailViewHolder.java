package com.developernca.gkformm.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.developernca.gkformm.R;

/**
 * Created on 8/17/2018.
 *
 * @author Nyein Chan Aung
 */

class DetailViewHolder extends RecyclerView.ViewHolder {

    private TextView tvQuestion;
    private TextView tvAnswer;
    private ImageButton fbShareBtn;
    private ImageButton feedbackBtn;

    DetailViewHolder(View itemView) {
        super(itemView);
        tvQuestion = itemView.findViewById(R.id.idTvQuestion);
        tvAnswer = itemView.findViewById(R.id.idTvAnswer);
        fbShareBtn = itemView.findViewById(R.id.idBtnFbShare);
        feedbackBtn = itemView.findViewById(R.id.idBtnFeedBack);
    }

    TextView getTvQuestion() {
        return tvQuestion;
    }

    TextView getTvAnswer() {
        return tvAnswer;
    }

    ImageButton getFbShareBtn() {
        return fbShareBtn;
    }

    ImageButton getFeedbackBtn() {
        return feedbackBtn;
    }
}

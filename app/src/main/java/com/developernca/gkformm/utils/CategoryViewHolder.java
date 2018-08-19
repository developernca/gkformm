package com.developernca.gkformm.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.developernca.gkformm.R;

/**
 * Created on 8/15/2018.
 *
 * @author Nyein Chan Aung
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    private TextView tvCategoryName;
    private Button btnToDetail;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        this.tvCategoryName = itemView.findViewById(R.id.idTvCategoryName);
        this.btnToDetail = itemView.findViewById(R.id.idBtnToDetail);
    }

    public TextView getTvCategoryName() {
        return tvCategoryName;
    }

    public Button getBtnToDetail() {
        return btnToDetail;
    }
}

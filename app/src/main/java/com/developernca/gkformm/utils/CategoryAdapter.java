package com.developernca.gkformm.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.developernca.gkformm.R;
import com.developernca.gkformm.bean.Category;

import java.util.ArrayList;

/**
 * Created on 8/15/2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private ArrayList<Category> categoryList;
    private CategoryAdapterInteractionListener mListener;
    /**
     * This is used to prevent from double clicking the toDetailButton.
     * Once the user click toDetailButton, it cannot be clicked until the next
     * screen is shown. So, it must be set to true when clicked it and must be
     * reset to it false state in the DetailActivity onStop() method and
     * MainActivity onDetailBtnClick() method's Fragment transaction part.
     * Though it is static, this variable initialize in constructor.
     */
    public static boolean isBtnCLicking;

    /**
     * Listener interface for interacting with this category adapter.
     */
    public interface CategoryAdapterInteractionListener {
        /**
         * Call back for detail button click.
         *
         * @param tag A tag object attached to the button
         */
        void onButtonClick(DetailBtnTag tag);
    }

    public CategoryAdapter(ArrayList<Category> categoryList, CategoryAdapterInteractionListener mListener) {
        this.categoryList = categoryList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_cardview, parent, false);
        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        TextView tvCategoryName = holder.getTvCategoryName();
        tvCategoryName.setText(categoryList.get(position).getCategoryName());
        Button btnToDetail = holder.getBtnToDetail();
        DetailBtnTag tag = new DetailBtnTag();
        tag.detailName = categoryList.get(position).getDetailName();
        btnToDetail.setOnClickListener(view -> {
            mListener.onButtonClick(tag);
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class DetailBtnTag {
        public String detailName;
    }
}

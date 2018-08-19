package com.developernca.gkformm.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.developernca.gkformm.R;
import com.developernca.gkformm.bean.Details;
import com.developernca.gkformm.dialog.FbShareOptChooserDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created on 8/17/2018.
 *
 * @author Nyein Chan Aung
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailViewHolder> {

    private ArrayList<Details> detailList;
    private DetailAdapterInteractionListener mLinstener;

    public interface DetailAdapterInteractionListener {
        void onFbShareBtnClick(String q, String a);

        void onFeedbackBtnClick(String q, String a);
    }

    public DetailAdapter(ArrayList<Details> detailList, DetailAdapterInteractionListener mLinstener) {
        this.detailList = detailList;
        this.mLinstener = mLinstener;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_details_cardview, parent, false);
        return new DetailViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        String q = detailList.get(position).getQuestion();
        String a = detailList.get(position).getAnswer();
        holder.getTvQuestion().setText(detailList.get(position).getQuestion());
        holder.getTvAnswer().setText(detailList.get(position).getAnswer());
        holder.getFbShareBtn().setOnClickListener(v -> mLinstener.onFbShareBtnClick(q, a));
        holder.getFeedbackBtn().setOnClickListener(v -> mLinstener.onFeedbackBtnClick(q, a));

    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }
}

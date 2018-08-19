package com.developernca.gkformm.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developernca.gkformm.R;
import com.developernca.gkformm.bean.Category;
import com.developernca.gkformm.utils.CategoryAdapter;
import com.developernca.gkformm.utils.ConstantsAndUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} that hold a list of category for
 * general knowledge. When a user select a category from the list
 * he/she can see questions and answers of the selected category.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    /**
     * Recycler view for category list.
     */
    private RecyclerView rclCatList;
    /**
     * JSON object for category list
     */
    private JSONObject categoryStringJSON;
    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private int currentFont;

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onDetailBtnClick(String detailName);
    }

    public MainFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance(int currentFont) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ConstantsAndUtils.EXTRA_CURRENT_FNT, currentFont);
        fragment.setArguments(args);
        return fragment;
    }

    private void initRecyclerAdapter() {
        ArrayList<Category> catList = new ArrayList<>();
        try {
            JSONArray catArr = categoryStringJSON.getJSONArray("category");
            int len = catArr.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj = catArr.getJSONObject(i);
                Category cat = new Category(obj.getString("cat"), obj.getString("dtl"));
                catList.add(cat);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        rclCatList.setLayoutManager(new LinearLayoutManager(mContext));
        CategoryAdapter categoryAdapter = new CategoryAdapter(catList, tag -> mListener.onDetailBtnClick(tag.detailName));
        rclCatList.setAdapter(categoryAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            currentFont = args.getInt(ConstantsAndUtils.EXTRA_CURRENT_FNT);
        }
        String filename = ConstantsAndUtils.getJSONFileName(ConstantsAndUtils.CATEGORY_JSON_FILENAME, currentFont);
        categoryStringJSON = ConstantsAndUtils.getJSONObject(mContext, filename);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        rclCatList = v.findViewById(R.id.idRclViewCategory);
        initRecyclerAdapter();
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}

package com.sxu.basecomponent.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by juhg on 16/2/22.
 */
public abstract class BaseFragment extends Fragment {

    protected View mContentView;

    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getActivity() != null || !getActivity().isFinishing()) {
            loadData();
        }
        
        if (getLayoutResId() != 0) {
            mContentView = inflater.inflate(getLayoutResId(), null);
            //mContentView.setBackgroundColor(Color.WHITE);
            mContentView.setClickable(true);
            getViews();
            initFragment();
        }

        return mContentView;
    }

    protected void loadData(){}

    protected abstract int getLayoutResId();

    protected abstract void getViews();

    protected abstract void initFragment();
}

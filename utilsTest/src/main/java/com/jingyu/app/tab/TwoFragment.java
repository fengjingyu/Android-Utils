package com.jingyu.app.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jingyu.middle.base.BaseFragment;
import com.jingyu.utilstest.R;

public class TwoFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, R.layout.tab_fragment_two, container);
    }

}
package com.jingyu.test.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jingyu.middle.base.BaseFragment;
import com.jingyu.test.CatalogueActivity;
import com.jingyu.test.R;

public class OneFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, R.layout.tab_fragment_one, container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getViewById(R.id.catalogue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CatalogueActivity.actionStart(getActivity());
            }
        });
    }
}
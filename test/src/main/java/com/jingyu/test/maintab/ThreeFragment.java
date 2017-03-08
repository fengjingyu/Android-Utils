package com.jingyu.test.maintab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jingyu.middle.base.BaseFragment;
import com.jingyu.test.R;
import com.jingyu.test.material.MaterialActivity;
import com.jingyu.test.material.PercentLayoutActivity;

public class ThreeFragment extends BaseFragment implements View.OnClickListener {

    Button material;
    Button percentLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, R.layout.tab_fragment_three, container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWidget();
        setListener();
    }

    private void setListener() {
        material.setOnClickListener(this);
        percentLayout.setOnClickListener(this);
    }

    public void initWidget() {
        material = getViewById(R.id.material);
        percentLayout = getViewById(R.id.percentLayout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.material:
                MaterialActivity.actionStart(getActivity());
                break;
            case R.id.percentLayout:
                PercentLayoutActivity.actionStart(getActivity());
                break;
            default:
                break;
        }
    }
}
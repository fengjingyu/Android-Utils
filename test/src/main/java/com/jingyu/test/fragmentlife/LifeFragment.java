package com.jingyu.test.fragmentlife;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jingyu.android.middle.base.BaseFragment;
import com.jingyu.test.R;

public class LifeFragment extends BaseFragment {

    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return createView(inflater, R.layout.fragment_life, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int result = getIndex() % 5;
        if (result == 0) {
            view.setBackgroundResource(R.color.yellow);
        } else if (result == 1) {
            view.setBackgroundResource(R.color.green);
        } else if (result == 2) {
            view.setBackgroundResource(R.color.red);
        } else if (result == 3) {
            view.setBackgroundResource(R.color.blue);
        } else if (result == 4) {
            view.setBackgroundResource(R.color.c_orange_ff6600);
        }
    }
}

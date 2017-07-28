package com.jingyu.android.material;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jingyu.android.middle.base.BaseActivity;

public class AppMainActivity extends BaseActivity implements View.OnClickListener {

    private Button material;
    private Button percentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);

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

    public static void actionStart(Activity activity) {
        activity.startActivity(new Intent(activity, AppMainActivity.class));
    }
}

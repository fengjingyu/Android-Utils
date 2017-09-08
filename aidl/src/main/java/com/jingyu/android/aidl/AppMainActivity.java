package com.jingyu.android.aidl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jingyu.android.aidl.service.AIDLServiceActivity;
import com.jingyu.android.aidl.service.LocalServiceActivity;
import com.jingyu.android.middle.base.BaseActivity;

public class AppMainActivity extends BaseActivity {

    Button localService;
    Button aidl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);

        localService = getViewById(R.id.localService);
        aidl = getViewById(R.id.aidl);

        localService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalServiceActivity.actionStart(getActivity());
            }
        });

        aidl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AIDLServiceActivity.actionStart(getActivity());
            }
        });
    }

    public static void actionStart(Activity activity) {
        activity.startActivity(new Intent(activity, AppMainActivity.class));
    }
}

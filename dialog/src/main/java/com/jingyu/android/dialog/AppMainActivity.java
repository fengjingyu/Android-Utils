package com.jingyu.android.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jingyu.android.middle.base.BaseActivity;

public class AppMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);

        getViewById(R.id.systemDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemDialogActivity.actionStart(getActivity());
            }
        });
    }

    public static void actionStart(Activity activity) {
        activity.startActivity(new Intent(activity, AppMainActivity.class));
    }
}

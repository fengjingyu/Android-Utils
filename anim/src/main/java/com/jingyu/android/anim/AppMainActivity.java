package com.jingyu.android.anim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jingyu.android.middle.base.BaseActivity;

public class AppMainActivity extends BaseActivity {
    private Button propertyAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);

        propertyAnim = getViewById(R.id.propertyAnim);
        propertyAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PropertyAnimActivity.actionStart(getActivity());
            }
        });
    }

    public static void actionStart(Activity activity) {
        activity.startActivity(new Intent(activity, AppMainActivity.class));
    }
}

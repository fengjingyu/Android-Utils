package com.jingyu.android.kotlin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jingyu.android.middle.base.BaseActivity;

import org.jetbrains.annotations.Nullable;


public class AppMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);
    }

    public static void actionStart(@Nullable Activity activity) {
        activity.startActivity(new Intent(activity, AppMainActivity.class));
    }
}

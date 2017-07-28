package com.jingyu.android.material;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jingyu.android.middle.base.BaseActivity;

public class PercentLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percent_layout);
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, PercentLayoutActivity.class));
    }
}

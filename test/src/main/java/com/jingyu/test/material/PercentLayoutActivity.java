package com.jingyu.test.material;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.demo.middle.base.BaseActivity;
import com.jingyu.test.R;

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

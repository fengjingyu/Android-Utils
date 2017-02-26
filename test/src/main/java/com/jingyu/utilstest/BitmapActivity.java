package com.jingyu.utilstest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.jingyu.middle.base.BaseActivity;
import com.jingyu.test.R;

public class BitmapActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);



    }

    public static void actionStart(FragmentActivity activity) {
        activity.startActivity(new Intent(activity, BitmapActivity.class));
    }
}

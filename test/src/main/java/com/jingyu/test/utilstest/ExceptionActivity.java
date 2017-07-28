package com.jingyu.test.utilstest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.demo.middle.base.BaseActivity;


/**
 * @author fengjingyu@foxmail.com
 */
public class ExceptionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int a = 1 / 0;
    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, ExceptionActivity.class));
    }
}
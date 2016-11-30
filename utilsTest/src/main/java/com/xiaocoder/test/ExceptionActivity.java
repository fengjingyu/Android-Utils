package com.xiaocoder.test;

import android.os.Bundle;

import com.xiaocoder.test_middle.base.BaseActivity;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public class ExceptionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int a = 1 / 0;

    }

}
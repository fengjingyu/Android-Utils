package com.jingyu.utilstest;

import android.os.Bundle;

import com.jingyu.middle.base.BaseActivity;


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
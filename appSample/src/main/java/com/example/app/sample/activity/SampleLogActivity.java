package com.example.app.sample.activity;

import android.os.Bundle;

import com.example.middle.Sp;
import com.example.middle.base.BaseActivity;
import com.jingyu.utils.function.helper.LogHelper;

public class SampleLogActivity extends BaseActivity {

    //-------------修改配置去App、Config里----------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 日志 提示
     */
    public void log() {
        LogHelper.i(1);
        LogHelper.i(null);
        LogHelper.i(new Object());
        LogHelper.i("demo");
        LogHelper.i(false);

        LogHelper.shortToast(1);
        LogHelper.shortToast(null);
        LogHelper.shortToast(new Object());
        LogHelper.shortToast("demo");
        LogHelper.shortToast(false);

        LogHelper.dLongToast(1);
        LogHelper.dLongToast(null);
        LogHelper.dLongToast(new Object());
        LogHelper.dLongToast("demo");
        LogHelper.dLongToast(false);
    }

    /**
     * sp
     */
    public void sp() {
        Sp.setUserId("1");
        Sp.getUserId();

        Sp.setLogin(false);
        Sp.isLogin();
    }

}

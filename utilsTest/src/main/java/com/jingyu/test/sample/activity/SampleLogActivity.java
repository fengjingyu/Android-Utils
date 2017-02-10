package com.jingyu.test.sample.activity;

import android.os.Bundle;

import com.jingyu.test_middle.Sp;
import com.jingyu.test_middle.base.BaseActivity;
import com.jingyu.utils.function.helper.Logger;

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
        Logger.i(1);
        Logger.i(null);
        Logger.i(new Object());
        Logger.i("demo");
        Logger.i(false);

        Logger.shortToast(1);
        Logger.shortToast(null);
        Logger.shortToast(new Object());
        Logger.shortToast("demo");
        Logger.shortToast(false);

        Logger.dLongToast(1);
        Logger.dLongToast(null);
        Logger.dLongToast(new Object());
        Logger.dLongToast("demo");
        Logger.dLongToast(false);
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

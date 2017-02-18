package com.jingyu.utilstest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jingyu.middle.base.BaseActivity;
import com.jingyu.middle.config.Config;
import com.jingyu.test.R;
import com.jingyu.utils.function.helper.CleanCacheHelper;
import com.jingyu.utils.util.UtilIoAndr;

import java.io.File;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public class ClearCacheActivity extends BaseActivity implements View.OnClickListener {

    private Button clear;
    private File dir;
    private CleanCacheHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_cache);

        initWidgets();
        setListeners();
        logic();
    }

    public void initWidgets() {
        clear = getViewById(R.id.clear);
    }

    private void logic() {
        dir = UtilIoAndr.createDirInAndroid(getApplicationContext(), Config.APP_NAME);

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("测试");
        dialog.setMessage("缓存清理中");

        helper = new CleanCacheHelper(dialog, false);
    }

    public void setListeners() {
        clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        helper.removeFileAsyn(dir);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.quit();
    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, ClearCacheActivity.class));
    }
}
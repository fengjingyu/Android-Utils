package com.jingyu.android.test.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.android.middle.config.Config;
import com.jingyu.android.test.R;
import com.jingyu.utils.function.CacheCleaner;

import java.io.File;

/**
 * @author fengjingyu@foxmail.com
 */
public class CacheCleanActivity extends BaseActivity implements View.OnClickListener {

    private Button clear;
    private File dir;
    private CacheCleaner helper;

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
        dir = Config.getAppDir(getApplicationContext());

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("测试");
        dialog.setMessage("缓存清理中");

        helper = new CacheCleaner(dialog, false);
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
        activityContext.startActivity(new Intent(activityContext, CacheCleanActivity.class));
    }
}
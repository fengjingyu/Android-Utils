package com.xiaocoder.test;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xiaocoder.test_middle.base.BaseActivity;
import com.xiaocoder.test_middle.config.ConfigFile;
import com.xiaocoder.utils.function.helper.CleanCacheHelper;
import com.xiaocoder.utils.util.UtilIoAndr;

import java.io.File;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public class ClearCacheActivity extends BaseActivity implements View.OnClickListener {

    private Button clear;
    private CleanCacheHelper helper;
    private File dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_cache);

        initWidgets();
        setListeners();
    }

    public void initWidgets() {
        clear = getViewById(R.id.clear);
        // 如果没有该dir会创建再返回，有则返回该dir
        dir = UtilIoAndr.createDirInAndroid(getApplicationContext(), ConfigFile.APP_ROOT);

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
}
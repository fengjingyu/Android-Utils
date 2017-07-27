package com.jingyu.test.tab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.demo.middle.base.BaseActivity;
import com.jingyu.test.R;
import com.jingyu.utils.exception.CrashHandler;
import com.jingyu.utils.exception.ExceptionDb;
import com.jingyu.utils.function.ActivityCollector;
import com.jingyu.utils.function.Logger;

public class TestMainActivity extends BaseActivity {

    public static final int CLICK_QUIT_INTERVAL = 1000;

    private long lastClickQuitTime;

    private RadioGroup tab_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.demo.init.R.layout.activity_main);
        initTabGroup();
    }

    protected void initTabGroup() {
        tab_group = getViewById(R.id.main_tab_group);
        tab_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                hideAllFragment();
                if (checkedId == R.id.main_tab_item1) {
                    showFragmentByClass(OneFragment.class, R.id.main_content);
                } else if (checkedId == R.id.main_tab_item2) {
                    showFragmentByClass(TwoFragment.class, R.id.main_content);
                } else if (checkedId == R.id.main_tab_item3) {
                    showFragmentByClass(ThreeFragment.class, R.id.main_content);
                } else if (checkedId == R.id.main_tab_item4) {
                    showFragmentByClass(FourFragment.class, R.id.main_content);
                } else if (checkedId == R.id.main_tab_item5) {
                    showFragmentByClass(FiveFragment.class, R.id.main_content);
                }
            }
        });
        ((RadioButton) tab_group.findViewById(R.id.main_tab_item1)).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        long quitTime = System.currentTimeMillis();
        if (quitTime - lastClickQuitTime <= CLICK_QUIT_INTERVAL) {
            ActivityCollector.appExit();
        } else {
            lastClickQuitTime = quitTime;
            Logger.shortToast("快速再按一次退出");
        }
    }

    /**
     * 进入首页时，把未上传的异常信息上传到服务器
     */
    protected void uploadException() {
        ExceptionDb exceptionModelDb = CrashHandler.getInstance().getExceptionDb();
        if (exceptionModelDb != null) {
            Logger.d(exceptionModelDb.queryCount());
            Logger.d(exceptionModelDb.queryUploadFail(ExceptionDb.SORT_DESC));
        }
    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, TestMainActivity.class));
    }
}

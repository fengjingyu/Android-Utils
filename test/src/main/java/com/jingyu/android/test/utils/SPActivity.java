package com.jingyu.android.test.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.jingyu.android.common.log.Logger;
import com.jingyu.android.common.sp.SPHelper;
import com.jingyu.android.middle.AppSp;
import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.android.test.R;

import java.util.HashSet;
import java.util.Set;

public class SPActivity extends BaseActivity {

    private SPHelper spHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        getViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        getViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        spHelper = new SPHelper(getApplication(), "ceshi_sp");
    }

    private void clear() {
        spHelper.clear();
    }

    private void save() {

        AppSp.setUserName("小明");
        Logger.d(AppSp.getUserName());

        AppSp.setUserPhone("1234567");
        Logger.d(AppSp.getUserPhone());


        Set<String> set = new HashSet<>();
        set.add("aa");
        set.add("bb");
        set.add("cc");
        spHelper.spPut("0", set);
        spHelper.spPut("1", "123 ");
        spHelper.spPut("2", 456);
        spHelper.spPut("3", 789.1F);
        spHelper.spPut("4", false);
        spHelper.spPut("5", " abc ");

        // 会覆盖之前的String类型,转为float类型
        spHelper.spPut("1", 0.1f);

        String result2 = spHelper.spGet("5", "java") + spHelper.spGet("1", 0.0f) + spHelper.spGet("2", 0) + spHelper.spGet("3", 0.1f)
                + spHelper.spGet("4", true) + spHelper.spGet("6", null) + spHelper.spGet("7", "    jkl");
        Logger.shortToast(result2);
    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, SPActivity.class));
    }
}

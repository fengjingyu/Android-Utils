package com.jingyu.android.test.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.jingyu.android.common.activity.ActivityManager;
import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.android.test.R;
import com.jingyu.java.mytool.file.FileCreater;

/**
 * @author fengjingyu@foxmail.com
 */
public class TaskActivity3 extends BaseActivity {

    TextView info;
    Button start_taskactivity0;
    Button to_taskactivity0;
    Button to_taskactivity1;
    Button to_taskactivity2;
    Button finish_taskactivity1;
    Button finish_taskactivity2;
    Button finish_current_activity;
    Button finish_all_activity;
    Button finish_activity_then_startActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task3);
        initWidgets();
        setListeners();
        info();
    }

    public void initWidgets() {
        info = getViewById(R.id.info);
        start_taskactivity0 = getViewById(R.id.start_taskactivity0);
        to_taskactivity0 = getViewById(R.id.to_taskactivity0);
        to_taskactivity1 = getViewById(R.id.to_taskactivity1);
        to_taskactivity2 = getViewById(R.id.to_taskactivity2);
        finish_taskactivity1 = getViewById(R.id.finish_taskactivity1);
        finish_taskactivity2 = getViewById(R.id.finish_taskactivity2);
        finish_current_activity = getViewById(R.id.finish_current_activity);
        finish_all_activity = getViewById(R.id.finish_all_activity);
        finish_activity_then_startActivity = getViewById(R.id.finish_activity_then_startActivity);
    }

    private void info() {
        info.setText("");
        info.append(ActivityManager.getStack().size() + "-----栈的大小---" + FileCreater.LINE_SEPARATOR);
        for (Activity item : ActivityManager.getStack()) {
            info.append(item.getClass() + "----" + FileCreater.LINE_SEPARATOR);
        }
        info.append("TaskActivity0是否存在--" + ActivityManager.isActivityExist(TaskActivity0.class) + FileCreater.LINE_SEPARATOR);
        info.append("TaskActivity1是否存在--" + ActivityManager.isActivityExist(TaskActivity1.class) + FileCreater.LINE_SEPARATOR);
        info.append("TaskActivity2是否存在--" + ActivityManager.isActivityExist(TaskActivity2.class) + FileCreater.LINE_SEPARATOR);
        info.append("TaskActivity3是否存在--" + ActivityManager.isActivityExist(TaskActivity3.class) + FileCreater.LINE_SEPARATOR);
        info.append("当前页面--" + ActivityManager.getCurrentActivity() + FileCreater.LINE_SEPARATOR);
        info.append("TaskActivity1是否存在--" + ActivityManager.getActivity(TaskActivity1.class) + FileCreater.LINE_SEPARATOR);
        info.append("TaskActivity2是否存在--" + ActivityManager.getActivity(TaskActivity2.class) + FileCreater.LINE_SEPARATOR);
    }

    public void setListeners() {
        start_taskactivity0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskActivity0.actionStart(getActivity());
            }
        });

        to_taskactivity0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.toActivity(TaskActivity0.class);
            }
        });

        to_taskactivity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.toActivity(TaskActivity1.class);
            }
        });

        to_taskactivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.toActivity(TaskActivity2.class);
            }
        });

        finish_taskactivity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishActivity(TaskActivity1.class);
                info();
            }
        });
        finish_taskactivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishActivity(TaskActivity2.class);
                info();
            }
        });

        finish_all_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishAllActivity();
            }
        });

        finish_current_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishCurrentActivity();
            }
        });

        finish_activity_then_startActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishCurrentActivity();
                startActivity(new Intent(getActivity(), TaskActivity3.class));
            }
        });
    }

    public static void actionStart(Activity activity) {
        activity.startActivity(new Intent(activity, TaskActivity3.class));
    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, TaskActivity3.class));
    }

}

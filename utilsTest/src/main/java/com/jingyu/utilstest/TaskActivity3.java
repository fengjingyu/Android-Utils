package com.jingyu.utilstest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jingyu.middle.base.BaseActivity;
import com.jingyu.utils.function.helper.ActivityCollector;
import com.jingyu.utils.util.UtilIo;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public class TaskActivity3 extends BaseActivity {

    TextView info;
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
        info.append(ActivityCollector.getStack().size() + "-----栈的大小---" + UtilIo.LINE_SEPARATOR);
        for (Activity item : ActivityCollector.getStack()) {
            info.append(item.getClass() + "----" + UtilIo.LINE_SEPARATOR);
        }
        info.append("TaskActivity0是否存在--" + ActivityCollector.isActivityExist(TaskActivity0.class) + UtilIo.LINE_SEPARATOR);
        info.append("TaskActivity1是否存在--" + ActivityCollector.isActivityExist(TaskActivity1.class) + UtilIo.LINE_SEPARATOR);
        info.append("TaskActivity2是否存在--" + ActivityCollector.isActivityExist(TaskActivity2.class) + UtilIo.LINE_SEPARATOR);
        info.append("TaskActivity3是否存在--" + ActivityCollector.isActivityExist(TaskActivity3.class) + UtilIo.LINE_SEPARATOR);
        info.append("当前页面--" + ActivityCollector.getCurrentActivity() + UtilIo.LINE_SEPARATOR);
        info.append("TaskActivity1是否存在--" + ActivityCollector.getActivity(TaskActivity1.class) + UtilIo.LINE_SEPARATOR);
        info.append("TaskActivity2是否存在--" + ActivityCollector.getActivity(TaskActivity2.class) + UtilIo.LINE_SEPARATOR);
    }

    public void setListeners() {
        to_taskactivity0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCollector.toActivity(TaskActivity0.class);
            }
        });

        to_taskactivity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCollector.toActivity(TaskActivity1.class);
            }
        });

        to_taskactivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCollector.toActivity(TaskActivity2.class);
            }
        });

        finish_taskactivity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCollector.finishActivity(TaskActivity1.class);
                info();
            }
        });
        finish_taskactivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCollector.finishActivity(TaskActivity2.class);
                info();
            }
        });

        finish_all_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCollector.finishAllActivity();
            }
        });

        finish_current_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCollector.finishCurrentActivity();
            }
        });

        finish_activity_then_startActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCollector.finishCurrentActivity();
                startActivity(new Intent(getActivity(), TaskActivity1.class));
            }
        });
    }

    public static void actionStart(Activity activity) {
        activity.startActivity(new Intent(activity, TaskActivity3.class));
    }

}

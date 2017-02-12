package com.jingyu.utilstest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jingyu.middle.base.BaseActivity;


/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public class TaskActivity1 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1);
        getViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskActivity2.actionStart(getActivity());
            }
        });
    }

    public static void actionStart(Activity activityContext) {
        activityContext.startActivity(new Intent(activityContext, TaskActivity1.class));
    }


}

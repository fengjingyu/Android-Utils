package com.jingyu.utilstest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.demo.middle.base.BaseActivity;
import com.jingyu.test.R;

/**
 * @author fengjingyu@foxmail.com
 */
public class TaskActivity0 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task0);

        getViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskActivity1.actionStart(getActivity());
            }
        });
    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, TaskActivity0.class));
    }

}

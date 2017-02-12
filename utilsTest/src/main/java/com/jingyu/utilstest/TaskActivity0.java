package com.jingyu.utilstest;

import android.os.Bundle;
import android.view.View;

import com.jingyu.middle.base.BaseActivity;

/**
 * @author fengjingyu@foxmail.com
 * @description
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

}

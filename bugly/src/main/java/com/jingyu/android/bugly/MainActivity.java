package com.jingyu.android.bugly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jingyu.android.middle.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getViewById(R.id.testBuglyCrash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CrashReport.testJavaCrash();
                //int a = 1 / 0;
                String a = null;
                System.out.println(a.length());
            }
        });

    }

    public static void actionStart(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
    }
}

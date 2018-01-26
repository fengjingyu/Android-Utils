package com.jingyu.android.init;

import android.widget.RadioGroup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;

import com.jingyu.android.init.fragment.tab.TabFragmentFour;
import com.jingyu.android.init.fragment.tab.TabFragmentOne;
import com.jingyu.android.init.fragment.tab.TabFragmentThree;
import com.jingyu.android.init.fragment.tab.TabFragmentTwo;
import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.utils.function.ActivityCollector;
import com.jingyu.utils.function.Logger;

public class MainActivity extends BaseActivity {

    public static final int CLICK_QUIT_INTERVAL = 1000;

    private long lastClickQuitTime;

    private RadioGroup tab_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabGroup();

    }

    private void initTabGroup() {
        tab_group = getViewById(R.id.tab_group);
        tab_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                hideAllFragment();
                if (checkedId == R.id.tab_item1) {
                    showFragmentByClass(TabFragmentOne.class, R.id.content);
                } else if (checkedId == R.id.tab_item2) {
                    showFragmentByClass(TabFragmentTwo.class, R.id.content);
                } else if (checkedId == R.id.tab_item3) {
                    showFragmentByClass(TabFragmentThree.class, R.id.content);
                } else if (checkedId == R.id.tab_item4) {
                    showFragmentByClass(TabFragmentFour.class, R.id.content);
                }
            }
        });
        ((RadioButton) tab_group.findViewById(R.id.tab_item1)).setChecked(true);
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

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, MainActivity.class));
    }

}

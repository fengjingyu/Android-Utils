package com.xiaocoder.test.stack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xiaocoder.utils.function.helper.ActivityHelper;
import com.xiaocoder.utils.util.UtilIo;
import com.xiaocoder.test.MainActivity;
import com.xiaocoder.test.R;
import com.xiaocoder.test_middle.base.BaseActivity;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class StackActivity extends BaseActivity {

    TextView stack_desc;
    Button to_main_activity;
    Button to_search_activity1;
    Button to_search_activity2;
    Button finish_activity;
    Button finish_activity2;
    Button finish_current_activity;
    Button finish_all_activity;
    Button finish_activity_then_startActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack);
        initWidgets();
        setListeners();
    }

    public void initWidgets() {
        stack_desc = getViewById(R.id.stack_desc);
        to_main_activity = getViewById(R.id.to_main_activity);
        to_search_activity1 = getViewById(R.id.to_search_activity1);
        to_search_activity2 = getViewById(R.id.to_search_activity2);
        finish_activity = getViewById(R.id.finish_activity);
        finish_activity2 = getViewById(R.id.finish_activity2);
        finish_current_activity = getViewById(R.id.finish_current_activity);
        finish_all_activity = getViewById(R.id.finish_all_activity);
        finish_activity_then_startActivity = getViewById(R.id.finish_activity_then_startActivity);

        desc();

    }

    private void desc() {
        stack_desc.setText("");
        stack_desc.append(ActivityHelper.getStack().size() + "-----栈的大小---" + UtilIo.LINE_SEPARATOR);
        for (Activity item : ActivityHelper.getStack()) {
            stack_desc.append(item.getClass() + "----" + UtilIo.LINE_SEPARATOR);
        }
        stack_desc.append("MainActivity是否存在--" + ActivityHelper.isActivityExist(MainActivity.class) + UtilIo.LINE_SEPARATOR);
        stack_desc.append("SearchActivity是否存在--" + ActivityHelper.isActivityExist(SearchActivity.class) + UtilIo.LINE_SEPARATOR);
        stack_desc.append("SearchActivity2是否存在--" + ActivityHelper.isActivityExist(SearchActivity2.class) + UtilIo.LINE_SEPARATOR);
        stack_desc.append("StackActivity是否存在--" + ActivityHelper.isActivityExist(StackActivity.class) + UtilIo.LINE_SEPARATOR);
        stack_desc.append("当前页面--" + ActivityHelper.getCurrentActivity() + UtilIo.LINE_SEPARATOR);
        stack_desc.append("SearchActivity是否存在--" + ActivityHelper.getActivity(SearchActivity.class) + UtilIo.LINE_SEPARATOR);
        stack_desc.append("SearchActivity2是否存在--" + ActivityHelper.getActivity(SearchActivity2.class) + UtilIo.LINE_SEPARATOR);
    }

    public void setListeners() {
        to_main_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHelper.toActivity(MainActivity.class);
            }
        });

        to_search_activity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHelper.toActivity(SearchActivity.class);
            }
        });

        to_search_activity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHelper.toActivity(SearchActivity2.class);
            }
        });

        finish_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHelper.finishActivity(SearchActivity.class);
                desc();
            }
        });
        finish_activity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHelper.finishActivity(SearchActivity2.class);
                desc();
            }
        });

        finish_all_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHelper.finishAllActivity();
            }
        });

        finish_current_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHelper.finishCurrentActivity();
            }
        });

        finish_activity_then_startActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 以下两种方式的周期是一样的
                ActivityHelper.finishCurrentActivity();
                startActivity(new Intent(getActivity(), SearchActivity.class));
//                com.xiaocoder.android_test.stack.StackActivity@2de63894---finish
//                com.xiaocoder.android_test.stack.StackActivity@2de63894---onPause
//                com.xiaocoder.android_test.stack.SearchActivity@25c4d4fb---onCreate
//                com.xiaocoder.android_test.stack.SearchActivity@25c4d4fb---onStart
//                com.xiaocoder.android_test.stack.SearchActivity@25c4d4fb---onResume
//                com.xiaocoder.android_test.stack.StackActivity@2de63894---onStop
//                com.xiaocoder.android_test.stack.StackActivity@2de63894---onDestroy


//                startActivity(new Intent(getActivity(),SearchActivity.class));
//                ActivityHelper.finishCurrentActivity();
//                com.xiaocoder.android_test.stack.StackActivity@2de63894---finish
//                com.xiaocoder.android_test.stack.StackActivity@2de63894---onPause
//                com.xiaocoder.android_test.stack.SearchActivity@25c4d4fb---onCreate
//                com.xiaocoder.android_test.stack.SearchActivity@25c4d4fb---onStart
//                com.xiaocoder.android_test.stack.SearchActivity@25c4d4fb---onResume
//                com.xiaocoder.android_test.stack.StackActivity@2de63894---onStop
//                com.xiaocoder.android_test.stack.StackActivity@2de63894---onDestroy
            }
        });

    }

}

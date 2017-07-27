package com.jingyu.test.fragmentlife;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.demo.middle.base.BaseActivity;
import com.jingyu.test.R;

import java.util.List;

public class FragmentLifeActivity extends BaseActivity implements View.OnClickListener {

    private TextView fragmentsNum;
    private Button addFragment;
    private Button addFragment2Stack;
    private Button clearAllFragment;
    private Button replaceFragment;
    private Button replaceFragment2Stack;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_life);

        initWidget();
        setListener();
        logic();
    }

    public void initWidget() {
        fragmentsNum = getViewById(R.id.fragmentsNum);
        addFragment = getViewById(R.id.addFragment);
        addFragment2Stack = getViewById(R.id.addFragment2Stack);
        clearAllFragment = getViewById(R.id.clearAllFragment);
        replaceFragment = getViewById(R.id.replaceFragment);
        replaceFragment2Stack = getViewById(R.id.replaceFragment2Stack);
    }

    public void setListener() {
        addFragment.setOnClickListener(this);
        addFragment2Stack.setOnClickListener(this);
        clearAllFragment.setOnClickListener(this);
        replaceFragment.setOnClickListener(this);
        replaceFragment2Stack.setOnClickListener(this);
    }

    private void logic() {
        addFragment(R.id.fragment_layout, getFragment());
        setNumHint();
    }

    @NonNull
    private LifeFragment getFragment() {
        LifeFragment lifeFragment = new LifeFragment();
        lifeFragment.setIndex(index++);
        return lifeFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addFragment:
                addFragment(R.id.fragment_layout, getFragment());
                break;
            case R.id.addFragment2Stack:
                addFragment(R.id.fragment_layout, getFragment(), true);
                break;
            case R.id.clearAllFragment:
                removeAllFragments();
                break;
            case R.id.replaceFragment:
                replaceFragment(R.id.fragment_layout, getFragment());
                break;
            case R.id.replaceFragment2Stack:
                replaceFragment(R.id.fragment_layout, getFragment(), true);
                break;
        }
        setNumHint();
    }

    private void setNumHint() {
        fragmentsNum.setText("有" + getFragmentsNum() + "个fragment");
    }

    private int getFragmentsNum() {
        int count = 0;
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                //如果之前有add replace,然后remove,getFragments()集合里会有空元素
                count++;
            }
        }
        return count;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setNumHint();
    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, FragmentLifeActivity.class));
    }

}

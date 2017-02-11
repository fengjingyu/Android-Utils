package com.jingyu.utils.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.jingyu.utils.function.helper.ActivityCollector;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.util.UtilInput;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * @author  fengjingyu@foxmail.com
 * @description
 */
public abstract class PlusActivity extends FragmentActivity {

    @SuppressWarnings("unchecked")
    public <T extends View> T getViewById(int id) {
        return (T) findViewById(id);
    }

    @SuppressWarnings("unchecked")
    public <T extends Fragment> T getFragmentByTag(String tag) {
        return (T) getSupportFragmentManager().findFragmentByTag(tag);
    }

    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            Logger.e(this, "回收后重新创建了");
        }

        ActivityCollector.addActivityToStack(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.delActivityFromStack(this);
    }

    @Override
    public void finish() {
        // 隐藏键盘
        UtilInput.hiddenInputMethod(this);
        super.finish();
    }

    public void addFragment(int layout_id, Fragment fragment, String tag, boolean isToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(layout_id, fragment, tag);
        if (isToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void addFragment(int layout_id, Fragment fragment) {
        addFragment(layout_id, fragment, fragment.getClass().getSimpleName(), false);
    }

    public void replaceFragment(int layout_id, Fragment fragment, String tag, boolean isToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(layout_id, fragment, tag);
        if (isToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void replaceFragment(int layout_id, Fragment fragment) {
        replaceFragment(layout_id, fragment, fragment.getClass().getSimpleName(), false);
    }

    public void removeFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }

    /**
     * 之前必须有add
     */
    public void showFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    public Fragment showFragmentByClass(Class<? extends Fragment> fragment_class, int layout_id) {
        Fragment fragment = getFragmentByTag(fragment_class.getSimpleName());
        if (fragment == null) {
            try {
                Constructor<? extends Fragment> cons = fragment_class.getConstructor();
                fragment = cons.newInstance();
                addFragment(layout_id, fragment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showFragment(fragment);
        }
        return fragment;
    }

    public void hideFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(fragment);
        ft.commitAllowingStateLoss();
    }

    public void hideFragment(List<Fragment> fragments) {
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                hideFragment(fragment);
            }
        }
    }

    public void hideAllFragment() {
        hideFragment(getSupportFragmentManager().getFragments());
    }

    /**
     * 这里得重写,否则startforresult时, 无法回调到fragment中的方法 ,
     * 如果fragment中又有嵌套的话,fragmetn中的该方法也得重写
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.i(this + "---onActivityResult");
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                Logger.i(this + "onActivityResult---" + fragment.toString());
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}

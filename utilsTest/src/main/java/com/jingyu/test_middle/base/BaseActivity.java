package com.jingyu.test_middle.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.util.UtilBroadcast;
import com.jingyu.test.R;
import com.jingyu.utils.application.PlusActivity;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public abstract class BaseActivity extends PlusActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i(this + "---onCreate");
        initReceiver();
    }

    /**
     * 是否有网络的回调，可能统一处理应用对网络转换的逻辑
     */
    private BroadcastReceiver mNetReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();

            boolean hasConnectivity = info != null && info.isConnected();

            if (hasConnectivity) {
                Logger.dShortToast("有网");
                onNetNormal();
            } else {
                Logger.dShortToast("无网");
                onNetLoss();
            }
        }
    };

    protected void onNetNormal() {
    }

    protected void onNetLoss() {
    }

    @Override
    public void addFragment(int layout_id, Fragment fragment, String tag, boolean isToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // 增加了动画效果
        ft.setCustomAnimations(R.anim.anim_alpha_in, R.anim.anim_alpha);
        ft.add(layout_id, fragment, tag);
        if (isToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }

    /**
     * 之前必须有add
     */
    @Override
    public void showFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // 增加了动画效果
        ft.setCustomAnimations(R.anim.anim_alpha_in, R.anim.anim_alpha);
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }


    private void initReceiver() {
        UtilBroadcast.register(this, 1000, ConnectivityManager.CONNECTIVITY_ACTION, mNetReceiver);
    }

    private void unbindReceiver() {
        UtilBroadcast.unRegister(this, mNetReceiver);
    }

    @Override
    protected void onDestroy() {
        unbindReceiver();
        super.onDestroy();
        Logger.i(this + "---onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.i(this + "---onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.i(this + "---onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.i(this + "---onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.i(this + "---onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.i(this + "---onRestart");
    }

    @Override
    public void finish() {
        super.finish();
        Logger.i(this + "---finish");
    }
}

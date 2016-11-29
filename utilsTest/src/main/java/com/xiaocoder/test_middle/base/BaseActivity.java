package com.xiaocoder.test_middle.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.xiaocoder.test.R;
import com.xiaocoder.utils.application.BActivity;
import com.xiaocoder.utils.io.LogHelper;
import com.xiaocoder.utils.util.UtilBroadcast;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public abstract class BaseActivity extends BActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogHelper.i(this + "---onCreate");
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
                LogHelper.dShortToast("有网");
                onNetNormal();
            } else {
                LogHelper.dShortToast("无网");
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
        ft.setCustomAnimations(R.anim.xc_anim_alpha_in, R.anim.xc_anim_alpha);
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
        ft.setCustomAnimations(R.anim.xc_anim_alpha_in, R.anim.xc_anim_alpha);
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
        LogHelper.i(this + "---onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogHelper.i(this + "---onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogHelper.i(this + "---onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogHelper.i(this + "---onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogHelper.i(this + "---onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogHelper.i(this + "---onRestart");
    }

    @Override
    public void finish() {
        super.finish();
        LogHelper.i(this + "---finish");
    }
}

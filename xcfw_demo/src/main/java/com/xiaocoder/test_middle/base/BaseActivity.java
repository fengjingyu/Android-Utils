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
import com.xiaocoder.android_xcfw.application.XCActivity;
import com.xiaocoder.android_xcfw.io.XCLog;
import com.xiaocoder.android_xcfw.util.UtilBroadcast;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public abstract class BaseActivity extends XCActivity {

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
                if (mNetListener != null) {
                    XCLog.dShortToast("有网");
                    mNetListener.onNetNormal();
                }
            } else {
                if (mNetListener != null) {
                    XCLog.dShortToast("无网");
                    mNetListener.onNetLoss();
                }
            }
        }
    };

    interface OnNetChangeListener {
        void onNetLoss();

        void onNetNormal();
    }

    private OnNetChangeListener mNetListener;

    public void setOnNetChangeListener(OnNetChangeListener listener) {
        mNetListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XCLog.i(this + "---onCreate");

        initReceiver();
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
        XCLog.i(this + "---onDestroy");
    }

    /**
     * activity进入动画
     */
    public void activityAnimation() {
        overridePendingTransition(R.anim.baseactivity_slide_right_in, R.anim.baseactivity_slide_right_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        activityAnimation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        XCLog.i(this + "---onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        XCLog.i(this + "---onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        XCLog.i(this + "---onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        XCLog.i(this + "---onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        XCLog.i(this + "---onRestart");
    }

    @Override
    public void finish() {
        super.finish();
        activityAnimation();
        XCLog.i(this + "---finish");
    }
}

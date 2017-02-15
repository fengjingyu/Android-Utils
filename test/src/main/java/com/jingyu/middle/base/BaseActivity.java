package com.jingyu.middle.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.jingyu.utils.application.PlusActivity;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.util.UtilBroadcast;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public abstract class BaseActivity extends PlusActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.i(this + "---onCreate");
        super.onCreate(savedInstanceState);
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

            boolean hasConnectivity = (info != null && info.isConnected());

            if (hasConnectivity) {
                //Logger.dShortToast(getActivity() + "--有网");
                onNetNormal();
            } else {
                //Logger.dShortToast(getActivity() + "--无网");
                onNetLoss();
            }
        }
    };

    protected void onNetNormal() {
    }

    protected void onNetLoss() {
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
        Logger.i(this + "---onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        Logger.i(this + "---onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Logger.i(this + "---onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Logger.i(this + "---onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Logger.i(this + "---onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Logger.i(this + "---onRestart");
        super.onRestart();
    }

    @Override
    public void finish() {
        Logger.i(this + "---finish");
        super.finish();
    }

    @Override
    public void onBackPressed() {
        Logger.i(this + "---onBackPressed");
        super.onBackPressed();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Logger.i(this + "---onNewIntent");
        super.onNewIntent(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Logger.i(this + "---onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }
}

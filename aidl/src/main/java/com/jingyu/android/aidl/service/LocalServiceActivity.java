package com.jingyu.android.aidl.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.jingyu.android.aidl.R;
import com.jingyu.android.basictools.log.Logger;
import com.jingyu.android.middle.base.BaseActivity;

public class LocalServiceActivity extends BaseActivity implements View.OnClickListener {

    private Button startService;
    private Button stopService;
    private Button bindService;
    private Button unbindService;

    boolean mBound = false;
    private LocalService.LocalBinder myServiceBinder;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myServiceBinder = (LocalService.LocalBinder) service;
            mBound = true;
            Logger.d(this + "--onServiceConnected--" + myServiceBinder.getService().getInfo());
        }

        // 这个如果是系统kill才会调用
        @Override
        public void onServiceDisconnected(ComponentName name) {
            myServiceBinder = null;
            mBound = false;
            Logger.d(this + "--onServiceDisconnected--" + name);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_service);
        initWidget();
        setListener();
    }

    public void initWidget() {
        startService = getViewById(R.id.startService);
        stopService = getViewById(R.id.stopService);
        bindService = getViewById(R.id.bindService);
        unbindService = getViewById(R.id.unbindService);
    }

    public void setListener() {
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startService:
                LocalService.actionStart(getActivity());
                break;
            case R.id.stopService:
                LocalService.actionStop(getActivity());
                break;
            case R.id.bindService:
                bindService(new Intent(this, LocalService.class), serviceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.unbindService:
                try {
                    unbindService(serviceConnection);
                    mBound = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.longToast("服务未创建 或 服务已经销毁了 或 未绑定服务 ,调用unbindService() crash了");
                }
                break;
        }
    }

    public static void actionStart(Activity activity) {
        activity.startActivity(new Intent(activity, LocalServiceActivity.class));
    }
}

package com.jingyu.test.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.jingyu.middle.base.BaseActivity;
import com.jingyu.test.R;
import com.jingyu.utils.function.Logger;

public class MyServiceActivity extends BaseActivity implements View.OnClickListener {

    private Button startService;
    private Button stopService;
    private Button bindService;
    private Button unbindService;

    private MyService.MyServiceBinder myServiceBinder;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myServiceBinder = (MyService.MyServiceBinder) service;
            Logger.i(this + "--onServiceConnected--" + myServiceBinder.getInfo());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Logger.i(this + "--onServiceDisconnected--" + name);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myservice);
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
                MyService.actionStart(getActivity());
                break;
            case R.id.stopService:
                MyService.actionStop(getActivity());
                break;
            case R.id.bindService:
                bindService(new Intent(this, MyService.class), serviceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.unbindService:
                try {
//                    if (myServiceBinder != null && !myServiceBinder.isServiceDestroy()) {
                    unbindService(serviceConnection);
//                    } else {
//                        Logger.longToast("服务未创建 或 服务已经销毁了 ,拦截了unbindService()方法的调用");
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.longToast("服务未创建 或 服务已经销毁了 ,调用unbindService() crash了");
                }
                break;
        }
    }

    public static void actionStart(FragmentActivity activity) {
        activity.startActivity(new Intent(activity, MyServiceActivity.class));
    }
}

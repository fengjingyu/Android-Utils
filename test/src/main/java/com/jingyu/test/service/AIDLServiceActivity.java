package com.jingyu.test.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.jingyu.middle.base.BaseActivity;
import com.jingyu.test.R;
import com.jingyu.utils.function.Logger;

public class AIDLServiceActivity extends BaseActivity implements View.OnClickListener {

    private Button startService;
    private Button stopService;
    private Button bindService;
    private Button unbindService;

    private IRemoteService remoteService;

    private boolean mBound;

    private final AIDLCallBack aidlCallBack = new AIDLCallBack.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String onListener() throws RemoteException {
            return "onListener";
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            remoteService = IRemoteService.Stub.asInterface(service);

            try {
                Logger.i(this + "--onServiceConnected--" + remoteService.getInfo());
                Logger.i(this + "--onServiceConnected--" + remoteService.getNames().toString());
                AIDLBean aidlBean = new AIDLBean();
                aidlBean.setAddress("gz");
                aidlBean.setAge(20);
                aidlBean.setName("测试");
                Logger.i(this + "--onServiceConnected--" + remoteService.getAIDLBean(aidlBean));
                Logger.i(this + "--onServiceConnected--" + remoteService.registerCallBack(aidlCallBack));

            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            remoteService = null;
            Logger.i(this + "--onServiceDisconnected--" + name);
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_service);
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
                AIDLService.actionStart(getActivity());
                break;
            case R.id.stopService:
                AIDLService.actionStop(getActivity());
                break;
            case R.id.bindService:
                bindService(new Intent(this, AIDLService.class), serviceConnection, BIND_AUTO_CREATE);
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

    public static void actionStart(FragmentActivity activity) {
        activity.startActivity(new Intent(activity, AIDLServiceActivity.class));
    }
}

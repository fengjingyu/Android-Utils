package com.jingyu.android.aidl.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import com.jingyu.android.aidl.R;
import com.jingyu.android.common.log.Logger;
import com.jingyu.android.middle.base.BaseActivity;
public class AIDLServiceActivity extends BaseActivity implements View.OnClickListener {

    private Button startService;
    private Button stopService;
    private Button bindService;
    private Button unbindService;
    private Button repeatCall;

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
            Logger.d(this + "--onServiceConnected--" + name);
            remoteService = IRemoteService.Stub.asInterface(service);
            mBound = true;
            call();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Logger.d(this + "--onServiceDisconnected--" + name);
            remoteService = null;
            mBound = false;
        }
    };

    private void call() {
        if (remoteService == null) {
            Logger.d("remoteService为null");
            return;
        }
        try {
            Logger.d(remoteService.getInfo());
            Logger.d(remoteService.getNames());

            AIDLBean in = new AIDLBean();
            in.setName("client_in");
            in.setId(1);
            Logger.d("getBeanIn(),in修饰符,客户端传入" + in);
            AIDLBean beanIn = remoteService.getBeanIn(in);
            Logger.d("getBeanIn(),in修饰符,客户端收到返回" + beanIn);

            AIDLBean out = new AIDLBean();
            out.setName("client_out");
            out.setId(1);
            Logger.d("getBeanOut(),out修饰符,客户端传入" + out);
            AIDLBean beanOut = remoteService.getBeanOut(out);
            Logger.d("getBeanOut(),out修饰符,客户端收到返回" + beanOut);

            AIDLBean inout = new AIDLBean();
            inout.setName("client_inout");
            inout.setId(1);
            Logger.d("getBeanInOut(),inout修饰符,客户端传入" + inout);
            AIDLBean beanInOut = remoteService.getBeanInOut(inout);
            Logger.d("getBeanInOut(),inout修饰符,客户端收到返回" + beanInOut);

            AIDLBean in2 = new AIDLBean();
            in2.setName("client_in2");
            in2.setId(1);
            Logger.d("getBeanIn2(),in修饰符,客户端传入" + in2);
            remoteService.getBeanIn2(in2);
            Logger.d("getBeanIn2(),in修饰符,此时客户端的" + in2);

            AIDLBean out2 = new AIDLBean();
            out2.setName("client_out2");
            out2.setId(1);
            Logger.d("getBeanOut2(),out修饰符,客户端传入" + out2);
            remoteService.getBeanOut2(out2);
            Logger.d("getBeanOut2(),out修饰符,此时客户端的" + out2);

            AIDLBean inout2 = new AIDLBean();
            inout2.setName("client_inout2");
            inout2.setId(1);
            Logger.d("getBeanInOut2(),inout修饰符,客户端传入" + inout2);
            remoteService.getBeanInOut2(inout2);
            Logger.d("getBeanInOut2(),inout修饰符,此时客户端的" + inout2);


            Logger.d(remoteService.registerCallBack(aidlCallBack));

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

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
        repeatCall = getViewById(R.id.repeatCall);
    }

    public void setListener() {
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
        repeatCall.setOnClickListener(this);
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
            case R.id.repeatCall:
                call();
                break;
        }
    }

    public static void actionStart(Activity activity) {
        activity.startActivity(new Intent(activity, AIDLServiceActivity.class));
    }
}

package com.jingyu.test.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;

import com.jingyu.utils.function.Logger;
import com.jingyu.utils.util.UtilSystem;

import java.util.ArrayList;
import java.util.List;

public class AIDLService extends Service {

    private List<String> list = new ArrayList<>();

    private final IRemoteService.Stub aidlBinder = new IRemoteService.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String getInfo() throws RemoteException {
            StringBuilder sb = new StringBuilder();
            sb.append(this.getClass().getName());
            sb.append(", 线程名=" + Thread.currentThread().getName());
            sb.append(", pid = " + Process.myPid());
            sb.append(", 进程名= " + UtilSystem.getProcessName(getApplicationContext()));
            return sb.toString();
        }

        @Override
        public List<String> getNames() throws RemoteException {
            return list;
        }

        @Override
        public AIDLBean getAIDLBean(AIDLBean bean) throws RemoteException {
            //bean.setAddress("china");
            // return bean;
            AIDLBean aidlBean = new AIDLBean();
            aidlBean.setAddress("aidl_" + bean.getName());
            return aidlBean;
        }

        @Override
        public String registerCallBack(AIDLCallBack callBack) throws RemoteException {
            if (callBack != null) {
                return callBack.onListener();
            }
            return null;
        }
    };

    @Override
    public void onCreate() {
        Logger.i(this + "--onCreate()");
        super.onCreate();

        list.add("a");
        list.add("b");
        list.add("c");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.i(this + "--onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Logger.i(this + "--onBind()");
        return aidlBinder;
    }

    @Override
    public void onDestroy() {
        Logger.i(this + "--onDestroy()");
        super.onDestroy();
    }

    public static void actionStart(Context context) {
        context.startService(new Intent(context, AIDLService.class));
    }

    public static void actionStop(Context context) {
        context.stopService(new Intent(context, AIDLService.class));
    }
}

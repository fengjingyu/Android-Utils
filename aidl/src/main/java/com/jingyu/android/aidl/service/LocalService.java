package com.jingyu.android.aidl.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import com.jingyu.android.common.log.Logger;
import com.jingyu.android.common.util.SystemUtil;

public class LocalService extends Service {

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {

        LocalService getService() {
            return LocalService.this;
        }

    }

    @Override
    public void onCreate() {
        Logger.d(this + "--onCreate()");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d(this + "--onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Logger.d(this + "--onBind()");
        return mBinder;
    }

    @Override
    public void onDestroy() {
        Logger.d(this + "--onDestroy()");
        super.onDestroy();
    }

    public static void actionStart(Context context) {
        context.startService(new Intent(context, LocalService.class));
    }

    public static void actionStop(Context context) {
        context.stopService(new Intent(context, LocalService.class));
    }

    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName());
        sb.append(", 线程名=" + Thread.currentThread().getName());
        sb.append(", pid = " + Process.myPid());
        sb.append(", 进程名= " + SystemUtil.getProcessName(getApplicationContext()));
        return sb.toString();
    }

}

package com.jingyu.test.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;

import com.jingyu.utils.function.Logger;
import com.jingyu.utils.util.UtilSystem;

public class LocalService extends Service {

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {

        LocalService getService() {
            return LocalService.this;
        }

    }

    @Override
    public void onCreate() {
        Logger.i(this + "--onCreate()");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.i(this + "--onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Logger.i(this + "--onBind()");
        return mBinder;
    }

    @Override
    public void onDestroy() {
        Logger.i(this + "--onDestroy()");
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
        sb.append(", 进程名= " + UtilSystem.getProcessName(getApplicationContext()));
        return sb.toString();
    }

}

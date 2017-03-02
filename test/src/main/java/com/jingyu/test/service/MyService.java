package com.jingyu.test.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;

import com.jingyu.utils.function.Logger;
import com.jingyu.utils.util.UtilSystem;

public class MyService extends Service {

    private boolean isDestroy;
    private MyServiceBinder serviceBinder = new MyServiceBinder();

    public class MyServiceBinder extends Binder {

        String getInfo() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.getClass().getName());
            sb.append(", 线程名=" + Thread.currentThread().getName());
            sb.append(", pid = " + Process.myPid());
            sb.append(", 进程名= " + UtilSystem.getProcessName(getApplicationContext()));
            return sb.toString();
        }

        MyService getService() {
            return MyService.this;
        }

        boolean isServiceDestroy() {
            return isDestroy;
        }
    }

    @Override
    public void onCreate() {
        isDestroy = false;
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
        return serviceBinder;
    }

    @Override
    public void onDestroy() {
        isDestroy = true;
        Logger.i(this + "--onDestroy()");
        super.onDestroy();
    }

    public static void actionStart(Context context) {
        context.startService(new Intent(context, MyService.class));
    }

    public static void actionStop(Context context) {
        context.stopService(new Intent(context, MyService.class));
    }
}

package com.jingyu.test.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;

import com.jingyu.utils.util.UtilSystem;

public class NewProcessService extends Service {

    private NewProcessService.NewProcessServiceBinder newProcessServiceBinder = new NewProcessServiceBinder();

    public class NewProcessServiceBinder extends Binder {

        String getInfo() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.getClass().getName());
            sb.append(", 线程名=" + Thread.currentThread().getName());
            sb.append(", pid = " + Process.myPid());
            sb.append(", 进程名= " + UtilSystem.getProcessName(getApplicationContext()));
            return sb.toString();
        }

        NewProcessService getNewProcessService() {
            return NewProcessService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return newProcessServiceBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static void actionStart(Context context) {
        context.startService(new Intent(context, NewProcessService.class));
    }

    public static void actionStop(Context context) {
        context.stopService(new Intent(context, NewProcessService.class));
    }
}

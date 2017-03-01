package com.jingyu.test.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.jingyu.utils.function.Logger;

public class MyService extends Service {

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
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static void actionStart(Context context) {
        context.startService(new Intent(context, MyService.class));
    }
}

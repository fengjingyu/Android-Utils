package com.jingyu.test;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.test.material.PercentLayoutActivity;

import java.io.File;

public class NotifyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        getViewById(R.id.notify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification();
            }
        });
    }

    int id = 0;
    int content = 0;


    private void notification() {
        Intent intent;
        if (content % 2 == 0)
            intent = new Intent(getActivity(), NotifyActivity.class);
        else
            intent = new Intent(getActivity(), PercentLayoutActivity.class);

        content++;
        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle("测试标题" + content)
                .setContentText("测试文本" + content)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true) // 或者 cancel(id)
                .setContentIntent(PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setSound(Uri.fromFile(new File(("/system/media/audio/ringtones/World.ogg"))))
                .setLights(Color.BLUE,1000,1000)// led
                .setPriority(NotificationCompat.PRIORITY_MAX)//原生的有效果,小米5没效果需要去设置页面的悬浮通知
                .setTicker("ticker")
                .build();

        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(id, notification);

    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, NotifyActivity.class));
    }
}

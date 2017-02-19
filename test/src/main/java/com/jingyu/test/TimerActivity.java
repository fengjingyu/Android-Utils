package com.jingyu.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;

import com.jingyu.middle.base.BaseActivity;
import com.jingyu.utils.function.Logger;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author fengjingyu@foxmail.com
 * @description 几种计时方式
 */
public class TimerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        countDownTimer();
        timerTask();
        scheduledExecutorService();
    }

    //---------------------CountDownTimer-------------------
    int count = 0;

    private void countDownTimer() {
        // 这个是在ui线程里回调
        CountDownTimer timer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Logger.temp(count++);
            }

            @Override
            public void onFinish() {
                Logger.temp(count + "--onEnd");
            }
        };
        timer.start();
    }

    //------------------------TimerTask-----------------------
    Timer timer;

    private void timerTask() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            int index = 0;

            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        index = index + 1;
                        Logger.shortToast(index);
                    }
                });
            }
        };
        timer.schedule(task, 1000, 3000);
    }

    //----------------------ScheduledExecutorService--------------
    ScheduledExecutorService scheduled;

    private void scheduledExecutorService() {
        scheduled = Executors.newScheduledThreadPool(3);
        scheduled.schedule(new Runnable() {
            @Override
            public void run() {
                Logger.i("5秒后执行一次");
            }
        }, 5, TimeUnit.SECONDS);

        scheduled.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Logger.i("2秒后开始执行，每隔6秒执行一次");
            }
        }, 2, 6, TimeUnit.SECONDS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer.purge();
        scheduled.shutdown();
    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, TimerActivity.class));
    }

}

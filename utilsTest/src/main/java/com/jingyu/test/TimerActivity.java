package com.jingyu.test;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;

import com.jingyu.utils.function.Constants;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.test_middle.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public class TimerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timer();
        timer2();
        timer3();

    }


    int count = 0;

    private void timer() {

        CountDownTimer timer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Logger.i(count++);
            }

            @Override
            public void onFinish() {
                Logger.i(Constants.TAG_TEST, count + "--onEnd");
            }
        };

        timer.start();

    }


    Timer timer;

    private void timer2() {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer.purge();
        scheduled.shutdown();
    }

    ScheduledExecutorService scheduled;

    private void timer3() {
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


}

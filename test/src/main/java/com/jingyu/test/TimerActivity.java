package com.jingyu.test;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.utils.function.Logger;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author fengjingyu@foxmail.com
 *  几种计时方式
 */
public class TimerActivity extends BaseActivity implements View.OnClickListener {
    private Button countTimer;
    private Button timerTask;
    private Button scheduledExecutorService;
    private Button alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        countTimer = getViewById(R.id.countTimer);
        timerTask = getViewById(R.id.timerTask);
        scheduledExecutorService = getViewById(R.id.scheduledExecutorService);
        alarm = getViewById(R.id.alarm);

        countTimer.setOnClickListener(this);
        timerTask.setOnClickListener(this);
        scheduledExecutorService.setOnClickListener(this);
        alarm.setOnClickListener(this);
    }


    //---------------------CountDownTimer-------------------
    private void countDownTimer() {

        // 这个是在ui线程里回调
        final CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Logger.d(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Logger.d("countDownTimer--onEnd");
            }
        };
        countDownTimer.start();
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
                Logger.d("5秒后执行一次");
            }
        }, 5, TimeUnit.SECONDS);

        scheduled.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Logger.d("2秒后开始执行，每隔6秒执行一次");
            }
        }, 2, 6, TimeUnit.SECONDS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (timer != null) {
            timer.cancel();
            timer.purge();
        }

        if (scheduled != null) {
            scheduled.shutdown();
        }
    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, TimerActivity.class));
    }

    public void alarm() {
        AlarmManager systemService = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, new Intent(getActivity(), RequestPermissionActivity.class), 0);
        long triggerAtTime = SystemClock.elapsedRealtime() + 10 * 1000;
        systemService.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);

//        long triggerAtTime = System.currentTimeMillis() + 10 * 1000;
//        systemService.set(AlarmManager.RTC_WAKEUP, triggerAtTime, pendingIntent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.countTimer:
                countDownTimer();
                break;
            case R.id.timerTask:
                timer.cancel();
                timerTask();
                break;
            case R.id.scheduledExecutorService:
                scheduled.shutdown();
                scheduledExecutorService();
                break;
            case R.id.alarm:
                alarm();
                break;
            default:
                break;
        }
    }
}

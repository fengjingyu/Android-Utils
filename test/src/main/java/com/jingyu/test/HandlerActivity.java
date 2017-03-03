package com.jingyu.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jingyu.middle.base.BaseActivity;
import com.jingyu.utils.function.Logger;

/**
 * @author fengjingyu@foxmail.com
 */
public class HandlerActivity extends BaseActivity {
    private Button button1;
    private Button button2;
    private Button button3;
    private TextView content;
    private LooperThread looperThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        button1 = getViewById(R.id.button1);
        button2 = getViewById(R.id.button2);
        button3 = getViewById(R.id.button3);
        content = getViewById(R.id.content);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread() {
                    @Override
                    public void run() {
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                content.setText("button1 update");
                            }
                        });
                    }
                }.start();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                content.setText("button2 update");
                            }
                        });
                    }
                }.start();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                looperThread = new LooperThread();
                looperThread.start();
                looperThread.getHandler().sendEmptyMessage(1);
            }
        });
    }

    class LooperThread extends Thread {
        private Handler mHandler;
        private final Object mLock = new Object();

        public void run() {
            Looper.prepare();// 线程和looper关联
            synchronized (mLock) {
                // 这个handler默认是与当前的线程的looper关联
                mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        Logger.i(msg.what + this.toString());
                        // content.setText("button3 update");// 会crash，ui只能在主线程里更新，这个mHandler不是主线程的
                    }
                };
                mLock.notifyAll();
            }
            // 如果不调用loop开始循环，则不会执行消息队列中的消息
            Looper.loop();
        }

        public Handler getHandler() {
            synchronized (mLock) {
                if (mHandler == null) {
                    try {
                        mLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return mHandler;
            }
        }

        public void exit() {
            getHandler().post(new Runnable() {
                public void run() {
                    Looper.myLooper().quit();
                }
            });
        }
    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, HandlerActivity.class));
    }
}

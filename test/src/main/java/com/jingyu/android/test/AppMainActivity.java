package com.jingyu.android.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.android.test.learn.ContactsActivity;
import com.jingyu.android.test.learn.DestroyGCActivity;
import com.jingyu.android.test.learn.HandlerActivity;
import com.jingyu.android.test.learn.JsonActivity;
import com.jingyu.android.test.learn.NotifyActivity;
import com.jingyu.android.test.learn.PermissionActivity;
import com.jingyu.android.test.learn.TimerActivity;
import com.jingyu.android.test.learn.fragmentlife.FragmentLifeActivity;
import com.jingyu.android.test.utils.AdapterActivity;
import com.jingyu.android.test.utils.BitmapActivity;
import com.jingyu.android.test.utils.CacheCleanActivity;
import com.jingyu.android.test.utils.CamareActivity;
import com.jingyu.android.test.utils.CloneActivity;
import com.jingyu.android.test.utils.DirActivity;
import com.jingyu.android.test.utils.DownloadActivity;
import com.jingyu.android.test.utils.EncryptActivity;
import com.jingyu.android.test.utils.ExceptionActivity;
import com.jingyu.android.test.utils.HttpActivity;
import com.jingyu.android.test.utils.LogActivity;
import com.jingyu.android.test.utils.SPActivity;
import com.jingyu.android.test.utils.TaskActivity0;
import com.jingyu.utils.exception.CrashHandler;
import com.jingyu.utils.exception.ExceptionDb;
import com.jingyu.utils.function.ActivityCollector;
import com.jingyu.utils.function.Logger;


public class AppMainActivity extends BaseActivity implements View.OnClickListener {

    public static final int CLICK_QUIT_INTERVAL = 1000;
    private long lastClickQuitTime;

    //scrollview1
    private Button timer;
    private Button destroygc;
    private Button handler;
    private Button fragmentLife;
    private Button runPermission;
    private Button contacts;
    private Button notification;
    private Button json;

    //scrollview2
    private Button log;
    private Button exception;
    private Button sp;
    private Button encrypt;
    private Button activity_collector;
    private Button http;
    private Button clone;
    private Button camare;
    private Button cache;
    private Button adapter;
    private Button dir;
    private Button bitmap;
    private Button download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);

        initWidget();
        setListener();

        initWidget2();
        setListener2();

    }

    private void setListener() {
        timer.setOnClickListener(this);
        destroygc.setOnClickListener(this);
        handler.setOnClickListener(this);
        fragmentLife.setOnClickListener(this);
        runPermission.setOnClickListener(this);
        contacts.setOnClickListener(this);
        notification.setOnClickListener(this);
        json.setOnClickListener(this);
    }

    private void initWidget() {
        timer = getViewById(R.id.timer);
        destroygc = getViewById(R.id.destroygc);
        handler = getViewById(R.id.handler);
        fragmentLife = getViewById(R.id.fragmentLife);
        runPermission = getViewById(R.id.runPermission);
        contacts = getViewById(R.id.contacts);
        notification = getViewById(R.id.notification);
        json = getViewById(R.id.json);
    }

    private void onClickAction(View v) {
        switch (v.getId()) {
            case R.id.timer:
                TimerActivity.actionStart(getActivity());
                break;
            case R.id.destroygc:
                DestroyGCActivity.actionStart(getActivity());
                break;
            case R.id.handler:
                HandlerActivity.actionStart(getActivity());
                break;
            case R.id.fragmentLife:
                FragmentLifeActivity.actionStart(getActivity());
                break;
            case R.id.runPermission:
                PermissionActivity.actionStart(getActivity());
                break;
            case R.id.contacts:
                ContactsActivity.actionStart(getActivity());
                break;
            case R.id.notification:
                NotifyActivity.actionStart(getActivity());
                break;
            case R.id.json:
                JsonActivity.actionStart(getActivity());
                break;
            default:
                break;
        }
    }

    private void setListener2() {
        log.setOnClickListener(this);
        exception.setOnClickListener(this);
        sp.setOnClickListener(this);
        encrypt.setOnClickListener(this);
        activity_collector.setOnClickListener(this);
        http.setOnClickListener(this);
        clone.setOnClickListener(this);
        camare.setOnClickListener(this);
        cache.setOnClickListener(this);
        adapter.setOnClickListener(this);
        dir.setOnClickListener(this);
        bitmap.setOnClickListener(this);
        download.setOnClickListener(this);
    }

    private void initWidget2() {
        log = getViewById(R.id.log);
        exception = getViewById(R.id.exception);
        sp = getViewById(R.id.sp);
        encrypt = getViewById(R.id.encrypt);
        activity_collector = getViewById(R.id.activity_collector);
        http = getViewById(R.id.http);
        clone = getViewById(R.id.clone);
        camare = getViewById(R.id.camare);
        cache = getViewById(R.id.cache);
        adapter = getViewById(R.id.adapter);
        dir = getViewById(R.id.dir);
        bitmap = getViewById(R.id.bitmap);
        download = getViewById(R.id.download);
    }

    private void onClickAction2(View v) {
        switch (v.getId()) {
            case R.id.log:
                LogActivity.actionStart(getActivity());
                break;
            case R.id.exception:
                ExceptionActivity.actionStart(getActivity());
                break;
            case R.id.sp:
                SPActivity.actionStart(getActivity());
                break;
            case R.id.encrypt:
                EncryptActivity.actionStart(getActivity());
                break;
            case R.id.activity_collector:
                TaskActivity0.actionStart(getActivity());
                break;
            case R.id.http:
                HttpActivity.actionStart(getActivity());
                break;
            case R.id.clone:
                CloneActivity.actionStart(getActivity());
                break;
            case R.id.camare:
                CamareActivity.actionStart(getActivity());
                break;
            case R.id.cache:
                CacheCleanActivity.actionStart(getActivity());
                break;
            case R.id.adapter:
                AdapterActivity.actionStart(getActivity());
                break;
            case R.id.dir:
                DirActivity.actionStart(getActivity());
                break;
            case R.id.bitmap:
                BitmapActivity.actionStart(getActivity());
                break;
            case R.id.download:
                DownloadActivity.actionStart(getActivity());
                break;
            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        onClickAction(v);
        onClickAction2(v);
    }

    @Override
    public void onBackPressed() {
        long quitTime = System.currentTimeMillis();
        if (quitTime - lastClickQuitTime <= CLICK_QUIT_INTERVAL) {
            ActivityCollector.appExit();
        } else {
            lastClickQuitTime = quitTime;
            Logger.shortToast("快速再按一次退出");
        }
    }

    /**
     * 进入首页时，把未上传的异常信息上传到服务器
     */
    protected void uploadException() {
        ExceptionDb exceptionModelDb = CrashHandler.getInstance().getExceptionDb();
        if (exceptionModelDb != null) {
            Logger.d(exceptionModelDb.queryCount());
            Logger.d(exceptionModelDb.queryUploadFail(ExceptionDb.SORT_DESC));
        }
    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, AppMainActivity.class));
    }
}

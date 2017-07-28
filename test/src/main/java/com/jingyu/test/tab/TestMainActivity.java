package com.jingyu.test.tab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.test.ContactsActivity;
import com.jingyu.test.DestroyGCActivity;
import com.jingyu.test.HandlerActivity;
import com.jingyu.test.LeakCanaryActivity;
import com.jingyu.test.NotifyActivity;
import com.jingyu.test.PropertyAnimActivity;
import com.jingyu.test.R;
import com.jingyu.test.RequestPermissionActivity;
import com.jingyu.test.SystemDialogActivity;
import com.jingyu.test.TimerActivity;
import com.jingyu.test.download.DownloadActivity;
import com.jingyu.test.fragmentlife.FragmentLifeActivity;
import com.jingyu.test.material.MaterialActivity;
import com.jingyu.test.material.PercentLayoutActivity;
import com.jingyu.test.service.AIDLServiceActivity;
import com.jingyu.test.service.LocalServiceActivity;
import com.jingyu.test.utilstest.AdapterActivity;
import com.jingyu.test.utilstest.BitmapActivity;
import com.jingyu.test.utilstest.CacheCleanActivity;
import com.jingyu.test.utilstest.CamareActivity;
import com.jingyu.test.utilstest.CloneActivity;
import com.jingyu.test.utilstest.DirActivity;
import com.jingyu.test.utilstest.EncryptActivity;
import com.jingyu.test.utilstest.ExceptionActivity;
import com.jingyu.test.utilstest.HttpActivity;
import com.jingyu.test.utilstest.LogActivity;
import com.jingyu.test.utilstest.SPActivity;
import com.jingyu.test.utilstest.TaskActivity0;
import com.jingyu.utils.exception.CrashHandler;
import com.jingyu.utils.exception.ExceptionDb;
import com.jingyu.utils.function.ActivityCollector;
import com.jingyu.utils.function.Logger;

public class TestMainActivity extends BaseActivity implements View.OnClickListener {

    public static final int CLICK_QUIT_INTERVAL = 1000;
    private long lastClickQuitTime;

    //scrollview1
    private Button propertyAnim;
    private Button leakCanary;
    private Button timer;
    private Button systemDialog;
    private Button destroygc;
    private Button handler;
    private Button fragmentLife;
    private Button runPermission;
    private Button contacts;
    private Button notification;
    private Button service;
    private Button aidl;
    private Button download;

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

    //scrollview3
    private Button material;
    private Button percentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();
        setListener();

        initWidget2();
        setListener2();

        initWidget3();
        setListener3();
    }

    private void setListener() {
        propertyAnim.setOnClickListener(this);
        leakCanary.setOnClickListener(this);
        timer.setOnClickListener(this);
        systemDialog.setOnClickListener(this);
        destroygc.setOnClickListener(this);
        handler.setOnClickListener(this);
        fragmentLife.setOnClickListener(this);
        runPermission.setOnClickListener(this);
        contacts.setOnClickListener(this);
        notification.setOnClickListener(this);
        service.setOnClickListener(this);
        aidl.setOnClickListener(this);
        download.setOnClickListener(this);
    }

    private void initWidget() {
        propertyAnim = getViewById(R.id.propertyAnim);
        leakCanary = getViewById(R.id.leakCanary);
        timer = getViewById(R.id.timer);
        systemDialog = getViewById(R.id.systemDialog);
        destroygc = getViewById(R.id.destroygc);
        handler = getViewById(R.id.handler);
        fragmentLife = getViewById(R.id.fragmentLife);
        runPermission = getViewById(R.id.runPermission);
        contacts = getViewById(R.id.contacts);
        notification = getViewById(R.id.notification);
        service = getViewById(R.id.service);
        aidl = getViewById(R.id.aidl);
        download = getViewById(R.id.download);
    }

    private void onClickAction(View v) {
        switch (v.getId()) {
            case R.id.propertyAnim:
                PropertyAnimActivity.actionStart(getActivity());
                break;
            case R.id.leakCanary:
                LeakCanaryActivity.actionStart(getActivity());
                break;
            case R.id.timer:
                TimerActivity.actionStart(getActivity());
                break;
            case R.id.systemDialog:
                SystemDialogActivity.actionStart(getActivity());
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
                RequestPermissionActivity.actionStart(getActivity());
                break;
            case R.id.contacts:
                ContactsActivity.actionStart(getActivity());
                break;
            case R.id.notification:
                NotifyActivity.actionStart(getActivity());
                break;
            case R.id.service:
                LocalServiceActivity.actionStart(getActivity());
                break;
            case R.id.aidl:
                AIDLServiceActivity.actionStart(getActivity());
                break;
            case R.id.download:
                DownloadActivity.actionStart(getActivity());
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
            default:
                break;
        }
    }

    private void setListener3() {
        material.setOnClickListener(this);
        percentLayout.setOnClickListener(this);
    }

    public void initWidget3() {
        material = getViewById(R.id.material);
        percentLayout = getViewById(R.id.percentLayout);
    }

    private void onClickAction3(View v) {
        switch (v.getId()) {
            case R.id.material:
                MaterialActivity.actionStart(getActivity());
                break;
            case R.id.percentLayout:
                PercentLayoutActivity.actionStart(getActivity());
                break;
            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        onClickAction(v);
        onClickAction2(v);
        onClickAction3(v);
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
        activityContext.startActivity(new Intent(activityContext, TestMainActivity.class));
    }
}

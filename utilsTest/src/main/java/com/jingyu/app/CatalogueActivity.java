package com.jingyu.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jingyu.middle.base.BaseActivity;
import com.jingyu.app.recyclerview.RecycleViewActivity;
import com.jingyu.app.recyclerview.WaterFallActivity;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utilstest.CamareActivity;
import com.jingyu.utilstest.ClearCacheActivity;
import com.jingyu.utilstest.ContactsActivity;
import com.jingyu.utilstest.ExceptionActivity;
import com.jingyu.utilstest.R;
import com.jingyu.utilstest.AdapterActivity;
import com.jingyu.utilstest.TaskActivity1;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public class CatalogueActivity extends BaseActivity implements View.OnClickListener {

    private Button timer;
    private Button clearcache;
    private Button contacts;
    private Button destroygc;
    private Button exception;
    private Button photo;
    private Button seachRecoder;
    private Button simpleadapter;
    private Button recyclerView;
    private Button recycleView_waterfall;
    private Button leakcanary;
    private Button propertyAnim;
    private Button handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);

        initWidgets();
        setListeners();
    }

    public void initWidgets() {
        timer = getViewById(R.id.timer);
        clearcache = getViewById(R.id.clearcache);
        contacts = getViewById(R.id.contacts);
        destroygc = getViewById(R.id.destroygc);
        exception = getViewById(R.id.exception);
        photo = getViewById(R.id.photo);
        seachRecoder = getViewById(R.id.seachRecoder);
        simpleadapter = getViewById(R.id.simpleadapter);
        recyclerView = getViewById(R.id.recyclerView);
        recycleView_waterfall = getViewById(R.id.recycleView_waterfall);
        leakcanary = getViewById(R.id.leakcanary);
        propertyAnim = getViewById(R.id.propertyAnim);
        handler = getViewById(R.id.handler);
    }

    public void setListeners() {
        timer.setOnClickListener(this);
        clearcache.setOnClickListener(this);
        contacts.setOnClickListener(this);
        destroygc.setOnClickListener(this);
        exception.setOnClickListener(this);
        photo.setOnClickListener(this);
        seachRecoder.setOnClickListener(this);
        simpleadapter.setOnClickListener(this);
        recyclerView.setOnClickListener(this);
        recycleView_waterfall.setOnClickListener(this);
        leakcanary.setOnClickListener(this);
        propertyAnim.setOnClickListener(this);
        handler.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.timer:
                intent = new Intent(this, TimerActivity.class);
                break;
            case R.id.clearcache:
                intent = new Intent(this, ClearCacheActivity.class);
                break;
            case R.id.contacts:
                intent = new Intent(this, ContactsActivity.class);
                break;
            case R.id.destroygc:
                intent = new Intent(this, DestroyGCActivity.class);
                break;
            case R.id.exception:
                intent = new Intent(this, ExceptionActivity.class);
                break;

            case R.id.photo:
                intent = new Intent(this, CamareActivity.class);
                break;
            case R.id.seachRecoder:
                intent = new Intent(this, TaskActivity1.class);
                break;
            case R.id.simpleadapter:
                intent = new Intent(this, AdapterActivity.class);
                break;
            case R.id.recyclerView:
                intent = new Intent(this, RecycleViewActivity.class);
                break;
            case R.id.recycleView_waterfall:
                intent = new Intent(this, WaterFallActivity.class);
                break;
            case R.id.leakcanary:
                intent = new Intent(this, LeakCanaryActivity.class);
                break;
            case R.id.propertyAnim:
                intent = new Intent(this, PropertyAnimActivity.class);
                break;
            case R.id.handler:
                intent = new Intent(this, HandlerActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.i(this, "---onNewIntent");
    }

    public static void actionStart(Activity activity) {
        activity.startActivity(new Intent(activity, CatalogueActivity.class));
    }
}

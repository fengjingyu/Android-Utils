package com.jingyu.test.maintab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jingyu.middle.base.BaseFragment;
import com.jingyu.test.R;
import com.jingyu.utilstest.AdapterActivity;
import com.jingyu.utilstest.CacheCleanActivity;
import com.jingyu.utilstest.CamareActivity;
import com.jingyu.utilstest.CloneActivity;
import com.jingyu.utilstest.EncryptActivity;
import com.jingyu.utilstest.ExceptionActivity;
import com.jingyu.utilstest.HttpActivity;
import com.jingyu.utilstest.LogActivity;
import com.jingyu.utilstest.SPActivity;
import com.jingyu.utilstest.TaskActivity0;

public class TwoFragment extends BaseFragment implements View.OnClickListener {

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, R.layout.tab_fragment_two, container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWidget();
        setListeners();
    }

    private void setListeners() {
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
    }

    private void initWidget() {
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
    }


    @Override
    public void onClick(View v) {
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
            default:
                break;
        }
    }
}
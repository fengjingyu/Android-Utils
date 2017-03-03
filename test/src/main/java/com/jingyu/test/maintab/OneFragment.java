package com.jingyu.test.maintab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jingyu.middle.base.BaseFragment;
import com.jingyu.test.ContactsActivity;
import com.jingyu.test.DestroyGCActivity;
import com.jingyu.test.NotifyActivity;
import com.jingyu.test.RequestPermissionActivity;
import com.jingyu.test.fragmentlife.FragmentLifeActivity;
import com.jingyu.test.HandlerActivity;
import com.jingyu.test.LeakCanaryActivity;
import com.jingyu.test.PercentLayoutActivity;
import com.jingyu.test.PropertyAnimActivity;
import com.jingyu.test.R;
import com.jingyu.test.SystemDialogActivity;
import com.jingyu.test.TimerActivity;
import com.jingyu.test.service.LocalServiceActivity;
import com.jingyu.test.service.AIDLServiceActivity;

public class OneFragment extends BaseFragment implements View.OnClickListener {

    private Button percentLayout;
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
    private Button service_process;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, R.layout.tab_fragment_one, container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWidget();
        setListener();
    }

    private void setListener() {
        percentLayout.setOnClickListener(this);
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
        service_process.setOnClickListener(this);
    }

    private void initWidget() {
        percentLayout = getViewById(R.id.percentLayout);
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
        service_process = getViewById(R.id.service_process);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.percentLayout:
                PercentLayoutActivity.actionStart(getActivity());
                break;
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
            case R.id.service_process:
                AIDLServiceActivity.actionStart(getActivity());
                break;
            default:
                break;

        }
    }
}
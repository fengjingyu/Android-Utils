package com.jingyu.android.middle.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jingyu.android.common.activity.PlusActivity;
import com.jingyu.android.common.log.Logger;
import com.jingyu.android.common.util.BroadcastUtil;
import com.jingyu.android.init.R;
import com.jingyu.android.middle.AppHttp;
import com.jingyu.android.middle.config.okhttp.MyInterceptor;
import com.jingyu.android.middle.config.okhttp.req.MyReqInfo;
import com.jingyu.android.middle.config.okhttp.resp.MyRespHandler;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public abstract class BaseActivity extends PlusActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.d(this + "---onCreate");
        super.onCreate(savedInstanceState);
        hiddenTitleActionBar();
        initReceiver();
    }

    public void hiddenTitleActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
    }

    /**
     * 是否有网络的回调，可能统一处理应用对网络转换的逻辑
     */
    private BroadcastReceiver mNetReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();

            boolean hasConnectivity = (info != null && info.isConnected());

            if (hasConnectivity) {
                //Logger.dShortToast(getActivity() + "--有网");
                onNetNormal();
            } else {
                //Logger.dShortToast(getActivity() + "--无网");
                onNetLoss();
            }
        }
    };

    protected void onNetNormal() {
    }

    protected void onNetLoss() {
    }

    private void initReceiver() {
        BroadcastUtil.register(this, 1000, ConnectivityManager.CONNECTIVITY_ACTION, mNetReceiver);
    }

    private void unbindReceiver() {
        BroadcastUtil.unRegister(this, mNetReceiver);
    }

    @Override
    protected void onDestroy() {
        unbindReceiver();
        Logger.d(this + "---onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        Logger.d(this + "---onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Logger.d(this + "---onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Logger.d(this + "---onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Logger.d(this + "---onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Logger.d(this + "---onRestart");
        super.onRestart();
    }

    @Override
    public void finish() {
        Logger.d(this + "---finish");
        super.finish();
    }

    @Override
    public void onBackPressed() {
        Logger.d(this + "---onBackPressed");
        super.onBackPressed();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Logger.d(this + "---onNewIntent");
        super.onNewIntent(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Logger.d(this + "---onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    private View nonet;

    public void netFailChangeBg(String title, final MyReqInfo myReqInfo, final MyRespHandler myRespHandler, final MyInterceptor myInterceptor) {
        if (nonet != null) {
            return;
        }

        nonet = LayoutInflater.from(this).inflate(R.layout.view_no_net, null);

        View back = nonet.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView textView = (TextView) nonet.findViewById(R.id.title);
        if (title != null) {
            textView.setText(title);
        }

        ViewGroup contentParent = (ViewGroup) (getWindow().getDecorView().findViewById(android.R.id.content));

        ViewGroup contentView = (ViewGroup) contentParent.getChildAt(0);

        contentView.setVisibility(View.INVISIBLE);

        contentParent.addView(nonet);

        nonet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppHttp.http(myReqInfo, myRespHandler, myInterceptor);
            }
        });
    }

    public void netSuccessChangeBg() {
        ViewGroup contentParent = (ViewGroup) (getWindow().getDecorView().findViewById(android.R.id.content));
        int count = contentParent.getChildCount();

        if (count > 1) {
            View contentView = contentParent.getChildAt(0);
            if (nonet != null) {
                contentParent.removeView(nonet);
                nonet = null;
            }
            contentView.setVisibility(View.VISIBLE);
        }
    }
}

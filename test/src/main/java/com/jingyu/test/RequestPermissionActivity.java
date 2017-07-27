package com.jingyu.test;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.demo.middle.base.BaseActivity;
import com.jingyu.utils.function.Logger;
import com.jingyu.utils.util.UtilSystem;

public class RequestPermissionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_permission_activity);

        getViewById(R.id.call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean permissionGranted = isPermissionGranted(Manifest.permission.CALL_PHONE);
                if (permissionGranted) {
                    call();
                } else {
                    permissionRequest(new String[]{Manifest.permission.CALL_PHONE}, 1001);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1001:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call();
                } else {
                    Logger.shortToast("拒绝了权限");
                }
                break;
        }
    }

    public void call() {
        startActivity(UtilSystem.getCallIntent("10086"));
    }

    public static void actionStart(FragmentActivity activity) {
        activity.startActivity(new Intent(activity, RequestPermissionActivity.class));
    }
}

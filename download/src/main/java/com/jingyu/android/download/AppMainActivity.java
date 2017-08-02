package com.jingyu.android.download;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jingyu.android.middle.base.BaseActivity;

public class AppMainActivity extends BaseActivity {
    private Button download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);

        download = getViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadActivity.actionStart(getActivity());
            }
        });
    }

    public static void actionStart(Activity activity) {
        activity.startActivity(new Intent(activity, AppMainActivity.class));
    }
}

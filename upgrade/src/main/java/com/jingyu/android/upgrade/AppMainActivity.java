package com.jingyu.android.upgrade;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jingyu.android.middle.Http;
import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.android.middle.http.json.JsonModel;
import com.jingyu.android.middle.http.json.JsonRespHandler;
import com.jingyu.utils.download.DownloadInfo;
import com.jingyu.utils.function.DirHelper;
import com.jingyu.utils.http.ReqInfo;
import com.jingyu.utils.http.RespInfo;

public class AppMainActivity extends BaseActivity {

    public static boolean isVersionOK = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);

        request();
    }

    public void request() {
        Http.get("http://www.baidu.com", null, new JsonRespHandler() {
            @Override
            public JsonModel onParse2Model(ReqInfo reqInfo, RespInfo respInfo) {
                return new JsonModel();
            }

            @Override
            public boolean onMatchAppStatusCode(ReqInfo reqInfo, RespInfo respInfo, JsonModel resultBean) {
                return true;
            }

            @Override
            public void onSuccessAll(ReqInfo reqInfo, RespInfo respInfo, JsonModel resultBean) {
                super.onSuccessAll(reqInfo, respInfo, resultBean);

                DownloadInfo downloadInfo = new DownloadInfo();
                downloadInfo.setUrl("http://192.168.0.102/android/appv2.apk");
                downloadInfo.setFile(DirHelper.ExternalAndroid.getFile(getApplicationContext(), "files/download", "appv2.apk"));
                downloadInfo.setRange(false);
                downloadInfo.setForceUpgrade(false);
                downloadInfo.setContent("修复bug");
                downloadInfo.setTitle("V2.0.0最新版本升级");

                // 加入是不存在升级 或 可选升级
                if (!downloadInfo.isForceUpgrade()) {
                    isVersionOK = true;
                }

                UpgradeActivity.actionStart(getActivity(), downloadInfo);
            }

            @Override
            public void onEnd(ReqInfo reqInfo, RespInfo respInfo) {
                super.onEnd(reqInfo, respInfo);
                if (respInfo.isSuccessAll()) {
                    netSuccessChangeBg();
                } else {
                    netFailChangeBg("app", reqInfo, this, null);
                }
            }
        });
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, AppMainActivity.class);
        activity.startActivity(intent);
    }
}

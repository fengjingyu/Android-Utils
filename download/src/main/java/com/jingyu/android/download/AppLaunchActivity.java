package com.jingyu.android.download;

import com.jingyu.android.init.LaunchActivity;
import com.jingyu.android.middle.Http;
import com.jingyu.android.middle.http.json.JsonModel;
import com.jingyu.android.middle.http.json.JsonRespHandler;
import com.jingyu.utils.function.DirHelper;
import com.jingyu.utils.http.ReqInfo;
import com.jingyu.utils.http.RespInfo;

public class AppLaunchActivity extends LaunchActivity {

    @Override
    protected void action() {
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
                downloadInfo.setFile(DirHelper.ExternalAndroid.getFile(getApplicationContext(), "upgrade", "upgradeapp.apk"));
                downloadInfo.setRangeDownload(false);
                downloadInfo.setForceUpgrade(false);
                downloadInfo.setContent("修复bug");
                downloadInfo.setTitle("V2.0.0最新版本升级");
                UpgradeActivity.actionStart(getActivity(), downloadInfo);
            }
        });
    }
}

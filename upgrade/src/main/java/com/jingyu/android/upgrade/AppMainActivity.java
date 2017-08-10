package com.jingyu.android.upgrade;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jingyu.android.middle.Http;
import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.android.middle.http.json.JsonModel;
import com.jingyu.android.middle.http.json.JsonRespHandler;
import com.jingyu.utils.download.DownloadInfo;
import com.jingyu.utils.encryption.md5.Md5Helper;
import com.jingyu.utils.function.DirHelper;
import com.jingyu.utils.http.ReqInfo;
import com.jingyu.utils.http.RespInfo;

public class AppMainActivity extends BaseActivity {

    public static boolean isGoOn = false;

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
                downloadInfo.setUrl("http://192.168.0.102/android/appv4.apk");
                downloadInfo.setFile(DirHelper.ExternalAndroid.getFile(getApplicationContext(), "files/download", Md5Helper.MD5Encode(downloadInfo.getUrl()) + ".apk"));
                downloadInfo.setRange(false);
                downloadInfo.setUpgrade(DownloadInfo.Upgrade.CHOICE);
                downloadInfo.setContent("修复bug");
                downloadInfo.setTitle("V2.0.0最新版本升级");

                // 不存在升级 或 可选升级
                if (downloadInfo.isNoneUpgrade() || downloadInfo.isChoiceUpgrade()) {
                    isGoOn = true;
                    loadData();
                }

                // 需要升级
                if (downloadInfo.isChoiceUpgrade() || downloadInfo.isForceUpgrade()) {
                    UpgradeActivity.actionStart(getActivity(), downloadInfo);
                }
            }

            @Override
            public void onEnd(ReqInfo reqInfo, RespInfo respInfo) {
                super.onEnd(reqInfo, respInfo);
                if (respInfo.isSuccessAll()) {
                    netSuccessChangeBg();
                } else {
                    //todo
                    netFailChangeBg("升级检查失败", reqInfo, this, null);
                }
            }
        });
    }

    private void loadData() {

    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, AppMainActivity.class);
        activity.startActivity(intent);
    }
}

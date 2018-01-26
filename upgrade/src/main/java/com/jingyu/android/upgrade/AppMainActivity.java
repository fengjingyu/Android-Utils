package com.jingyu.android.upgrade;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jingyu.android.middle.AppHttp;
import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.android.middle.config.okhttp.req.MyReqInfo;
import com.jingyu.android.middle.config.okhttp.resp.JsonRespHandler;
import com.jingyu.android.middle.config.okhttp.resp.MyRespInfo;
import com.jingyu.utils.download.DownloadInfo;
import com.jingyu.utils.encryption.md5.Md5Helper;
import com.jingyu.utils.function.FileHelper;

public class AppMainActivity extends BaseActivity {

    public static boolean isGoOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);

        request();
    }

    public void request() {
        AppHttp.get("http://www.baidu.com", null, new JsonRespHandler() {
            @Override
            public MyJsonBean onParse2Model(MyReqInfo reqInfo, MyRespInfo respInfo) {
                return new MyJsonBean();
            }

            @Override
            public boolean onMatchAppStatusCode(MyReqInfo reqInfo, MyRespInfo respInfo, MyJsonBean resultBean) {
                return true;
            }

            @Override
            public void onSuccessAll(MyReqInfo reqInfo, MyRespInfo respInfo, MyJsonBean resultBean) {
                super.onSuccessAll(reqInfo, respInfo, resultBean);

                DownloadInfo downloadInfo = new DownloadInfo();
                downloadInfo.setUrl("http://192.168.0.102/android/appv4.apk");
                downloadInfo.setFile(FileHelper.ExternalAndroid.getFile(getApplicationContext(), "files/download", Md5Helper.MD5Encode(downloadInfo.getUrl()) + ".apk"));
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
            public void onEnd(MyReqInfo reqInfo, MyRespInfo respInfo) {
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

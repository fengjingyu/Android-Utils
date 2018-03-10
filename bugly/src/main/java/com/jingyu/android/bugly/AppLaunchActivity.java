package com.jingyu.android.bugly;
import com.jingyu.android.common.util.SystemUtil;
import com.jingyu.android.init.LaunchActivity;
import com.tencent.bugly.crashreport.CrashReport;

public class AppLaunchActivity extends LaunchActivity {

    @Override
    protected void action() {

        //初始化之前的可选设置
        CrashReport.UserStrategy userStrategy = new CrashReport.UserStrategy(getApplicationContext());
        userStrategy.setAppChannel(SystemUtil.getAppMetaData(getApplicationContext(), "CHANNEL"));  //设置渠道
        userStrategy.setAppVersion(SystemUtil.getVersionName(getApplicationContext()));      //App的版本
        userStrategy.setAppPackageName(getApplicationContext().getPackageName());  //App的包名
        userStrategy.setAppReportDelay(10000);   //默认10秒后联网上传报告

        //初始化
        CrashReport.setIsDevelopmentDevice(getApplicationContext(), BuildConfig.DEBUG);//在初始化Bugly之前通过以下接口把调试设备设置成“开发设备”。
        CrashReport.initCrashReport(getApplicationContext(), "8455525942", BuildConfig.DEBUG, userStrategy); //appid, 每一条Crash都会被立即上报,自定义日志将会在Logcat中输出,建议在测试阶段建议设置成true，发布时设置为false

        //初始化之后的可选设置
        CrashReport.setUserId("123456");//定位每个指定用户发生Crash的情况
        CrashReport.putUserData(getApplicationContext(), "userkey", "uservalue");// 最多可以有9对自定义的key-value（超过则添加失败）；key限长50字节，value限长200字节，过长截断；key必须匹配正则：[a-zA-Z[0-9]]+。
        CrashReport.setUserSceneTag(getApplicationContext(), 1001); // 用于标明App的某个“场景”。以最后设置的标签为准，标签id需大于0

        try {
            //..
        } catch (Throwable thr) {
            CrashReport.postCatchedException(thr);  // bugly会将这个throwable上报
        }

        MainActivity.actionStart(getActivity());
    }
}

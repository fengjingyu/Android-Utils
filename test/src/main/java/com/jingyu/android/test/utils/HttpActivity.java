package com.jingyu.android.test.utils;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jingyu.android.middle.AppFile;
import com.jingyu.android.middle.AppHttp;
import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.android.middle.config.okhttp.MyHttpClient;
import com.jingyu.android.middle.config.okhttp.MyInterceptor;
import com.jingyu.android.middle.config.okhttp.req.MyReqInfo;
import com.jingyu.android.middle.config.okhttp.resp.BaseRespHandler;
import com.jingyu.android.middle.config.okhttp.resp.JsonRespHandler;
import com.jingyu.android.middle.config.okhttp.resp.MyRespInfo;
import com.jingyu.utils.function.FileHelper;
import com.jingyu.utils.function.Logger;
import com.jingyu.utils.util.UtilIo;
import com.jingyu.android.test.utils.model.TestModel;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

import okhttp3.Interceptor;

public class HttpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        request3();
    }


    public void request2() {
        AppHttp.download("http://10.0.2.2:8080/android/test.apk", null, new BaseRespHandler() {
            @Override
            public void onSuccessForDownload(MyReqInfo reqInfo, MyRespInfo respInfo, InputStream inputStream) {
                super.onSuccessForDownload(reqInfo, respInfo, inputStream);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Logger.shortToast("onSuccessForDownload");
                    }
                });
                File file = FileHelper.createFile(AppFile.getAppDir(getApplicationContext()), "1.apk");
                UtilIo.inputStream2File(inputStream, file);
            }

            @Override
            public void onFailure(MyReqInfo reqInfo, MyRespInfo respInfo) {
                super.onFailure(reqInfo, respInfo);
                Logger.shortToast("onFailure");
            }
        });
    }

    public void request3() {
        new MyHttpClient().http(new MyReqInfo("http://10.0.2.2:8080/android/test.apk", true), new BaseRespHandler(getActivity()) {

            @Override
            public void onSuccessForDownload(MyReqInfo reqInfo, MyRespInfo respInfo, InputStream inputStream) {
                super.onSuccessForDownload(reqInfo, respInfo, inputStream);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Logger.shortToast("onSuccessForDownload");
                    }
                });
                File file = FileHelper.createFile(AppFile.getAppDir(getApplicationContext()), "3.apk");
                UtilIo.inputStream2File(inputStream, file);
            }

            @Override
            public void onFailure(MyReqInfo reqInfo, MyRespInfo respInfo) {
                super.onFailure(reqInfo, respInfo);
                Logger.shortToast("onFailure");
            }
        }, new MyInterceptor() {
            @Override
            public boolean interceptReqSend(MyReqInfo reqInfo) {
                Logger.d("interceptReqSend --false");
                return false;
            }

            @Override
            public void interceptRespEnd(MyReqInfo reqInfo, MyRespInfo respInfo) {
                Logger.d("interceptReqSend--interceptRespEnd");
            }
        });
    }


    public void request() {

        // 可以无参数，仅访问url
        AppHttp.get("url", null, null);

        HashMap<String, Object> map = new HashMap<>();
        map.put("param1", "value1");
        map.put("param2", new File("path"));

        // 可以有参数,不回调
        AppHttp.post("url", map, null);

        // 可以有参数，回调不处理
        AppHttp.post("url", map, new JsonRespHandler());

        // 可以有参数, 处理回调
        AppHttp.post("url", map, new JsonRespHandler() {

            @Override
            public void onSuccessButCodeWrong(MyReqInfo reqInfo, MyRespInfo respInfo, MyJsonBean resultBean) {
                super.onSuccessButCodeWrong(reqInfo, respInfo, resultBean);
                Logger.dLongToast(resultBean.getMsg());
            }

            @Override
            public void onSuccessButParseWrong(MyReqInfo reqInfo, MyRespInfo respInfo) {
                super.onSuccessButParseWrong(reqInfo, respInfo);
                Logger.dShortToast("解析失败---服务端返回的内容是---" + respInfo.getDataString());
            }

            @Override
            public void onSuccessAll(MyReqInfo reqInfo, MyRespInfo respInfo, MyJsonBean resultBean) {
                super.onSuccessAll(reqInfo, respInfo, resultBean);
                Logger.shortToast(resultBean.getMsg());
            }
        });

        // 请求失败
        AppHttp.get("url", map, new JsonRespHandler() {
            @Override
            public void onFailure(MyReqInfo reqInfo, MyRespInfo respInfo) {
                super.onFailure(reqInfo, respInfo);
                Logger.d("onFailure");
            }
        });

        // 也可以创建一个继承BaseRespHander的类,手动解析,以后不同页面调用相同接口时,可以复用该类的解析层
        AppHttp.post("url", map, new BaseRespHandler<TestModel>() {

            @Override
            public TestModel onParse2Model(MyReqInfo reqInfo, MyRespInfo respInfo) {
                TestModel model = new TestModel();
                model.setMsg("你好");
                model.setCode("0");
                return model; // 如果解析失败返回null即可
            }

            @Override
            public void onSuccessAll(MyReqInfo reqInfo, MyRespInfo respInfo, TestModel resultBean) {
                super.onSuccessAll(reqInfo, respInfo, resultBean);
            }
        });

    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, HttpActivity.class));
    }
}

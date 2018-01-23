package com.jingyu.android.test.utils;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jingyu.android.middle.Http;
import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.android.middle.config.AppFile;
import com.jingyu.android.middle.http.BaseRespHandler;
import com.jingyu.android.middle.http.json.JsonModel;
import com.jingyu.android.middle.http.json.JsonRespHandler;
import com.jingyu.utils.function.FileHelper;
import com.jingyu.utils.function.Logger;
import com.jingyu.utils.http.IHttp.Interceptor;
import com.jingyu.utils.http.ReqInfo;
import com.jingyu.utils.http.RespHandlerAdapter;
import com.jingyu.utils.http.RespInfo;
import com.jingyu.utils.http.okhttp.OkClient;
import com.jingyu.utils.util.UtilIo;
import com.jingyu.android.test.utils.model.TestModel;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

public class HttpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        request3();
    }


    public void request2() {
        Http.download("http://10.0.2.2:8080/android/test.apk", null, new BaseRespHandler() {
            @Override
            public void onSuccessForDownload(ReqInfo reqInfo, RespInfo respInfo, InputStream inputStream) {
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
            public void onFailure(ReqInfo reqInfo, RespInfo respInfo) {
                super.onFailure(reqInfo, respInfo);
                Logger.shortToast("onFailure");
            }
        });
    }

    public void request3() {
        new OkClient().http(new ReqInfo("http://10.0.2.2:8080/android/test.apk", true), new RespHandlerAdapter() {

            @Override
            public void onSuccessForDownload(ReqInfo reqInfo, RespInfo respInfo, InputStream inputStream) {
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
            public void onFailure(ReqInfo reqInfo, RespInfo respInfo) {
                super.onFailure(reqInfo, respInfo);
                Logger.shortToast("onFailure");
            }
        }, new Interceptor() {
            @Override
            public boolean interceptReqSend(ReqInfo reqInfo) {
                Logger.d("interceptReqSend --false");
                return false;
            }

            @Override
            public void interceptRespEnd(ReqInfo reqInfo, RespInfo respInfo) {
                Logger.d("interceptReqSend--interceptRespEnd");
            }
        });
    }


    public void request() {

        // 可以无参数，仅访问url
        Http.get("url", null, null);

        HashMap<String, Object> map = new HashMap<>();
        map.put("param1", "value1");
        map.put("param2", new File("path"));

        // 可以有参数,不回调
        Http.post("url", map, null);

        // 可以有参数，回调不处理
        Http.post("url", map, new JsonRespHandler());

        // 可以有参数, 处理回调
        Http.post("url", map, new JsonRespHandler() {

            @Override
            public void onSuccessButCodeWrong(ReqInfo reqInfo, RespInfo respInfo, JsonModel resultBean) {
                super.onSuccessButCodeWrong(reqInfo, respInfo, resultBean);
                Logger.dLongToast(resultBean.getMsg());
            }

            @Override
            public void onSuccessButParseWrong(ReqInfo reqInfo, RespInfo respInfo) {
                super.onSuccessButParseWrong(reqInfo, respInfo);
                Logger.dShortToast("解析失败---服务端返回的内容是---" + respInfo.getDataString());
            }

            @Override
            public void onSuccessAll(ReqInfo reqInfo, RespInfo respInfo, JsonModel resultBean) {
                super.onSuccessAll(reqInfo, respInfo, resultBean);
                Logger.shortToast(resultBean.getMsg());
            }
        });

        // 请求失败
        Http.get("url", map, new JsonRespHandler() {
            @Override
            public void onFailure(ReqInfo reqInfo, RespInfo respInfo) {
                super.onFailure(reqInfo, respInfo);
                Logger.d("onFailure");
            }
        });

        // 也可以创建一个继承BaseRespHander的类,手动解析,以后不同页面调用相同接口时,可以复用该类的解析层
        Http.post("url", map, new BaseRespHandler<TestModel>() {

            @Override
            public TestModel onParse2Model(ReqInfo reqInfo, RespInfo respInfo) {
                TestModel model = new TestModel();
                model.setMsg("你好");
                model.setCode("0");
                return model; // 如果解析失败返回null即可
            }

            @Override
            public void onSuccessAll(ReqInfo reqInfo, RespInfo respInfo, TestModel resultBean) {
                super.onSuccessAll(reqInfo, respInfo, resultBean);
            }
        });

    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, HttpActivity.class));
    }
}

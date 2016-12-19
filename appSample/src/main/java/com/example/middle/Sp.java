package com.example.middle;

import android.app.Activity;
import android.content.Context;

import com.jingyu.utils.function.helper.ActivityManager;
import com.jingyu.utils.function.helper.SPHelper;


/**
 * @email fengjingyu@foxmail.com
 * @description 以下代码可以通过 SpCodeGenerator_V1.0.jar 生成
 */
public class Sp {

    public static final String USER_NAME = "userName";
    public static final String USER_ID = "userId";
    public static final String USER_TOKEN = "userToken";
    public static final String IS_LOGIN = "isLogin";
    public static final String USER_HEAD = "userHeader";
    public static final String USER_PHONE_NUM = "userPhoneNum";
    public static final String IS_INSTALL = "isInstall";

    public static boolean isFirstInstallApp() {

        return SPHelper.spGet(IS_INSTALL, true);

    }

    public static String getUserId() {

        return SPHelper.spGet(USER_ID, "");

    }

    public static String getUserToken() {

        return SPHelper.spGet(USER_TOKEN, "");

    }

    public static String getUserName() {

        return SPHelper.spGet(USER_NAME, "");

    }

    public static String getUserHead() {

        return SPHelper.spGet(USER_HEAD, "");
    }

    public static String getUserPhoneNum() {

        return SPHelper.spGet(USER_PHONE_NUM, "");
    }

    /**
     * 获取登录状态
     */
    public static boolean isLogin() {

        return SPHelper.spGet(IS_LOGIN, false);

    }

    public static void setUserId(String userId) {

        SPHelper.spPut(USER_ID, userId);

    }

    public static void setUserToken(String userToken) {

        SPHelper.spPut(USER_TOKEN, userToken);

    }

    public static void setUserName(String userName) {

        SPHelper.spPut(USER_NAME, userName);

    }

    public static void setUserHeader(String userHeader) {

        SPHelper.spPut(USER_HEAD, userHeader);

    }

    public static void setUserPhoneNum(String userPhoneNum) {

        SPHelper.spPut(USER_PHONE_NUM, userPhoneNum);

    }

    /**
     * 保存登录状态
     */
    public static void setLogin(boolean isLogin) {

        SPHelper.spPut(IS_LOGIN, isLogin);

    }

    public static void setFirstInstallApp(boolean value) {

        SPHelper.spPut(IS_INSTALL, value);

    }

    public static void loginOut(Class<? extends Activity> clazz, Context context) {
        setLogin(false);
        setUserId("");
        setUserToken("");
        setUserHeader("");
        setUserName("");
        setUserPhoneNum("");
        //TODO 注销的代码 。。

        ActivityManager.finishAllActivity();
        //TODO 添加跳转代码
    }


}

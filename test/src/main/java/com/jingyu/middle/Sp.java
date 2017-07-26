package com.jingyu.middle;

import android.app.Application;

import com.jingyu.utils.function.SPHelper;


/**
 * @author fengjingyu@foxmail.com
 */
public class Sp {
    private Sp() {
    }

    public static final String SP_FILE_NAME = "sp_file";

    public static final String USER_NAME = "userName";
    public static final String USER_ID = "userId";
    public static final String USER_TOKEN = "userToken";
    public static final String IS_LOGIN = "isLogin";
    public static final String USER_HEAD = "userHeader";
    public static final String USER_PHONE_NUM = "userPhoneNum";
    public static final String IS_INSTALL = "isInstall";

    private static SPHelper spHelper;

    public static void initSP(Application application) {
        spHelper = new SPHelper(application, SP_FILE_NAME);
    }

    public static String getUserId() {
        return spHelper.spGet(USER_ID, "");
    }

    public static String getUserToken() {
        return spHelper.spGet(USER_TOKEN, "");
    }

    public static String getUserName() {
        return spHelper.spGet(USER_NAME, "");
    }

    public static String getUserHead() {
        return spHelper.spGet(USER_HEAD, "");
    }

    public static String getUserPhoneNum() {
        return spHelper.spGet(USER_PHONE_NUM, "");
    }

    public static boolean isLogin() {
        return spHelper.spGet(IS_LOGIN, false);
    }

    public static boolean isFirstInstallApp() {
        return spHelper.spGet(IS_INSTALL, true);
    }

    public static void setUserId(String userId) {
        spHelper.spPut(USER_ID, userId);
    }

    public static void setUserToken(String userToken) {
        spHelper.spPut(USER_TOKEN, userToken);
    }

    public static void setUserName(String userName) {
        spHelper.spPut(USER_NAME, userName);
    }

    public static void setUserHeader(String userHeader) {
        spHelper.spPut(USER_HEAD, userHeader);
    }

    public static void setUserPhoneNum(String userPhoneNum) {
        spHelper.spPut(USER_PHONE_NUM, userPhoneNum);
    }

    public static void setLogin(boolean isLogin) {
        spHelper.spPut(IS_LOGIN, isLogin);
    }

    public static void setFirstInstallApp(boolean value) {
        spHelper.spPut(IS_INSTALL, value);
    }

}

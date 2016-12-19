package com.example.middle;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.app.MainActivity;
import com.jingyu.utils.function.helper.Logger;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public class Jumper {

    /**
     * 安装apk
     */
    public static void toInstallAPK(Context context, String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + path), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 跳到发短信
     *
     * @param context 上下文
     * @param phone   电话
     * @param content 短信内容
     */
    public static void toSendSMS(Context context, String phone, String content) {
        try {
            Uri uri = Uri.parse("smsto:" + phone);
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", content);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Logger.shortToast("您没有短信功能，此功能不能正常进行！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳到外部电话
     *
     * @param context 上下文
     * @param phone   电话
     */
    public static void toPhone(Context context, String phone) {
        try {
            Uri uri = Uri.parse("tel:" + phone);
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Logger.shortToast("您没有拨打电话功能，此功能不能正常进行！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳到外部浏览器
     *
     * @param context 上下文
     * @param url     地址
     */
    public static void toWeb(Context context, String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Logger.shortToast("您没有浏览器，此功能不能正常进行，请安装浏览器后在试！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void toActivity(Context context, Intent intent) {
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void toMainActivity(Activity activityContext) {
        toActivity(activityContext, new Intent(activityContext, MainActivity.class));
    }

}

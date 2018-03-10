package com.jingyu.android.test.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;

import com.jingyu.android.common.log.Logger;
import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.android.test.R;
import com.jingyu.java.mytool.encryption.Md5;
import com.jingyu.java.mytool.encryption.aes.Aes;
import com.jingyu.java.mytool.encryption.des.Des;
import com.jingyu.java.mytool.util.StringUtil;

public class EncryptActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);

        testEncrypt();
    }

    private void testEncrypt() {
        String one = Md5.encode("123456abc");
        String two = Md5.encode("123456abc");
        Logger.d(one);
        Logger.d(two);
        Logger.d(StringUtil.equals(one, two)); // true

        String des_close = Des.encodeRequestStr("today is haha 123");
        String des_open = Des.decodeResponseStr(des_close);
        Logger.d(des_close);
        Logger.d(des_open);

        String aes_close = Aes.encodeRequestStr("computer 123 macpro");
        String aes_open = Aes.decodeResponseStr(aes_close);
        Logger.d(aes_close);
        Logger.d(aes_open);

        try {
            String base64_e = Base64.encodeToString("123  HEHE".getBytes(),Base64.DEFAULT);
            String base64_d = new String(Base64.decode(base64_e,Base64.DEFAULT), "utf-8");
            Logger.d(base64_e);
            Logger.d(base64_d);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String base64_e = new String(Base64.encode("123  HEHE".getBytes(), Base64.DEFAULT), "utf-8");
            String base64_d = new String(Base64.decode(base64_e.getBytes(), Base64.DEFAULT), "utf-8");
            Logger.d(base64_e);
            Logger.d(base64_d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, EncryptActivity.class));
    }
}

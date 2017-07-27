package com.jingyu.utilstest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;

import com.demo.middle.base.BaseActivity;
import com.jingyu.test.R;
import com.jingyu.utils.encryption.aes.AesEncryptAndDecrypt;
import com.jingyu.utils.encryption.des.DesEncryptAndDecrypt;
import com.jingyu.utils.encryption.md5.Md5Helper;
import com.jingyu.utils.encryption.rsa.Base64Helper;
import com.jingyu.utils.function.Logger;
import com.jingyu.utils.util.UtilString;

public class EncryptActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);

        testEncrypt();
    }

    private void testEncrypt() {
        String one = Md5Helper.MD5Encode("123456abc");
        String two = Md5Helper.MD5Encode2("123456abc");
        Logger.d(one);
        Logger.d(two);
        Logger.d(UtilString.equals(one, two)); // true

        String des_close = DesEncryptAndDecrypt.encodeRequestStr("today is haha 123");
        String des_open = DesEncryptAndDecrypt.decodeResponseStr(des_close);
        Logger.d(des_close);
        Logger.d(des_open);

        String aes_close = AesEncryptAndDecrypt.encodeRequestStr("computer 123 macpro");
        String aes_open = AesEncryptAndDecrypt.decodeResponseStr(aes_close);
        Logger.d(aes_close);
        Logger.d(aes_open);

        try {
            String base64_e = Base64Helper.encode("123  HEHE".getBytes());
            String base64_d = new String(Base64Helper.decode(base64_e), "utf-8");
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

package com.xiaocoder.test;

import android.os.Bundle;
import android.util.Base64;

import com.xiaocoder.utils.encryption.aes.AesEncryptAndDecrypt;
import com.xiaocoder.utils.encryption.des.DesEncryptAndDecrypt;
import com.xiaocoder.utils.encryption.md5.UtilMd5;
import com.xiaocoder.utils.encryption.rsa.UtilBase64;
import com.xiaocoder.utils.io.LogHelper;
import com.xiaocoder.utils.io.SPHelper;
import com.xiaocoder.utils.util.UtilIo;
import com.xiaocoder.utils.util.UtilIoAndr;
import com.xiaocoder.utils.json.JsonBean;
import com.xiaocoder.utils.json.JsonParse;
import com.xiaocoder.utils.util.UtilString;
import com.xiaocoder.utils.util.UtilSystem;
import com.xiaocoder.test_middle.base.BaseActivity;
import com.xiaocoder.test_middle.config.ConfigFile;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWidgets();
    }

    public void initWidgets() {
        testSp();
        testLog();
        testUUID();
        testClone();
        testSecret();
        testJsonFormat();
        testLinkedBlockQueue();
        testSubList();
        testRuntime();
    }

    private void testRuntime() {
        LogHelper.i("Runtime.getRuntime().availableProcessors()--" + Runtime.getRuntime().availableProcessors());
        LogHelper.i("Runtime.getRuntime().maxMemory()--" + Runtime.getRuntime().maxMemory());
    }

    private void testSp() {
        SPHelper.spPut("1", "123 ");
        SPHelper.spPut("2", 456);
        SPHelper.spPut("3", 789.1F);
        SPHelper.spPut("4", false);
        SPHelper.spPut("5", " abc ");

        //String result = SPHelper.spGet("1", "abc") + SPHelper.spGet("2", 0) + SPHelper.spGet("3", 0.1f)+ SPHelper.spGet("4", true) + SPHelper.spGet("5", "abc") + SPHelper.spGet("6", null) + SPHelper.spGet("7", "    jkl");
        //LogHelper.shortToast(result);

        SPHelper.spPut("1", 0.1f);

        String result2 = SPHelper.spGet("5", "java") + SPHelper.spGet("1", 0.0f) + SPHelper.spGet("2", 0) + SPHelper.spGet("3", 0.1f)
                + SPHelper.spGet("4", true) + SPHelper.spGet("6", null) + SPHelper.spGet("7", "    jkl");
        LogHelper.shortToast(result2);
    }

    private void testLog() {
        TestModel model = null;
        // 不会报错
        LogHelper.i(model);

        LogHelper.i(1);
        LogHelper.i(true);
        LogHelper.i(false);
        Object obj = null;
        LogHelper.i(obj);

        try {
            LogHelper.e("123");
            LogHelper.e("345");
            LogHelper.e("678");
            int i = 1 / 0;
        } catch (Exception e) {
            LogHelper.e(this, "--oncreate()--", e);
        }
        // LogHelper.clearLog();
        LogHelper.e(this, "1234567890");
        LogHelper.tempPrint("android--" + System.currentTimeMillis());

        LogHelper.i(UtilIo.getAllFilesByDirQueue(UtilIoAndr.createDirInSDCard(ConfigFile.APP_ROOT), new ArrayList<File>()));

        UtilIo.toFileByBytes(UtilIoAndr.createFileInAndroid(this, ConfigFile.APP_ROOT, "lalala.txt"), "写入的内容--1234567890987654321abc".getBytes(), true);
    }

    private void testUUID() {
        LogHelper.i("UUID----" + UUID.randomUUID() + "----" + UUID.randomUUID().toString().length());
    }

    private void testClone() {
        TestModel testModel = new TestModel();
        testModel.setCode(200);
        testModel.setMsg("ceshi");

        TestModel simple = (TestModel) testModel.simpleClone();

        TestModel deep = (TestModel) testModel.deepClone();

        LogHelper.i("原始数据---" + testModel);
        LogHelper.i("浅克隆---" + simple);
        LogHelper.i("深克隆---" + deep);

        TestModel simple2 = (TestModel) testModel.simpleClone();
        simple2.setMsg("123");
        simple2.setCode(300);

        TestModel deep2 = (TestModel) testModel.deepClone();
        deep2.setMsg("123123");
        deep2.setCode(302);

        LogHelper.i("原始数据---" + testModel);
        LogHelper.i("浅克隆---" + simple2);
        LogHelper.i("深克隆---" + deep2);
    }

    private void testSecret() {
        String one = UtilMd5.MD5Encode("123456abc");
        String two = UtilMd5.MD5Encode2("123456abc");
        LogHelper.i(one);
        LogHelper.i(two);
        LogHelper.i(UtilString.equals(one, two)); // true

        String des_close = DesEncryptAndDecrypt.encodeRequestStr("today is haha 123");
        String des_open = DesEncryptAndDecrypt.decodeResponseStr(des_close);
        LogHelper.i(des_close);
        LogHelper.i(des_open);

        String aes_close = AesEncryptAndDecrypt.encodeRequestStr("computer 123 macpro");
        String aes_open = AesEncryptAndDecrypt.decodeResponseStr(aes_close);
        LogHelper.i(aes_close);
        LogHelper.i(aes_open);

        try {
            String base64_e = UtilBase64.encode("123  HEHE".getBytes());
            String base64_d = new String(UtilBase64.decode(base64_e), "utf-8");
            LogHelper.i(base64_e);
            LogHelper.i(base64_d);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String base64_e = new String(Base64.encode("123  HEHE".getBytes(), Base64.DEFAULT), "utf-8");
            String base64_d = new String(Base64.decode(base64_e.getBytes(), Base64.DEFAULT), "utf-8");
            LogHelper.i(base64_e);
            LogHelper.i(base64_d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testLinkedBlockQueue() {

        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        queue.add("d");

//        try {
//            LogHelper.i(queue.take()); // a  //take 类似 poll remove
//            LogHelper.i(queue.take()); // b
//            LogHelper.i(queue.take()); // c
//            LogHelper.i(queue.take()); // d
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        LogHelper.i(queue.peek()); //a  // peek 类似 element
        LogHelper.i(queue.peek()); //a
        LogHelper.i(queue.peek()); //a
        LogHelper.i(queue.peek()); //a
        queue.remove("a");
        LogHelper.i(queue.peek()); //b
        LogHelper.i(queue.peek()); //b
        LogHelper.i(queue.peek()); //b
    }

    private void testJsonFormat() {
        String str = "{\n" +
                "    \"code\": 0,\n" +
                "    \"msg\": \"success\",\n" +
                "    \"data\": [\n" +
                "\"/cr/100/200/aaa.mp4\"\n" +
                "    ]\n" +
                "}\n";

        String str2 = " {\"code\":0,\"msg\":\"成功\",\"data\":[[\"板蓝根\",\"白云山\"]]}";

        JsonBean bean = JsonParse.getJsonParseData(str2, JsonBean.class);
        List beans = bean.getListList("data", new ArrayList<ArrayList>());

        try {
            LogHelper.i(beans.toString());
            LogHelper.i(beans.get(0).toString());
            if (beans.get(0) instanceof List) {
                LogHelper.i("List");
            } else if (beans.get(0) instanceof String[]) {
                LogHelper.i("string[]");
            } else {
                LogHelper.i(beans.get(0).getClass().toString());

                JSONArray array = (JSONArray) beans.get(0);
                int count = array.length();
                for (int i = 0; i < count; i++) {
                    LogHelper.i((String) array.get(i));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogHelper.e(this.toString() + "---exception");
        }

        LogHelper.i(UtilSystem.getDeviceId(this) + "--------------deviceId");
    }

    public void testSubList() {
        List<TestModel> list = new ArrayList<>();
        TestModel model = new TestModel();
        TestModel model2 = new TestModel();
        TestModel model3 = new TestModel();
        TestModel model4 = new TestModel();

        list.add(model);
        list.add(model2);
        list.add(model3);
        list.add(model4);

        List<TestModel> sub = list.subList(0, 2);

        LogHelper.i("list", list.size());//4
        LogHelper.i("list", sub.size());//2
        LogHelper.i("list", list.get(0));//相同@1234567
        LogHelper.i("list", sub.get(0));//相同@1234567
        LogHelper.i("list", list.get(1));//相同@0123456
        LogHelper.i("list", sub.get(1));//相同@0123456
    }


}
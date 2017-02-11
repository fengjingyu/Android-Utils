package com.jingyu.utilstest;

import android.os.Bundle;
import android.util.Base64;

import com.jingyu.middle.base.BaseActivity;
import com.jingyu.middle.config.ConfigFile;
import com.jingyu.utils.encryption.aes.AesEncryptAndDecrypt;
import com.jingyu.utils.encryption.md5.Md5Helper;
import com.jingyu.utils.encryption.rsa.Base64Helper;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.function.helper.SPHelper;
import com.jingyu.utils.json.JsonBean;
import com.jingyu.utils.util.UtilIo;
import com.jingyu.utils.util.UtilIoAndr;
import com.jingyu.utils.util.UtilString;
import com.jingyu.utils.util.UtilSystem;
import com.jingyu.utils.encryption.des.DesEncryptAndDecrypt;
import com.jingyu.utils.json.JsonParse;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

/**
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
        Logger.i("Runtime.getRuntime().availableProcessors()--" + Runtime.getRuntime().availableProcessors());
        Logger.i("Runtime.getRuntime().maxMemory()--" + Runtime.getRuntime().maxMemory());
    }

    private void testSp() {
        SPHelper.spPut("1", "123 ");
        SPHelper.spPut("2", 456);
        SPHelper.spPut("3", 789.1F);
        SPHelper.spPut("4", false);
        SPHelper.spPut("5", " abc ");

        //String result = SPHelper.spGet("1", "abc") + SPHelper.spGet("2", 0) + SPHelper.spGet("3", 0.1f)+ SPHelper.spGet("4", true) + SPHelper.spGet("5", "abc") + SPHelper.spGet("6", null) + SPHelper.spGet("7", "    jkl");
        //Logger.shortToast(result);

        SPHelper.spPut("1", 0.1f);

        String result2 = SPHelper.spGet("5", "java") + SPHelper.spGet("1", 0.0f) + SPHelper.spGet("2", 0) + SPHelper.spGet("3", 0.1f)
                + SPHelper.spGet("4", true) + SPHelper.spGet("6", null) + SPHelper.spGet("7", "    jkl");
        Logger.shortToast(result2);
    }

    private void testLog() {
        TestModel model = null;
        // 不会报错
        Logger.i(model);

        Logger.i(1);
        Logger.i(true);
        Logger.i(false);
        Object obj = null;
        Logger.i(obj);

        try {
            Logger.e("123");
            Logger.e("345");
            Logger.e("678");
            int i = 1 / 0;
        } catch (Exception e) {
            Logger.e(this, "--oncreate()--", e);
        }
        // Logger.clearLog();
        Logger.e(this, "1234567890");
        Logger.tempPrint("android--" + System.currentTimeMillis());

        Logger.i(UtilIo.getAllFilesByDirQueue(UtilIoAndr.createDirInSDCard(ConfigFile.APP_ROOT), new ArrayList<File>()));

        UtilIo.toFileByBytes(UtilIoAndr.createFileInAndroid(this, ConfigFile.APP_ROOT, "lalala.txt"), "写入的内容--1234567890987654321abc".getBytes(), true);
    }

    private void testUUID() {
        Logger.i("UUID----" + UUID.randomUUID() + "----" + UUID.randomUUID().toString().length());
    }

    private void testClone() {
        TestModel testModel = new TestModel();
        testModel.setCode(200);
        testModel.setMsg("ceshi");

        TestModel simple = (TestModel) testModel.simpleClone();

        TestModel deep = (TestModel) testModel.deepClone();

        Logger.i("原始数据---" + testModel);
        Logger.i("浅克隆---" + simple);
        Logger.i("深克隆---" + deep);

        TestModel simple2 = (TestModel) testModel.simpleClone();
        simple2.setMsg("123");
        simple2.setCode(300);

        TestModel deep2 = (TestModel) testModel.deepClone();
        deep2.setMsg("123123");
        deep2.setCode(302);

        Logger.i("原始数据---" + testModel);
        Logger.i("浅克隆---" + simple2);
        Logger.i("深克隆---" + deep2);
    }

    private void testSecret() {
        String one = Md5Helper.MD5Encode("123456abc");
        String two = Md5Helper.MD5Encode2("123456abc");
        Logger.i(one);
        Logger.i(two);
        Logger.i(UtilString.equals(one, two)); // true

        String des_close = DesEncryptAndDecrypt.encodeRequestStr("today is haha 123");
        String des_open = DesEncryptAndDecrypt.decodeResponseStr(des_close);
        Logger.i(des_close);
        Logger.i(des_open);

        String aes_close = AesEncryptAndDecrypt.encodeRequestStr("computer 123 macpro");
        String aes_open = AesEncryptAndDecrypt.decodeResponseStr(aes_close);
        Logger.i(aes_close);
        Logger.i(aes_open);

        try {
            String base64_e = Base64Helper.encode("123  HEHE".getBytes());
            String base64_d = new String(Base64Helper.decode(base64_e), "utf-8");
            Logger.i(base64_e);
            Logger.i(base64_d);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String base64_e = new String(Base64.encode("123  HEHE".getBytes(), Base64.DEFAULT), "utf-8");
            String base64_d = new String(Base64.decode(base64_e.getBytes(), Base64.DEFAULT), "utf-8");
            Logger.i(base64_e);
            Logger.i(base64_d);
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
//            Logger.i(queue.take()); // a  //take 类似 poll remove
//            Logger.i(queue.take()); // b
//            Logger.i(queue.take()); // c
//            Logger.i(queue.take()); // d
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        Logger.i(queue.peek()); //a  // peek 类似 element
        Logger.i(queue.peek()); //a
        Logger.i(queue.peek()); //a
        Logger.i(queue.peek()); //a
        queue.remove("a");
        Logger.i(queue.peek()); //b
        Logger.i(queue.peek()); //b
        Logger.i(queue.peek()); //b
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
            Logger.i(beans.toString());
            Logger.i(beans.get(0).toString());
            if (beans.get(0) instanceof List) {
                Logger.i("List");
            } else if (beans.get(0) instanceof String[]) {
                Logger.i("string[]");
            } else {
                Logger.i(beans.get(0).getClass().toString());

                JSONArray array = (JSONArray) beans.get(0);
                int count = array.length();
                for (int i = 0; i < count; i++) {
                    Logger.i((String) array.get(i));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(this.toString() + "---exception");
        }

        Logger.i(UtilSystem.getDeviceId(this) + "--------------deviceId");
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

        Logger.i("list", list.size());//4
        Logger.i("list", sub.size());//2
        Logger.i("list", list.get(0));//相同@1234567
        Logger.i("list", sub.get(0));//相同@1234567
        Logger.i("list", list.get(1));//相同@0123456
        Logger.i("list", sub.get(1));//相同@0123456
    }


}
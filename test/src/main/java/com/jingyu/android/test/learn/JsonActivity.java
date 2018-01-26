package com.jingyu.android.test.learn;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jingyu.android.test.R;
import com.jingyu.utils.function.Logger;

import org.json.JSONObject;

public class JsonActivity extends AppCompatActivity {
    String JSON = "json";

    String json = "{\"msg\":\"msg\",\"code\":\"code\",\"data\":[{\"name\":\"name\",\"age\":\"age\",\"address\":\"address\"},\n" +
            "{\"name\":\"name2\",\"age\":\"age2\",\"address\":\"address2\"}\n" +
            "]}";

    String json2 = "[{\"name\":\"name\",\"age\":\"age\",\"address\":\"address\"},\n" +
            "{\"name\":\"name2\",\"age\":\"age2\",\"address\":\"address2\"}\n" +
            "]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        try {
            String json = "{\"key\":\"value\"}";
            JSONObject jsonObject = new JSONObject(json);
            Logger.i(JSON, jsonObject.toString());
            Logger.i(JSON, jsonObject.getString("key"));
            Logger.i(JSON, "---------------1----------------");

            String json2 = "{\"key\":{\"key1\":\"value1\"}}";
            JSONObject jsonObject2 = new JSONObject(json2);
            Logger.i(JSON, jsonObject2.toString());
            Logger.i(JSON, jsonObject2.getString("key"));
            Logger.i(JSON, jsonObject2.getJSONObject("key"));
            Logger.i(JSON, "---------------2----------------");

//            String json3 = "{\"key\":\"{\"name:xioaming\"}\"}";// 构建json异常
//            String json3 = "{\"key\":\"name:xioaming\"}";//可以构建json,取出的value是String
//            String json3 = "{\"key\":\"{\"key\":\"value\"}\"}";//构建json异常
//            String json3 = "{\"\"key\":\"{\"key\":\"value\"}\"}";//构建json异常
//            String json3 = "{\"key\":\"[\"key1\":\"value1\"]\"}";//构建json异常
//            String json3 = "";//构建json异常
//            String json3 = "{}"; // 可以构建json,但getString(key)异常
//            String json3 = "{\"key\":\"\"}"; // 可以构建json,但是getString(key)异常--无法构建JsonObject,optString(key)返回""
//            String json3 = "{\"key\":null}"; // 可以构建json,getString(key),optString(key)都返回null
            String json3 = "{\"key\":{\"key\":\"value\"}}";//可以构建json,getString(key)取出的string,可以继续构建JsonObject
            Logger.i(JSON, "--" + json3);
            JSONObject jsonObject3 = new JSONObject(json3);
            Logger.i(JSON, jsonObject3.toString());
            Logger.i(JSON, jsonObject3.optString("key"));
            Logger.i(JSON, jsonObject3.getString("key"));
            Logger.i(JSON, jsonObject3.getJSONObject("key"));
            Logger.i(JSON, "--------------3-----------------");

            //*json的形式
            //      ""(构建json异常)
            //      "ab"   @@   "\"{a:b}\""   @@  "\"[a,b]\""(构建json异常)
            //      "{}"(可以构建json,但是get值异常
            //      "[]"(可以构建json,但是get值异常
            //      "{key:value}"(可以构建json,可以get获取值)
            //      "[{},{}]"(可以构建json,可以get获取值)
            //*json内的键值对的形式
            //      "{\"key\":\"\"}"(getString(key)获取异常)
            //      "{\"key\":\"ab\"}"  @@  "{\"key\":\"{}\"}" @@ "{\" key\":\"[]\"}" (通过getString(key)可以获取String,但是该String无法再次构建JsonObject和JsonArray)
            //      "{\"key\":{}}"(可以通过getString(key)获取后再次构建JsonObject)
            //      "{\"key\":[]}"(可以通过getString(key)获取后再次构建JsonAarray)
            // ***key 与 value的内容内,双引号内不能再次嵌套双引号,否则构建json失败

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void actionStart(Activity activity) {
        activity.startActivity(new Intent(activity, JsonActivity.class));
    }
}

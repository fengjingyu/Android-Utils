package com.jingyu.test.utilstest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.demo.middle.base.BaseActivity;
import com.jingyu.test.R;
import com.jingyu.utils.function.Logger;
import com.jingyu.test.utilstest.model.TestModel;

public class CloneActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clone);
        testClone();
    }

    private void testClone() {
        TestModel testModel = new TestModel();
        testModel.setCode("200");
        testModel.setMsg("ceshi");

        TestModel simple = (TestModel) testModel.simpleClone();
        TestModel deep = (TestModel) testModel.deepClone();

        Logger.d("原始数据---" + testModel);
        Logger.d("浅克隆---" + simple);
        Logger.d("深克隆---" + deep);

        TestModel simple2 = (TestModel) testModel.simpleClone();
        simple2.setMsg("123");
        simple2.setCode("300");

        TestModel deep2 = (TestModel) testModel.deepClone();
        deep2.setMsg("123123");
        deep2.setCode("302");

        Logger.d("原始数据---" + testModel);
        Logger.d("浅克隆---" + simple2);
        Logger.d("深克隆---" + deep2);
    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, CloneActivity.class));
    }
}

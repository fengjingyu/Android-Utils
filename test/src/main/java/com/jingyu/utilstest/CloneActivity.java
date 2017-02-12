package com.jingyu.utilstest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jingyu.test.R;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utilstest.model.TestModel;

public class CloneActivity extends AppCompatActivity {

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

        Logger.i("原始数据---" + testModel);
        Logger.i("浅克隆---" + simple);
        Logger.i("深克隆---" + deep);

        TestModel simple2 = (TestModel) testModel.simpleClone();
        simple2.setMsg("123");
        simple2.setCode("300");

        TestModel deep2 = (TestModel) testModel.deepClone();
        deep2.setMsg("123123");
        deep2.setCode("302");

        Logger.i("原始数据---" + testModel);
        Logger.i("浅克隆---" + simple2);
        Logger.i("深克隆---" + deep2);
    }
}

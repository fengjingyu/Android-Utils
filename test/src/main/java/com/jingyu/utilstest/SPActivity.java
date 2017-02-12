package com.jingyu.utilstest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jingyu.test.R;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.function.helper.SPHelper;

public class SPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        testSp();
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
}

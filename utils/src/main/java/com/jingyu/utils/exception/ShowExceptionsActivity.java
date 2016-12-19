package com.jingyu.utils.exception;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @email fengjingyu@foxmail.com
 * @description 使用时记得在清单文件中注册 ShowExceptionsActivity
 */
public class ShowExceptionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.jingyu.utils.R.layout.activity_show_exception);
        TextView tv = (TextView) findViewById(com.jingyu.utils.R.id.tv);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String text = bundle.getString(CrashHandler.EXCEPTION_INFO);
            tv.setText(text);
        }
    }
}

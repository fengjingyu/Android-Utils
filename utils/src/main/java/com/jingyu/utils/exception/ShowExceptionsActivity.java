package com.jingyu.utils.exception;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.jingyu.utils.R;

/**
 * @author fengjingyu@foxmail.com
 * @description 使用时记得在清单文件中注册 ShowExceptionsActivity
 */
public class ShowExceptionsActivity extends Activity {
    public static final String EXCEPTION_INFO = "exception_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.jingyu.utils.R.layout.activity_show_exception);
        TextView tv = (TextView) findViewById(R.id.tv);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String text = bundle.getString(EXCEPTION_INFO);
            tv.setText(text);
        }
    }

    public static void actionStart(Context context, String info) {
        Intent intent = new Intent(context, ShowExceptionsActivity.class);
        intent.putExtra(EXCEPTION_INFO, info);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

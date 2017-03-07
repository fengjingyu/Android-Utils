package com.jingyu.test.material;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.jingyu.middle.base.BaseActivity;
import com.jingyu.test.R;

public class ToolBarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar);

        Toolbar toolbar = getViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public static void actionStart(Activity activity) {
        activity.startActivity(new Intent(activity, ToolBarActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }
}

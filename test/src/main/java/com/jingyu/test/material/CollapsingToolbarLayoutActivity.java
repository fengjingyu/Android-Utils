package com.jingyu.test.material;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.app.ActionBar;

import com.jingyu.middle.base.BaseActivity;
import com.jingyu.test.R;

import static android.R.attr.value;

public class CollapsingToolbarLayoutActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing_toolbar_layout);

        Toolbar toolbar = getViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = getViewById(R.id.collapsingToolbarLayout);
        ImageView imageView = getViewById(R.id.imageView);
        TextView textView = getViewById(R.id.textView);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle("android");
        textView.setText(getContent());
    }

    public String getContent() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 200; i++) {
            stringBuilder.append("material design");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case value:
                break;
            default:
                break;
        }
        return true;
    }

    public static void actionStart(Activity activity) {
        activity.startActivity(new Intent(activity, CollapsingToolbarLayoutActivity.class));
    }
}

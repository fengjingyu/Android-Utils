package com.jingyu.test.sample.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.jingyu.test_middle.Image;
import com.jingyu.test_middle.base.BaseActivity;


public class SampleImageActivity extends BaseActivity {

    //-------------修改配置去App、Config里----------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 图片加载
     */
    public void image() {

        Image.displayImage("url", new ImageView(this));

    }


}

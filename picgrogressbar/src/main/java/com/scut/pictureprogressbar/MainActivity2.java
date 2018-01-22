package com.scut.pictureprogressbar;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yanzhikai.pictureprogressbar.PictureProgressBar;

public class MainActivity2 extends AppCompatActivity {
    Button btn_start;
    PictureProgressBar pb_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btn_start = (Button) findViewById(R.id.btn_start);
        pb_2 = (PictureProgressBar) findViewById(R.id.pb_2);

        //使用属性动画来实现进度的变化
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d("sdsa", "onAnimationUpdate: " + Integer.parseInt(animation.getAnimatedValue().toString()));
                pb_2.setProgress(Integer.parseInt(animation.getAnimatedValue().toString()));
                if (pb_2.getProgress() >= pb_2.getMax()) {
                    //进度满了之后改变图片
                    pb_2.setPicture(R.drawable.b666);
                }
            }
        });
        valueAnimator.setDuration(10000);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_2.setPicture(R.drawable.b333);
                valueAnimator.start();
            }
        });
    }
}

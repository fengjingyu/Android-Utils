package com.jingyu.app;

import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import com.jingyu.middle.base.BaseActivity;
import com.jingyu.utils.util.UtilScreen;
import com.jingyu.utilstest.R;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public class PropertyAnimActivity extends BaseActivity {

    private Button animButton;
    private Button animButton2;
    private Button animButton3;
    private Button animButton4;

    private boolean flag;

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_anim);

        animButton = getViewById(R.id.animButton);
        animButton2 = getViewById(R.id.animButton2);
        animButton3 = getViewById(R.id.animButton3);
        animButton4 = getViewById(R.id.animButton4);

        animButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demo1();
            }
        });

        animButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demo2();
            }
        });

        animButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demo3();
            }
        });

        animButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demo4(animButton4);
            }
        });
    }

    private void demo3() {
        int time = 1000;
        ValueAnimator oaTranslate = ValueAnimator.ofFloat(700);
        oaTranslate.setDuration(time);
        oaTranslate.setInterpolator(new BounceInterpolator());
        oaTranslate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                System.out.println(i++ + "--" + animation.getAnimatedFraction() + "---" + animation.getAnimatedValue());
                float value = (Float) animation.getAnimatedValue();
                animButton3.setTranslationX(value);
                animButton3.setTranslationY(-getY2(value));
            }
        });
        // 旋转
        ObjectAnimator oaRotation = ObjectAnimator.ofFloat(animButton3, "rotation", 0f, -720f);
        oaRotation.setDuration(time);
        // 透明度
        ObjectAnimator oaAlpha = ObjectAnimator.ofFloat(animButton3, "alpha", 0.8f, 0.2f);
        oaAlpha.setDuration(time);
        // x缩放
        ObjectAnimator oaScaleX = ObjectAnimator.ofFloat(animButton3, "scaleX", 1f, 0.5f);
        oaScaleX.setDuration(time);
        // y缩放
        ObjectAnimator oaScaleY = ObjectAnimator.ofFloat(animButton3, "scaleY", 1f, 0.5f);
        oaScaleY.setDuration(time);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(oaTranslate, oaRotation, oaAlpha, oaScaleX, oaScaleY);
        set.start();
    }

    /**
     * 这里是根据三个坐标点{（0,0），（350,350），（700,0）}计算出来的抛物线方程
     */
    private float getY2(float x) {
        return -0.0028571428F * x * x + 2.0F * x;
    }

    private void demo2() {

        ValueAnimator oaTranslate = ValueAnimator.ofFloat(700);
        oaTranslate.setDuration(1500);
        oaTranslate.setInterpolator(new BounceInterpolator());
        oaTranslate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                System.out.println(i++ + "--" + animation.getAnimatedFraction() + "---" + animation.getAnimatedValue());
                float value = (Float) animation.getAnimatedValue();
                animButton2.setTranslationX(-value);
                animButton2.setTranslationY(-getY2(value));
            }
        });

        // 旋转
        ObjectAnimator oaRotation = ObjectAnimator.ofFloat(animButton2, "rotation", 0f, -360f);
        oaRotation.setDuration(1500);
        // 透明度
        ObjectAnimator oaAlpha = ObjectAnimator.ofFloat(animButton2, "alpha", 0.8f, 0.2f);
        oaAlpha.setDuration(1500);
        // x缩放
        // ObjectAnimator oaScaleX = ObjectAnimator.ofFloat(animButton2, "scaleX", 1f, 0.5f);
        // oaScaleX.setDuration(1500);
        // y缩放
        // ObjectAnimator oaScaleY = ObjectAnimator.ofFloat(animButton2, "scaleY", 1f, 0.5f);
        // oaScaleY.setDuration(1500);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(oaTranslate, oaRotation, oaAlpha);
        set.start();
    }

    private void demo1() {
        if (flag) {
            flag = false;
            animButton.animate().
                    // x方向
                            translationXBy(-(UtilScreen.getScreenWidthPx(PropertyAnimActivity.this) - UtilScreen.dip2px(PropertyAnimActivity.this, 100))).
                    setInterpolator(new DecelerateInterpolator()).
                    // y方向
                            translationYBy(UtilScreen.getScreenHeightPx(PropertyAnimActivity.this) - UtilScreen.dip2px(PropertyAnimActivity.this, 100)).
                    setInterpolator(new AccelerateInterpolator(1.0f)).
                    // 旋转
                            rotationBy(-1080).setDuration(2000).start();

        } else {
            flag = true;
            animButton.animate().
                    // x方向
                            translationX(UtilScreen.getScreenWidthPx(PropertyAnimActivity.this) - UtilScreen.dip2px(PropertyAnimActivity.this, 100)).
                    setInterpolator(new AccelerateInterpolator(1.0f)).
                    // y方向
                            translationY(-(UtilScreen.getScreenHeightPx(PropertyAnimActivity.this) - UtilScreen.dip2px(PropertyAnimActivity.this, 100))).
                    setInterpolator(new DecelerateInterpolator()).
                    // 旋转
                            rotation(1080).setDuration(2000).start();
        }
    }

    //分300步进行移动动画
    final int count = 300;

    /**
     * 要start 动画的那张图片的ImageView
     */
    private void demo4(View view) {

        Keyframe[] keyframes = new Keyframe[count];
        final float keyStep = 1f / (float) count;
        float key = keyStep;
        for (int i = 0; i < count; ++i) {
            keyframes[i] = Keyframe.ofFloat(key, i + 1);
            key += keyStep;
        }

        PropertyValuesHolder pvhX = PropertyValuesHolder.ofKeyframe("translationX", keyframes);
        key = keyStep;
        for (int i = 0; i < count; ++i) {
            keyframes[i] = Keyframe.ofFloat(key, -getY(i + 1));
            key += keyStep;
        }

        PropertyValuesHolder pvhY = PropertyValuesHolder.ofKeyframe("translationY", keyframes);
        ObjectAnimator yxBouncer = ObjectAnimator.ofPropertyValuesHolder(view, pvhY, pvhX).setDuration(1500);
        yxBouncer.setInterpolator(new BounceInterpolator());
        yxBouncer.start();
    }

    final float a = -1f / 75f;

    /**
     * 这里是根据三个坐标点{（0,0），（300,0），（150,300）}计算出来的抛物线方程
     */
    private float getY(float x) {
        return a * x * x + 4 * x;
    }


    /**
     * y = ax2 + bx + c , a为负数，开口向下
     *
     * @param args
     */
    public static void main(String[] args) {
        final float[][] points = {{0, 0}, {350, 350}, {700, 0}};
        calculate(points);
    }

    /**
     * a = (y1 * (x2 - x3) + y2 * (x3 - x1) + y3 * (x1 - x2)) / (x1 * x1 * (x2 -
     * x3) + x2 * x2 * (x3 - x1) + x3 * x3 * (x1 - x2))
     * b = (y1 - y2) / (x1 - x2) - a * (x1 + x2);
     * c = y1 - (x1 * x1) * a - x1 * b;
     */
    private static void calculate(float[][] points) {
        float x1 = points[0][0];
        float y1 = points[0][1];
        float x2 = points[1][0];
        float y2 = points[1][1];
        float x3 = points[2][0];
        float y3 = points[2][1];

        final float a = (y1 * (x2 - x3) + y2 * (x3 - x1) + y3 * (x1 - x2))
                / (x1 * x1 * (x2 - x3) + x2 * x2 * (x3 - x1) + x3 * x3 * (x1 - x2));
        final float b = (y1 - y2) / (x1 - x2) - a * (x1 + x2);
        final float c = y1 - (x1 * x1) * a - x1 * b;

        System.out.println("-a->" + a + " b->" + b + " c->" + c);
    }
}


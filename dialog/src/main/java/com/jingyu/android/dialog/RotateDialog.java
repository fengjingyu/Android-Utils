package com.jingyu.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import com.jingyu.android.common.util.ScreenUtil;
import com.jingyu.android.middle.App;

public class RotateDialog extends Dialog {

    // 如果这里使用getLayoutInflater(),则获取不到双圈的dialog，用LayoutInflater.from可以
    private LayoutInflater dialogInflater;

    private ViewGroup dialogLayout;

    private Animation anim;

    private ImageView img;

    public RotateDialog(Context context) {
        super(context, R.style.TransDialog);
        dialogInflater = LayoutInflater.from(context);

        initDialog();
    }

    public void initDialog() {
        dialogLayout = (ViewGroup) dialogInflater.inflate(R.layout.dialog_rotate_img, null);
        img = (ImageView) dialogLayout.findViewById(R.id.rotate_img);

        setContentView(dialogLayout);
        setWindowLayoutStyleAttr();

        anim = getRatoteAnimation();
    }

    public void setWindowLayoutStyleAttr() {
        setCanceledOnTouchOutside(true);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = 0.05f;
        lp.gravity = Gravity.TOP;
        lp.y = ScreenUtil.dip2px(App.getApplication(), 96 + 50);
        window.setAttributes(lp);
    }

    @Override
    public void show() {
        super.show();
        img.startAnimation(anim);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        img.clearAnimation();
    }

    public Animation getRatoteAnimation() {
        RotateAnimation animation = new RotateAnimation(0.0f, 720.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        return animation;
    }
}

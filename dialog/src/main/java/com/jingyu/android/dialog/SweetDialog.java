package com.jingyu.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationSet;

import com.jingyu.android.middle.App;
import com.jingyu.utils.util.UtilScreen;

import cn.pedant.SweetAlert.OptAnimationLoader;

/**
 * Created by xtyx_jy on 2017/4/9.
 */

public class SweetDialog extends Dialog {

    private AnimationSet mModalInAnim;
    protected View view;

    public SweetDialog(@NonNull Context context) {
        super(context, R.style.TransDialog);
        view = LayoutInflater.from(context).inflate(R.layout.dialog_sweet, null);
        setContentView(view);
        setWindowLayoutStyleAttr();
        initAnim();
    }


    public void initAnim() {
        mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_in);
    }

    public void setWindowLayoutStyleAttr() {
        setCanceledOnTouchOutside(true);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = 0.05f;
        lp.gravity = Gravity.CENTER;
        //lp.width = WindowManager.LayoutParams.MATCH_PARENT
        window.setAttributes(lp);
    }

    @Override
    public void show() {
        super.show();
        view.startAnimation(mModalInAnim);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        view.clearAnimation();
    }
}

package com.jingyu.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class AddressDialog extends Dialog {

    public AddressDialog(Context context) {
        super(context, R.style.TransDialog);
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_address, null));
        setWindowLayoutStyleAttr();
    }


    public void setWindowLayoutStyleAttr() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.7f;
        lp.dimAmount = 0.3f;
        lp.gravity = Gravity.BOTTOM;
        lp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.dialogAnim);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

}

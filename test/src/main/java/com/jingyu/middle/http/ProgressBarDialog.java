package com.jingyu.middle.http;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jingyu.test.R;


/**
 * @author fengjingyu@foxmail.com
 */
public class ProgressBarDialog extends Dialog {

    private ViewGroup dialogLayout;

    private TextView textView;

    public TextView getTextView() {
        return textView;
    }

    public ViewGroup getDialogLayout() {
        return dialogLayout;
    }

    public ProgressBarDialog(Context context) {
        super(context, R.style.TransDialog);
        dialogLayout = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.dialog_system, null);
        textView = (TextView) dialogLayout.findViewById(R.id.dialog_system_textview);
        setContentView(dialogLayout);
        setWindowLayoutStyleAttr();
    }

    private void setWindowLayoutStyleAttr() {
        setCanceledOnTouchOutside(true);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (lp != null) {
            lp.alpha = 0.7f;
            lp.dimAmount = 0.3f;
            window.setAttributes(lp);
        }
    }

}




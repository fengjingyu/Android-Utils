package com.jingyu.utils.http;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.function.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fengjingyu@foxmail.com
 * @description 管理需要显示加载dialog的http请求
 * 当有多个并发请求都需要显示dialog时，只显示一个，且多个请求都返回后，才关闭dialog
 */
public class DialogManager {
    /**
     * 每一个activity对应一个DialogManager
     */
    protected static DialogManager currentManager;
    /**
     * 记录需要加载dialog的网络请求
     * <p/>
     * String:哪一个网络请求tag
     * Boolean:为null或false表示还没发请求 或 请求完成了 ，为true表示请求未完成
     */
    protected Map<String, Boolean> mHttpDialogRecoder;

    /**
     * 加载中的dialog
     */
    protected Dialog mHttpDialog;

    /**
     * 当前在哪个页面
     */
    protected Activity mCurrentActivity;

    /**
     * @param activity 当前activity
     */
    private DialogManager(Activity activity) {
        this.mCurrentActivity = activity;
        this.mHttpDialogRecoder = new HashMap<>();
    }

    public static DialogManager getInstance(Activity activity) {

        if (currentManager == null) {
            currentManager = new DialogManager(activity);
        } else {

            if (currentManager.getmCurrentActivity() == activity) {
                // do nothing
            } else {
                currentManager.forceCloseDialog();
                currentManager = new DialogManager(activity);
            }
        }

        return currentManager;
    }

    public boolean isAllRequestEnd() {

        for (Map.Entry<String, Boolean> entry : mHttpDialogRecoder.entrySet()) {
            if (entry.getValue()) {
                // 表示还有请求没有回来
                return false;
            }
        }
        return true;

    }

    /**
     * 当dialog不为null  且 show了，才会加入到集合中
     *
     * @param hashCode
     */
    public void show(String hashCode) {

        if (mHttpDialog == null) {
            return;
        }

        if (isAllRequestEnd()) {
            // 没有正在进行的请求 或 所有已发出的请求都已返回
            showHttpDialog();
        }
        // 记录将发出且需要转dialog的请求
        mHttpDialogRecoder.put(hashCode, true);
    }

    public void close(String hashCode) {

        if (mHttpDialog == null) {
            return;
        }

        if (mHttpDialogRecoder.containsKey(hashCode)) {
            // 从集合中删除需要转dialog的请求
            mHttpDialogRecoder.remove(hashCode);

            if (isAllRequestEnd()) {
                closeHttpDialog();
            }
        }

    }

    private void showHttpDialog() {
        if (mHttpDialog != null && !mHttpDialog.isShowing()) {
            mHttpDialog.show();
            Logger.i(Constants.TAG_RESP_HANDLER, this.toString() + "---showHttpDialog()");
        }
    }

    private void closeHttpDialog() {
        if (mHttpDialog != null && mHttpDialog.isShowing()) {
            mHttpDialog.cancel();
            Logger.i(Constants.TAG_RESP_HANDLER, this.toString() + "---closeHttpDialog()");
        }
    }

    public void updateHttpDialog(Dialog httpDialog) {

        if (httpDialog == null) {
            return;
        }

        if (isAllRequestEnd()) {
            // 只有在所有请求都返回了，才可以更新dialog
            if (mHttpDialog != null) {
                mHttpDialog.cancel();
                mHttpDialog = null;
            }

            mHttpDialog = httpDialog;

            mHttpDialog.setCanceledOnTouchOutside(false);
            mHttpDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        onDialogKeyBackAction();
                    }
                    return false;
                }
            });
        }
    }

    public void onDialogKeyBackAction() {

        forceCloseDialog();

        // 关闭activity
        mCurrentActivity.finish();
    }

    public void forceCloseDialog() {
        // 清空集合
        mHttpDialogRecoder.clear();
        // 关闭dialog
        closeHttpDialog();
    }

    public Activity getmCurrentActivity() {
        return mCurrentActivity;
    }

    public boolean isDialogNull() {
        return mHttpDialog == null;
    }

    public void clear() {
        currentManager = null;
    }

}

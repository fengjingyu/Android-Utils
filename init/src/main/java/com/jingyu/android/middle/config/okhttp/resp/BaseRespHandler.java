package com.jingyu.android.middle.config.okhttp.resp;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.jingyu.android.basictools.log.Logger;
import com.jingyu.android.init.MainActivity;
import com.jingyu.android.middle.config.okhttp.MyCallback;
import com.jingyu.android.middle.config.okhttp.loading.DialogManager;
import com.jingyu.android.middle.config.okhttp.loading.HttpLoadingDialog;
import com.jingyu.android.middle.config.okhttp.req.MyReqInfo;
import com.jingyu.java.mytool.basic.util.StringUtil;

/**
 * @author fengjingyu@foxmail.com
 */
public abstract class BaseRespHandler<T> extends MyRespHandler<T> {

    private Activity activityContext;

    /**
     * 服务端定义的成功状态码
     **/
    //todo
    public static final String REQ_SUCCESS = "1";

    public BaseRespHandler(Activity activityContext) {
        super();
        this.activityContext = activityContext;
    }

    public BaseRespHandler() {
    }

    @Override
    public void onReadySendRequest(MyReqInfo myReqInfo) {
        super.onReadySendRequest(myReqInfo);
        showDialog(myReqInfo);
    }

    /**
     * 如果显示dialog，则isShowDialog为true 且 activityContext非空
     */
    private void showDialog(MyReqInfo myReqInfo) {
        if (activityContext != null && myReqInfo.isShowDialog()) {
            DialogManager dialogManager = DialogManager.getInstance(activityContext);
            // TODO 设置dialog
            Dialog dialog = new HttpLoadingDialog(activityContext);
            addDialogListener(dialog);
            dialogManager.setDialog(dialog);
            dialogManager.mayShow(toString());
        }
    }

    /**
     * 解析是否成功的规则，根据项目的json而定
     */
    @Override
    public boolean onMatchAppStatusCode(MyReqInfo myReqInfo, MyRespInfo myRespInfo, T resultBean) {
        super.onMatchAppStatusCode(myReqInfo, myRespInfo, resultBean);
        //TODO 解析规则
        if (resultBean instanceof IRespMsgCode) {
            if (StringUtil.equals(((IRespMsgCode) resultBean).getCode(), REQ_SUCCESS)) {
                return true;
            } else {
                statusCodeWrongToast(resultBean);
                return false;
            }
        } else {
            Logger.e(MyCallback.TAG_HTTP, "onMatchAppStatusCode()中的返回结果不是IHttpRespInfo类型", null);
            throw new RuntimeException("onMatchAppStatusCode()中的返回结果不是IHttpRespInfo类型");
        }
    }

    public void statusCodeWrongToast(T resultBean) {
        Logger.shortToast(((IRespMsgCode) resultBean).getMsg());
    }

    @Override
    public void onFailure(MyReqInfo myReqInfo, MyRespInfo myRespInfo) {
        super.onFailure(myReqInfo, myRespInfo);
        failureToast();
    }

    private void failureToast() {
        Logger.shortToast("网络出错啦");
    }

    @Override
    public void onEnd(MyReqInfo myReqInfo, MyRespInfo myRespInfo) {
        super.onEnd(myReqInfo, myRespInfo);
        closeDialog(myReqInfo.isShowDialog());
    }

    private void closeDialog(boolean isShowDialog) {
        if (activityContext != null && isShowDialog) {
            DialogManager.getInstance(activityContext).mayClose(toString());
        }
    }

    public final void addDialogListener(Dialog dialog) {
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    closeDialog(true);
                    //todo
                    if (activityContext != null && !(activityContext instanceof MainActivity)) {
                        activityContext.finish();
                    }
                }
                return false;
            }
        });
    }

}

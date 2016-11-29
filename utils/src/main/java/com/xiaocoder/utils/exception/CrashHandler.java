package com.xiaocoder.utils.exception;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import com.xiaocoder.utils.application.Constants;
import com.xiaocoder.utils.function.helper.ActivityHelper;
import com.xiaocoder.utils.util.UtilIo;
import com.xiaocoder.utils.util.UtilIoAndroid;
import com.xiaocoder.utils.io.LogHelper;
import com.xiaocoder.utils.util.UtilDate;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class CrashHandler implements UncaughtExceptionHandler {

    private static CrashHandler INSTANCE = new CrashHandler();

    private Context mContext;

    /**
     * 异常信息
     */
    private Map<String, String> mInfos = new HashMap<String, String>();
    /**
     * 是否显示异常界面
     */
    private boolean mIsShowExceptionActivity;
    /**
     * 存储SD卡的哪个目录
     */
    private String mCrashDir;
    /**
     * 异常上传接口
     */
    private IException2Server uploadServer;
    /**
     * 异常db
     */
    private ExceptionDb exceptionModelDb;
    /**
     * 存入数据库的时间
     */
    private String tempTime;

    public void setUploadServer(IException2Server uploadServer) {
        this.uploadServer = uploadServer;
    }

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    public ExceptionDb getExceptionModelDb() {
        return exceptionModelDb;
    }

    public CrashHandler init(boolean isInit, Context context, String crashDir, boolean isShowExceptionActivity) {

        if (isInit) {

            mContext = context;

            mIsShowExceptionActivity = isShowExceptionActivity;
            mCrashDir = crashDir;

            exceptionModelDb = ExceptionDb.getInstance(mContext);

            Thread.setDefaultUncaughtExceptionHandler(this);
        }
        return INSTANCE;
    }

    /**
     * 当 UncaughtException 发生时会转入该函数来处理
     * <p/>
     * try catch的异常是不会回调这个方法的
     */
    @Override
    public synchronized void uncaughtException(Thread thread, Throwable ex) {

        // 收集设备参数信息
        collectDeviceInfo(mContext);

        // 设备参数信息 异常信息 写到crash目录的日志文件中
        String info = saveCrashInfo2File(ex);

        // 打印到控制台、写到app目录的log中
        toLogcat(info);

        // 是否打开showExcpetionAcivity
        toShowExceptionActivity(info);

        // 存入数据库，此时还未存userid，子类中根据需要是否存储
        ExceptionBean model = sava2DB(info);

        //上传到服务器
        if (uploadServer != null) {
            uploadServer.uploadException2Server(info, ex, thread, model, exceptionModelDb);
        }

        endException();

    }

    private ExceptionBean sava2DB(String info) {

        ExceptionBean model = new ExceptionBean(
                info,
                tempTime,
                ExceptionBean.UPLOAD_NO,
                "",
                tempTime + Constants.UNDERLINE + UUID.randomUUID()
        );

        if (exceptionModelDb != null) {
            exceptionModelDb.insert(model);
        }

        return model;

    }

    /**
     * 在控制台显示 ，同时写入到log中
     */
    public void toLogcat(String hint) {

        LogHelper.e(hint);

    }

    public static final int QUIT_FREEZE_TIME = 2000;

    public void endException() {

        showToast(mContext, "很抱歉，程序遭遇异常，即将退出！");

        try {
            Thread.sleep(QUIT_FREEZE_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (mContext != null) {
            ActivityHelper.appExit();
        }

    }

    /**
     * 收集设备参数信息
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);

            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                mInfos.put("versionName", versionName);
                mInfos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            LogHelper.e("an error occured when collect package info--", e);
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mInfos.put(field.getName(), field.get(null).toString());
                LogHelper.i(field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                e.printStackTrace();
                LogHelper.e("an error occured when collect crash info--", e);
            }
        }
    }

    /**
     * 保存错误信息到crash目录的文件中 
     */
    public String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : mInfos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();

        String result = writer.toString();
        sb.append(result);

        try {
            tempTime = System.currentTimeMillis() + "";
            String time = UtilDate.format(new Date(), UtilDate.FORMAT_LONG);
            String fileName = "crash-" + time + "-" + tempTime + ".log";

            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                FileOutputStream fos = new FileOutputStream(UtilIoAndroid.createFileInSDCard(mCrashDir, fileName));
                fos.write(("crash=" + fileName + UtilIo.LINE_SEPARATOR + sb.toString()).getBytes());
                fos.close();

            }

            return "crash=" + fileName + UtilIo.LINE_SEPARATOR + sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            LogHelper.e("an error occured while writing file--", e);
        }

        return sb.toString();
    }

    public static final String EXCEPTION_INFO = "exception_info";

    /**
     * 打开一个activity展示异常信息
     */
    public void toShowExceptionActivity(String info) {
        if (mIsShowExceptionActivity) {
            Intent intent = new Intent(mContext, ShowExceptionsActivity.class);
            intent.putExtra(EXCEPTION_INFO, info);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    /**
     * 显示提示信息，需要在线程中显示Toast
     */
    private void showToast(final Context context, final String msg) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();
    }

}

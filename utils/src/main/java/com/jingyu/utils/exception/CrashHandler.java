package com.jingyu.utils.exception;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

import com.jingyu.utils.function.Constants;
import com.jingyu.utils.function.helper.ActivityCollector;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.util.UtilDate;
import com.jingyu.utils.util.UtilIo;
import com.jingyu.utils.util.UtilIoAndr;
import com.jingyu.utils.util.UtilSystem;

import java.io.File;
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
 * @author fengjingyu@foxmail.com
 * @description
 */
public class CrashHandler implements UncaughtExceptionHandler {

    private static CrashHandler INSTANCE = new CrashHandler();

    private Application application;
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
    private String mCrashDirName;
    /**
     * 异常上传接口
     */
    private IException2Server uploadServer;
    /**
     * 异常db
     */
    private ExceptionDb exceptionDb;
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

    public ExceptionDb getExceptionDb() {
        return exceptionDb;
    }

    public CrashHandler init(Application app, boolean isShowExceptionActivity, String crashDirName) {
        application = app;
        mIsShowExceptionActivity = isShowExceptionActivity;
        mCrashDirName = crashDirName;
        exceptionDb = ExceptionDb.getInstance(application);
        Thread.setDefaultUncaughtExceptionHandler(this);

        return INSTANCE;
    }

    /**
     * 当 UncaughtException 发生时会转入该函数来处理
     * <p/>
     * try catch的异常是不会回调这个方法的
     */
    @Override
    public synchronized void uncaughtException(Thread thread, Throwable ex) {
        if (application != null && !UtilSystem.getProcessName(application).contains("crash")) {
            // 收集设备参数信息
            collectDeviceInfo(application);

            // 设备参数信息 异常信息 写到crash目录的日志文件中
            String info = saveCrashInfo2File(ex);

            // 打印到控制台、写到app目录的log中
            toLogcat(info);

            // 存入数据库
            ExceptionInfo model = sava2DB(info);

            // 是否打开showExcpetionAcivity
            toShowExceptionActivity(info);

            //上传到服务器
            if (uploadServer != null) {
                uploadServer.uploadException2Server(info, ex, thread, model, exceptionDb);
            }

            showToast(application, "ProcessName--" + UtilSystem.getProcessName(application) + ", ProcessId--" + UtilSystem.getPid() + UtilIo.LINE_SEPARATOR
                    + ", thread.getName()--" + thread.getName() + ", thread.getId()---" + thread.getId());
        }

        endException();
    }

    private ExceptionInfo sava2DB(String info) {
        ExceptionInfo model = new ExceptionInfo(
                info,
                tempTime,
                ExceptionInfo.UPLOAD_NO,
                "",
                tempTime + Constants.UNDERLINE + UUID.randomUUID()
        );

        if (exceptionDb != null) {
            exceptionDb.insert(model);
        }

        return model;
    }

    /**
     * 在控制台显示 ，同时写入到log中
     */
    public void toLogcat(String hint) {
        Logger.e(hint);
    }

    public void endException() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ActivityCollector.finishAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
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
            Logger.e("an error occured when collect package info--", e);
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mInfos.put(field.getName(), field.get(null).toString());
                Logger.i(field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                e.printStackTrace();
                Logger.e("an error occured when collect crash info--", e);
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

        FileOutputStream fos = null;
        String fileName = null;
        try {
            tempTime = System.currentTimeMillis() + "";
            fileName = "crash-" + UtilDate.format(new Date(), UtilDate.FORMAT_LONG) + "-" + tempTime + ".log";

            File fileInSDCard = UtilIoAndr.createFileInSDCard(mCrashDirName, fileName);
            if (fileInSDCard != null && fileInSDCard.exists()) {
                fos = new FileOutputStream(fileInSDCard);
                fos.write(("crash=" + fileName + UtilIo.LINE_SEPARATOR + sb.toString()).getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("CrashHandler--an error occured while writing file--", e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "crash=" + fileName + UtilIo.LINE_SEPARATOR + sb.toString();
    }

    /**
     * 打开一个activity展示异常信息
     */
    public void toShowExceptionActivity(String info) {
        if (mIsShowExceptionActivity) {
            ShowExceptionsActivity.actionStart(application, info);
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

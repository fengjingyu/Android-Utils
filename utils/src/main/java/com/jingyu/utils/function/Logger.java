package com.jingyu.utils.function;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.jingyu.utils.json.JsonParse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.entity.FileEntity;

import static com.jingyu.utils.function.DirHelper.Internal.getAppDir;

/**
 * @author fengjingyu@foxmail.com
 * @description 1 可以控制频率的吐司
 * 2 输出log到控制台
 * 3 输出log到文件
 * 4 日志的清空
 * <p/>
 * 使用前初始化initLog方法
 */
public class Logger {

    public static final String TAG_SYSTEM_OUT = "System.out";
    /**
     * 可查看如url 参数 返回的json等
     */
    public static final String TAG_HTTP = "http";
    /**
     * 可查看http里每个方法的调用顺序
     */
    public static final String TAG_RESP_HANDLER = "respHandler";
    /**
     * DB的相关操作记录
     */
    public static final String TAG_DB = "db";
    /**
     * 如异常、重要的日志等用该tag，e()方法里就是用了该tag
     */
    public static final String TAG_ALERT = "alert";
    /**
     * 以下两个是临时测试查看的tag
     */
    public static final String TAG_TEMP = "temp";

    private static Options options = new Options();
    /**
     * 记录上一次吐司的时间,屏蔽连续吐司
     */
    private static long recordLastToastTime;

    private static Application application;

    public static class Options {
        /**
         * 调试土司是否显示
         */
        public boolean isShowDebugToast = false;
        /**
         * i类型的日志是否打印到日志文件中,e类型的日志不受该值控制
         */
        public boolean isLog2File = false;
        /**
         * 日志是否输出到控制台
         */
        public boolean isLog2Console = false;
        /**
         * 毫秒内的连续toast不显示
         */
        public int toastTimeGap = 2000;
        /**
         * 字节,缓存文件达到70M就会清空 1024*1024*70 = 73400320
         */
        public long logFileLimitSize = 73400320;
        /**
         * 编码
         */
        public String encoding = "utf-8";
        /**
         * 在sd卡的目录下创建文件夹 例如传“aa/bb”或 "aa"
         */
        public File logDir;
        /**
         * 默认日志文件名
         */
        private String logFileName = "log_file.txt";
        /**
         * 默认临时文件名,比如json很长，有时控制台未必会全部打印出来,则打印到临时文件中查看
         */
        private String tempFileName = "temp_file.txt";

        /**
         * 仅在有sd卡的时候写日志
         */
        public File getLogFile() {
            return DirHelper.createFile(getLogDir(), logFileName);
        }

        /**
         * 仅在有sd卡的时候写日志
         */
        public File getTempFile() {
            return DirHelper.createFile(getLogDir(), tempFileName);
        }

        public File getLogDir() {
            File dir = DirHelper.createDir(logDir);
            if (dir != null) {
                return dir;
            } else {
                return logDir = DirHelper.getAndroidDir(application, "Logger");
            }
        }
    }

    public static Options getOptions() {
        return options;
    }

    public static void initLog(Application application, Options options) {
        Logger.application = application;
        if (options != null) {
            Logger.options = options;
        }
    }

    public static void longToast(Object msg) {
        longToast(false, msg);
    }

    public static void longToast(boolean showImmediately, Object msg) {
        toast(showImmediately, msg, Toast.LENGTH_LONG);
    }

    public static void shortToast(Object msg) {
        shortToast(false, msg);
    }

    public static void shortToast(boolean showImmediately, Object msg) {
        toast(showImmediately, msg, Toast.LENGTH_SHORT);
    }

    /**
     * 调试的toast , 上线前开关关闭
     */
    public static void dShortToast(boolean showImmediately, Object msg) {
        if (options.isShowDebugToast) {
            shortToast(showImmediately, msg);
        }
    }

    /**
     * 调试的toast , 上线前开关关闭
     */
    public static void dShortToast(Object msg) {
        dShortToast(false, msg);
    }

    /**
     * 调试的toast , 上线前开关关闭
     */
    public static void dLongToast(Object msg) {
        dLongToast(false, msg);
    }

    /**
     * 调试的toast , 上线前开关关闭
     */
    public static void dLongToast(boolean showImmediately, Object msg) {
        if (options.isShowDebugToast) {
            longToast(showImmediately, msg);
        }
    }

    public static void dErrorToast(Object msg) {
        if (options.isShowDebugToast) {
            longToast(true, "error--" + msg);
        }
        write2LogFile("error--" + msg, true);
    }

    /**
     * @param isShowImmediately true为立即显示，不受时间的限制
     * @param msg               消息，可以object null
     * @param showtType         土司的类型 如 long short
     */
    private static void toast(boolean isShowImmediately, Object msg, int showtType) {
        if (application == null) {
            return;
        }

        if (isShowImmediately || System.currentTimeMillis() - recordLastToastTime > options.toastTimeGap) {
            Toast.makeText(application, msg + "", showtType).show();
            recordLastToastTime = System.currentTimeMillis();
        }
    }

    /**
     * 以tag打印到控制台 和 文件
     */
    public static void i(Context context, Object msg) {
        if (options.isLog2Console) {
            Log.i(context.getClass().getSimpleName(), msg + "");
        }
        if (options.isLog2File) {
            write2LogFile(context.getClass().getSimpleName() + "---" + msg, true);
        }
    }

    public static void i(String tag, Object msg) {
        if (options.isLog2Console) {
            Log.i(tag, msg + "");
        }
        if (options.isLog2File) {
            write2LogFile(msg + "", true);
        }
    }

    public static void i(Object msg) {
        if (options.isLog2Console) {
            Log.i(TAG_SYSTEM_OUT, msg + "");
        }
        if (options.isLog2File) {
            write2LogFile(msg + "", true);
        }
    }

    public static void temp(Object msg) {
        i(TAG_TEMP, msg);
    }

    /**
     * e不管是否上线，都会打印日志到本地，并输出到控制台
     */
    public static void e(String hint) {
        Log.e(TAG_ALERT, hint);
        write2LogFile("error--" + hint, true);
    }

    public static void e(Context context, String hint) {
        Log.e(TAG_ALERT, context.getClass().getSimpleName() + "--" + hint);
        write2LogFile("error--" + context.getClass().getSimpleName() + "--" + hint, true);
    }

    public static void e(String hint, Exception e) {
        e.printStackTrace();
        Log.e(TAG_ALERT, hint + "--Exception-->" + e.toString() + "--" + e.getMessage());
        write2LogFile("error--" + "Exception-->" + hint + "-->" + e.toString() + "--" + e.getMessage(), true);
    }

    public static void e(Context context, String hint, Exception e) {
        e.printStackTrace();
        Log.e(TAG_ALERT, "Exception-->" + context.getClass().getSimpleName() + "--" + hint + "--" + e.toString() + "--" + e.getMessage());
        write2LogFile("error--" + "Exception-->" + context.getClass().getSimpleName() + "--" + hint + "--" + e.toString() + "--" + e.getMessage(), true);
    }

    /**
     * 删除日志文件
     */
    public static synchronized void deleteLogFile() {
        File file = options.getLogFile();
        if (file != null && file.exists()) {
            file.delete();
        }
    }

    /**
     * 只在有sd卡的时候，才会打印日志
     */
    private static synchronized void write2LogFile(String content, boolean is_append) {

        if (application == null) {
            return;
        }

        if (TextUtils.isEmpty(content)) {
            return;
        }

        RandomAccessFile raf = null;

        try {
            File file = options.getLogFile();

            if (file == null || !file.exists()) {
                return;
            }

            if (file.length() > options.logFileLimitSize || !is_append) {
                // 日志满了
                // 不追加写入
                file.delete();
                file.createNewFile();
            }

            raf = new RandomAccessFile(file, "rw");
            long len = raf.length();
            raf.seek(len);
            raf.write((content + "-->" + DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT).format(new Date()) + "  end  " + System.getProperty("line.separator")).getBytes(options.encoding));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized static void write2TempFile(String content) {
        write2TempFile(content, false);
    }

    public synchronized static void write2TempFile(String content, boolean append) {
        if (options.isLog2File) {

            if (application == null) {
                return;
            }

            FileOutputStream fos = null;
            try {
                File file = options.getTempFile();
                if (file == null || !file.exists()) {
                    return;
                }
                fos = new FileOutputStream(file, append);
                fos.write(content.getBytes());
                fos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 格式化打印json到控制台
     */
    public static void logFormatContent(boolean bindLineAndlLog, String tag, String hint, String str) {
        if (options.isLog2Console) {
            String content = JsonParse.format(str);
            try {
                Log.i(tag, "－－－－－－－－－－－－－－－－－－  " + hint + " MSG BEGIN －－－－－－－－－－－－－－－－－－");
                if (content != null) {
                    if (bindLineAndlLog) {
                        String[] msgLines = content.split("\n");
                        for (String oneLine : msgLines) {
                            Log.i(tag, "| " + oneLine);
                        }
                    } else {
                        Log.i(tag, content);
                    }
                } else {
                    Log.i(tag, "传入logFormatContent()的内容为空");
                }
                Log.i(tag, "－－－－－－－－－－－－－－－－－－  " + hint + "  MSG END －－－－－－－－－－－－－－－－－－");
            } catch (Exception ex) {
                Log.e(TAG_ALERT, "logFormatContent----" + content, ex);
            }
        }
    }

    /**
     * 格式化打印json到控制台
     */
    public static void logFormatContent(String tag, String hint, String content) {
        logFormatContent(false, tag, hint, content);
    }

}
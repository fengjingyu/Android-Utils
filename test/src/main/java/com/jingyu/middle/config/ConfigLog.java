package com.jingyu.middle.config;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public class ConfigLog {

    /**
     * 是否打开调试日志开关 , 上线前，改为CLOSE
     */
    private static DebugControl DEBUG_CONTROL = DebugControl.DEFINE;

    /**
     * OPEN: 默认的配置，开发环境可以用这个值
     * CLOSE：默认的配置，上线版本用这个值
     * DEFINE: 可以修改配置，测试环境可以用这个值
     */
    public enum DebugControl {
        CLOSE, OPEN, DEFINE
    }

    /**
     * 是否打印日志到控制台
     */
    private static boolean IS_LOG_2_CONSOLE;
    /**
     * 是否弹出调试的土司
     */
    private static boolean IS_SHOW_DEBUG_TOAST;
    /**
     * 是否初始化crashHandler,上线前得关
     */
    private static boolean IS_INIT_CRASH_HANDLER;
    /**
     * 是否打印异常的日志到屏幕， 上线前得关
     */
    private static boolean IS_SHOW_EXCEPTION_ACTIVITY;
    /**
     * 是否打印日志到手机文件中,i()中的上线前全部关闭
     */
    private static boolean IS_LOG_2_FILE;

    static {
        if (DEBUG_CONTROL == DebugControl.DEFINE) {

            // i()方法是否打印到控制台
            IS_LOG_2_CONSOLE = true;

            // i()方法是否打印到本地log日志; e()方法都会打印到log日志，不受该值控制
            IS_LOG_2_FILE = false;

            // 调试土司是否开启
            IS_SHOW_DEBUG_TOAST = true;

            // 是否初始化crashhandler
            IS_INIT_CRASH_HANDLER = true;

            // 是否打印出异常界面（只有在IS_INIT_CRASH_HANDLER 为true时，该设置才有效）
            IS_SHOW_EXCEPTION_ACTIVITY = true;

        } else if (DEBUG_CONTROL == DebugControl.OPEN) {

            // i()方法是否打印到控制台
            IS_LOG_2_CONSOLE = true;

            // i()方法是否打印到本地log日志; e()方法都会打印到log日志，不受该值控制
            IS_LOG_2_FILE = true;

            // 调试土司是否开启
            IS_SHOW_DEBUG_TOAST = true;

            // 是否初始化crashhandler
            IS_INIT_CRASH_HANDLER = true;

            // 是否打印出异常界面（只有在IS_INIT_CRASH_HANDLER 为true时，该设置才有效）
            IS_SHOW_EXCEPTION_ACTIVITY = true;

        } else if (DEBUG_CONTROL == DebugControl.CLOSE) {

            // i()方法是否打印到控制台
            IS_LOG_2_CONSOLE = false;

            // i()方法是否打印到本地log日志; e()方法都会打印到log日志，不受该值控制
            IS_LOG_2_FILE = false;

            // 调试土司是否开启
            IS_SHOW_DEBUG_TOAST = false;

            // 是否初始化crashhandler
            IS_INIT_CRASH_HANDLER = true;

            // 是否打印出异常界面（只有在IS_INIT_CRASH_HANDLER 为true时，该设置才有效）
            IS_SHOW_EXCEPTION_ACTIVITY = false;

        } else {
            throw new RuntimeException("没有找到与DEBUG_CONTROL匹配的枚举值");
        }
    }

    public static DebugControl getDebugControl() {
        return DEBUG_CONTROL;
    }

    public static boolean isLog2Console() {
        return IS_LOG_2_CONSOLE;
    }

    public static boolean isShowDebugToast() {
        return IS_SHOW_DEBUG_TOAST;
    }

    public static boolean isInitCrashHandler() {
        return IS_INIT_CRASH_HANDLER;
    }

    public static boolean isShowExceptionActivity() {
        return IS_SHOW_EXCEPTION_ACTIVITY;
    }

    public static boolean isLog2File() {
        return IS_LOG_2_FILE;
    }
}

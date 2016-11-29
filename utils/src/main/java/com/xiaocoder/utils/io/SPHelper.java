package com.xiaocoder.utils.io;

import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * @email fengjingyu@foxmail.com
 * @description 使用前初始化initSP方法
 */

/**
 * @author xiaocoder
 *         2015-2-28 上午11:30:18
 *         <p/>
 *         log("--" + TAG, (sp.getString("09876561", null) == null) + "");//true
 *         sp.edit().putString("123456abcd2", null).commit();
 *         log("--" + TAG, (sp.getString("123456abcd2", "abc") + ""));//abc
 *         log("--" + TAG, (sp.getString("123456abcd2", "abc") == null) + "");//false
 *         log("--" + TAG, (sp.getString("123456abcd2", null) == null) + "");//true
 *         log("--" + TAG, (sp.getString("123456abcd2", null) + "") + "");//null
 *         <p/>
 *         sp.edit().putString("123456789", "1").apply();
 *         sp.edit().putString("123456789", null).apply();
 *         log("--" + TAG, sp.getString("123456789", "abc"));// "abc"
 */

public class SPHelper {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    /**
     * @param context
     * @param fileName 不要加 ".xml"的后缀名
     * @param mode     Context.Mode 下有四种模式常量
     */
    public static void initXCSP(Context context, String fileName, int mode) {
        SPHelper.sharedPreferences = context.getSharedPreferences(fileName, mode);
    }

    public static boolean putString(String key, String value) {
        editor = sharedPreferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static boolean putInt(String key, int value) {
        editor = sharedPreferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static boolean putLong(String key, long value) {
        editor = sharedPreferences.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static boolean putFloat(String key, float value) {
        editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public static boolean putBoolean(String key, boolean value) {
        editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean putStringSet(String key, Set<String> values) {
        editor = sharedPreferences.edit();
        editor.putStringSet(key, values);
        return editor.commit();
    }

    public static String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }


    public static long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public static float getFloat(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }


    public static boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

    public static Set<String> getStringSet(String key, Set<String> defValues) {
        return sharedPreferences.getStringSet(key, defValues);
    }


    public static void spPut(String key, boolean value) {
        putBoolean(key, value);
    }

    public static void spPut(String key, int value) {
        putInt(key, value);
    }

    public static void spPut(String key, long value) {
        putLong(key, value);
    }

    public static void spPut(String key, float value) {
        putFloat(key, value);
    }

    public static void spPut(String key, String value) {
        putString(key, value);
    }

    public static String spGet(String key, String default_value) {
        return getString(key, default_value);
    }

    public static int spGet(String key, int default_value) {
        return getInt(key, default_value);
    }

    public static long spGet(String key, long default_value) {
        return getLong(key, default_value);
    }

    public static boolean spGet(String key, boolean default_value) {
        return getBoolean(key, default_value);
    }

    public static float spGet(String key, float default_value) {
        return getFloat(key, default_value);
    }

}
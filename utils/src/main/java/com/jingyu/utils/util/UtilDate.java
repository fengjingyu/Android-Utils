package com.jingyu.utils.util;

import com.jingyu.utils.function.Constants;
import com.jingyu.utils.function.helper.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UtilDate {

    private static final String TIME_FLAG = "time-----";

    public static String FORMAT_VERY_SHORT = "MM月dd日";

    public static String FORMAT_HH_MM = "HH:mm";

    public static String FORMAT_MM_DD_HH_MM = "MM月dd日 HH:mm";

    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_SHORT = "yyyy-MM-dd";

    /**
     * 英文全称  如：2010-12-01 23:15:06
     */
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";

    /**
     * 精确到毫秒的完整时间    如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";

    public static String FORMAT_FULL_S = "yyyyMMdd_HHmmssS";

    /**
     * 中文简写  如：2010年12月01日
     */
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd";


    /**
     * 029
     * 中文全称  如：2010年12月01日  23时15分06秒
     * 030
     */
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";

    /**
     * 029
     * 中文全称  如：2010年12月01日  23:15分
     * 030
     */
    public static String FORMAT_LONG_CN_1 = "yyyy年MM月dd日 HH:mm";

    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    public static String FORMAT_SHORT_SHORT = "MM-dd  HH:mm";

    public static String FORMAT_SHORT_DATA = "MM/dd";

    /**
     * 把一个时间段,按HH:mm:ss显示
     *
     * @param millisecond 毫秒
     * @return
     */
    public static String formatTime(long millisecond) {
        long h, m, s;
        h = millisecond / 1000 / 60 / 60;
        m = millisecond / 1000 / 60 % 60;
        s = millisecond / 1000 % 60;
        String time = (h < 10 ? "0" + h : h) + ":" +
                (m < 10 ? "0" + m : m) + ":" +
                (s < 10 ? "0" + s : s);
        return time;
    }

    /**
     * 传入一个时间差， 获取剩余时间
     */
    public static String[] getRemainTime(long differ) {
        differ = differ / 1000;
        String[] result = new String[4];
        long days = differ / (60 * 60 * 24); // 天
        long hours = (differ - days * 60 * 60 * 24) / (60 * 60); // 小时
        long minutes = (differ - days * (60 * 60 * 24) - hours * (60 * 60)) / (60); // 分
        long seconds = (differ - (days * 60 * 60 * 24) - (hours * 60 * 60)) - minutes * (60); // 秒

        if (days < 10) {
            result[0] = ("0" + days);
        } else {
            result[0] = (days + "");
        }

        if (hours < 10) {
            result[1] = ("0" + hours);
        } else {
            result[1] = (hours + "");
        }

        if (minutes < 10) {
            result[2] = ("0" + minutes);
        } else {
            result[2] = (minutes + "");
        }

        if (seconds < 10) {
            result[3] = ("0" + seconds);
        } else {
            result[3] = (seconds + "");
        }
        return result;
    }

    public static String getRemainTimeNoDay(long time) {
        time = time / 1000;
        String h = String.valueOf(time / 3600);
        String m = String.valueOf(time % 3600 / 60);
        String s = String.valueOf(time % 3600 % 60);

        if (h.length() == 1) {
            h = "0" + h;
        }
        if (m.length() == 1) {
            m = "0" + m;
        }
        if (s.length() == 1) {
            s = "0" + s;
        }
        return h + ":" + m + ":" + s;
    }

    /**
     * 根据预设格式返回当前日期
     */
    public static String getNow() {
        return format(new Date());
    }

    /**
     * 根据用户格式返回当前日期
     *
     * @param format
     * @return
     */
    public static String getNow(String format) {

        return format(new Date(), format);
    }

    /**
     * 使用预设格式格式化日期
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, getDatePattern());
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */
    public static String format(Date date, String pattern) {

        String returnValue = "";
        if (date != null) {

            SimpleDateFormat df = new SimpleDateFormat(pattern);

            returnValue = df.format(date);

        }

        return (returnValue);
    }

    /**
     * 使用预设格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @return
     */
    public static Date parse(String strDate) {
        return parse(strDate, getDatePattern());
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);

        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 在日期上增加数个整月
     *
     * @param date 日期
     * @param n    要增加的月数
     * @return
     */
    public static Date addMonth(Date date, int n) {

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.add(Calendar.MONTH, n);

        return cal.getTime();
    }

    /**
     * 在日期上增加天数
     *
     * @param date 日期
     * @param n    要增加的天数
     * @return
     */
    public static Date addDay(Date date, int n) {

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.add(Calendar.DATE, n);

        return cal.getTime();
    }

    /**
     * 获取时间戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);

        Calendar calendar = Calendar.getInstance();

        return df.format(calendar.getTime());
    }


    /**
     * 148
     * 获取日期年份
     *
     * @param date 日期
     * @return
     */

    public static String getYear(Date date) {
        return format(date).substring(0, 4);
    }

    /**
     * 按默认格式的字符串距离今天的天数
     *
     * @param date 日期字符串
     *             * @return
     */
    public static int countDays(String date) {

        long t = Calendar.getInstance().getTime().getTime();

        Calendar c = Calendar.getInstance();

        c.setTime(parse(date));

        long t1 = c.getTime().getTime();

        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;

    }


    /**
     * 按用户格式字符串距离今天的天数
     *
     * @param date   日期字符串
     * @param format 日期格式
     * @return
     */

    public static int countDays(String date, String format) {

        long t = Calendar.getInstance().getTime().getTime();

        Calendar c = Calendar.getInstance();

        c.setTime(parse(date, format));

        long t1 = c.getTime().getTime();

        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern() {
        return FORMAT_LONG;
    }

    public static boolean isToday(long time) {
        Date mayTime = new Date(time);
        Date today = new Date();
        String mayData = format(mayTime, FORMAT_SHORT);
        String todayDate = format(today, FORMAT_SHORT);
        if (mayData.equals(todayDate)) {
            return true;
        }
        return false;
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp
     * @return
     */
    @Deprecated
    public static String convertTimeToFormat(long timeStamp) {

        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;

        Logger.i(Constants.TAG_SYSTEM_OUT, time + "---时间差");
        Logger.i(Constants.TAG_SYSTEM_OUT, curTime + "---当前时间");
        Logger.i(Constants.TAG_SYSTEM_OUT, timeStamp + "---timeStamp");

        if (time < 60 && time >= 0) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            return time / 3600 / 24 + "天前";
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 + "个月前";
        } else if (time >= 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 / 12 + "年前";
        } else {
            return "刚刚";
        }
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，如
     * 2分钟内 无显示
     * 2分钟-今天  2分钟-今天 HH:mm
     * 昨天 昨天 HH:mm
     * 前天 前天 HH:mm
     * 今年 MM:DD HH:mm
     * 去年 去年 MM:DD HH:mm
     * 前年 前年 MM:DD HH:mm
     * 更远 yyyy:MM:DD HH:mm
     * 毫秒计算
     *
     * @param time
     * @return
     */
    @Deprecated
    public static synchronized String convertTime(long time) {
        long curTime = System.currentTimeMillis();
        String showTimeFormat = "";
        Date mayTime = new Date(time);
        long temp = curTime - time;
        if (temp < 120 * 1000 && temp >= 0) {
            showTimeFormat = format(mayTime, FORMAT_HH_MM);
            return showTimeFormat;
        }

        // Date today = UtilDate.parse("2015-01-01 02:02:02.001", UtilDate.FORMAT_FULL);
        Date today = new Date();
        //时间值
        String mayTime_FORMAT_SHORT = format(mayTime, FORMAT_SHORT);
        String mayTime_FORMAT_SHORT_YEAR = getYear(mayTime);
        Logger.i(TIME_FLAG, "时间为：" + UtilDate.format(mayTime, UtilDate.FORMAT_FULL));
        if (mayTime_FORMAT_SHORT != null && !mayTime_FORMAT_SHORT.trim().toString().equals("")) {
            //今天的时间yyyy-MM-dd
            String today_str = format(today, FORMAT_SHORT);
            String thisYear_str = getYear(today);

            //昨天的时间 yyyy-MM-dd
            Calendar calLastDay = Calendar.getInstance();
            calLastDay.setTime(today);
            calLastDay.add(Calendar.DAY_OF_YEAR, -1);

            String lastDay = format(calLastDay.getTime(), FORMAT_SHORT);

            //前天的时间 yyyy-MM-dd
            Calendar calPreviousDay = Calendar.getInstance();
            calPreviousDay.setTime(today);
            calPreviousDay.add(Calendar.DAY_OF_YEAR, -2);
            String previousDay = format(calPreviousDay.getTime(), FORMAT_SHORT);

            //去年的时间 yyyy
            Calendar calLastYear = Calendar.getInstance();
            calLastYear.setTime(today);
            calLastYear.add(Calendar.YEAR, -1);
            String lastYear = getYear(calLastYear.getTime());

            //前年的时间 yyyy
            Calendar calPreviousYear = Calendar.getInstance();
            calPreviousYear.setTime(today);
            calPreviousYear.add(Calendar.YEAR, -2);
            String previousYear = getYear(calPreviousYear.getTime());

            //首先判断是否是今天
            if (mayTime_FORMAT_SHORT.equals(today_str)) {
                //今天，则显示为 13:12
                showTimeFormat = format(mayTime, FORMAT_HH_MM);
            } else if (mayTime_FORMAT_SHORT.equals(lastDay)) {
                //昨天
                showTimeFormat = "昨天 " + format(mayTime, FORMAT_HH_MM);
            } else if (mayTime_FORMAT_SHORT.equals(previousDay)) {
                //昨天
                showTimeFormat = "前天 " + format(mayTime, FORMAT_HH_MM);
                Logger.i(TIME_FLAG, "前天:" + showTimeFormat);
            } else if (mayTime_FORMAT_SHORT_YEAR.equals(thisYear_str)) {
                //今年
                showTimeFormat = format(mayTime, FORMAT_MM_DD_HH_MM);
            } else if (mayTime_FORMAT_SHORT_YEAR.equals(lastYear)) {
                //去年
                showTimeFormat = "去年  " + format(mayTime, FORMAT_MM_DD_HH_MM);
                Logger.i(TIME_FLAG, "去年:" + showTimeFormat);

            } else if (mayTime_FORMAT_SHORT_YEAR.equals(previousYear)) {
                //前年
                showTimeFormat = "前年  " + format(mayTime, FORMAT_MM_DD_HH_MM);
            } else {
                //除此以外
                showTimeFormat = format(mayTime, FORMAT_LONG_CN_1);
            }

        }
        return showTimeFormat;
    }
}

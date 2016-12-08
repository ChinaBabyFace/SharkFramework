package com.soaringcloud.kit.box;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * <b>DateTools。</b>
 * <p>
 * <b>详细说明：</b>
 * </p>
 * <!-- 在此添加详细说明 --> 无。
 * <p>
 * <b>修改列表：</b>
 * </p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF">
 * <td>序号</td>
 * <td>作者</td>
 * <td>修改日期</td>
 * <td>修改内容</td>
 * </tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr>
 * <td>1</td>
 * <td>renyuxiang</td>
 * <td>2014年6月14日 下午1:26:08</td>
 * <td>建立类型</td>
 * </tr>
 * <p/>
 * </table>
 *
 * @author renyuxiang
 * @version 1.0
 * @since 1.0
 */
public class DateKit {

    public static final String PATTERN1 = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN2 = "yyyyMMddHHmmss";
    public static final String PATTERN3 = "yyyy-MM-dd";
    public static final String PATTERN4 = "yyyy年MM月dd日";
    public static final String PATTERN5 = "yyyy-MM-dd HH:mm";

    /**
     * <b>stringConvertDateByRegx。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 通过正则表达式将String日期转为Date对象。
     *
     * @param dateString
     * @param regx
     * @return
     */
    public static Date stringConvertDateByRegx(String dateString, String regx) {
        try {
            Pattern GRUP_PATTERN = Pattern.compile(regx);
            if (GRUP_PATTERN.matcher(dateString).lookingAt()) {
                dateString = dateString.substring(dateString.indexOf("(") + 1, dateString.indexOf(")"));
                return new Date(Long.parseLong(dateString));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date stringConvertDateByPattern(String dateStr, String formatStr) {
        if (dateStr == null || formatStr == null) {
        } else {
            try {
                SimpleDateFormat dd = new SimpleDateFormat(formatStr, Locale.getDefault());
                return dd.parse(dateStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * <b>dateConvertStringByYYYYMMDDHHMMSS。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 日期对象按“yyyyMMddHHmmss”的模板转为String。
     *
     * @param date
     * @return
     */
    public static String dateConvertStringByYYYYMMDDHHMMSS(Date date) {
        String dateString = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(PATTERN2, Locale.getDefault());
            dateString = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

    /**
     * <b>dateConvertStringByYYYYMMDDHHMMSS。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 将当前日期对象按“yyyyMMddHHmmss”的模板转为String。
     *
     * @return
     */
    public static String getCurrentTimeByYYYYMMDDHHMMSS() {
        return dateConvertStringByYYYYMMDDHHMMSS(Calendar.getInstance().getTime());
    }

    /**
     * <b>longStringConvertDate。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 将字符型的日期毫秒数直接生成为日期对象。
     *
     * @param longTime
     * @return
     */
    public static Date longStringConvertDate(String longTime) {
        try {
            return new Date(Timestamp.valueOf(longTime).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <b>dateConvertCalendar。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 将日期对象转为Calendar对象。
     *
     * @param date
     * @return
     */
    public static Calendar dateConvertCalendar(Date date) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <b>dateConvertStringByPattern。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 将日期对象按Pattern模板转为String串。
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String dateConvertStringByPattern(Date date, String pattern) {
        String dateString = "";
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
            dateString = format.format(date);
        }
        return dateString;
    }

    public static boolean isSameDay(Calendar cd1, Calendar cd2) {
        return cd1.get(Calendar.YEAR) == cd2.get(Calendar.YEAR) &&
                cd1.get(Calendar.MONTH) == cd2.get(Calendar.MONTH) &&
                cd1.get(Calendar.DAY_OF_MONTH) == cd2.get(Calendar.DAY_OF_MONTH);
    }

    public static Calendar getDayMinTimeCalendar(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    public static Calendar getDayMaxCalendar(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    public static String getDayLogString(Calendar calendar) {
       return calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);
    }
}

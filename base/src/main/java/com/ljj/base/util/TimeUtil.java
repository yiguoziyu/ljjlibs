package com.ljj.base.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 1 on 2017/12/18.
 */

public class TimeUtil {
    //    "yyyy-MM-dd HH:mm:ss"
    //转换当前时间为字符串
    public static String getTime(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    //转换时间戳为字符串
    public static String getTime(Long time, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(time);
    }

    //字符串转成时间戳
    public static Long getTime(String time){
       SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }


    public static String format(long timeStamp) {
        long curTimeMillis = System.currentTimeMillis();
        Date curDate = new Date(curTimeMillis);
        int todayHoursSeconds = curDate.getHours() * 60 * 60;
        int todayMinutesSeconds = curDate.getMinutes() * 60;
        int todaySeconds = curDate.getSeconds();
        int todayMillis = (todayHoursSeconds + todayMinutesSeconds + todaySeconds) * 1000;
        long todayStartMillis = curTimeMillis - todayMillis;
        if (timeStamp >= todayStartMillis) {
            return "今天";
        }
        int oneDayMillis = 24 * 60 * 60 * 1000;
        long yesterdayStartMilis = todayStartMillis - oneDayMillis;
        if (timeStamp >= yesterdayStartMilis) {
            return "昨天";
        }
        long yesterdayBeforeStartMilis = yesterdayStartMilis - oneDayMillis;
        if (timeStamp >= yesterdayBeforeStartMilis) {
            return "前天";
        }
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(timeStamp));
    }

    public static String getTimeStateNew(String long_time) {
        String long_by_13 = "1000000000000";
        String long_by_10 = "1000000000";
        if (Long.valueOf(long_time) / Long.valueOf(long_by_13) < 1) {
            if (Long.valueOf(long_time) / Long.valueOf(long_by_10) >= 1) {
                long_time = long_time + "000";
            }
        }
        Timestamp time = new Timestamp(Long.valueOf(long_time));
        Timestamp now = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        long day_conver = 1000 * 60 * 60 * 24;
        long hour_conver = 1000 * 60 * 60;
        long min_conver = 1000 * 60;
        long time_conver = now.getTime() - time.getTime();
        long temp_conver;
        if ((time_conver / day_conver) < 3) {
            temp_conver = time_conver / day_conver;
            if (temp_conver <= 2 && temp_conver >= 1) {
                return temp_conver + "天前";
            } else {
                temp_conver = (time_conver / hour_conver);
                if (temp_conver >= 1) {
                    return temp_conver + "小时前";
                } else {
                    temp_conver = (time_conver / min_conver);
                    if (temp_conver >= 1) {
                        return temp_conver + "分钟前";
                    } else {
                        return "刚刚";
                    }
                }
            }
        } else {
            return format.format(time);
        }
    }

}

package com.naive.customview01.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * Created by zgyi on 2016/12/1.
 */

public class DateUtils {

    public static Date stringToDate(String source, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = simpleDateFormat.parse(source);
        } catch (ParseException e) {
            Log.e("error", "字符串转换日期异常");
        }
        return date;
    }

    public static String getYesterday(String currentDate) {
        Calendar calendar = Calendar.getInstance();
        Date date = stringToDate(currentDate, "yyyy-MM-dd");
        String yesterday = "";
        try {
            calendar.setTime(date);
            int day = calendar.get(Calendar.DATE);
            calendar.set(Calendar.DATE, day - 1);
            yesterday = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", "getYesterday异常");
        }
        return yesterday;
    }

    public static String getTomorrow(String currentDate) {
        Calendar calendar = Calendar.getInstance();
        Date date = stringToDate(currentDate, "yyyy-MM-dd");
        String tomorrow = "";
        try {
            calendar.setTime(date);
            int day = calendar.get(Calendar.DATE);
            calendar.set(Calendar.DATE, day + 1);
            tomorrow = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", "getTomorrow异常");
        }
        return tomorrow;
    }

    public static ArrayList<String> getDateList(String currentDate, int dayNumber) {
        if (dayNumber == 0 || TextUtils.isEmpty(currentDate)) {
            return null;
        }
        ArrayList<String> dateList = new ArrayList<>();
        String tempDate = currentDate;
        for (int i = 0; i < Math.abs(dayNumber); i++) {
            if (dayNumber > 0) {
                tempDate = getTomorrow(tempDate);
                dateList.add(tempDate);
            } else if (dayNumber < 0) {
                tempDate = getYesterday(tempDate);
                dateList.add(tempDate);
            }
        }
        if (dayNumber < 0) {
            Collections.reverse(dateList);
        }
        return dateList;
    }

    /**
     * 获取星期几
     *
     * @param dateStr 日期 yyyy-MM-dd
     * @return
     */
    public static String getWeekOfDate(String dateStr) {
        Date dt = stringToDate(dateStr, "yyyy-MM-dd");
        String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
}

package com.wuchaooooo.service.zjutsds.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {
    private static final DateFormat SIMPLE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final String YYYYMMDDHHDDSS      = "yyyyMMddHHmmss";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATEPICKER = "yyyy-MM-dd HH:mm";
    public static String getSimpleDate() {
        return SIMPLE_FORMAT.format(new Date());
    }

    public static String getSimpleDateTime() {
        DateFormat dateFormat = new SimpleDateFormat(STANDARD_FORMAT);
        return dateFormat.format(new Date());
    }


    public static String getDateString(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }
    public static String getTimestamp() {
        return toLocaleString(getCurrentTime(), YYYYMMDDHHDDSS);
    }
//    public static void main(String[] args) {
//    	Date d = getDateFromString("05-13 15:08:00","MMDDHHDDSS");
//		System.out.println(d.getTime());
//	}
    
    /**
     * 把日期型转换成字符串形式。
     * 
     * @param date
     *            日期
     * @param dateFormat
     *            日期格式，例如"yyyy/MM/dd"、"yyyy年MM月dd"
     * @return 日期字符串
     */
    public static String toLocaleString(Date date, String dateFormat) {
        if (date == null) {
            return "";
        }

        if (StringUtils.isBlank(dateFormat)) {
            return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(date);
        }

        return new SimpleDateFormat(dateFormat).format(date);
    }
    /**
     * 取得系统当前日期
     */
    public static Date getCurrentTime() {
        return Calendar.getInstance().getTime();
    }
    
    public static Date dateAddDays(Date date, int days) {
        if (date == null) {
            return null;
        }
        Calendar cald = Calendar.getInstance();
        cald.setTime(date);
        cald.add(Calendar.DAY_OF_YEAR, days);
        return cald.getTime();
    }
    
    public static Date getDateFromString(String dateStr, String pattern) {
        if ((pattern == null) || ("".equals(pattern))) {
            pattern = "yyyy-MM-dd";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        return date;
    }
}

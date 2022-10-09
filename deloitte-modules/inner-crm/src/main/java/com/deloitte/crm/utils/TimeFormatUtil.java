package com.deloitte.crm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeFormatUtil {

    /**
     *
     * @param timetype 时间格式
     * @param day      对比今天相差的天数
     * @return
     */
    public static String getDayTime(String timetype , int day){
        SimpleDateFormat spd = new SimpleDateFormat(timetype);
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date()); // 设置今天日期
        // 计算日期
        cal.add(Calendar.DATE,day);
        return spd.format(cal.getTime());
    }

    /**
     *
     * @param timetype 时间格式
     * @param month    对比今天相差的月数
     * @return
     */
    public static String getMonthTime(String timetype, int month){
        SimpleDateFormat spd = new SimpleDateFormat(timetype);
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH,month);
        return spd.format(cal.getTime());
    }

    /**
     *
     * @param timetype 时间格式
     * @param year    对比今天相差的年数
     * @return
     */
    public static String getYearTime(String timetype, int year){
        SimpleDateFormat spd = new SimpleDateFormat(timetype);
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(Calendar.YEAR,year);
        return spd.format(cal.getTime());
    }


    /**
     *  计算2个日期天数差
     * @param starttime
     * @param endtime
     * @return
     */
    public static int between_days(String timetype,String starttime, String endtime) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timetype);// 自定义时间格式

        Calendar calendar_a = Calendar.getInstance();// 获取日历对象
        Calendar calendar_b = Calendar.getInstance();

        Date date_a = null;
        Date date_b = null;

        try {
            date_a = simpleDateFormat.parse(starttime);//字符串转Date
            date_b = simpleDateFormat.parse(endtime);
            calendar_a.setTime(date_a);// 设置日历
            calendar_b.setTime(date_b);
        } catch (ParseException e) {
            e.printStackTrace();//格式化异常
        }

        long time_a = calendar_a.getTimeInMillis();
        long time_b = calendar_b.getTimeInMillis();

        int between_days = (int)(time_b - time_a) / (1000 * 3600 * 24);//计算相差天数

        return between_days;
    }

    /**
     * 传入时间 计算相差天数后得时间
     * @param timetype
     * @param atime
     * @param addnum
     * @return
     */
    public static String getAddTime(String timetype, String atime, int addnum){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timetype);

        Date sultime = null;
        try {
            sultime =  simpleDateFormat.parse(atime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(sultime != null){
            Calendar cal = new GregorianCalendar();
            cal.setTime(sultime);
            cal.add(Calendar.DATE,addnum);
            return simpleDateFormat.format(cal.getTime());
        }else {
            return atime;
        }

    }
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
}

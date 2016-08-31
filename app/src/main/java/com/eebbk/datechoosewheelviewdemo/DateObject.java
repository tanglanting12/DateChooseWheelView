package com.eebbk.datechoosewheelviewdemo;
import com.eebbk.datechoosewheelviewdemo.util.DateTimeUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.util.Log;
import android.widget.NumberPicker;
/**
 * Created by 20254863 on 2016/8/30.
 */

public class DateObject extends Object{
    private int year ;
    private int month;
    private int day;
    private int week;
    private int hour;
    private int minute;
    private String listItem;


    /**
     * 日期对象的4个参数构造器，用于设置日期
     * @param year
     * @param month
     * @param day
     * @author sxzhang
     */
    public DateObject(int year, int month, int day,boolean ifHaveWeek) {
        super();
        this.year = year;
        this.month =month;
        this.day = day ;
        this.week = setWeekDate();

        if(isToday(this.year,this.month,this.day)){
            this.listItem = "今天";
        }
        else {
            if(ifHaveWeek) {
                this.listItem = month + "月" + day + "日 " + getDayOfWeekCN(this.week);
            }
            else {
                this.listItem = month + "月" + day + "日 ";
            }
        }
    }

    private  boolean isToday(int year, int month , int day){
        Calendar nowCalendar = Calendar.getInstance();
       return day== Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }
    /**
     * 设置星期几
     */
    private int setWeekDate() {
        try {
            Calendar calendar = DateTimeUtility
                    .convertDateToCalendar(DateTimeUtility.covertStringToDate(
                            this.year + "-" + (this.month) + "-" + this.day +" "
                                   ,
                            "yyyy-MM-dd "));
            return calendar.get(Calendar.DAY_OF_WEEK);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return 0;
    }
    /**
     * 日期对象的2个参数构造器，用于设置时间
     * @param hour2
     * @param minute2
     * @param isHourType true:传入的是hour; false: 传入的是minute
     * @author sxzhang
     */
    public DateObject(int hour2,int minute2,boolean isHourType) {
        super();
        if(isHourType == true && hour2 != -1){		//设置小时
            if(hour2 > 24){
                this.hour = hour2 % 24;
            }else
                this.hour = hour2;
            this.listItem =  this.hour + "";
        }else if(isHourType == false && minute2 != -1){	//设置分钟
            if(minute2 > 60)
                this.minute = minute2 % 60;
            else
                this.minute = minute2;
            this.listItem =  this.minute + "";
        }
    }
    public DateObject(int year) {
        super();
        this.year = year ;
        this.listItem=  this.year + "";

    }
    public DateObject(int data,String mode){
        super();
        if(mode == "month") {
            this.month = data;
            this.listItem=  this.month + "";
        }
        else if(mode == "day") {
            this.day = data;
            this.listItem=  this.day + "";
        }

    }


    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public int getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;
    }
    public String getListItem() {
        return listItem;
    }
    public void setListItem(String listItem) {
        this.listItem = listItem;
    }


    /**
     * 根据day_of_week得到汉字星期
     * @return
     */
    public static String getDayOfWeekCN(int day_of_week){
        String result = null;
        switch(day_of_week){
            case 1:
                result = "周日";
                break;
            case 2:
                result = "周一";
                break;
            case 3:
                result = "周二";
                break;
            case 4:
                result = "周三";
                break;
            case 5:
                result = "周四";
                break;
            case 6:
                result = "周五";
                break;
            case 7:
                result = "周六";
                break;
            default:
                break;
        }
        return result;
    }
}


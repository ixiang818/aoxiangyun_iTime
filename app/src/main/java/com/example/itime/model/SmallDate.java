package com.example.itime.model;

import java.io.Serializable;

public class SmallDate implements Serializable {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public SmallDate(int year, int month, int day, int hour, int minute, int second) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
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

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public String display_time(){//显示年月日
        String[] month1={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        String display;
        display=month1[month]+"  "+day+" , "+year;
        return display;
    }

    public String display_small_time(){//显示年月日时分秒
        String[] month2={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        String display;
        String time = null;
        if(hour<10&&minute>=10)
            time="0"+hour+":"+minute;
        if(hour>=10&&minute<10)
            time=hour+":0"+minute;
        if(hour<10&&minute<10)
            time="0"+hour+":0"+minute;
        if(hour>=10&&minute>=10)
            time=hour+":"+minute;
        display=month2[month]+"  "+day+" , "+year+"   "+time;
        return display;

    }
}

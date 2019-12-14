package com.example.itime.model;

import java.io.Serializable;
import java.util.Calendar;

public class Date implements Serializable {

    private String title;
    private String description;
    private SmallDate time;
    private int imageId;
    private long time_difference;

    public Date(String title, String description, SmallDate time,int imageId) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.imageId=imageId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SmallDate getTime() {
        return time;
    }

    public void setTime(SmallDate time) {
        this.time = time;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public long getTime_difference() {
        return time_difference;
    }

    public void setTime_difference(long time_difference) {
        this.time_difference = time_difference;
    }

    public String time_difference()//倒数日期
    {
        String difference="";
        Calendar calendar1=Calendar.getInstance();//获得Calendar这个类的实例
        Calendar calendar2=Calendar.getInstance();
        calendar2.set(this.time.getYear(),this.time.getMonth(),this.time.getDay(),time.getHour(),time.getMinute(),time.getSecond());
        long time1 = calendar1.getTimeInMillis();//返回此Calendar以毫秒为单位的时间
        long time2 = calendar2.getTimeInMillis();
        time_difference=time2-time1;//间隔时间
        int between_days= (int) (time_difference/(1000*3600*24));//换算
        if(between_days==0)
            difference="Today";
        else if(between_days>0)
            difference=between_days+" Days";
        else if(between_days<0) {
            between_days = -between_days;
            difference = between_days + " Days\n  Ago";
        }
        return difference;
    }

    public int between_year()
    {
        int difference=0;
        Calendar calendar1=Calendar.getInstance();//获得Calendar这个类的实例
        Calendar calendar2=Calendar.getInstance();
        calendar2.set(this.time.getYear(),this.time.getMonth(),this.time.getDay(),time.getHour(),time.getMinute(),time.getSecond());
        long time1 = calendar1.get(Calendar.YEAR);
        long time2 = calendar2.get(Calendar.YEAR);
        difference=(int)(time2-time1);//间隔
        return difference;
    }

    public int between_month()
    {
        int difference=0;
        Calendar calendar1=Calendar.getInstance();//获得Calendar这个类的实例
        Calendar calendar2=Calendar.getInstance();
        calendar2.set(this.time.getYear(),this.time.getMonth(),this.time.getDay(),time.getHour(),time.getMinute(),time.getSecond());
        long time1 = calendar1.get(Calendar.MONTH);
        long time2 = calendar2.get(Calendar.MONTH);
        difference=(int)(time2-time1);//间隔
        return difference;
    }

    public int between_day()
    {
        int difference=0;
        Calendar calendar1=Calendar.getInstance();//获得Calendar这个类的实例
        Calendar calendar2=Calendar.getInstance();
        calendar2.set(this.time.getYear(),this.time.getMonth(),this.time.getDay(),time.getHour(),time.getMinute(),time.getSecond());
        long time1 = calendar1.get(Calendar.DATE);
        long time2 = calendar2.get(Calendar.DATE);
        difference=(int)(time2-time1);//间隔
        return difference;
    }

    public int between_hour()
    {
        int difference=0;
        Calendar calendar1=Calendar.getInstance();//获得Calendar这个类的实例
        Calendar calendar2=Calendar.getInstance();
        calendar2.set(this.time.getYear(),this.time.getMonth(),this.time.getDay(),time.getHour(),time.getMinute(),time.getSecond());
        long time1 = calendar1.get(Calendar.HOUR);
        long time2 = calendar2.get(Calendar.HOUR);
        difference=(int)(time2-time1);//间隔
        return difference;
    }

    public int between_min()
    {
        int difference=0;
        Calendar calendar1=Calendar.getInstance();//获得Calendar这个类的实例
        Calendar calendar2=Calendar.getInstance();
        calendar2.set(this.time.getYear(),this.time.getMonth(),this.time.getDay(),time.getHour(),time.getMinute(),time.getSecond());
        long time1 = calendar1.get(Calendar.MINUTE);
        long time2 = calendar2.get(Calendar.MINUTE);
        difference=(int)(time2-time1);//间隔
        return difference;
    }

    public int between_sec()
    {
        int difference=0;
        Calendar calendar1=Calendar.getInstance();//获得Calendar这个类的实例
        Calendar calendar2=Calendar.getInstance();
        calendar2.set(this.time.getYear(),this.time.getMonth(),this.time.getDay(),time.getHour(),time.getMinute(),time.getSecond());
        long time1 = calendar1.get(Calendar.SECOND);
        long time2 = calendar2.get(Calendar.SECOND);
        difference=(int)(time2-time1);//间隔
        return difference;
    }
}

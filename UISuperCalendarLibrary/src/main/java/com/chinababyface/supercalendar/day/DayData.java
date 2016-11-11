package com.chinababyface.supercalendar.day;

import java.util.Calendar;

/**
 * Created by renyuxiang on 2016/3/4.
 */
public class DayData<T> {
    private T data;
    private Calendar calendar;

    public DayData(Calendar mCalendar, T mData) {
        setCalendar(mCalendar);
        setData(mData);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}

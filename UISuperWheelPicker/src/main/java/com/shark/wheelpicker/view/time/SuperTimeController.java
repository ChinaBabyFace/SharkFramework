package com.shark.wheelpicker.view.time;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.View;

import com.shark.wheelpicker.R;
import com.shark.wheelpicker.core.SuperController;
import com.shark.wheelpicker.core.WheelVerticalView;
import com.shark.wheelpicker.core.adapter.NumericWheelAdapter;
import com.soaringcloud.kit.box.LogKit;

import java.text.DateFormatSymbols;
import java.util.Calendar;

/**
 * Created by renyuxiang on 2016/12/5.
 */

public class SuperTimeController extends SuperController {
    public static final String WHEEL_AM_PM_KEY = "wheel_am_pm_key";
    public static final String WHEEL_HOUR_KEY = "wheel_hour_key";
    public static final String WHEEL_MINUTE_KEY = "wheel_minute_key";
    public static final long DAY_TIME = 86400000;
    private boolean isFutureAvailable = true;
    private boolean isTime12 = false;
    private boolean isSameDay = false;
    private int baseAmPmIndex = -1;
    private int baseHourIndex;
    private int baseMinIndex;
    private int amPmIndex = -1;
    private int hourIndex;
    private int minIndex;
    private int hourMax = 23;
    private int minMax = 56;
    private Calendar baseCalendar;
    private Calendar currentCalendar;
    private String[] amPmLabelArray;

    public SuperTimeController(Context context) {
        super(context);
        amPmLabelArray = DateFormatSymbols.getInstance().getAmPmStrings();
        currentCalendar = Calendar.getInstance();
    }

    @Override
    public View createView() {
        if (!isFutureAvailable && currentCalendar.getTimeInMillis() > baseCalendar.getTimeInMillis()) {
            currentCalendar = (Calendar) baseCalendar.clone();
        }
        hourIndex = currentCalendar.get(Calendar.HOUR_OF_DAY);
        minIndex = currentCalendar.get(Calendar.MINUTE);
        amPmIndex = currentCalendar.get(Calendar.AM_PM);

        baseAmPmIndex = baseCalendar.get(Calendar.AM_PM);
        baseHourIndex = baseCalendar.get(Calendar.HOUR_OF_DAY);
        baseMinIndex = baseCalendar.get(Calendar.MINUTE);

        isTime12 = !DateFormat.is24HourFormat(getContext());
        isSameDay = isSameDay(currentCalendar, baseCalendar);
        View root = View.inflate(getContext(), R.layout.super_time_picker_layout, null);
        WheelVerticalView wheelAmPm = (WheelVerticalView) root.findViewById(R.id.am_pm_picker);
        WheelVerticalView wheelHour = (WheelVerticalView) root.findViewById(R.id.hour_picker);
        WheelVerticalView wheelMinute = (WheelVerticalView) root.findViewById(R.id.minute_picker);
        if (isTime12) {
            hourIndex %= 12;
            baseHourIndex %= 12;
            getWheelMap().put(WHEEL_AM_PM_KEY, wheelAmPm);
        } else {
            wheelAmPm.setVisibility(View.GONE);
        }
        getWheelMap().put(WHEEL_HOUR_KEY, wheelHour);
        getWheelMap().put(WHEEL_MINUTE_KEY, wheelMinute);
        refreshWheel();
        return root;
    }

    @Override
    public void saveCurrentValue() {
        WheelVerticalView wheelHour = getWheelMap().get(WHEEL_HOUR_KEY);
        NumericWheelAdapter adapterHour = (NumericWheelAdapter) wheelHour.getViewAdapter();
        hourIndex = wheelHour.getCurrentItem() + adapterHour.getMinValue();

        WheelVerticalView wheelMinute = getWheelMap().get(WHEEL_MINUTE_KEY);
        NumericWheelAdapter adapterMinute = (NumericWheelAdapter) wheelMinute.getViewAdapter();
        minIndex = wheelMinute.getCurrentItem() + adapterMinute.getMinValue();

        if (isTime12) {
            WheelVerticalView wheelAmPm = getWheelMap().get(WHEEL_AM_PM_KEY);
            /*1代表下午，则追加12个小时的时间*/
            if (wheelAmPm.getCurrentItem() == 1) {
                LogKit.e(this, "Pm+12");
                amPmIndex = Calendar.PM;
            }
            if (wheelAmPm.getCurrentItem() == 0) {
                LogKit.e(this, "Am-12");
                amPmIndex = Calendar.AM;
            }
        }
    }

    @Override
    public void refreshWheel() {
        minMax = 59;
        LogKit.e(this, "1 hourIndex:" + hourIndex + ",minIndex:" + minIndex + ",amPmIndex:" + amPmIndex);
        LogKit.e(this, "isTime12:" + isTime12);
        if (isTime12) {
            hourMax = 11;
            //如果与基准日期同一天且不允许显示未来时间，则修正大小值
            if (isSameDay && !isFutureAvailable) {
                if (amPmIndex >= baseAmPmIndex) {
                    amPmIndex = baseAmPmIndex;
                    hourMax = baseHourIndex;
                }
                //如果当前值和基准值位于同一个半天且当前小时值大于基准值则进一步修正
                if (amPmIndex == baseAmPmIndex && hourIndex >= hourMax) {
                    hourIndex = hourMax;
                    minMax = baseMinIndex;
                    if (minIndex >= minMax) {
                        minIndex = minMax;
                    }
                }

            } else {

            }
            LogKit.e(this, "2 hourIndex:" + hourIndex + ",minIndex:" + minIndex + ",amPmIndex:" + amPmIndex);
            WheelVerticalView wheelAmPm = getWheelMap().get(WHEEL_AM_PM_KEY);
            notifyArrayWheel(wheelAmPm, amPmIndex, amPmLabelArray);
        } else {
            hourMax = 23;
            //如果与基准日期同一天且不允许显示未来时间，则修正大小值
            if (isSameDay && !isFutureAvailable) {
                hourMax = baseHourIndex;
                if (hourIndex >= hourMax) {
                    hourIndex = hourMax;
                    minMax = baseMinIndex;
                    if (minIndex >= minMax) {
                        minIndex = minMax;
                    }
                }
            } else {
                //否则什么都不用处理
            }
            LogKit.e(this, "3 hourIndex:" + hourIndex + ",minIndex:" + minIndex + ",amPmIndex:" + amPmIndex);
        }

        WheelVerticalView wheelHour = getWheelMap().get(WHEEL_HOUR_KEY);
        notifyNumericWheel(wheelHour, 0, hourMax, hourIndex, "%02d");

        WheelVerticalView wheelMinute = getWheelMap().get(WHEEL_MINUTE_KEY);
        notifyNumericWheel(wheelMinute, 0, minMax, minIndex, "%02d");
    }

    public boolean isSameDay(Calendar current, Calendar base) {
        if (current.get(Calendar.YEAR) != base.get(Calendar.YEAR)) {
            return false;
        }
        if (current.get(Calendar.MONTH) != base.get(Calendar.MONTH)) {
            return false;
        }
        if (current.get(Calendar.DAY_OF_MONTH) != base.get(Calendar.DAY_OF_MONTH)) {
            return false;
        }
        return true;
    }

    public void setFutureAvailable(boolean futureAvailable) {
        isFutureAvailable = futureAvailable;
    }

    public void setDefaultCalendar(Calendar defaultCalendar) {
        this.currentCalendar = defaultCalendar;
    }

    public void setBaseCalendar(Calendar baseCalendar) {
        this.baseCalendar = baseCalendar;
    }

    public Calendar getPickResult() {
        currentCalendar.set(Calendar.HOUR_OF_DAY, hourIndex);
        currentCalendar.set(Calendar.MINUTE, minIndex);
        if (isTime12 && amPmIndex == 1) {
            currentCalendar.add(Calendar.HOUR_OF_DAY, 12);
        }
        return currentCalendar;
    }
}

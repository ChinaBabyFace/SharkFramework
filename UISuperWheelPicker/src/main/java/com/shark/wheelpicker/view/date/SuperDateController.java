package com.shark.wheelpicker.view.date;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import com.shark.wheelpicker.R;
import com.shark.wheelpicker.core.AbstractWheel;
import com.shark.wheelpicker.core.SuperController;
import com.shark.wheelpicker.core.WheelVerticalView;
import com.shark.wheelpicker.core.adapter.NumericWheelAdapter;
import com.shark.wheelpicker.core.callback.OnWheelScrollListener;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by renyuxiang on 2016/12/5.
 */

public class SuperDateController extends SuperController implements OnWheelScrollListener {
    public static final String WHEEL_YEAR_KEY = "wheel_year_key";
    public static final String WHEEL_MONTH_KEY = "wheel_month_key";
    public static final String WHEEL_DAY_KEY = "wheel_day_key";
    private Calendar defaultCalendar;
    private Calendar currentCalendar;
    private int maxYear;
    private int minYear;
    private HashMap<String, WheelVerticalView> wheelMap;

    public SuperDateController(Context context) {
        super(context);
        wheelMap = new HashMap<>();
    }

    @Override
    public View createView() {
        View root = View.inflate(getContext(), R.layout.super_date_picker_layout, null);
        WheelVerticalView wheelYear = (WheelVerticalView) root.findViewById(R.id.year_picker);
        WheelVerticalView wheelMonth = (WheelVerticalView) root.findViewById(R.id.month_picker);
        WheelVerticalView wheelDay = (WheelVerticalView) root.findViewById(R.id.day_picker);
        wheelMap.put(WHEEL_YEAR_KEY, wheelYear);
        wheelMap.put(WHEEL_MONTH_KEY, wheelMonth);
        wheelMap.put(WHEEL_DAY_KEY, wheelDay);
        refreshAllWheel();
        return root;
    }

    public void refreshCurrentCalendar() {
        WheelVerticalView wheelYear = wheelMap.get(WHEEL_YEAR_KEY);
        NumericWheelAdapter adapterYear = (NumericWheelAdapter) wheelYear.getViewAdapter();
        getCurrentCalendar().set(Calendar.YEAR, wheelYear.getCurrentItem() + adapterYear.getMinValue());


        WheelVerticalView wheelMonth = wheelMap.get(WHEEL_MONTH_KEY);
        NumericWheelAdapter adapterMonth = (NumericWheelAdapter) wheelMonth.getViewAdapter();
        getCurrentCalendar().set(Calendar.MONTH, wheelMonth.getCurrentItem() + adapterMonth.getMinValue() - 1);

        WheelVerticalView wheelDay = wheelMap.get(WHEEL_DAY_KEY);
        NumericWheelAdapter adapterDay = (NumericWheelAdapter) wheelDay.getViewAdapter();
        getCurrentCalendar().set(Calendar.DAY_OF_MONTH, wheelDay.getCurrentItem() + adapterDay.getMinValue());
    }

    public void refreshAllWheel() {
        WheelVerticalView wheelYear = wheelMap.get(WHEEL_YEAR_KEY);
        notifyWheel(wheelYear, getMinYear(), getMaxYear(), getCurrentCalendar().get(Calendar.YEAR),"%04d");

        WheelVerticalView wheelMonth = wheelMap.get(WHEEL_MONTH_KEY);
        notifyWheel(wheelMonth, 1, 12, getCurrentCalendar().get(Calendar.MONTH) + 1,"%02d");

        WheelVerticalView wheelDay = wheelMap.get(WHEEL_DAY_KEY);
        notifyWheel(wheelDay, 1, getCurrentMonthDayCount(), getCurrentCalendar().get(Calendar.DAY_OF_MONTH),"%02d");
    }

    public void notifyWheel(WheelVerticalView wheel, int min, int max, int defaultValue,String format) {
        if (min > max) {
            int i = max;
            max = min;
            min = i;
        }
        if (wheel.getViewAdapter() == null) {
            NumericWheelAdapter adapter = new NumericWheelAdapter(getContext(), min, max,format);
            adapter.setTextSize(30);
            adapter.setTextTypeface(Typeface.DEFAULT);
            wheel.setViewAdapter(adapter);
            wheel.addScrollingListener(this);
        }
        NumericWheelAdapter adapter = (NumericWheelAdapter) (wheel.getViewAdapter());
        adapter.setMaxValue(max);
        adapter.setMinValue(min);
        wheel.setCurrentItem(defaultValue - min);
    }

    public void setDefaultCalendar(Calendar calendar) {
        defaultCalendar = (Calendar) calendar.clone();
        setCurrentCalendar((Calendar) defaultCalendar.clone());
    }

    public int getCurrentMonthDayCount() {
        return getCurrentCalendar().getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public Calendar getDefaultCalendar() {
        return defaultCalendar;
    }

    public Calendar getCurrentCalendar() {
        return currentCalendar;
    }

    public void setCurrentCalendar(Calendar currentCalendar) {
        this.currentCalendar = currentCalendar;
    }

    public int getMaxYear() {
        return maxYear;
    }

    public int getMinYear() {
        return minYear;
    }

    public void setMaxYear(int maxYear) {
        this.maxYear = maxYear;
    }

    public void setMinYear(int minYear) {
        this.minYear = minYear;
    }

    @Override
    public void onScrollingStarted(AbstractWheel wheel) {

    }

    @Override
    public void onScrollingFinished(AbstractWheel wheel) {
        refreshCurrentCalendar();
        refreshAllWheel();
    }
}

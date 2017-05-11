package com.shark.wheelpicker.view.date;

import android.content.Context;
import android.view.View;

import com.shark.wheelpicker.R;
import com.shark.wheelpicker.core.SuperController;
import com.shark.wheelpicker.core.WheelVerticalView;
import com.shark.wheelpicker.core.adapter.NumericWheelAdapter;
import com.shark.wheelpicker.core.callback.OnWheelScrollListener;

import java.util.Calendar;

/**
 * Created by renyuxiang on 2016/12/5.
 */

public class SuperDateController extends SuperController implements OnWheelScrollListener {
    public static final String WHEEL_YEAR_KEY = "wheel_year_key";
    public static final String WHEEL_MONTH_KEY = "wheel_month_key";
    public static final String WHEEL_DAY_KEY = "wheel_day_key";
    private Calendar defaultCalendar;
    private Calendar currentCalendar;
    private Calendar maxCalendar;
    private Calendar minCalendar;

    public SuperDateController(Context context) {
        super(context);
        defaultCalendar = Calendar.getInstance();
        currentCalendar = Calendar.getInstance();
        maxCalendar = Calendar.getInstance();
        minCalendar = Calendar.getInstance();
        maxCalendar.add(Calendar.YEAR, 50);
        minCalendar.add(Calendar.YEAR, -50);
    }

    @Override
    public View createView() {
        View root = View.inflate(getContext(), R.layout.super_date_picker_layout, null);
        WheelVerticalView wheelYear = (WheelVerticalView) root.findViewById(R.id.am_pm_picker);
        WheelVerticalView wheelMonth = (WheelVerticalView) root.findViewById(R.id.month_picker);
        WheelVerticalView wheelDay = (WheelVerticalView) root.findViewById(R.id.day_picker);
        getWheelMap().put(WHEEL_YEAR_KEY, wheelYear);
        getWheelMap().put(WHEEL_MONTH_KEY, wheelMonth);
        getWheelMap().put(WHEEL_DAY_KEY, wheelDay);
        refreshWheel();
        return root;
    }

    @Override
    public void saveCurrentValue() {
        WheelVerticalView wheelYear = getWheelMap().get(WHEEL_YEAR_KEY);
        NumericWheelAdapter adapterYear = (NumericWheelAdapter) wheelYear.getViewAdapter();
        getCurrentCalendar().set(Calendar.YEAR, wheelYear.getCurrentItem() + adapterYear.getMinValue());


        WheelVerticalView wheelMonth = getWheelMap().get(WHEEL_MONTH_KEY);
        NumericWheelAdapter adapterMonth = (NumericWheelAdapter) wheelMonth.getViewAdapter();
        getCurrentCalendar().set(Calendar.MONTH, wheelMonth.getCurrentItem() + adapterMonth.getMinValue() - 1);

        WheelVerticalView wheelDay = getWheelMap().get(WHEEL_DAY_KEY);
        NumericWheelAdapter adapterDay = (NumericWheelAdapter) wheelDay.getViewAdapter();
        getCurrentCalendar().set(Calendar.DAY_OF_MONTH, wheelDay.getCurrentItem() + adapterDay.getMinValue());
    }

    @Override
    public void refreshWheel() {

        WheelVerticalView wheelYear = getWheelMap().get(WHEEL_YEAR_KEY);
        notifyNumericWheel(wheelYear, getMinCalendar().get(Calendar.YEAR), getMaxCalendar().get(Calendar.YEAR),
                getCurrentCalendar().get(Calendar.YEAR), "%04d");

        int maxMonth = 12;
        int minMonth = 1;
        int maxDay = getCurrentMonthDayCount();
        int minDay = 1;
        if (getCurrentCalendar().get(Calendar.YEAR) == getMaxCalendar().get(Calendar.YEAR)) {
            maxMonth = getMaxCalendar().get(Calendar.MONTH) + 1;
            if (getCurrentCalendar().get(Calendar.MONTH) == getMaxCalendar().get(Calendar.MONTH)) {
                maxDay = getMaxCalendar().get(Calendar.DAY_OF_MONTH);
            }
        }

        if (getCurrentCalendar().get(Calendar.YEAR) == getMinCalendar().get(Calendar.YEAR)){
            minMonth = getMinCalendar().get(Calendar.MONTH) + 1;
            if (getCurrentCalendar().get(Calendar.MONTH) == getMinCalendar().get(Calendar.MONTH)) {
                minDay = getMinCalendar().get(Calendar.DAY_OF_MONTH);
            }
        }

        WheelVerticalView wheelMonth = getWheelMap().get(WHEEL_MONTH_KEY);
        notifyNumericWheel(wheelMonth, minMonth, maxMonth, getCurrentCalendar().get(Calendar.MONTH) + 1, "%02d");

        WheelVerticalView wheelDay = getWheelMap().get(WHEEL_DAY_KEY);
        notifyNumericWheel(wheelDay, minDay, maxDay, getCurrentCalendar().get(Calendar.DAY_OF_MONTH),
                "%02d");
    }

    public void setDefaultCalendar(Calendar calendar) {
        defaultCalendar = (Calendar) calendar.clone();
        setCurrentCalendar((Calendar) defaultCalendar.clone());
    }

    public int getCurrentMonthDayCount() {
        return getCurrentCalendar().getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public Calendar getCurrentCalendar() {
        return currentCalendar;
    }

    public void setCurrentCalendar(Calendar currentCalendar) {
        this.currentCalendar = currentCalendar;
    }

    public Calendar getMaxCalendar() {
        return maxCalendar;
    }

    public void setMaxCalendar(Calendar maxCalendar) {
        this.maxCalendar = maxCalendar;
    }

    public Calendar getMinCalendar() {
        return minCalendar;
    }

    public void setMinCalendar(Calendar minCalendar) {
        this.minCalendar = minCalendar;
    }
}

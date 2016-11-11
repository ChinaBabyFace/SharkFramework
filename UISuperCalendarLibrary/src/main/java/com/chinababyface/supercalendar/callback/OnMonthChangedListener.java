package com.chinababyface.supercalendar.callback;

import com.chinababyface.supercalendar.month.MonthCardView;

import java.util.Calendar;

/**
 * Created by renyuxiang on 2016/3/4.
 */
public interface OnMonthChangedListener<T extends MonthCardView> {
    void onFocusMonthChanged(Calendar currentCalendar,Calendar startCalendar, Calendar endCalendar, T monthView);
}

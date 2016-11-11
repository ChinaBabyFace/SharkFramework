package com.chinababyface.supercalendar.callback;

import com.chinababyface.supercalendar.day.BaseDayCell;

/**
 * Created by renyuxiang on 2016/3/4.
 */
public interface OnDayCellClickedListener<T extends BaseDayCell> {
    void onClick(T cell);
}

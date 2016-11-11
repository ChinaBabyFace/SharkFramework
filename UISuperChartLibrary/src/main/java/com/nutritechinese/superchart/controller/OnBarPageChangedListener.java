package com.nutritechinese.superchart.controller;

import com.nutritechinese.superchart.bar.BarPageView;

import java.util.Calendar;

/**
 * Created by renyuxiang on 2016/3/22.
 */
public interface OnBarPageChangedListener<T extends BarPageView> {
    void onFocusWeekChanged(Calendar calendar, T pageView);
}

package com.nutritechinese.superchart.bar;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by renyuxiang on 2016/3/15.
 */
public abstract class BarPageView<T extends BarItem> extends View {
    private List<T> barList;
    private int barCount;
    private Calendar calendar;

    public BarPageView(Context context) {
        super(context);
        init();
    }

    public BarPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BarPageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        barList = new ArrayList<>();
    }


    @Override
    protected abstract void onDraw(Canvas canvas);


    public List<T> getBarList() {
        return barList;
    }

    public void setBarList(List<T> barList) {
        this.barList = barList;
    }

    public int getBarCount() {
        return barCount;
    }

    public void setBarCount(int barCount) {
        this.barCount = barCount;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}

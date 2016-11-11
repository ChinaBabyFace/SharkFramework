package com.nutritechinese.superchart.controller;

import android.support.v4.view.ViewPager;

import com.nutritechinese.superchart.bar.BarPageView;
import com.nutritechinese.superchart.common.ChartViewPagerAdapter;
import com.nutritechinese.superchart.common.SlideDirection;

import java.util.Calendar;

/**
 * Created by renyuxiang on 2016/3/16.
 */
public class PageChartController<T extends BarPageView> {
    private ViewPager viewPager;
    private ChartViewPagerAdapter<T> adapter;
    private int mCurrentIndex = 498;
    private SlideDirection mDirection;
    private T[] pageViewArray;
    private Calendar centerCalendar;
    private OnBarPageChangedListener onBarPageChangedlistener;

    public PageChartController(ViewPager viewPager, T[] pageViewArray) {
        setPageViewArray(pageViewArray);
        setViewPager(viewPager);
        init();
    }

    public void init() {
        adapter = new ChartViewPagerAdapter<T>(pageViewArray);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                measureDirection(position);
                updateView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void measureDirection(int arg0) {
        if (arg0 > mCurrentIndex) {
            mDirection = SlideDirection.RIGHT;

        } else if (arg0 < mCurrentIndex) {
            mDirection = SlideDirection.LEFT;
        }
        mCurrentIndex = arg0;
    }

    private void updateView(int arg0) {
        pageViewArray = adapter.getAllItems();
        if (mDirection == SlideDirection.RIGHT) {
            centerCalendar.add(Calendar.DAY_OF_MONTH, 7);
            pageViewArray[arg0 % pageViewArray.length].setCalendar(centerCalendar);
            if (getOnBarPageChangedlistener() != null) {
                getOnBarPageChangedlistener().onFocusWeekChanged(centerCalendar, pageViewArray[arg0 % pageViewArray.length]);
            }
            pageViewArray[arg0 % pageViewArray.length].invalidate();
        } else if (mDirection == SlideDirection.LEFT) {
            centerCalendar.add(Calendar.DAY_OF_MONTH, -7);
            pageViewArray[arg0 % pageViewArray.length].setCalendar(centerCalendar);
            if (getOnBarPageChangedlistener() != null) {
                getOnBarPageChangedlistener().onFocusWeekChanged(centerCalendar, pageViewArray[arg0 % pageViewArray.length]);
            }
            pageViewArray[arg0 % pageViewArray.length].invalidate();
        }
        mDirection = SlideDirection.NO_SILDE;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public T[] getPageViewArray() {
        return pageViewArray;
    }

    public void setPageViewArray(T[] pageViewArray) {
        this.pageViewArray = pageViewArray;
    }

    public OnBarPageChangedListener getOnBarPageChangedlistener() {
        return onBarPageChangedlistener;
    }

    public void setOnBarPageChangedlistener(OnBarPageChangedListener onBarPageChangedlistener) {
        this.onBarPageChangedlistener = onBarPageChangedlistener;
    }

    public Calendar getCenterCalendar() {
        return centerCalendar;
    }

    public void setCenterCalendar(Calendar centerCalendar) {
        this.centerCalendar = centerCalendar;
    }

    public void refresh() {
        pageViewArray = adapter.getAllItems();
        if (getOnBarPageChangedlistener() != null) {
            getOnBarPageChangedlistener().onFocusWeekChanged(centerCalendar, pageViewArray[mCurrentIndex% pageViewArray.length]);
        }
        pageViewArray[mCurrentIndex% pageViewArray.length].invalidate();
    }
}

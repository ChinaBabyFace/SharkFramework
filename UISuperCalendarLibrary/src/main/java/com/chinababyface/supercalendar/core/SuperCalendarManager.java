package com.chinababyface.supercalendar.core;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.chinababyface.supercalendar.callback.OnDayCellClickedListener;
import com.chinababyface.supercalendar.callback.OnMonthChangedListener;
import com.chinababyface.supercalendar.em.CalendarSlideDirection;
import com.chinababyface.supercalendar.month.MonthCardView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ChinaBabyFace on 2016/3/4.
 */
public class SuperCalendarManager<T extends MonthCardView> {
    private Context context;
    private ViewPager mViewPager;
    private int mCurrentIndex = 498;
    private T[] cardViewArray;
    private CalendarViewAdapter<T> adapter;
    private CalendarSlideDirection mDirection = CalendarSlideDirection.NO_SILDE;
    private OnDayCellClickedListener onDayCellClickListener;
    private OnMonthChangedListener onMonthChangedListener;
    private Calendar currentCalendar;

    public SuperCalendarManager(Context context, ViewPager viewPager) {
        this.setContext(context);
        this.mViewPager = viewPager;
//        this.initData();
    }

    public void initData() {
        currentCalendar = Calendar.getInstance();
        adapter = (CalendarViewAdapter<T>) mViewPager.getAdapter();
        cardViewArray = adapter.getAllItems();
        for (int i = 0; i < cardViewArray.length; i++) {
            if (getOnDayCellClickListener() != null) {
                cardViewArray[i].setOnDayCellClickedListener(getOnDayCellClickListener());
            }
            cardViewArray[i].setCurrentCalendar(currentCalendar);
        }
        mViewPager.setCurrentItem(498);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.e("CA", "P:" + position);
                measureDirection(position);
                updateCalendarView(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    public void nextMonth() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    public void preMontn() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }

    public void gotoMonth(Calendar targetCalendar) {
        Calendar showDate = cardViewArray[mViewPager.getCurrentItem() % adapter.getAllItems().length].getCurrentCalendar();
        int nowYear = showDate.get(Calendar.YEAR);
        int nowMonth = showDate.get(Calendar.MONTH);
        int gapYear = Math.abs(targetCalendar.get(Calendar.YEAR) - nowYear);
        int gapMonth = Math.abs(targetCalendar.get(Calendar.MONTH) + 1 - nowMonth);
        int gap = gapYear * 12 + gapMonth;
        int start = mViewPager.getCurrentItem();
        int flag = 1;
        if (nowYear > targetCalendar.get(Calendar.YEAR)
                || ((nowYear == targetCalendar.get(Calendar.YEAR)) && (nowMonth > targetCalendar.get(Calendar.MONTH)))) {
            flag = flag * -1;
        }
        for (int i = 1; i <= gap; i++) {
            mViewPager.setCurrentItem(start + flag * i);
        }
    }

    public void backToday() {
        int start = mViewPager.getCurrentItem();
        int flag = 1;
        if (start == 498) {
            mViewPager.setCurrentItem(start);
        }
        if (start > 498) {
            flag = -1;
            for (int i = 1; i <= Math.abs(start - 498); i++) {
                Log.e("CA", "Turn:" + i);
                mViewPager.setCurrentItem(start + flag * i);
            }
        }
        if (start < 498) {
            for (int i = 1; i <= Math.abs(start - 498); i++) {
                Log.e("CA", "Turn:" + i);
                mViewPager.setCurrentItem(start + flag * i);
            }
        }

    }

    /**
     * 计算方向
     *
     * @param arg0
     */
    private void measureDirection(int arg0) {
        if (arg0 > mCurrentIndex) {
            mDirection = CalendarSlideDirection.RIGHT;

        } else if (arg0 < mCurrentIndex) {
            mDirection = CalendarSlideDirection.LEFT;
        }
        mCurrentIndex = arg0;
    }


    /**
     * <b>getToday。</b>
     * <p>
     * <b>详细说明：</b>
     * </p>
     * <!-- 在此添加详细说明 --> 获取今天的日期。
     */
    public String getToday() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        String currentDate = sdf.format(date); // 当期日期
        int year_c = Integer.parseInt(currentDate.split("-")[0]);
        int month_c = Integer.parseInt(currentDate.split("-")[1]);
        int day_c = Integer.parseInt(currentDate.split("-")[2]);
        return year_c + "/" + month_c + "/" + day_c;
    }


    // 更新日历视图
    private void updateCalendarView(int arg0) {
        cardViewArray = adapter.getAllItems();
        if (mDirection == CalendarSlideDirection.RIGHT) {
            currentCalendar.add(Calendar.MONTH, 1);
            cardViewArray[arg0 % cardViewArray.length].setCurrentCalendar(currentCalendar);
            if (getOnMonthChangedListener() != null) {
                getOnMonthChangedListener().onFocusMonthChanged(currentCalendar,cardViewArray[arg0 % cardViewArray.length].getFirstCalendar(), cardViewArray[arg0 % cardViewArray.length].getEndCalendar(), cardViewArray[arg0 % cardViewArray.length]);
            }
            cardViewArray[arg0 % cardViewArray.length].updateCardView();
        } else if (mDirection == CalendarSlideDirection.LEFT) {
            currentCalendar.add(Calendar.MONTH, -1);
            cardViewArray[arg0 % cardViewArray.length].setCurrentCalendar(currentCalendar);
            if (getOnMonthChangedListener() != null) {
                getOnMonthChangedListener().onFocusMonthChanged(currentCalendar,cardViewArray[arg0 % cardViewArray.length].getFirstCalendar(), cardViewArray[arg0 % cardViewArray.length].getEndCalendar(), cardViewArray[arg0 % cardViewArray.length]);
            }
            cardViewArray[arg0 % cardViewArray.length].updateCardView();
        } else {
        }
        mDirection = CalendarSlideDirection.NO_SILDE;
    }

    public void refresh() {
        if (getOnMonthChangedListener() != null) {
            cardViewArray[mCurrentIndex % cardViewArray.length].setCurrentCalendar(currentCalendar);
            if (getOnMonthChangedListener() != null) {
                getOnMonthChangedListener().onFocusMonthChanged(currentCalendar,cardViewArray[mCurrentIndex % cardViewArray.length].getFirstCalendar(), cardViewArray[mCurrentIndex % cardViewArray.length].getEndCalendar(), cardViewArray[mCurrentIndex % cardViewArray.length]);
            }
            cardViewArray[mCurrentIndex % cardViewArray.length].updateCardView();
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public OnDayCellClickedListener getOnDayCellClickListener() {
        return onDayCellClickListener;
    }

    public void setOnDayCellClickListener(OnDayCellClickedListener onDayCellClickListener) {
        this.onDayCellClickListener = onDayCellClickListener;
    }

    public OnMonthChangedListener getOnMonthChangedListener() {
        return onMonthChangedListener;
    }

    public void setOnMonthChangedListener(OnMonthChangedListener onMonthChangedListener) {
        this.onMonthChangedListener = onMonthChangedListener;
    }
}

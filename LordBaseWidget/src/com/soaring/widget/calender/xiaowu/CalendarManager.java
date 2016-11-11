package com.soaring.widget.calender.xiaowu;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

import com.soaring.widget.calender.common.CalendarViewAdapter;
import com.soaring.widget.calender.common.CustomDate;
import com.soaring.widget.calender.em.CalendarSlideDirection;
import com.soaring.widget.calender.xiaowu.CalendarCard.OnCellClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarManager {

	private Context context;
	private ViewPager mViewPager;
	private int mCurrentIndex = 498;
	private int mCurrent = 498;
	private CalendarCard[] mShowViews;
	private CalendarViewAdapter<CalendarCard> adapter;
	private CalendarSlideDirection mDirection = CalendarSlideDirection.NO_SILDE;
	private OnCellClickListener onCellClickListener;
	private OnPageChangeListener onPageChangeListener;
	private List<Calendar> recordList;
	private Calendar selectCalendar;

	private String currentDate = new SimpleDateFormat("yyyy-M-d").format(new Date());
	private int year_c = Integer.parseInt(currentDate.split("-")[0]);
	private int month_c = Integer.parseInt(currentDate.split("-")[1]);
	private int day_c;


	public CalendarManager(Context context, ViewPager viewPager) {
		this.setContext(context);
		this.mViewPager = viewPager;
	}

	public void initData() {
		CalendarCard[] views = new CalendarCard[3];
		for (int i = 0; i < 3; i++) {
			if (getOnCellClickListener() != null) {
				views[i] = new CalendarCard(getContext(), getOnCellClickListener());
			} else {
				views[i] = new CalendarCard(getContext());
			}
		}
		adapter = new CalendarViewAdapter<CalendarCard>(views);
		mShowViews = adapter.getAllItems();
		setViewPager();
	}

	private void setViewPager() {
		mViewPager.setAdapter(adapter);
		mViewPager.setCurrentItem(498);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				Log.e("CA", "P:" + position);
				measureDirection(position);
				updateCalendarView(position);

				if (getOnPageChangeListener() != null) {
					getOnPageChangeListener().onPageSelected(position);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				if (getOnPageChangeListener() != null) {
					getOnPageChangeListener().onPageScrolled(arg0, arg1, arg2);
				}
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				if (getOnPageChangeListener() != null) {
					getOnPageChangeListener().onPageScrollStateChanged(arg0);
				}
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
		@SuppressWarnings("static-access")
		CustomDate showDate = (adapter.getAllItems())[mViewPager.getCurrentItem() % adapter.getAllItems().length].getmShowDate();
		int nowYear = showDate.year;
		int nowMonth = showDate.month;
		int gapYear = Math.abs(targetCalendar.get(Calendar.YEAR) - nowYear);
		int gapMonth = Math.abs(targetCalendar.get(Calendar.MONTH) + 1 - nowMonth);
		int gap = gapYear * 12 + gapMonth;
		int start = mViewPager.getCurrentItem();
		int flag = 1;
		if (nowYear > targetCalendar.get(Calendar.YEAR)
				|| ((nowYear == targetCalendar.get(Calendar.YEAR)) && (nowMonth > targetCalendar.get(Calendar.MONTH)))) {
			flag = flag * -1;
		}
		Log.e("CA", "nowYear:" + nowYear + ",nowMonth:" + nowMonth + ",gapYear:" + gapYear + ",gapMonth:" + gapMonth + ",gap:" + gap + ",start:"
				+ start + ",flag:" + flag);
		for (int i = 1; i <= gap; i++) {
			Log.e("CA", "Turn:" + i);
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
	 * <b>getCurrentMonthDay。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> 获取当前显示的日期。
	 * 
	 * @param arg0
	 * @return
	 */
	public String getCurrentMonthDay(int arg0) {
		day_c = Integer.parseInt(currentDate.split("-")[2]);
		if (arg0 > mCurrent) {
			month_c++;
			if (month_c >= 13) {
				month_c = 1;
				year_c++;
			}

		} else if (arg0 < mCurrent) {

			month_c--;
			if (month_c <= 0) {
				month_c = 12;
				year_c--;
			}

		}
		mCurrent = arg0;
		if (getSelectCalendar() != null && year_c == getSelectCalendar().get(Calendar.YEAR)
				&& (month_c - 1) == getSelectCalendar().get(Calendar.MONTH)) {
			day_c = getSelectCalendar().get(Calendar.DAY_OF_MONTH);
		}
		return year_c + "/" + month_c + "/" + day_c;

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
		mShowViews = adapter.getAllItems();
		if (mDirection == CalendarSlideDirection.RIGHT) {
			mShowViews[arg0 % mShowViews.length].rightSlide();
		} else if (mDirection == CalendarSlideDirection.LEFT) {
			mShowViews[arg0 % mShowViews.length].leftSlide();
		} else {
		}
		mDirection = CalendarSlideDirection.NO_SILDE;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public OnCellClickListener getOnCellClickListener() {
		return onCellClickListener;
	}

	public void setOnCellClickListener(OnCellClickListener onCellClickListener) {
		this.onCellClickListener = onCellClickListener;
	}

	public OnPageChangeListener getOnPageChangeListener() {
		return onPageChangeListener;
	}

	public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
		this.onPageChangeListener = onPageChangeListener;
	}

	public List<Calendar> getRecordList() {
		return recordList;
	}

	public Calendar getSelectCalendar() {
		return selectCalendar;
	}

	public void setSelectCalendar(Calendar selectCalendar) {
		this.selectCalendar = selectCalendar;
		for (int i = 0; i < mShowViews.length; i++) {
			mShowViews[i].setCalendar(selectCalendar);
			mShowViews[i].update();
		}
	}

	public void setRecordList(List<Calendar> recordList) {
		this.recordList = recordList;
		for (int i = 0; i < mShowViews.length; i++) {
			mShowViews[i].setRecordList(recordList);
			mShowViews[i].update();
		}
	}

}

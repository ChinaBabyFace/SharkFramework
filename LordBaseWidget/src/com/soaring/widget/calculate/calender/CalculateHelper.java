package com.soaring.widget.calculate.calender;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

import com.soaring.widget.calculate.calender.CalculateCard.OnCellClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalculateHelper {
	public int viewpagerDefaultItem = 498;
	private Context context;
	private ViewPager mViewPager;
	private int mCurrentIndex = viewpagerDefaultItem;
	private int mCurrent = viewpagerDefaultItem;
	private CalculateCard[] mShowViews;
	private CalculateViewAdapter<CalculateCard> adapter;
	private SildeDirection mDirection = SildeDirection.NO_SILDE;
	private OnCellClickListener onCellClickListener;
	private OnPageChangeListener onPageChangeListener;
	private List<Calendar> recordList;
	private Calendar selectCalendar;
	private int averagePeriodDays;
	private int menstrualPeriodDays;

	private String currentDate = new SimpleDateFormat("yyyy-M-d").format(new Date());
	private int year_c = Integer.parseInt(currentDate.split("-")[0]);
	private int month_c = Integer.parseInt(currentDate.split("-")[1]);
	private int day_c;

	enum SildeDirection {
		RIGHT, LEFT, NO_SILDE
	}

	public CalculateHelper(Context context, ViewPager viewPager) {
		this.setContext(context);
		this.mViewPager = viewPager;
	}

	public void initData() {
		CalculateCard[] views = new CalculateCard[3];
		for (int i = 0; i < 3; i++) {
			if (getOnCellClickListener() != null) {
				views[i] = new CalculateCard(getContext(), getOnCellClickListener());
			} else {
				views[i] = new CalculateCard(getContext());
			}
		}
		adapter = new CalculateViewAdapter<CalculateCard>(views);
		mShowViews = adapter.getAllItems();
		setViewPager();
	}

	private void setViewPager() {
		mViewPager.setAdapter(adapter);
		mViewPager.setCurrentItem(viewpagerDefaultItem);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
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
		CalculateCustomDate showDate = (adapter.getAllItems())[mViewPager.getCurrentItem() % adapter.getAllItems().length].getmShowDate();
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
		if (start == viewpagerDefaultItem) {
			mViewPager.setCurrentItem(start);
		}
		if (start > viewpagerDefaultItem) {
			flag = -1;
			for (int i = 1; i <= Math.abs(start - viewpagerDefaultItem); i++) {
				Log.e("CA", "Turn:" + i);
				mViewPager.setCurrentItem(start + flag * i);
			}
		}
		if (start < viewpagerDefaultItem) {
			for (int i = 1; i <= Math.abs(start - viewpagerDefaultItem); i++) {
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
			mDirection = SildeDirection.RIGHT;

		} else if (arg0 < mCurrentIndex) {
			mDirection = SildeDirection.LEFT;
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
		return year_c + "年" + month_c +"月" ;

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

	/**
	 * <b>getRowNumber。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> 获取日期的行数。
	 * 
	 * @param date
	 * @return
	 */
	public int getRowNumber(CalculateCustomDate date) {
		int rownum = 0;
		int dayFromDate = CalculateDateUtil.getWeekDayFromDate(date.year, date.month);
		String format = new SimpleDateFormat("yyyy-M-d").format(date);
		int day = Integer.parseInt(format.split("-")[2]);
		switch ((day + dayFromDate) / 7) {
		case 0:
			rownum = 0;
			break;
		case 1:
			rownum = 1;
			break;
		case 2:
			rownum = 2;
			break;
		case 3:
			rownum = 3;
			break;
		case 4:
			rownum = 4;
			break;
		case 5:
			rownum = 5;
			break;
		case 6:
			rownum = 6;
			break;

		default:
			break;
		}
		return rownum;
	}

	/**
	 * <b>getColumnNumber。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> 获取日期的列数。
	 * 
	 * @param date
	 * @return
	 */
	public int getColumnNumber(CalculateCustomDate date) {
		int rownum = 0;
		int dayFromDate = CalculateDateUtil.getWeekDayFromDate(date.year, date.month);
		String format = new SimpleDateFormat("yyyy-M-d").format(date);
		int day = Integer.parseInt(format.split("-")[2]);
		switch ((day + dayFromDate) % 7) {
		case 0:
			rownum = 0;
			break;
		case 1:
			rownum = 1;
			break;
		case 2:
			rownum = 2;
			break;
		case 3:
			rownum = 3;
			break;
		case 4:
			rownum = 4;
			break;
		case 5:
			rownum = 5;
			break;
		case 6:
			rownum = 6;
			break;

		default:
			break;
		}
		return rownum;
	}

	// 更新日历视图
	private void updateCalendarView(int arg0) {
		mShowViews = adapter.getAllItems();
		if (mDirection == SildeDirection.RIGHT) {
			Log.e("CA", "R");
			mShowViews[arg0 % mShowViews.length].rightSlide();
		} else if (mDirection == SildeDirection.LEFT) {
			Log.e("CA", "L");
			mShowViews[arg0 % mShowViews.length].leftSlide();
		} else {
			Log.e("CA", "N");
		}
		mDirection = SildeDirection.NO_SILDE;
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

	public void setMenstrualPeriodCalendar(Calendar selectCalendar){
		this.selectCalendar = selectCalendar;
		for (int i = 0; i < mShowViews.length; i++) {
			mShowViews[i].setCalendar(selectCalendar);
			mShowViews[i].setAveragePeriodDays(averagePeriodDays);
			mShowViews[i].setMenstrualPeriodDays(menstrualPeriodDays);
			mShowViews[i].update();
		}
	}
	
	public int getAveragePeriodDays() {
		return averagePeriodDays;
	}

	public void setAveragePeriodDays(int averagePeriodDays) {
		this.averagePeriodDays = averagePeriodDays;
	}

	public int getMenstrualPeriodDays() {
		return menstrualPeriodDays;
	}

	public void setMenstrualPeriodDays(int menstrualPeriodDays) {
		this.menstrualPeriodDays = menstrualPeriodDays;
	}
}

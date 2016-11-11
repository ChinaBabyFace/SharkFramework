
package com.soaring.widget.wheelpicker.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.soaring.widget.R;
import com.soaring.widget.wheelpicker.core.AbstractWheel;
import com.soaring.widget.wheelpicker.core.OnWheelChangedListener;
import com.soaring.widget.wheelpicker.core.OnWheelScrollListener;
import com.soaring.widget.wheelpicker.core.adapter.ArrayWheelAdapter;
import com.soaringcloud.kit.box.DisplayKit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateTimePicker2 {

	public static final int DATE_PICKER = 1;
	public static final int TIME_PICKER = 2;
	public static final int DATE_TIME_PICKER = 3;

	private OnPositiveButtonClick onPositiveButtonClick;
	private Context context;
	private View layout;
	private PopupWindow window;
	private AbstractWheel datePickerYear;
	private AbstractWheel datePickerMonth;
	private AbstractWheel datePickerDate;
	private AbstractWheel datePickerHour;
	private AbstractWheel datePickerMinute;
	private TextView pickerTitle;
	private TextView btnOK;
	private TextView btnCancel;
	private boolean scrolling = false;

	/**
	 * 弹出窗口标题
	 */
	private String title;

	/**
	 * 弹出窗口宽度， 默认为Match Parent
	 */
	private int windowWidth = -1;

	/**
	 * 弹出窗口高度
	 */
	private int windowHeight;

	/**
	 * 弹出窗口是否可获得焦点，默认为false
	 */
	private boolean focusable = false;

	/**
	 * 指定时间往前计算的年份下限，默认为30年
	 */
	private int currentYearBefroe = 30;

	/**
	 * 指定时间往后计算的年份上限，默认为30年
	 */
	private int currentYearAfter = 30;

	/**
	 * 日期下限
	 */
	private Date minDate;

	/**
	 * 日期上限
	 */
	private Date maxDate;

	/**
	 * 指定的当前时间
	 */
	private Calendar currentDate;

	/**
	 * 拾取器类型
	 */
	private int pickerType;

	/**
	 * 可以设置日期为之前的日期
	 */
	private boolean canSetDateBefore = true;

	/**
	 * 默认的日期输出格式 yyyy-MM-dd
	 */
	private DateFormat defaultDateFormat;

	private boolean isInited = false;
	private int yearIndex;
	private int monthIndex;
	private int dateIndex;
	private int hourIndex;
	private int minuteIndex;
	private String[] yearLabel;
	private String[] monthLabel = new String[12];
	private String[] dateLabel = new String[31];
	private String[] hourLabel = new String[24];
	private String[] minuteLabel = new String[60];
	private int[] yearValue;
	private int[] monthValue = new int[12];
	private int[] dateValue = new int[31];
	private int[] hourValue = new int[24];
	private int[] minuteValue = new int[60];
	private Calendar calendarTool = Calendar.getInstance();
	
	private int lastYearIndex;
	private int lastMonthIndex;
	private int lastDateIndex;
	private int lastHourIndex;
	private int lastMinuteIndex;

	/**
	 * 
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 基本的构造方法
	 * @param context
	 * @param pickerType
	 */
	public DateTimePicker2(Context context, int pickerType, boolean canSetDateBefore, String pickerTitle) {
		this.context = context;
		this.currentDate = Calendar.getInstance(Locale.CHINA);
		this.pickerType = pickerType;
		this.title = pickerTitle;
		this.canSetDateBefore = canSetDateBefore;
		windowHeight = DisplayKit.getScaleValue((Activity) context, 210);
	}

	/**
	 * 
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 指定了日期上下限的构造方法
	 * @param context
	 * @param pickerType
	 * @param minDate
	 * @param maxDate
	 */
	public DateTimePicker2(Context context, int pickerType, boolean canSetDateBefore, String pickerTitle, Date minDate, Date maxDate) {
		this(context, pickerType, canSetDateBefore, pickerTitle);
		this.minDate = minDate;
		this.maxDate = maxDate;
	}
	
	/**
	 * 
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 指定了日期上下限和默认时间的构造方法
	 * @param context
	 * @param pickerType
	 * @param canSetDateBefore
	 * @param pickerTitle
	 * @param currentDate
	 * @param minDate
	 * @param maxDate
	 */
	public DateTimePicker2(Context context, int pickerType, boolean canSetDateBefore, String pickerTitle, Date currentDate, Date minDate, Date maxDate) {
		this(context, pickerType, canSetDateBefore, pickerTitle);
		this.currentDate.setTime(currentDate);
		this.minDate = minDate;
		this.maxDate = maxDate;
	}

	/**
	 * 
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 指定了当前日期和当前日期前后区间的构造方法
	 * @param context
	 * @param pickerType
	 * @param currentDate
	 * @param currentYearBefroe
	 * @param currentYearAfter
	 */
	public DateTimePicker2(Context context, int pickerType, boolean canSetDateBefore, String pickerTitle, Date currentDate, int currentYearBefroe, int currentYearAfter) {
		this(context, pickerType, canSetDateBefore, pickerTitle);
		this.currentDate.setTime(currentDate);
		this.currentYearBefroe = currentYearBefroe;
		this.currentYearAfter = currentYearAfter;
	}

	/**
	 * 
	 * <b>initData。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 初始化数据
	 */
	private void initData() {
		switch (pickerType) {
			case DATE_PICKER:
				defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
				currentDate.set(Calendar.HOUR_OF_DAY, 0);
				currentDate.set(Calendar.MINUTE, 0);
				currentDate.set(Calendar.MILLISECOND, 0);
				initYear();
				initDate();
				break;
			case DATE_TIME_PICKER:
				defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
				currentDate.set(Calendar.MILLISECOND, 0);
				initYear();
				initDate();
				initTimeData();
				break;
			case TIME_PICKER:
				defaultDateFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
				initTimeData();
				break;
		}
	}

	private void reloadData() {
		if (isInited) {
			initYear();
			monthIndex = currentDate.get(Calendar.MONTH);
			dateIndex = currentDate.get(Calendar.DAY_OF_MONTH) - 1;
		}
	}

	/**
	 * 
	 * <b>initYear。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 初始化日期数据（年）
	 */
	private void initYear() {
		isInited = true;
		Calendar minCalendar = Calendar.getInstance(Locale.CHINA);
		Calendar maxCalendar = Calendar.getInstance(Locale.CHINA);
		int currentYear = currentDate.get(Calendar.YEAR);
		int minYear = this.currentYearBefroe;
		int maxYear = this.currentYearAfter;
		if (null != minDate) {
			minCalendar.setTime(minDate);
			minYear = minCalendar.get(Calendar.YEAR);
		} else {
			minYear = currentYear - this.currentYearBefroe;
		}
		if (null != maxDate) {
			maxCalendar.setTime(maxDate);
			maxYear = maxCalendar.get(Calendar.YEAR);
		} else {
			maxYear = currentYear + this.currentYearAfter;
		}
		if (minYear > maxYear) {
			int i = maxYear;
			maxYear = minYear;
			minYear = i;
		}
		int range = maxYear - minYear + 1;
		yearIndex = 0;
		yearLabel = new String[range];
		yearValue = new int[range];
		if (maxYear < currentYear) {
			yearIndex = range - 1;
		}
		for (int index = 0; index < range; index++) {
			yearLabel[index] = String.valueOf(minYear + index) + "年";
			yearValue[index] = minYear + index;
			if ((minYear + index) == currentYear) yearIndex = index;
		}
	}

	/**
	 * 
	 * <b>initDate。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 初始化日期数据（月、日）
	 */
	private void initDate() {
		DateFormat monthFormat;
		DateFormat dateFormat;
		Calendar calendarForInit = Calendar.getInstance();
		monthIndex = calendarForInit.get(Calendar.MONTH);
		dateIndex = calendarForInit.get(Calendar.DAY_OF_MONTH) - 1;
		monthIndex = currentDate.get(Calendar.MONTH);
		dateIndex = currentDate.get(Calendar.DAY_OF_MONTH) - 1;
		switch (pickerType) {
			case DATE_PICKER:
				monthFormat = new SimpleDateFormat("M月", Locale.CHINA);
				dateFormat = new SimpleDateFormat("d日", Locale.CHINA);
				break;
			case DATE_TIME_PICKER:
				monthFormat = new SimpleDateFormat("M月", Locale.CHINA);
				dateFormat = new SimpleDateFormat("d日", Locale.CHINA);
				break;
			default:
				monthFormat = new SimpleDateFormat("MM", Locale.CHINA);
				dateFormat = new SimpleDateFormat("dd", Locale.CHINA);
		}
		calendarForInit.set(Calendar.MONTH, 0);
		for (int index = 0; index < 12; index++) {
			monthLabel[index] = monthFormat.format(calendarForInit.getTime());
			monthValue[index] = index;
			calendarForInit.set(Calendar.MONTH, index + 1);
		}
		calendarForInit.set(Calendar.DAY_OF_MONTH, 1);
		for (int index = 1; index <= 31; index++) {
			dateLabel[index - 1] = dateFormat.format(calendarForInit.getTime());
			dateValue[index - 1] = index;
			calendarForInit.set(Calendar.DAY_OF_MONTH, index + 1);
		}
	}

	/**
	 * 
	 * <b>initTimeData。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 初始化时间数据
	 */
	private void initTimeData() {
		Calendar calendarForInit = Calendar.getInstance();
		hourIndex = calendarForInit.get(Calendar.HOUR_OF_DAY);
		minuteIndex = calendarForInit.get(Calendar.MINUTE);
		for (int index = 0; index < 24; index++) {
			hourLabel[index] = String.format("%02d", index);
			hourValue[index] = index;
		}
		for (int index = 0; index < 60; index++) {
			minuteLabel[index] = String.format("%02d", index);
			minuteValue[index] = index;
		}
	}

	/**
	 * 
	 * <b>checkDateOrder。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 检查日期，所选日期不能早于当前日期
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	private boolean checkDateOrder(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis() >= currentDate.getTimeInMillis();
	}
	
	private boolean checkDateOrder(int year, int month, int day, int hour, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, hour, minute, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis() >= currentDate.getTimeInMillis();
	}
	
	private boolean checkMinDate(int year, int month, int day) {
		if(null != minDate){
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, day, 0, 0, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return (calendar.getTimeInMillis() < minDate.getTime());
		}
		return false;
	}
	
//	private boolean checkMinDate(int year, int month, int day, int hour, int minute) {
//		if(null != minDate){
//			Calendar calendar = Calendar.getInstance();
//			calendar.set(year, month, day, hour, minute, 0);
//			calendar.set(Calendar.MILLISECOND, 0);
//			if (calendar.getTimeInMillis() < minDate.getTime()) return false;
//			return true;
//		}
//		return false;
//	}
	
	private boolean checkMaxDate(int year, int month, int day) {
		if(null != maxDate){
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, day, 0, 0, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return (calendar.getTimeInMillis() > maxDate.getTime());
		}
		return false;
	}
	
//	private boolean checkMaxDate(int year, int month, int day, int hour, int minute) {
//		if(null != minDate){
//			Calendar calendar = Calendar.getInstance();
//			calendar.set(year, month, day, hour, minute, 0);
//			calendar.set(Calendar.MILLISECOND, 0);
//			if (calendar.getTimeInMillis() < maxDate.getTime()) return false;
//			return true;
//		}
//		return false;
//	}

	/**
	 * 
	 * <b>checkDateLegal。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 检查所选日期是否合法
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	private boolean checkDateLegal(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		return calendar.get(Calendar.MONTH) == month && calendar.get(Calendar.DAY_OF_MONTH) == day;
	}

	/**
	 * 
	 * <b>updateDatePicker。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 更新日期拾取器
	 */
	private void updateDatePicker() {
		calendarTool.clear();
		if (datePickerDate.getCurrentItem() > 27) {
			if (!checkDateLegal(yearValue[datePickerYear.getCurrentItem()], monthValue[datePickerMonth.getCurrentItem()], dateValue[datePickerDate.getCurrentItem()])) {
				calendarTool.set(yearValue[datePickerYear.getCurrentItem()], monthValue[datePickerMonth.getCurrentItem()], dateValue[datePickerDate.getCurrentItem()]);
				monthIndex = calendarTool.get(Calendar.MONTH);
				dateIndex = 0;
				datePickerMonth.setCurrentItem(monthIndex, true);
				datePickerDate.setCurrentItem(dateIndex, true);
			} else {
				yearIndex = datePickerYear.getCurrentItem();
				monthIndex = datePickerMonth.getCurrentItem();
				dateIndex = datePickerDate.getCurrentItem();
			}
		} else {
			yearIndex = datePickerYear.getCurrentItem();
			monthIndex = datePickerMonth.getCurrentItem();
			dateIndex = datePickerDate.getCurrentItem();
		}
		if(checkMinDate(yearValue[yearIndex], monthValue[monthIndex], dateValue[dateIndex])){
			calendarTool.clear();
			calendarTool.setTime(minDate);
			monthIndex = calendarTool.get(Calendar.MONTH);
			dateIndex = calendarTool.get(Calendar.DAY_OF_MONTH) - 1;
			yearIndex = 0;
			datePickerYear.setCurrentItem(yearIndex, true);
			datePickerMonth.setCurrentItem(monthIndex, true);
			datePickerDate.setCurrentItem(dateIndex, true);
		}
		if(checkMaxDate(yearValue[yearIndex], monthValue[monthIndex], dateValue[dateIndex])){
			calendarTool.clear();
			calendarTool.setTime(maxDate);
			monthIndex = calendarTool.get(Calendar.MONTH);
			dateIndex = calendarTool.get(Calendar.DAY_OF_MONTH) - 1;
			yearIndex = yearValue.length - 1;
			datePickerYear.setCurrentItem(yearIndex, true);
			datePickerMonth.setCurrentItem(monthIndex, true);
			datePickerDate.setCurrentItem(dateIndex, true);
		}
	}

	/**
	 * 
	 * <b>initComponent。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 初始化UI组件
	 */
	private void initComponent() {
		initData();
		scrolling = false;
		switch (pickerType) {
			case DATE_PICKER:
				initDatePickerView();
				break;
			case TIME_PICKER:
				initTimePickerView();
				break;
			case DATE_TIME_PICKER:
				initDateTimePickerView();
				break;
		}
		lastYearIndex = yearIndex;
		lastMonthIndex = monthIndex;
		lastDateIndex = dateIndex;
		lastHourIndex = hourIndex;
		lastMinuteIndex = minuteIndex;
	}

	/**
	 * 
	 * <b>initDatePickerView。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 初始化日期拾取器组件
	 */
	private void initDatePickerView() {
		layout = View.inflate(context, R.layout.wheel_picker_date_layout2, null);
		btnOK = (TextView) layout.findViewById(R.id.btn_ok);
		btnCancel = (TextView) layout.findViewById(R.id.btn_cancel);
		pickerTitle = (TextView) layout.findViewById(R.id.picker_title);
		pickerTitle.setText(title);
		datePickerYear = (AbstractWheel) layout.findViewById(R.id.date_picker_year);
		datePickerMonth = (AbstractWheel) layout.findViewById(R.id.date_picker_month);
		datePickerDate = (AbstractWheel) layout.findViewById(R.id.date_picker_day);
		ArrayWheelAdapter<String> yearAdapter = new ArrayWheelAdapter<String>(context, yearLabel);
		yearAdapter.setTextSize(16);
		yearAdapter.setTextTypeface(Typeface.DEFAULT);
		datePickerYear.setViewAdapter(yearAdapter);
		datePickerYear.setCurrentItem(yearIndex);
		ArrayWheelAdapter<String> monthAdapter = new ArrayWheelAdapter<String>(context, monthLabel);
		monthAdapter.setTextSize(16);
		monthAdapter.setTextTypeface(Typeface.DEFAULT);
		datePickerMonth.setViewAdapter(monthAdapter);
		datePickerMonth.setCurrentItem(monthIndex);
		ArrayWheelAdapter<String> dayAdapter = new ArrayWheelAdapter<String>(context, dateLabel);
		dayAdapter.setTextSize(16);
		dayAdapter.setTextTypeface(Typeface.DEFAULT);
		datePickerDate.setViewAdapter(dayAdapter);
		datePickerDate.setCurrentItem(dateIndex);

		datePickerYear.addChangingListener(new OnWheelChangedListener() {

			public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
				if (!scrolling) {
					updateDatePicker();
				}
			}
		});

		datePickerYear.addScrollingListener(new OnWheelScrollListener() {

			public void onScrollingStarted(AbstractWheel wheel) {
				scrolling = true;
			}

			public void onScrollingFinished(AbstractWheel wheel) {
				scrolling = false;
				updateDatePicker();
			}
		});

		datePickerMonth.addChangingListener(new OnWheelChangedListener() {

			public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
				if (!scrolling) {
					updateDatePicker();
				}
			}
		});

		datePickerMonth.addScrollingListener(new OnWheelScrollListener() {

			public void onScrollingStarted(AbstractWheel wheel) {
				scrolling = true;
			}

			public void onScrollingFinished(AbstractWheel wheel) {
				scrolling = false;
				updateDatePicker();
			}
		});

		datePickerDate.addChangingListener(new OnWheelChangedListener() {

			public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
				if (!scrolling) {
					updateDatePicker();
				}
			}
		});

		datePickerDate.addScrollingListener(new OnWheelScrollListener() {

			public void onScrollingStarted(AbstractWheel wheel) {
				scrolling = true;
			}

			public void onScrollingFinished(AbstractWheel wheel) {
				scrolling = false;
				updateDatePicker();
			}
		});
		btnOK.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != window) {
					Calendar selectedDate = Calendar.getInstance();
					if (!canSetDateBefore) {
						if (checkDateOrder(yearValue[yearIndex], monthValue[monthIndex], dateValue[dateIndex])) {
							selectedDate.set(yearValue[yearIndex], monthValue[monthIndex], dateValue[dateIndex], 0, 0, 0);
							selectedDate.set(Calendar.MILLISECOND, 0);
						}else{
							monthIndex = selectedDate.get(Calendar.MONTH);
							dateIndex = selectedDate.get(Calendar.DAY_OF_MONTH) - 1;
							datePickerMonth.setCurrentItem(monthIndex);
							datePickerDate.setCurrentItem(dateIndex);
						}
					} else {
						selectedDate.set(yearValue[yearIndex], monthValue[monthIndex], dateValue[dateIndex], 0, 0, 0);
						selectedDate.set(Calendar.MILLISECOND, 0);
					}
					
					lastYearIndex = yearIndex;
					lastMonthIndex = monthIndex;
					lastDateIndex = dateIndex;
					lastHourIndex = hourIndex;
					lastMinuteIndex = minuteIndex;
					
					if (null != onPositiveButtonClick) {
						onPositiveButtonClick.onReceivedSelectedDate(selectedDate, defaultDateFormat.format(selectedDate.getTime()));
					}
					
					window.dismiss();
				}
			}

		});

		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != window) {
					if (null != datePickerYear) datePickerYear.setCurrentItem(lastYearIndex);
					if (null != datePickerMonth) datePickerMonth.setCurrentItem(lastMonthIndex);
					if (null != datePickerDate) datePickerDate.setCurrentItem(lastDateIndex);
					if (null != datePickerHour) datePickerHour.setCurrentItem(lastHourIndex);
					if (null != datePickerMinute) datePickerMinute.setCurrentItem(lastMinuteIndex);
					window.dismiss();
				}
			}
		});
	}

	/**
	 * 
	 * <b>initTimePickerView。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 初始化时间拾取器组件
	 */
	private void initTimePickerView() {
		layout = View.inflate(context, R.layout.wheel_picker_time_layout, null);
		btnOK = (TextView) layout.findViewById(R.id.btn_ok);
		btnCancel = (TextView) layout.findViewById(R.id.btn_cancel);
		pickerTitle = (TextView) layout.findViewById(R.id.picker_title);
		pickerTitle.setText(title);
		datePickerHour = (AbstractWheel) layout.findViewById(R.id.date_picker_hour);
		datePickerMinute = (AbstractWheel) layout.findViewById(R.id.date_picker_minute);
		ArrayWheelAdapter<String> hourAdapter = new ArrayWheelAdapter<String>(context, hourLabel);
		hourAdapter.setTextSize(16);
		hourAdapter.setTextTypeface(Typeface.DEFAULT);
		datePickerHour.setViewAdapter(hourAdapter);
		datePickerHour.setCurrentItem(hourIndex);
		ArrayWheelAdapter<String> minuteAdapter = new ArrayWheelAdapter<String>(context, minuteLabel);
		minuteAdapter.setTextSize(16);
		minuteAdapter.setTextTypeface(Typeface.DEFAULT);
		datePickerMinute.setViewAdapter(minuteAdapter);
		datePickerMinute.setCurrentItem(minuteIndex);

		datePickerHour.addScrollingListener(new OnWheelScrollListener() {

			public void onScrollingStarted(AbstractWheel wheel) {
			}

			public void onScrollingFinished(AbstractWheel wheel) {
				hourIndex = datePickerHour.getCurrentItem();
			}
		});

		datePickerMinute.addScrollingListener(new OnWheelScrollListener() {

			public void onScrollingStarted(AbstractWheel wheel) {
			}

			public void onScrollingFinished(AbstractWheel wheel) {
				minuteIndex = datePickerMinute.getCurrentItem();
			}
		});

		btnOK.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != window) {
					Calendar selectedTime = Calendar.getInstance();
					selectedTime.set(Calendar.HOUR_OF_DAY, hourValue[hourIndex]);
					selectedTime.set(Calendar.MINUTE, minuteValue[minuteIndex]);
					if (null != onPositiveButtonClick) {
						onPositiveButtonClick.onReceivedSelectedTime(selectedTime, defaultDateFormat.format(selectedTime.getTime()));
					}
					window.dismiss();
				}
			}

		});

		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != window) window.dismiss();
			}
		});
	}

	/**
	 * 
	 * <b>initDateTimePickerView。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 初始化日期时间拾取器组件
	 */
	private void initDateTimePickerView() {
		layout = View.inflate(context, R.layout.wheel_picker_date_time_layout, null);
		btnOK = (TextView) layout.findViewById(R.id.btn_ok);
		btnCancel = (TextView) layout.findViewById(R.id.btn_cancel);
		pickerTitle = (TextView) layout.findViewById(R.id.picker_title);
		pickerTitle.setText(title);
		datePickerYear = (AbstractWheel) layout.findViewById(R.id.date_picker_year);
		datePickerMonth = (AbstractWheel) layout.findViewById(R.id.date_picker_month);
		datePickerDate = (AbstractWheel) layout.findViewById(R.id.date_picker_day);
		datePickerHour = (AbstractWheel) layout.findViewById(R.id.date_picker_hour);
		datePickerMinute = (AbstractWheel) layout.findViewById(R.id.date_picker_minute);
		ArrayWheelAdapter<String> yearAdapter = new ArrayWheelAdapter<String>(context, yearLabel);
		yearAdapter.setTextSize(16);
		yearAdapter.setTextTypeface(Typeface.DEFAULT);
		datePickerYear.setViewAdapter(yearAdapter);
		datePickerYear.setCurrentItem(yearIndex);
		ArrayWheelAdapter<String> monthAdapter = new ArrayWheelAdapter<String>(context, monthLabel);
		monthAdapter.setTextSize(16);
		monthAdapter.setTextTypeface(Typeface.DEFAULT);
		datePickerMonth.setViewAdapter(monthAdapter);
		datePickerMonth.setCurrentItem(monthIndex);
		ArrayWheelAdapter<String> dateAdapter = new ArrayWheelAdapter<String>(context, dateLabel);
		dateAdapter.setTextSize(16);
		dateAdapter.setTextTypeface(Typeface.DEFAULT);
		datePickerDate.setViewAdapter(dateAdapter);
		datePickerDate.setCurrentItem(dateIndex);
		ArrayWheelAdapter<String> hourAdapter = new ArrayWheelAdapter<String>(context, hourLabel);
		hourAdapter.setTextSize(16);
		hourAdapter.setTextTypeface(Typeface.DEFAULT);
		datePickerHour.setViewAdapter(hourAdapter);
		datePickerHour.setCurrentItem(hourIndex);
		ArrayWheelAdapter<String> minuteAdapter = new ArrayWheelAdapter<String>(context, minuteLabel);
		minuteAdapter.setTextSize(16);
		minuteAdapter.setTextTypeface(Typeface.DEFAULT);
		datePickerMinute.setViewAdapter(minuteAdapter);
		datePickerMinute.setCurrentItem(minuteIndex);

		datePickerYear.addChangingListener(new OnWheelChangedListener() {

			public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
				if (!scrolling) {
					updateDatePicker();
				}
			}
		});

		datePickerYear.addScrollingListener(new OnWheelScrollListener() {

			public void onScrollingStarted(AbstractWheel wheel) {
				scrolling = true;
			}

			public void onScrollingFinished(AbstractWheel wheel) {
				scrolling = false;
				updateDatePicker();
			}
		});

		datePickerMonth.addChangingListener(new OnWheelChangedListener() {

			public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
				if (!scrolling) {
					updateDatePicker();
				}
			}
		});

		datePickerMonth.addScrollingListener(new OnWheelScrollListener() {

			public void onScrollingStarted(AbstractWheel wheel) {
				scrolling = true;
			}

			public void onScrollingFinished(AbstractWheel wheel) {
				scrolling = false;
				updateDatePicker();
			}
		});

		datePickerDate.addChangingListener(new OnWheelChangedListener() {

			public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
				if (!scrolling) {
					updateDatePicker();
				}
			}
		});

		datePickerDate.addScrollingListener(new OnWheelScrollListener() {

			public void onScrollingStarted(AbstractWheel wheel) {
				scrolling = true;
			}

			public void onScrollingFinished(AbstractWheel wheel) {
				scrolling = false;
				updateDatePicker();
			}
		});

		datePickerHour.addScrollingListener(new OnWheelScrollListener() {

			public void onScrollingStarted(AbstractWheel wheel) {}

			public void onScrollingFinished(AbstractWheel wheel) {
				hourIndex = datePickerHour.getCurrentItem();
			}
		});

		datePickerMinute.addScrollingListener(new OnWheelScrollListener() {

			public void onScrollingStarted(AbstractWheel wheel) {
			}

			public void onScrollingFinished(AbstractWheel wheel) {
				minuteIndex = datePickerMinute.getCurrentItem();
			}
		});

		btnOK.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != window) {
					Calendar selectedDate = Calendar.getInstance();
					if (!canSetDateBefore) {
						if (checkDateOrder(yearValue[yearIndex], monthValue[monthIndex], dateValue[dateIndex],hourValue[hourIndex], minuteValue[minuteIndex])) {
							selectedDate.set(yearValue[yearIndex], monthValue[monthIndex], dateValue[dateIndex], hourValue[hourIndex], minuteValue[minuteIndex], 0);
							selectedDate.set(Calendar.MILLISECOND, 0);
						}else{
							monthIndex = selectedDate.get(Calendar.MONTH);
							dateIndex = selectedDate.get(Calendar.DAY_OF_MONTH) - 1;
							datePickerMonth.setCurrentItem(monthIndex);
							datePickerDate.setCurrentItem(dateIndex);
						}
					} else {
						selectedDate.set(yearValue[yearIndex], monthValue[monthIndex], dateValue[dateIndex], hourValue[hourIndex], minuteValue[minuteIndex], 0);
						selectedDate.set(Calendar.MILLISECOND, 0);
					}
					if (null != onPositiveButtonClick) {
						onPositiveButtonClick.onReceivedSelectedDateTime(selectedDate, defaultDateFormat.format(selectedDate.getTime()));
					}
					window.dismiss();
				}
			}

		});

		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != window) window.dismiss();
			}
		});
	}

	/**
	 * 
	 * <b>generatePickerWindow。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 生成拾取器窗口
	 * @return
	 */
	public PopupWindow generatePickerWindow() {
		initComponent();
		window = new PopupWindow(layout, windowWidth, windowHeight, false);
		window.setOutsideTouchable(true);
		window.setBackgroundDrawable(context.getResources().getDrawable(R.color.white));
		window.setAnimationStyle(R.style.WheelPickerPopupAnimation);
		return window;
	}

	/**
	 * 
	 * <b>generatePicker。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 生成拾取器
	 */
	public void generatePicker() {
		initComponent();
		window = new PopupWindow(layout, windowWidth, windowHeight, false);
		window.setOutsideTouchable(true);
		window.setBackgroundDrawable(context.getResources().getDrawable(R.color.white));
		window.setAnimationStyle(R.style.WheelPickerPopupAnimation);
		// window.showAtLocation(anchor, Gravity.BOTTOM, 0, 0);
	}

	/**
	 * 
	 * <b>showPicker。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 显示拾取器
	 */
	public void showPicker(View anchor) {
		if (null != window && !window.isShowing()) {
			window.showAtLocation(anchor, Gravity.BOTTOM, 0, 0);
		} else {
			Log.d("DateTimePicker", "DateTimePicker ui component does not inited!");
		}
	}

	/**
	 * 
	 * <b>setPickerWidth。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 设置Picker的窗体宽度
	 * @param width
	 */
	public void setPickerWidth(int width) {
		this.windowWidth = width;
		if (null != window) {
			window.setWidth(windowWidth);
		}
	}

	/**
	 * 
	 * <b>setPickerHeight。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 设置Picker的窗体高度
	 * @param height
	 */
	public void setPickerHeight(int height) {
		this.windowHeight = height;
		if (null != window) {
			window.setHeight(windowHeight);
		}
	}

	/**
	 * 
	 * <b>getMinDate。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 获得拾取器显示的日期下限
	 * @return
	 */
	public Date getMinDate() {
		return minDate;
	}

	/**
	 * 
	 * <b>setMinDate。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 设置拾取器显示的日期下限
	 * @param minDate
	 */
	public void setMinDate(Date minDate) {
		this.minDate = minDate;
		reloadData();
	}

	/**
	 * 
	 * <b>getMaxDate。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 获得拾取器显示的日期上限
	 * @return
	 */
	public Date getMaxDate() {
		return maxDate;
	}

	/**
	 * 
	 * <b>setMaxDate。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 设置拾取器显示的日期上限
	 * @param maxDate
	 */
	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
		reloadData();
	}

	/**
	 * 
	 * <b>getCurrentDate。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 获取当前时间
	 * @return
	 */
	public Calendar getCurrentDate() {
		return currentDate;
	}

	/**
	 * 
	 * <b>setCurrentDate。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 设置当前时间，用于默认显示
	 * @param currentDate
	 */
	public void setCurrentDate(Calendar currentDate) {
		switch (pickerType) {
			case DATE_PICKER:
				currentDate.set(Calendar.HOUR_OF_DAY, 0);
				currentDate.set(Calendar.MINUTE, 0);
				currentDate.set(Calendar.MILLISECOND, 0);
				break;
			case DATE_TIME_PICKER:
				currentDate.set(Calendar.MILLISECOND, 0);
				break;
		}
		this.currentDate = currentDate;
		reloadData();
	}
	
	/**
	 * 
	 * <b>updateSelectedDate。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 后台更新所选日期
	 * @param calendar
	 */
	public void updateSelectedDate(Calendar calendar){
		yearIndex = 0;
		int year = calendar.get(Calendar.YEAR);
		for(int index = 0; index < yearValue.length; index++){
			if(yearValue[index] == year) yearIndex = index;
		}
		monthIndex = calendar.get(Calendar.MONTH);
		dateIndex = calendar.get(Calendar.DAY_OF_MONTH) - 1;
		hourIndex = calendar.get(Calendar.HOUR_OF_DAY);
		minuteIndex = calendar.get(Calendar.MINUTE);
		if(null != datePickerYear) datePickerYear.setCurrentItem(yearIndex);
		if(null != datePickerMonth) datePickerMonth.setCurrentItem(monthIndex);
		if(null != datePickerDate) datePickerDate.setCurrentItem(dateIndex);
		if(null != datePickerHour) datePickerHour.setCurrentItem(hourIndex);
		if(null != datePickerMinute) datePickerMinute.setCurrentItem(minuteIndex);
	}

	/**
	 * 
	 * <b>getCurrentYearBefroe。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 获取指定年数下限
	 * @return
	 */
	public int getCurrentYearBefroe() {
		return currentYearBefroe;
	}

	/**
	 * 
	 * <b>setCurrentYearBefroe。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 设置指定的当前时间往前计算的年数
	 * @param currentYearBefroe
	 */
	public void setCurrentYearBefroe(int currentYearBefroe) {
		this.currentYearBefroe = currentYearBefroe;
		reloadData();
	}

	/**
	 * 
	 * <b>getCurrentYearAfter。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 获取指定年数上限
	 * @return
	 */
	public int getCurrentYearAfter() {
		return currentYearAfter;
	}

	/**
	 * 
	 * <b>setCurrentYearAfter。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 设置指定的当前时间往后计算的年数
	 * @param currentYearAfter
	 */
	public void setCurrentYearAfter(int currentYearAfter) {
		this.currentYearAfter = currentYearAfter;
		reloadData();
	}

	/**
	 * 
	 * <b>isFocusable。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 判断拾取器是否可获得焦点
	 * @return
	 */
	public boolean isFocusable() {
		return focusable;
	}

	/**
	 * 
	 * <b>setFocusable。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 设置拾取器是否可获得焦点
	 * @param focusable
	 */
	public void setFocusable(boolean focusable) {
		this.focusable = focusable;
	}

	/**
	 * 
	 * <b>isShowing。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 拾取器是否已显示
	 * @return
	 */
	public boolean isShowing() {
		if (null != window) {
			return window.isShowing();
		} else {
			return false;
		}
	}

	/**
	 * 
	 * <b>getDefaultDateFormat。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 获得默认的日期格式
	 * @return
	 */
	public DateFormat getDefaultDateFormat() {
		return defaultDateFormat;
	}

	/**
	 * 
	 * <b>setDefaultDateFormat。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 更改日期格式为指定格式
	 * @param defaultDateFormat
	 */
	public void setDefaultDateFormat(DateFormat defaultDateFormat) {
		this.defaultDateFormat = defaultDateFormat;
	}

	/**
	 * 
	 * <b>setOnPositiveButtonClick。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 设置点击确认后的回调函数
	 * @param onPositiveButtonClick
	 */
	public void setOnPositiveButtonClick(OnPositiveButtonClick onPositiveButtonClick) {
		this.onPositiveButtonClick = onPositiveButtonClick;
	}

	/**
	 * 
	 * <b>OnPositiveButtonClick。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 点击确定按钮事件回调
	 * <p><b>修改列表：</b></p>
	 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
	 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
	 * <!-- 在此添加修改列表，参考第一行内容 -->
	 * <tr><td>1</td><td>Wancheng</td><td>2014-7-19 上午1:16:50</td><td>建立类型</td></tr>
	 * 
	 * </table>
	 * @version 1.0
	 * @author Wancheng
	 * @since 1.0
	 */
	public interface OnPositiveButtonClick {

		/**
		 * 
		 * <b>onReceivedSelectedDate。</b>  
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 用于接收日期拾取器结果
		 * @param selectedDate
		 * @param dateText
		 */
		void onReceivedSelectedDate(Calendar selectedDate, String dateText);

		/**
		 * 
		 * <b>onReceivedSelectedTime。</b>  
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 用于接收时间拾取器结果
		 * @param selectedTime
		 * @param timeText
		 */
		void onReceivedSelectedTime(Calendar selectedTime, String timeText);

		/**
		 * 
		 * <b>onReceivedSelectedDateTime。</b>  
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 用于接收日期时间拾取器结果
		 * @param selectedDateTime
		 * @param dateTimeText
		 */
		void onReceivedSelectedDateTime(Calendar selectedDateTime, String dateTimeText);
	}
}

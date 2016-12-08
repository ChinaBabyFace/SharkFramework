package com.soaring.widget.calender.xiaowu;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.soaring.widget.calender.common.CustomDate;
import com.soaring.widget.calender.common.DateUtil;
import com.soaring.widget.calender.em.CellState;
import com.soaring.widget.calender.view.CalendarCell;
import com.soaring.widget.calender.view.CalendarRow;
import com.soaringcloud.kit.box.JavaKit;

import java.util.Calendar;
import java.util.List;

/**
 * 自定义日历卡
 * 
 * @author wuwenjie
 * 
 */
public class CalendarCard extends View {

	private static final int TOTAL_COL = 7; // 7列
	private static final int TOTAL_ROW = 6; // 6行
	private int mViewWidth; // 视图的宽度
	private int mViewHeight; // 视图的高度
	private int mCellSpace; // 单元格间距
	private CalendarRow rows[] = new CalendarRow[TOTAL_ROW]; // 行数组，每个元素代表一行
	private static CustomDate mShowDate; // 自定义的日期，包括year,month,day
	private OnCellClickListener mCellClickListener; // 单元格点击回调事件
	private int touchSlop; //
	private boolean callBackCellSpace;

	private CalendarCell mClickCell;
	private float mDownX;
	private float mDownY;
	private List<Calendar> recordList;
	private Calendar calendar;

	/**
	 * 单元格点击的回调接口
	 * 
	 * @author wuwenjie
	 * 
	 */
	public interface OnCellClickListener {

		void clickDate(CustomDate date); // 回调点击的日期

		void changeDate(CustomDate date); // 回调滑动ViewPager改变的日期
	}

	public CalendarCard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public CalendarCard(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public CalendarCard(Context context) {
		super(context);
		init(context);
	}

	public CalendarCard(Context context, CustomDate showDate) {
		super(context);
		mShowDate = showDate;
		init(context);
	}

	public CalendarCard(Context context, OnCellClickListener listener) {
		super(context);
		this.mCellClickListener = listener;
		init(context);
	}

	public CalendarCard(Context context, CustomDate showDate, OnCellClickListener listener) {
		super(context);
		this.mCellClickListener = listener;
		mShowDate = showDate;
		init(context);
	}

	private void init(Context context) {
		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		initDate();
	}

	private void initDate() {
		mShowDate = new CustomDate();
	}

	private void fillDate() {
		int monthDay = DateUtil.getCurrentMonthDay(); // 今天
		int lastMonthDays = DateUtil.getMonthDays(mShowDate.year, mShowDate.month - 1); // 上个月的天数
		int currentMonthDays = DateUtil.getMonthDays(mShowDate.year, mShowDate.month); // 当前月的天数
		int firstDayWeek = DateUtil.getWeekDayFromDate(mShowDate.year, mShowDate.month);// 每月第一天的位置
		boolean isCurrentMonth = false;
		if (DateUtil.isCurrentMonth(mShowDate)) {
			isCurrentMonth = true;
		}
		int day = 0;
		for (int j = 0; j < TOTAL_ROW; j++) {
			rows[j] = new CalendarRow(j,TOTAL_COL);
			for (int i = 0; i < TOTAL_COL; i++) {
				int position = i + j * TOTAL_COL; // 单元格位置
				// 这个月的
				if (position >= firstDayWeek && position < firstDayWeek + currentMonthDays) {
					day++;
					rows[j].cells[i] = new CalendarCell(CustomDate.modifiDayForObject(mShowDate, day), CellState.CURRENT_MONTH_DAY, i, j,mCellSpace);

					// 今天
					if (isCurrentMonth && day == monthDay) {
						CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
						rows[j].cells[i] = new CalendarCell(date, CellState.TODAY, i, j,mCellSpace);
					}
					if (isCurrentMonth && day > monthDay) { // 如果比这个月的今天要大，表示还没到
						rows[j].cells[i] = new CalendarCell(CustomDate.modifiDayForObject(mShowDate, day), CellState.UNREACH_DAY, i, j,mCellSpace);
					}

					if (isSelectDayHasTip(rows[j].cells[i].date)) {
						rows[j].cells[i].setSelectDate(true);
						if (day == monthDay) {
							if (isCurrentMonth) {
								CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
								rows[j].cells[i] = new CalendarCell(date, CellState.TODAY, i, j,mCellSpace);
							}
						}
					} else {
						if (getCalendar() == null || getCalendar().equals("")) {
							if (day == monthDay) {
								if (isCurrentMonth) {
									CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
									rows[j].cells[i] = new CalendarCell(date, CellState.TODAY, i, j,mCellSpace);
								} else {
									CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
									rows[j].cells[i] = new CalendarCell(date, CellState.CURRENT_TODAY, i, j,mCellSpace);
								}
							}
						} else {
							if (isCustomDateEqualCalendarMonth(mShowDate, getCalendar())) {
								if (isCurrentMonth) {

								} else {
									CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
									rows[j].cells[i] = new CalendarCell(date, CellState.CURRENT_MONTH_DAY, i, j,mCellSpace);
								}

							} else {
								if (day == monthDay) {
									if (isCurrentMonth) {
										CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
										rows[j].cells[i] = new CalendarCell(date, CellState.TODAY, i, j,mCellSpace);
									} else {
										CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
										rows[j].cells[i] = new CalendarCell(date, CellState.CURRENT_TODAY, i, j,mCellSpace);
									}
								}

							}
						}
					}
					if (isDayHasTip(rows[j].cells[i].date)) {
						rows[j].cells[i].setTip(true);
					}
					// 过去一个月
				} else if (position < firstDayWeek) {
					rows[j].cells[i] = new CalendarCell(new CustomDate(mShowDate.year, mShowDate.month - 1, lastMonthDays - (firstDayWeek - position - 1)),
							CellState.PAST_MONTH_DAY, i, j,mCellSpace);
					// 下个月
				} else if (position >= firstDayWeek + currentMonthDays) {
					rows[j].cells[i] = new CalendarCell(
							(new CustomDate(mShowDate.year, mShowDate.month + 1, position - firstDayWeek - currentMonthDays + 1)),
							CellState.NEXT_MONTH_DAY, i, j,mCellSpace);
				}
			}
		}
		if (mCellClickListener != null) {
			mCellClickListener.changeDate(mShowDate);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < TOTAL_ROW; i++) {
			if (rows[i] != null) {
				rows[i].drawCells(canvas);
			}
		}
		Log.e("CA", "Show year:" + mShowDate.year + ",Show M:" + mShowDate.month);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mViewWidth = w;
		mViewHeight = h;
		mCellSpace = mViewWidth / TOTAL_COL;
		update();
//        mCellSpace =Math.min(mViewHeight / TOTAL_ROW, mViewWidth / TOTAL_COL);
		if (!callBackCellSpace) {
			callBackCellSpace = true;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mDownX = event.getX();
			mDownY = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			float disX = event.getX() - mDownX;
			float disY = event.getY() - mDownY;
			if (Math.abs(disX) < touchSlop && Math.abs(disY) < touchSlop) {
				int col = (int) (mDownX / mCellSpace);
				int row = (int) (mDownY / mCellSpace);
				measureClickCell(col, row);
			}
			break;
		default:
			break;
		}

		return true;
	}

	public boolean isDayHasTip(CustomDate cd) {
		if (JavaKit.isListEmpty(recordList)) {
			return false;
		}

		for (int i = 0; i < recordList.size(); i++) {
			if (isCustomDateEqualCalendar(cd, recordList.get(i))) {
				return true;
			}
		}
		return false;
	}

	public boolean isSelectDayHasTip(CustomDate cd) {
		if (calendar == null || calendar.equals("")) {
			return false;
		}
		return isCustomDateEqualCalendar(cd, getCalendar());
	}

	public boolean isCustomDateEqualCalendar(CustomDate cd, Calendar ca) {
		return cd.year == ca.get(Calendar.YEAR) && cd.month == ca.get(Calendar.MONTH) + 1 && cd.day == ca.get(Calendar.DAY_OF_MONTH);
	}

	public boolean isCustomDateEqualCalendarMonth(CustomDate cd, Calendar ca) {
		return cd.year == ca.get(Calendar.YEAR) && cd.month == ca.get(Calendar.MONTH) + 1;
	}

	/**
	 * 计算点击的单元格
	 * 
	 * @param col
	 * @param row
	 */
	private void measureClickCell(int col, int row) {
		if (col >= TOTAL_COL || row >= TOTAL_ROW)
			return;
		if (mClickCell != null) {
			rows[mClickCell.row].cells[mClickCell.col] = mClickCell;
		}
		if (rows[row] != null) {
			mClickCell = new CalendarCell(rows[row].cells[col].date, rows[row].cells[col].state, rows[row].cells[col].col, rows[row].cells[col].row,mCellSpace);

			CustomDate date = rows[row].cells[col].date;
			date.week = col;
			if (mCellClickListener != null) {
				mCellClickListener.clickDate(date);

			}
			// 刷新界面
			update();
		}
	}


	// 从左往右划，上一个月
	public void leftSlide() {
		if (mShowDate.month == 1) {
			mShowDate.month = 12;
			mShowDate.year -= 1;
		} else {
			mShowDate.month -= 1;
		}
		update();
	}

	// 从右往左划，下一个月
	public void rightSlide() {
		if (mShowDate.month == 12) {
			mShowDate.month = 1;
			mShowDate.year += 1;
		} else {
			mShowDate.month += 1;
		}
		update();
	}

	public void update() {
		fillDate();
		invalidate();
	}

	/**
	 * @return the mShowDate
	 */
	public static CustomDate getmShowDate() {
		return mShowDate;
	}

	/**
	 * @param mShowDate
	 *            the mShowDate to set
	 */
	public static void setmShowDate(CustomDate mShowDate) {
		CalendarCard.mShowDate = mShowDate;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public List<Calendar> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<Calendar> recordList) {
		this.recordList = recordList;
	}

}

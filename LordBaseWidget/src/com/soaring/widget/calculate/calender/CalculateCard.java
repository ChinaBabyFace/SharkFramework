package com.soaring.widget.calculate.calender;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.soaring.widget.SoaringWidgetSettings;
import com.soaringcloud.kit.box.JavaKit;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 自定义日历卡
 * 
 * @author wuwenjie
 * 
 */
public class CalculateCard extends View {

	public static CalculateCustomDate mShowDate; // 自定义的日期，包括year,month,day
	private static final int TOTAL_COL = 7; // 7列
	private static final int TOTAL_ROW = 6; // 6行

	private Paint mCirclePaint; // 绘制圆形的画笔
	private Paint mTextPaint; // 绘制文本的画笔
	private Paint redPointPaint;

	private int mViewWidth; // 视图的宽度
	private int mViewHeight; // 视图的高度
	private int mCellSpace; // 单元格间距
	private Row rows[] = new Row[TOTAL_ROW]; // 行数组，每个元素代表一行
	private OnCellClickListener mCellClickListener; // 单元格点击回调事件
	private int touchSlop; //
	private boolean callBackCellSpace;

	private Cell mClickCell;
	private float mDownX;
	private float mDownY;
	private List<Calendar> recordList;
	private Calendar calendar;
	private int averagePeriodDays;
	private int menstrualPeriodDays;

	/**
	 * 单元格点击的回调接口
	 * 
	 * @author wuwenjie
	 * 
	 */
	public interface OnCellClickListener {

		void clickDate(CalculateCustomDate date); // 回调点击的日期

		void changeDate(CalculateCustomDate date); // 回调滑动ViewPager改变的日期
	}

	public CalculateCard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public CalculateCard(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public CalculateCard(Context context) {
		super(context);
		init(context);
	}

	public CalculateCard(Context context, CalculateCustomDate showDate) {
		super(context);
		mShowDate = showDate;
		init(context);
	}

	public CalculateCard(Context context, CalculateCustomDate showDate, OnCellClickListener listener) {
		super(context);
		this.mCellClickListener = listener;
		mShowDate = showDate;
		init(context);
	}

	public CalculateCard(Context context, OnCellClickListener listener) {
		// TODO Auto-generated constructor stub
		super(context);
		this.mCellClickListener = listener;
		init(context);
	}

	private void init(Context context) {
		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mCirclePaint.setStyle(Paint.Style.FILL);
		mCirclePaint.setColor(Color.parseColor("#00CFC9")); // 蓝色色圆形
		redPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		redPointPaint.setStyle(Paint.Style.FILL);
		redPointPaint.setColor(Color.parseColor("#ff1212")); // 红色圆形
		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

		initDate();
	}

	private void initDate() {
		// if (mShowDate == null) {
		// }
		mShowDate = new CalculateCustomDate();
		fillDate();//
	}

	private void fillDate() {
		int monthDay = CalculateDateUtil.getCurrentMonthDay(); // 今天
		int lastMonthDays = CalculateDateUtil.getMonthDays(mShowDate.year, mShowDate.month - 1); // 上个月的天数
		int currentMonthDays = CalculateDateUtil.getMonthDays(mShowDate.year, mShowDate.month); // 当前月的天数
		int firstDayWeek = CalculateDateUtil.getWeekDayFromDate(mShowDate.year, mShowDate.month);// 每月第一天的位置
		boolean isCurrentMonth = false;
		if (CalculateDateUtil.isCurrentMonth(mShowDate)) {
			isCurrentMonth = true;
		}
		int day = 0;
		for (int j = 0; j < TOTAL_ROW; j++) {
			rows[j] = new Row(j);
			for (int i = 0; i < TOTAL_COL; i++) {
				int position = i + j * TOTAL_COL; // 单元格位置
				// 这个月的
				if (position >= firstDayWeek && position < firstDayWeek + currentMonthDays) {
					day++;
					rows[j].cells[i] = new Cell(CalculateCustomDate.modifiDayForObject(mShowDate, day), State.CURRENT_MONTH_DAY, i, j);

					// 今天
					if (isCurrentMonth && day == monthDay) {
						CalculateCustomDate date = CalculateCustomDate.modifiDayForObject(mShowDate, day);
						rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
					}
					if (isCurrentMonth && day > monthDay) { // 如果比这个月的今天要大，表示还没到
						rows[j].cells[i] = new Cell(CalculateCustomDate.modifiDayForObject(mShowDate, day), State.UNREACH_DAY, i, j);
					}

					if (isSelectDayHasTip(rows[j].cells[i].date)) {
						Log.e("*******", "我来过又********");
						rows[j].cells[i].setSelectDate(true);
						if (day == monthDay) {
							if (isCurrentMonth) {
								CalculateCustomDate date = CalculateCustomDate.modifiDayForObject(mShowDate, day);
								rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
							}
						}
					} else {
						if (getCalendar() == null || getCalendar().equals("")) {
							if (day == monthDay) {
								if (isCurrentMonth) {
									CalculateCustomDate date = CalculateCustomDate.modifiDayForObject(mShowDate, day);
									rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
								} else {
									CalculateCustomDate date = CalculateCustomDate.modifiDayForObject(mShowDate, day);
									rows[j].cells[i] = new Cell(date, State.CURRENT_TODAY, i, j);
								}
							}
						} else {
							if (isCustomDateEqualCalendarMonth(mShowDate, getCalendar())) {
								if (isCurrentMonth) {

								} else {
									CalculateCustomDate date = CalculateCustomDate.modifiDayForObject(mShowDate, day);
									rows[j].cells[i] = new Cell(date, State.CURRENT_MONTH_DAY, i, j);
								}

							} else {
								if (day == monthDay) {
									if (isCurrentMonth) {
										CalculateCustomDate date = CalculateCustomDate.modifiDayForObject(mShowDate, day);
										rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
									} else {
										CalculateCustomDate date = CalculateCustomDate.modifiDayForObject(mShowDate, day);
										rows[j].cells[i] = new Cell(date, State.CURRENT_TODAY, i, j);
									}
								}

							}
						}
					}
					if (isDayHasTip(rows[j].cells[i].date)) {
						rows[j].cells[i].setTip(true);
					}
					if (calendar !=null) {						
						if (rows[j].cells[i].date.year == calendar.get(Calendar.YEAR)) {
							if (rows[j].cells[i].date.month - 1 >= calendar.get(Calendar.MONTH)) {
								setMenstrualPeriodDay(rows[j].cells[i]);
							}
						} else if (rows[j].cells[i].date.year > calendar.get(Calendar.YEAR)) {
							setMenstrualPeriodDay(rows[j].cells[i]);
						}
					}
					// 过去一个月
				} else if (position < firstDayWeek) {
					rows[j].cells[i] = new Cell(new CalculateCustomDate(mShowDate.year, mShowDate.month - 1, lastMonthDays
							- (firstDayWeek - position - 1)), State.PAST_MONTH_DAY, i, j);
					// 下个月
				} else if (position >= firstDayWeek + currentMonthDays) {
					rows[j].cells[i] = new Cell((new CalculateCustomDate(mShowDate.year, mShowDate.month + 1, position - firstDayWeek
							- currentMonthDays + 1)), State.NEXT_MONTH_DAY, i, j);
				}
			}
		}
		if (mCellClickListener != null) {
			mCellClickListener.changeDate(mShowDate);
		}
	}

	@SuppressWarnings("deprecation")
	private void setMenstrualPeriodDay(Cell cell) {
		if (calendar != null) {
			Date date = new Date(cell.date.year, cell.date.month - 1, cell.date.day);
			Date menstrualDate = new Date(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			int myTime = (int) ((date.getTime() - menstrualDate.getTime()) / SoaringWidgetSettings.ONE_DAY_TIME);
			int someDate = (myTime % averagePeriodDays + averagePeriodDays) % averagePeriodDays;
			if (date.getTime()>=menstrualDate.getTime()){
                if (someDate >= 0 && someDate <= (menstrualPeriodDays - 1)) {
                    // 月经期
                    cell.setMenstrualDate(true);
                }
                if (someDate >= menstrualPeriodDays && someDate <= (averagePeriodDays - 20)) {
                    // 安全期
                    cell.setSafeDate(true);
                }
                if (someDate >= (averagePeriodDays - 19) && someDate <= (averagePeriodDays - 10)) {
                    // 排卵期
                    cell.setOvulatoryDate(true);
                }
                if (someDate >= (averagePeriodDays - 9) && someDate <= (averagePeriodDays - 1)) {
                    // 安全期
                    cell.setSafeDate(true);
                }
            }

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
//		mCellSpace = Math.min(mViewHeight / TOTAL_ROW, mViewWidth / TOTAL_COL);
        mCellSpace=mViewWidth/TOTAL_COL;
		if (!callBackCellSpace) {
			callBackCellSpace = true;
		}
		mTextPaint.setTextSize(mCellSpace / 4);
	}

	@SuppressLint("ClickableViewAccessibility")
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

	@SuppressLint("LongLogTag")
    public boolean isDayHasTip(CalculateCustomDate cd) {
		if (JavaKit.isListEmpty(recordList)) {
			return false;
		}

		for (int i = 0; i < recordList.size(); i++) {
			Log.v("recordList==================", recordList.get(i) + "");
			if (isCustomDateEqualCalendar(cd, recordList.get(i))) {
				return true;
			}
		}
		return false;
	}

	public boolean isSelectDayHasTip(CalculateCustomDate cd) {
		if (calendar == null || calendar.equals("")) {
			return false;
		}
		return isCustomDateEqualCalendar(cd, getCalendar());
	}

	public boolean isCustomDateEqualCalendar(CalculateCustomDate cd, Calendar ca) {
		return cd.year == ca.get(Calendar.YEAR) && cd.month == ca.get(Calendar.MONTH) + 1 && cd.day == ca.get(Calendar.DAY_OF_MONTH);
	}

	public boolean isCustomDateEqualCalendarMonth(CalculateCustomDate cd, Calendar ca) {
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
			rows[mClickCell.j].cells[mClickCell.i] = mClickCell;
		}
		if (rows[row] != null) {
			mClickCell = new Cell(rows[row].cells[col].date, rows[row].cells[col].state, rows[row].cells[col].i, rows[row].cells[col].j);

			CalculateCustomDate date = rows[row].cells[col].date;
			date.week = col;
			if (mCellClickListener != null) {
				mCellClickListener.clickDate(date);

			}
			// 刷新界面
			update();
		}
	}

	/**
	 * 组元素
	 * 
	 * @author wuwenjie
	 * 
	 */
	class Row {

		public int j;

		Row(int j) {
			this.j = j;
		}

		public Cell[] cells = new Cell[TOTAL_COL];

		// 绘制单元格
		public void drawCells(Canvas canvas) {
			for (int i = 0; i < cells.length; i++) {
				if (cells[i] != null) {
					cells[i].drawSelf(canvas);
				}
			}
		}

	}

	/**
	 * 单元格元素
	 * 
	 * @author wuwenjie
	 * 
	 */
	class Cell {

		public CalculateCustomDate date;
		public State state;
		public int i;
		public int j;
		private boolean isTip;
		private boolean isSelectDate;
		private boolean isMenstrualDate;
		private boolean isSafeDate;
		private boolean isOvulatoryDate;

		public Cell(CalculateCustomDate date, State state, int i, int j) {
			super();
			this.date = date;
			this.state = state;
			this.i = i;
			this.j = j;
		}

		public void drawSelf(Canvas canvas) {
			switch (state) {
			case TODAY: // 今天
				mTextPaint.setColor(Color.parseColor("#fffffe"));
				canvas.drawCircle((float) (mCellSpace * (i + 0.5)), (float) ((j + 0.5) * mCellSpace), mCellSpace / 3, mCirclePaint);
				break;
			case CURRENT_TODAY: // 今天
				redPointPaint.setColor(Color.RED);
				mTextPaint.setColor(Color.parseColor("#fffffe"));
				canvas.drawCircle((float) (mCellSpace * (i + 0.5)), (float) ((j + 0.5) * mCellSpace), mCellSpace / 3, mCirclePaint);
				break;
			case CURRENT_MONTH_DAY: // 当前月日期
				mTextPaint.setColor(Color.BLACK);
				break;
			case PAST_MONTH_DAY: // 过去一个月
			case NEXT_MONTH_DAY: // 下一个月
				mTextPaint.setColor(Color.parseColor("#fffffe"));
				break;
			case UNREACH_DAY: // 还未到的天
				mTextPaint.setColor(Color.GRAY);
				break;
			default:
				break;
			}
//			if (isSelectDate) {
//				mTextPaint.setColor(Color.parseColor("#fffffe"));
//				canvas.drawCircle((float) (mCellSpace * (i + 0.5)), (float) ((j + 0.5) * mCellSpace), mCellSpace / 3, redPointPaint);
//			}
			// 绘制文字
			String content = date.day + "";
			canvas.drawText(content, (float) ((i + 0.5) * mCellSpace - mTextPaint.measureText(content) / 2),
					(float) ((j + 0.7) * mCellSpace - mTextPaint.measureText(content, 0, 1) / 2), mTextPaint);
			if (isTip) {
				canvas.drawCircle((float) (mCellSpace * (i + 0.8)), (float) ((j + 0.1) * mCellSpace), mCellSpace / 12, redPointPaint);
			}
			if (isMenstrualDate) {
//				橙色
				redPointPaint.setColor(Color.parseColor("#FCB050"));
				canvas.drawCircle((float) (mCellSpace * (i + 0.8)), (float) ((j + 0.1) * mCellSpace), mCellSpace / 12, redPointPaint);
			}
			if (isSafeDate) {
//				浅蓝
				redPointPaint.setColor(Color.parseColor("#04C7F8"));
				canvas.drawCircle((float) (mCellSpace * (i + 0.8)), (float) ((j + 0.1) * mCellSpace), mCellSpace / 12, redPointPaint);
			}
			if (isOvulatoryDate) {
//				浅红
				redPointPaint.setColor(Color.parseColor("#E54C66"));
				canvas.drawCircle((float) (mCellSpace * (i + 0.8)), (float) ((j + 0.1) * mCellSpace), mCellSpace / 12, redPointPaint);
			}
		}

		public boolean isSelectDate() {
			return isSelectDate;
		}

		public void setSelectDate(boolean isSelectDate) {
			this.isSelectDate = isSelectDate;
		}

		public boolean isTip() {
			return isTip;
		}

		public void setTip(boolean isTip) {
			this.isTip = isTip;
		}

		public boolean isMenstrualDate() {
			return isMenstrualDate;
		}

		public void setMenstrualDate(boolean isMenstrualDate) {
			this.isMenstrualDate = isMenstrualDate;
		}

		public boolean isSafeDate() {
			return isSafeDate;
		}

		public void setSafeDate(boolean isSafeDate) {
			this.isSafeDate = isSafeDate;
		}

		public boolean isOvulatoryDate() {
			return isOvulatoryDate;
		}

		public void setOvulatoryDate(boolean isOvulatoryDate) {
			this.isOvulatoryDate = isOvulatoryDate;
		}
	}

	/**
	 * 
	 * @author wuwenjie 单元格的状态 当前月日期，过去的月的日期，下个月的日期
	 */
	enum State {
		TODAY, CURRENT_MONTH_DAY, PAST_MONTH_DAY, NEXT_MONTH_DAY, UNREACH_DAY, CURRENT_TODAY
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
	public static CalculateCustomDate getmShowDate() {
		return mShowDate;
	}

	/**
	 * @param mShowDate
	 *            the mShowDate to set
	 */
	public static void setmShowDate(CalculateCustomDate mShowDate) {
		CalculateCard.mShowDate = mShowDate;
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

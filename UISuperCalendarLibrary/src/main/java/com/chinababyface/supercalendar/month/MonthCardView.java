package com.chinababyface.supercalendar.month;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.chinababyface.supercalendar.callback.OnDayCellClickedListener;
import com.chinababyface.supercalendar.day.BaseDayCell;

import java.util.Calendar;

/**
 * 自定义日历卡
 *
 * @author wuwenjie
 */
public abstract class MonthCardView<T extends BaseDayCell> extends View {

    public static final int TOTAL_COL = 7; // 7列
    public static final int TOTAL_ROW = 6; // 6行
    protected MonthDayRow<T> rows[] = new MonthDayRow[TOTAL_ROW];
    private int cardViewWidth;
    private float cellSize;
    private int touchSlop;
    private float touchDownX;
    private float touchDownY;
    private Calendar firstCalendar;
    private Calendar endCalendar;
    private Calendar currentCalendar;
    private OnDayCellClickedListener onDayCellClickedListener;

    public MonthCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public MonthCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public MonthCardView(Context context) {
        super(context);
        init(context);
    }

    public Calendar getCurrentCalendar() {
        return currentCalendar;
    }

    public void setCurrentCalendar(Calendar currentCalendar) {
        this.currentCalendar = currentCalendar;
        if (currentCalendar != null) {
            currentCalendar.set(Calendar.DAY_OF_MONTH, 1);
            firstCalendar = (Calendar) currentCalendar.clone();
            endCalendar = (Calendar) currentCalendar.clone();

            firstCalendar.add(Calendar.MONTH, -1);
            endCalendar.add(Calendar.MONTH, 1);

            int lastMonthDayCount = firstCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int currentMonthDayCount = currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int firstDayWeek = currentCalendar.get(Calendar.DAY_OF_WEEK) - 1;

            firstCalendar.set(Calendar.DAY_OF_MONTH, lastMonthDayCount - firstDayWeek + 1);
            endCalendar.set(Calendar.DAY_OF_MONTH, TOTAL_ROW * TOTAL_COL - firstDayWeek - currentMonthDayCount + 1);
//            LogKit.e(this, "lastMonthDayCount:"+lastMonthDayCount);
//            LogKit.e(this, "currentMonthDayCount:"+currentMonthDayCount);
//            LogKit.e(this, "firstDayWeek:"+firstDayWeek);
//
//            LogKit.e(this, DateKit.getDayLogString(firstCalendar));
//            LogKit.e(this, DateKit.getDayLogString(endCalendar));

        }
    }

    private void init(Context context) {
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public abstract T getDayCell(Calendar date, int r, int c, float cellSize);

    public abstract MonthDayRow<T> getRow(int row, int colNum);

    public void fillDate() {
        Calendar currentMonthCalendar = getCurrentCalendar();
        currentMonthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        Calendar lastMonthCalendar = (Calendar) currentMonthCalendar.clone();
        lastMonthCalendar.add(Calendar.MONTH, -1);
        Calendar nextMonthCalendar = (Calendar) currentMonthCalendar.clone();
        nextMonthCalendar.add(Calendar.MONTH, 1);
        int lastMonthDayCount = lastMonthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int currentMonthDayCount = currentMonthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstDayWeek = currentMonthCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        int day = 0;

        for (int row = 0; row < TOTAL_ROW; row++) {
            rows[row] = getRow(row, TOTAL_COL);
            for (int col = 0; col < TOTAL_COL; col++) {
                int position = col + row * TOTAL_COL; // 单元格位置
                if (position >= firstDayWeek && position < firstDayWeek + currentMonthDayCount) {
                    //设置本月的日数据
                    day++;
                    currentMonthCalendar.set(Calendar.DAY_OF_MONTH, day);
                    rows[row].cellArray[col] = getDayCell(
                            (Calendar) (currentMonthCalendar.clone()),
                            row,
                            col,
                            getCellSize());
                } else if (position < firstDayWeek) {
                    //设置上一个月的数据
                    lastMonthCalendar.set(Calendar.DAY_OF_MONTH, lastMonthDayCount - (firstDayWeek - position - 1));
                    rows[row].cellArray[col] = getDayCell(
                            (Calendar) lastMonthCalendar.clone(),
                            row,
                            col,
                            getCellSize());
                } else if (position >= firstDayWeek + currentMonthDayCount) {
                    //设置看下一个月的数据
                    nextMonthCalendar.set(Calendar.DAY_OF_MONTH, position - firstDayWeek - currentMonthDayCount + 1);
                    rows[row].cellArray[col] = getDayCell(
                            (Calendar) nextMonthCalendar.clone(),
                            row,
                            col,
                            getCellSize());
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < TOTAL_ROW; i++) {
            if (rows[i] != null) {
                rows[i].onDraw(canvas);
            }
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        cardViewWidth = w;
        cellSize = (float) cardViewWidth / (float) TOTAL_COL;
        updateCardView();
        super.onSizeChanged(w, (int) (cellSize * TOTAL_ROW), oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDownX = event.getX();
                touchDownY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float disX = event.getX() - touchDownX;
                float disY = event.getY() - touchDownY;
                if (Math.abs(disX) < touchSlop && Math.abs(disY) < touchSlop) {
                    int col = (int) (touchDownX / cellSize);
                    int row = (int) (touchDownY / cellSize);
                    if (getOnDayCellClickedListener() != null) {
                        getOnDayCellClickedListener().onClick(rows[row].cellArray[col]);

                        updateCardView();
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }


    /**
     * 刷新整个View，重绘整个页面
     */
    public void updateCardView() {
        fillDate();
        invalidate();
    }

    public float getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public OnDayCellClickedListener getOnDayCellClickedListener() {
        return onDayCellClickedListener;
    }

    public void setOnDayCellClickedListener(OnDayCellClickedListener onDayCellClickedListener) {
        this.onDayCellClickedListener = onDayCellClickedListener;
    }

    public Calendar getFirstCalendar() {
        return firstCalendar;
    }

    public void setFirstCalendar(Calendar firstCalendar) {
        this.firstCalendar = firstCalendar;
    }

    public Calendar getEndCalendar() {
        return endCalendar;
    }

    public void setEndCalendar(Calendar endCalendar) {
        this.endCalendar = endCalendar;
    }
}

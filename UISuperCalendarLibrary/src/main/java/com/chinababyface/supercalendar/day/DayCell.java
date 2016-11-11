package com.chinababyface.supercalendar.day;

import android.graphics.Canvas;

import java.util.Calendar;

/**
 * Created by renyuxiang on 2016/3/3.
 */
public class DayCell<T> extends BaseDayCell<T> {

    public DayCell(Calendar date, T dDate, int r, int c, float cellSize) {
        super(date, dDate, r, c, cellSize);
    }

    @Override
    public void onDraw(Canvas canvas) {

    }

    //    public CustomDate date;
    //    public CellState state;
    //    private float mCellSpace; // 单元格间距
    //    public int col;
    //    public int row;
    //    private Paint mTextPaint; // 绘制文本的画笔
    //    private Paint redPointPaint;
    //    private Paint mCirclePaint; // 绘制圆形的画笔
    //
    //    private boolean isTip;
    //    private boolean isSelectDate;
    //
    //    public CalendarCell(CustomDate date, CellState state, int i, int j, float cellSize) {
    //        super();
    //        this.date = date;
    //        this.state = state;
    //        this.col = i;
    //        this.row = j;
    //        mCellSpace=cellSize;
    //        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //        redPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //        mTextPaint.setTextSize(mCellSpace / 4);
    //        redPointPaint.setStyle(Paint.Style.FILL);
    //        redPointPaint.setColor(Color.parseColor("#ff1212")); // 红色圆形
    //        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //        mCirclePaint.setStyle(Paint.Style.FILL);
    //        mCirclePaint.setColor(Color.parseColor("#00CFC9")); // 蓝色色圆形
    //    }
    //
    //    public void drawSelf(Canvas canvas) {
    //        switch (state) {
    //            case TODAY: // 今天
    //                mTextPaint.setColor(Color.parseColor("#fffffe"));
    //                canvas.drawCircle((float) (mCellSpace * (col + 0.5)), (float) ((row + 0.5) * mCellSpace), mCellSpace / 3, mCirclePaint);
    //                break;
    //            case CURRENT_TODAY: // 今天
    //                mTextPaint.setColor(Color.parseColor("#fffffe"));
    //                canvas.drawCircle((float) (mCellSpace * (col + 0.5)), (float) ((row + 0.5) * mCellSpace), mCellSpace / 3, mCirclePaint);
    //                break;
    //            case THIS_MONTH_DAY: // 当前月日期
    //                mTextPaint.setColor(Color.BLACK);
    //                break;
    //            case PAST_MONTH_DAY: // 过去一个月
    //            case NEXT_MONTH_DAY: // 下一个月
    //                mTextPaint.setColor(Color.parseColor("#fffffe"));
    //                break;
    //            case UNREACH_DAY: // 还未到的天
    //                mTextPaint.setColor(Color.GRAY);
    //                break;
    //            default:
    //                break;
    //        }
    //        if (isSelectDate) {
    //            mTextPaint.setColor(Color.parseColor("#fffffe"));
    //            canvas.drawCircle((float) (mCellSpace * (col + 0.5)), (float) ((row + 0.5) * mCellSpace), mCellSpace / 3, redPointPaint);
    //        }
    //        // 绘制文字
    //        String content = date.day + "";
    //        canvas.drawText(content, (float) ((col + 0.5) * mCellSpace - mTextPaint.measureText(content) / 2),
    //                (float) ((row + 0.7) * mCellSpace - mTextPaint.measureText(content, 0, 1) / 2), mTextPaint);
    //        if (isTip) {
    //            canvas.drawCircle((float) (mCellSpace * (col + 0.8)), (float) ((row + 0.1) * mCellSpace), mCellSpace / 12, redPointPaint);
    //        }
    //
    //    }
    //
    //    public boolean isSelectDate() {
    //        return isSelectDate;
    //    }
    //
    //    public void setSelectDate(boolean isSelectDate) {
    //        this.isSelectDate = isSelectDate;
    //    }
    //
    //    public boolean isTip() {
    //        return isTip;
    //    }
    //
    //    public void setTip(boolean isTip) {
    //        this.isTip = isTip;
    //    }
}

package com.chinababyface.supercalendar.day;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Calendar;

/**
 * Created by renyuxiang on 2016/3/4.
 */
public abstract class BaseDayCell<T> {
    private int row;
    private int col;
    private float cellSize;
    private Paint cellPaint;
    private T dayData;
    private Calendar calendar;

    public BaseDayCell(Calendar date, T dayData, int r, int c, float cellSize) {
        setCalendar(date);
        setDayData(dayData);
        setRow(r);
        setCol(c);
        setCellSize(cellSize);
    }

    public T getDayData() {
        return dayData;
    }

    public void setDayData(T dayData) {
        this.dayData = dayData;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public float getCellSize() {
        return cellSize;
    }

    public void setCellSize(float cellSize) {
        this.cellSize = cellSize;
    }

    public abstract void onDraw(Canvas canvas);

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Paint getCellPaint() {
        return cellPaint;
    }

    public void setCellPaint(Paint cellPaint) {
        this.cellPaint = cellPaint;
    }


}

package com.chinababyface.supercalendar.month;

import android.graphics.Canvas;

import com.chinababyface.supercalendar.day.BaseDayCell;

/**
 * Created by renyuxiang on 2016/3/3.
 */
public class MonthDayRow<T extends BaseDayCell> {
    public T[] cellArray;
    private int row;

    public MonthDayRow(int r,T[]cellArray) {
        setRow(r);
        setCellArray(cellArray);
    }

    public void onDraw(Canvas canvas) {
        for (int i = 0; i < cellArray.length; i++) {
            if (cellArray[i] != null) {
                cellArray[i].onDraw(canvas);
            }
        }
    }

    public T[] getCellArray() {
        return cellArray;
    }

    public void setCellArray(T[] cellArray) {
        this.cellArray = cellArray;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}

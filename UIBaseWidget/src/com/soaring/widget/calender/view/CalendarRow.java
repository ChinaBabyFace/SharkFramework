package com.soaring.widget.calender.view;

import android.graphics.Canvas;

/**
 * Created by renyuxiang on 2016/3/3.
 */
public class CalendarRow {
    public int row;
    public CalendarCell[] cells;

    public CalendarRow(int row, int colCount) {
        this.row = row;
        cells = new CalendarCell[colCount];
    }


    // 绘制单元格
    public void drawCells(Canvas canvas) {
        for (int i = 0; i < cells.length; i++) {
            if (cells[i] != null) {
                cells[i].drawSelf(canvas);
            }
        }
    }
}

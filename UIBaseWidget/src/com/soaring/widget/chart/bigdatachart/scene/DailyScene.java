package com.soaring.widget.chart.bigdatachart.scene;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.soaring.widget.chart.bigdatachart.point.ChartDatePoint;

import java.util.Calendar;
import java.util.List;

/**
 * Created by renyuxiang on 2015/9/24.
 */
public abstract class DailyScene extends SceneAdapter {
    //每次载入的数据量，以天为单位
    public static int DAY_CACHE_COUNT = 7;
    private DataLoader dataLoader;

    public DailyScene(Context context) {
        super(context);
    }

    public DataLoader getDataLoader() {
        return dataLoader;
    }

    public void setDataLoader(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public void drawTextInCenter(Canvas canvas, float left, float top, float right, float bottom, Paint mPaint, String content) {
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        float baseline = top + (bottom - top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(content, (left + right) * 0.5f, baseline, mPaint);
    }

    public enum DayType {
        HISTORY_DAY, RECENT_DAY, FUTURE_DAY
    }

    public interface DataLoader {
        List<ChartDatePoint> getData(Calendar baseDay, DayType dayType, int dayCount);
    }
}

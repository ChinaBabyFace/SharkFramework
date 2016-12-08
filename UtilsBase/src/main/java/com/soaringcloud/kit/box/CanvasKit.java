package com.soaringcloud.kit.box;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by renyuxiang on 2016/3/11.
 */
public class CanvasKit {
    public static float getFontHeight(Paint mPaint) {
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        return (float) Math.ceil(fontMetrics.descent - fontMetrics.top) + 2;
    }

    public static void drawTextInCenter(Canvas canvas, float left, float top, float right, float bottom, Paint
            mPaint, String content) {
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        float baseline = top + (bottom - top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(content, (left + right) * 0.5f, baseline, mPaint);
    }
}

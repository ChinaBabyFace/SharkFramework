package com.soaring.widget.chart.bigdatachart.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.soaring.widget.chart.bigdatachart.scene.ISurfaceView;
import com.soaring.widget.chart.bigdatachart.util.ChartSettings;

/**
 * Created by renyuxiang on 2015/9/9.
 */
public class ChartThread extends Thread {
    private Context context;
    private SurfaceHolder surfaceHolder;
    private boolean isRunning = false;
    private boolean isPaused = false;
    private ISurfaceView iSurfaceView;

    public ChartThread(Context context, SurfaceHolder surfaceHolder) {
        this.context = context;
        this.surfaceHolder = surfaceHolder;
    }

    /**
     * Calls the <code>run()</code> method of the Runnable object the receiver
     * holds. If no Runnable is set, does nothing.
     *
     * @see Thread#start
     */
    @Override
    public void run() {
        while (isRunning()) {
            long startTime = System.currentTimeMillis();
            if (!isPaused()) {
                Canvas mainCanvas = null;
                try {
                    mainCanvas = surfaceHolder.lockCanvas();
                    mainCanvas.setDrawFilter(new PaintFlagsDrawFilter(0,
                            Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
                    clearCanvas(mainCanvas);
                    if (iSurfaceView != null) {
                        iSurfaceView.onDrawScene(mainCanvas);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        surfaceHolder.unlockCanvasAndPost(mainCanvas);
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                }
            }
            while ((System.currentTimeMillis() - startTime) <= ChartSettings.TIME_IN_FRAME) {
                Thread.yield();
            }
        }
    }

    public boolean doTouchEvent(MotionEvent event) {
        try {
            iSurfaceView.onTouch(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void clearCanvas(Canvas canvas) {
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);
    }

    public void onPause() {
        setPaused(true);
    }

    public void onResume() {
        setPaused(false);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public ISurfaceView getSurfaceView() {
        return iSurfaceView;
    }

    public void setSurfaceView(ISurfaceView iSurfaceView) {
        this.iSurfaceView = iSurfaceView;
    }
}

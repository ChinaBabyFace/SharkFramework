package com.soaring.widget.chart.bigdatachart.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;

import com.soaring.widget.chart.bigdatachart.engine.ChartThread;
import com.soaring.widget.chart.bigdatachart.scene.SceneAdapter;

/**
 * Created by renyuxiang on 2015/9/8.
 */
public class ChartView extends GLSurfaceView implements Callback {

    private ChartThread chartThread;
    private SceneAdapter sceneAdapter;

    /**
     * Standard View constructor. In order to render something, you
     * must call {@link #setRenderer} to register a renderer.
     *
     * @param context
     * @param attrs
     */
    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        getHolder().setFormat(PixelFormat.TRANSPARENT);
        setZOrderOnTop(true);
        chartThread = new ChartThread(context, getHolder());
        setFocusable(true);
    }

    public ChartView(Context context) {
        super(context);
        getHolder().addCallback(this);
        getHolder().setFormat(PixelFormat.TRANSPARENT);
        setZOrderOnTop(true);
        chartThread = new ChartThread(context, getHolder());
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        if (getSceneAdapter() != null) {
            getSceneAdapter().setScreen(width, height);
        }
        chartThread.setRunning(true);
        if (chartThread.isAlive()) {
            chartThread.onResume();
        } else {
            chartThread.start();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        chartThread.onPause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        chartThread.doTouchEvent(event);
        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!hasWindowFocus) {
            chartThread.onPause();
        } else {
            chartThread.onResume();
        }
    }

    public SceneAdapter getSceneAdapter() {
        return sceneAdapter;
    }

    public void setSceneAdapter(SceneAdapter scene) {
        this.sceneAdapter = scene;
        this.chartThread.setSurfaceView(scene);
    }
}

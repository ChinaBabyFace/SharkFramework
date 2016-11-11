package com.soaring.widget.chart.bigdatachart.scene;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by renyuxiang on 2015/9/25.
 */
public interface ISurfaceView {
    void onDrawScene(Canvas canvas);

    void initScene();

    void onTouch(MotionEvent event);

    void recycleScene();

    void onKeyDown(int keyCode, KeyEvent event);
    void notifyDataSetChanged();
}

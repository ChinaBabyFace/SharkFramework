package com.nutritechinese.superchart.bar;

import android.graphics.Canvas;

/**
 * Created by renyuxiang on 2016/3/15.
 */
public abstract class BarItem<T> {
    private float height;
    private float wight;
    private float left;
    private T value;

    public abstract void onDraw(Canvas canvas);

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}

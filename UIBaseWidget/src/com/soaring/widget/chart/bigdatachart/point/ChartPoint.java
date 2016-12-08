package com.soaring.widget.chart.bigdatachart.point;

/**
 * Created by renyuxiang on 2015/9/24.
 */
public abstract class ChartPoint {
    private int x;
    private float y;

    public abstract String getXAxisPoint();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}

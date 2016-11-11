package com.soaring.widget.chart.bigdatachart.point;

import java.util.Calendar;

/**
 * Created by renyuxiang on 2015/9/11.
 */
public class ChartDatePoint extends ChartPoint {

    private Calendar calendar;
    private boolean isHasData;
    private boolean isLongPressing = false;
    private boolean isShowEffect = false;
    private int dataType;

    public void setIsLongPressing(boolean isLongPressing) {
        this.isLongPressing = isLongPressing;
    }

    public boolean isShowEffect() {
        return isShowEffect;
    }

    public void setIsShowEffect(boolean isShowEffect) {
        this.isShowEffect = isShowEffect;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public String getXAxisPoint() {
        return "" + getCalendar().get(Calendar.DAY_OF_MONTH);
    }

    public boolean isHasData() {
        return isHasData;
    }

    public void setHasData(boolean isHasData) {
        this.isHasData = isHasData;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
}

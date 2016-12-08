/*
 * Copyright (c) 2016. www.ihealthlabs.com
 */

package com.shark.wheelpicker.view.number;

import com.shark.wheelpicker.core.WheelVerticalView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renyuxiang on 2016/11/21.
 */

public class NumberParam {
    private int wheelItemCount;
    private int max;
    private int min;
    private int triggerMin;
    private int triggerMax;
    private int radix;
    private int defaultValue;
    private int currentValue;
    private List<WheelVerticalView> wheelList;

    public NumberParam() {
        wheelItemCount = 1;
        radix = 10;
        wheelList = new ArrayList<>();
    }

    public int getWheelItemCount() {
        return wheelItemCount;
    }

    public void setWheelItemCount(int wheelItemCount) {
        this.wheelItemCount = wheelItemCount;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getRadix() {
        return radix;
    }

    public void setRadix(int radix) {
        this.radix = radix;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(int defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public int getTriggerMin() {
        return triggerMin;
    }

    public void setTriggerMin(int triggerMin) {
        this.triggerMin = triggerMin;
    }

    public int getTriggerMax() {
        return triggerMax;
    }

    public void setTriggerMax(int triggerMax) {
        this.triggerMax = triggerMax;
    }

    public List<WheelVerticalView> getWheelList() {
        return wheelList;
    }

    public void setWheelList(List<WheelVerticalView> wheelList) {
        this.wheelList = wheelList;
    }
}

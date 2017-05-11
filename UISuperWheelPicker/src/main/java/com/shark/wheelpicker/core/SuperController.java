package com.shark.wheelpicker.core;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import com.shark.wheelpicker.core.adapter.ArrayWheelAdapter;
import com.shark.wheelpicker.core.adapter.NumericWheelAdapter;
import com.shark.wheelpicker.core.callback.OnWheelScrollListener;

import java.util.HashMap;

/**
 * Created by renyuxiang on 2016/12/5.
 */

public abstract class SuperController implements OnWheelScrollListener {
    private Context context;
    private HashMap<String, WheelVerticalView> wheelMap;


    public SuperController(Context context) {
        this.context = context;
        this.wheelMap = new HashMap<>();
    }

    public abstract View createView();

    public abstract void saveCurrentValue();

    public abstract void refreshWheel();

    public void notifyNumericWheel(WheelVerticalView wheel, int min, int max, int defaultValue, String format) {
        if (min > max) {
            int i = max;
            max = min;
            min = i;
        }
        if (wheel.getViewAdapter() == null) {
            NumericWheelAdapter adapter = new NumericWheelAdapter(getContext(), min, max, format);
            adapter.setTextSize(30);
            adapter.setTextTypeface(Typeface.DEFAULT);
            wheel.setViewAdapter(adapter);
            wheel.addScrollingListener(this);
        }
        NumericWheelAdapter adapter = (NumericWheelAdapter) (wheel.getViewAdapter());
        adapter.setMaxValue(max);
        adapter.setMinValue(min);
        wheel.setCurrentItem(defaultValue - min);
    }

    public void notifyArrayWheel(WheelVerticalView wheel, int defaultIndex, String[] labelArray) {
        if (wheel.getViewAdapter() == null) {
            ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<>(getContext(), labelArray);
            adapter.setTextSize(30);
            adapter.setTextTypeface(Typeface.DEFAULT);
            wheel.setViewAdapter(adapter);
            wheel.setCurrentItem(defaultIndex);
            wheel.addScrollingListener(this);
        }
        wheel.setCurrentItem(defaultIndex);
    }

    @Override
    public void onScrollingStarted(AbstractWheel wheel) {

    }

    @Override
    public void onScrollingFinished(AbstractWheel wheel) {
        saveCurrentValue();
        refreshWheel();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public HashMap<String, WheelVerticalView> getWheelMap() {
        return wheelMap;
    }

    public void setWheelMap(HashMap<String, WheelVerticalView> wheelMap) {
        this.wheelMap = wheelMap;
    }
}

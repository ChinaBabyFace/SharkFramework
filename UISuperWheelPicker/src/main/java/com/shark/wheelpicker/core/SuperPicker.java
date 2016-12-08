package com.shark.wheelpicker.core;

import android.content.Context;
import android.view.View;

/**
 * Created by renyuxiang on 2016/12/5.
 */

public abstract class SuperPicker {
    private Context context;
    private View pickerView;

    public abstract   void show();

    public SuperPicker(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public View getPickerView() {
        return pickerView;
    }

    public void setPickerView(View pickerView) {
        this.pickerView = pickerView;
    }
}

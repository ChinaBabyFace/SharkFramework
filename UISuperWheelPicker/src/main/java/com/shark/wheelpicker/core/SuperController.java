package com.shark.wheelpicker.core;

import android.content.Context;
import android.view.View;

/**
 * Created by renyuxiang on 2016/12/5.
 */

public abstract  class SuperController {
    private Context context;

    public SuperController(Context context) {
        this.context = context;
    }

    public abstract View createView();

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}

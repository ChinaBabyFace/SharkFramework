/*
 * Copyright (c) 2017.  任宇翔创建
 */

package com.shark.framework.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.shark.framework.controller.BaseViewController;

/**
 * Created by renyuxiang on 2017/5/11.
 */

public abstract class BaseActivity<T extends BaseViewController> extends FragmentActivity {
    private T activityController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        bind();
    }

    public T getActivityController() {
        return activityController;
    }

    public void setActivityController(T activityController) {
        this.activityController = activityController;
    }

    public abstract T createActivityController();

    public abstract void init();

    public abstract void bind();
}

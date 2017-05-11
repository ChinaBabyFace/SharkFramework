/*
 * Copyright (c) 2017.  任宇翔创建
 */

package com.shark.framework.view;

import android.support.v4.app.Fragment;

import com.shark.framework.controller.BaseViewController;

/**
 * Created by renyuxiang on 2017/5/11.
 */

public class BaseFragment<T extends BaseViewController> extends Fragment {
    private T fragmnentController;

    public T getFragmnentController() {
        return fragmnentController;
    }

    public void setFragmnentController(T fragmnentController) {
        this.fragmnentController = fragmnentController;
    }
}

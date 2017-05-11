package com.shark.framework;

import android.os.Bundle;

import com.shark.framework.controller.BaseViewController;
import com.shark.framework.view.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public BaseViewController createActivityController() {
        return null;
    }

    @Override
    public void init() {
//findViewById(R.id.textView);
    }

    @Override
    public void bind() {

    }
}

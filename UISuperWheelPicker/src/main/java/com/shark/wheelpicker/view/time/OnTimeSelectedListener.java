package com.shark.wheelpicker.view.time;

import android.content.DialogInterface;

import java.util.Calendar;

/**
 * Created by renyuxiang on 2016/12/5.
 */

public interface OnTimeSelectedListener {
    void onSelected(DialogInterface dialog,Calendar selectCalendar);

    void onCancel(DialogInterface dialog);
}

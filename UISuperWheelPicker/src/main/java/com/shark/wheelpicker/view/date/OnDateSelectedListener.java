package com.shark.wheelpicker.view.date;

import android.content.DialogInterface;

import java.util.Calendar;

/**
 * Created by renyuxiang on 2016/12/5.
 */

public interface OnDateSelectedListener {
    void onSelected(DialogInterface dialog, Calendar currentCalendar);

    void onCancel(DialogInterface dialog);
}

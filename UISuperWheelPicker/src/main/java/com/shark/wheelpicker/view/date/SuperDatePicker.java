package com.shark.wheelpicker.view.date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.shark.wheelpicker.R;
import com.shark.wheelpicker.core.SuperPicker;

import java.util.Calendar;

/**
 * Created by renyuxiang on 2016/12/5.
 */

public class SuperDatePicker extends SuperPicker {
    private SuperDateController controller;
    private OnDateSelectedListener onDateSelectedListener;
    private AlertDialog dialog;

    public SuperDatePicker(Context context) {
        super(context);
    }

    public static class Builder {
        private Context context;
        private SuperDateController controller;
        private OnDateSelectedListener pickerListener;

        public Builder(Context context) {
            this.context = context;
            controller = new SuperDateController(context);
        }

        public Builder setDefaultCalendar(Calendar calendar) {
            controller.setDefaultCalendar(calendar);
            return this;
        }

        public Builder setMaxYear(int max) {
            controller.setMaxYear(max);
            return this;
        }

        public Builder setMinYear(int min) {
            controller.setMinYear(min);
            return this;
        }


        public Builder setOnDateSelectedListener(OnDateSelectedListener listener) {
            pickerListener = listener;
            return this;
        }

        public SuperDatePicker create() {
            SuperDatePicker picker = new SuperDatePicker(context);
            picker.setController(controller);
            picker.setPickerView(controller.createView());
            picker.setOnDateSelectedListener(pickerListener);
            return picker;
        }

        public void show() {
            SuperDatePicker picker = create();
            picker.show();
        }
    }

    @Override
    public void show() {
        if (getContext() == null) {
            return;
        }
        if (dialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            dialog = builder.setView(getPickerView())
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (getOnDateSelectedListener() != null) {
                                getOnDateSelectedListener().onSelected(dialog, getController().getCurrentCalendar());
                            }
                        }
                    }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (getOnDateSelectedListener() != null) {
                                getOnDateSelectedListener().onCancel(dialog);
                            }
                        }
                    }).create();
        }
        dialog.show();
    }

    public SuperDateController getController() {
        return controller;
    }

    public void setController(SuperDateController controller) {
        this.controller = controller;
    }

    public OnDateSelectedListener getOnDateSelectedListener() {
        return onDateSelectedListener;
    }

    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        this.onDateSelectedListener = onDateSelectedListener;
    }
}

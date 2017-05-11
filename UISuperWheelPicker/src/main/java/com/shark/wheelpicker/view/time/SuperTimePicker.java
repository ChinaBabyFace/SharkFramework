package com.shark.wheelpicker.view.time;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.shark.wheelpicker.R;
import com.shark.wheelpicker.core.SuperPicker;

import java.util.Calendar;

/**
 * Created by renyuxiang on 2016/12/5.
 */

public class SuperTimePicker extends SuperPicker {
    private SuperTimeController controller;
    private OnTimeSelectedListener onTimeSelectedListener;
    private AlertDialog dialog;

    public SuperTimePicker(Context context) {
        super(context);
    }

    public static class Builder {
        private Context context;
        private SuperTimeController controller;
        private OnTimeSelectedListener pickerListener;

        public Builder(Context context) {
            this.context = context;
            controller = new SuperTimeController(context);
        }

        /**
         * 设置是否可以拨选未来时间，默认true，可以拨选
         *
         * @param isFutureAvailable
         * @return
         */
        public Builder setFutureAvailable(boolean isFutureAvailable) {
            controller.setFutureAvailable(isFutureAvailable);
            return this;
        }

        /**
         * 设置滚轮默认显示的时间，以毫秒为单位
         *
         * @param calendar
         * @return
         */
        public Builder setDefaultCalendar(Calendar calendar) {
            controller.setDefaultCalendar(calendar);
            return this;
        }

        public Builder setBaseCalendar(Calendar calendar) {
            controller.setBaseCalendar(calendar);
            return this;
        }

        public Builder setOnDateSelectedListener(OnTimeSelectedListener listener) {
            pickerListener = listener;
            return this;
        }

        public SuperTimePicker create() {
            SuperTimePicker picker = new SuperTimePicker(context);
            picker.setController(controller);
            picker.setPickerView(controller.createView());
            picker.setOnTimeSelectedListener(pickerListener);
            return picker;
        }

        public void show() {
            SuperTimePicker picker = create();
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
                            if (getOnTimeSelectedListener() != null) {
                                getOnTimeSelectedListener().onSelected(dialog, getController().getPickResult());
                            }
                        }
                    }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (getOnTimeSelectedListener() != null) {
                                getOnTimeSelectedListener().onCancel(dialog);
                            }
                        }
                    }).create();
        }
        dialog.show();
    }

    public SuperTimeController getController() {
        return controller;
    }

    public void setController(SuperTimeController controller) {
        this.controller = controller;
    }

    public OnTimeSelectedListener getOnTimeSelectedListener() {
        return onTimeSelectedListener;
    }

    public void setOnTimeSelectedListener(OnTimeSelectedListener onTimeSelectedListener) {
        this.onTimeSelectedListener = onTimeSelectedListener;
    }
}

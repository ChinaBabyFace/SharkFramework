/*
 * Copyright (c) 2016. www.ihealthlabs.com
 */

package com.shark.wheelpicker.view.number;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.shark.wheelpicker.R;
import com.shark.wheelpicker.core.SuperPicker;

/**
 * Created by renyuxiang on 2016/11/21.
 */

public class SuperNumberPicker extends SuperPicker {
    private SuperNumberController controller;
    private OnPickerDialogClickListener onPickerDialogClickListener;
    private AlertDialog dialog;

    public SuperNumberPicker(Context context) {
        super(context);
    }

    public static class Builder {
        private Context context;
        private SuperNumberController controller;
        private OnPickerDialogClickListener pickerListener;

        public Builder(Context context) {
            this.context = context;
            controller = new SuperNumberController(context);
        }

        public Builder addWheel(int wheelCount) {
            NumberParam param = new NumberParam();
            controller.getParamList().add(param);
            ((NumberParam) controller.getParamList().get(controller.getParamList().size() - 1)).setWheelItemCount
                    (wheelCount);
            return this;
        }

        public Builder min(int min) {
            ((NumberParam) controller.getParamList().get(controller.getParamList().size() - 1)).setMin(min);
            return this;
        }

        public Builder max(int max) {
            ((NumberParam) controller.getParamList().get(controller.getParamList().size() - 1)).setMax(max);
            return this;
        }

        public Builder triggerMin(int triggerMin) {
            ((NumberParam) controller.getParamList().get(controller.getParamList().size() - 1)).setTriggerMin
                    (triggerMin);
            return this;
        }

        public Builder triggerMax(int triggerMax) {
            ((NumberParam) controller.getParamList().get(controller.getParamList().size() - 1)).setTriggerMax
                    (triggerMax);
            return this;
        }

        private Builder radix(int radix) {
            ((NumberParam) controller.getParamList().get(controller.getParamList().size() - 1)).setRadix(radix);
            return this;
        }

        public Builder separator(String separator) {
            controller.getParamList().add(separator);
            return this;
        }

        public Builder setDefaultValue(String valueString) {
            controller.setDefaultValueString(valueString);
            return this;
        }

        /**
         * 若首位出现0是否保留
         * @param isKeepZero
         * @return
         */
        public Builder setKeepZero(boolean isKeepZero) {
            controller.setKeepZero(isKeepZero);
            return this;
        }

        /**
         * 设置滚轮上的内容是否要循环出现
         * @param isRecycle
         * @return
         */
        public Builder setWheelRecycle(boolean isRecycle) {
            controller.setWheelRecycle(isRecycle);
            return this;
        }
        public Builder setOnPickerDialogClickListener(OnPickerDialogClickListener listener) {
            pickerListener = listener;
            return this;
        }

        public SuperNumberPicker create() {
            SuperNumberPicker picker = new SuperNumberPicker(context);
            picker.setController(controller);
            picker.setPickerView(controller.createView());
            picker.setOnPickerDialogClickListener(pickerListener);
            return picker;
        }

        public void show() {
            SuperNumberPicker picker = create();
            picker.show();
        }

    }

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
                            if (getOnPickerDialogClickListener() != null) {
                                getOnPickerDialogClickListener().onClick(dialog, which, getCurrentValue());
                            }
                        }
                    }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (getOnPickerDialogClickListener() != null) {
                                getOnPickerDialogClickListener().onClick(dialog, which, getCurrentValue());
                            }
                        }
                    }).create();
        }
        dialog.show();
    }

    public SuperNumberController getController() {
        return controller;
    }

    public void setController(SuperNumberController controller) {
        this.controller = controller;
    }

    public String getCurrentValue() {
        return controller.getCurrentValue();
    }

    public OnPickerDialogClickListener getOnPickerDialogClickListener() {
        return onPickerDialogClickListener;
    }

    public void setOnPickerDialogClickListener(OnPickerDialogClickListener onPickerDialogClickListener) {
        this.onPickerDialogClickListener = onPickerDialogClickListener;
    }
}

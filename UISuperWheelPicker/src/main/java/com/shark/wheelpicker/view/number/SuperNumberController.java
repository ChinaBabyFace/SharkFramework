/*
 * Copyright (c) 2016. www.ihealthlabs.com
 */

package com.shark.wheelpicker.view.number;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shark.wheelpicker.R;
import com.shark.wheelpicker.core.AbstractWheel;
import com.shark.wheelpicker.core.WheelVerticalView;
import com.shark.wheelpicker.core.adapter.NumericWheelAdapter;
import com.shark.wheelpicker.core.callback.OnWheelScrollListener;
import com.soaringcloud.kit.box.LogKit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renyuxiang on 2016/11/22.
 */

public class SuperNumberController implements OnWheelScrollListener {
    public static int WHEEL_DATA_RANGE_MAX = 9;
    public static int WHEEL_DATA_RANGE_MIN = 0;

    private List<WheelVerticalView> wheelList;
    private List<Object> paramList;
    private String defaultValueString;
    private Context context;
    private boolean isKeepZero = false;
    private boolean isWheelRecycle = true;

    public SuperNumberController(Context context) {
        this.wheelList = new ArrayList<>();
        this.paramList = new ArrayList<>();
        this.context = context;
    }

    public View createView() {
        View root = View.inflate(context, R.layout.super_number_picker_layout, null);
        LinearLayout contentLayout = (LinearLayout) root.findViewById(R.id.picker_content);
        //此处禁止RTL防止在阿拉伯地区数组滚轮倒序摆放
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            contentLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        for (Object param : paramList) {
            if (param instanceof NumberParam) {
                NumberParam numberParam = (NumberParam) param;
                for (int i = 0; i < numberParam.getWheelItemCount(); i++) {
                    View wheelLayout = View.inflate(context, R.layout.super_number_picker_wheel_item_layout, null);
                    WheelVerticalView wheel = (WheelVerticalView) wheelLayout.findViewById(R.id.wheel_item);
                    numberParam.getWheelList().add(wheel);
                    LogKit.e(this, "Add wheel");
                    contentLayout.addView(wheelLayout);
                }
            }
            if (param instanceof String) {
                LogKit.e(this, "Add separator");
                String separator = (String) param;
                View textLayout = View.inflate(context, R.layout.super_number_picker_text_item_layout, null);
                ((TextView) textLayout.findViewById(R.id.separator)).setText(separator);
                contentLayout.addView(textLayout, new ViewGroup.LayoutParams(-2, -1));
            }
        }

        initDefaultValue();
        refreshAllWheel();
        return root;
    }

    public void initDefaultValue() {
        LogKit.e(this, "initDefaultValue start:" + defaultValueString);
        if (TextUtils.isEmpty(defaultValueString)) {
            defaultValueString = "0";
        }

        List<String> partList = new ArrayList<>();
        char[] temp = defaultValueString.toCharArray();
        boolean isFindDigit = false;
        for (char c : temp) {
            LogKit.e(this, "initDefaultValue:" + c);
            if (c >= 48 && c <= 57) {
                if (isFindDigit) {
                    partList.set(partList.size() - 1, partList.get(partList.size() - 1) + c);
                } else {
                    partList.add("" + c);
                }
                isFindDigit = true;
            } else {
                partList.add("" + c);
                isFindDigit = false;
            }
        }
        for (Object param : paramList) {
            if (param instanceof NumberParam) {
                NumberParam numberParam = (NumberParam) param;
                if (partList.size() > 0) {
                    int d = 0;
                    try {
                        d = Integer.parseInt(
                                getZeroString(numberParam.getWheelItemCount() - partList.get(0).length())
                                        +
                                        partList.get(0));
                    } catch (NumberFormatException e) {

                    }
                    numberParam.setDefaultValue(d);
                    numberParam.setCurrentValue(d);
                    partList.remove(0);
                } else {
                    numberParam.setDefaultValue(0);
                    numberParam.setCurrentValue(0);
                }

            }
            if (param instanceof String) {
                if (partList.size() > 0) {
                    partList.remove(0);
                }
            }
        }
    }

    public void refreshAllWheel() {
        long time = System.currentTimeMillis();
        handleWheelCurrentValue();
        boolean[] result = {false, false};
        for (Object param : paramList) {
            if (param instanceof NumberParam) {
                NumberParam numberParam = (NumberParam) param;
                result = bindWheelData(numberParam, result[0], result[1]);
            }
        }
        LogKit.e(this, "Bind data cost:" + (System.currentTimeMillis() - time));
    }

    public void handleWheelCurrentValue() {
        for (Object param : paramList) {
            if (param instanceof NumberParam) {
                NumberParam numberParam = (NumberParam) param;

                String currentValueString = "";

                for (int i = 0; i < numberParam.getWheelList().size(); i++) {
                    WheelVerticalView wheel = numberParam.getWheelList().get(i);
                    NumericWheelAdapter adapter = (NumericWheelAdapter) wheel.getViewAdapter();
                    if (adapter != null) {
                        currentValueString += (wheel.getCurrentItem() + adapter.getMinValue());
                    } else {
                        return;
                    }
                }
                numberParam.setCurrentValue(Integer.parseInt(
                        TextUtils.isEmpty(currentValueString)
                                ?
                                "0"
                                :
                                currentValueString));
            }
        }
    }

    public boolean[] bindWheelData(NumberParam numberParam, boolean isTriggerMin, boolean isTriggerMax) {
        int wheelCount = numberParam.getWheelList().size();

        Integer min = numberParam.getMin();
        Integer max = numberParam.getMax();
        Integer minTrigger = numberParam.getTriggerMin();
        Integer maxTrigger = numberParam.getTriggerMax();
        Integer currentValue = numberParam.getCurrentValue();

        char[] minArray = (getZeroString(wheelCount - min.toString().length()) + min.toString()).toCharArray();
        char[] maxArray = (getZeroString(wheelCount - max.toString().length()) + max.toString()).toCharArray();
        char[] minTriggerArray = (getZeroString(wheelCount - minTrigger.toString().length()) + minTrigger.toString())
                .toCharArray();
        char[] maxTriggerArray = (getZeroString(wheelCount - maxTrigger.toString().length()) + maxTrigger.toString())
                .toCharArray();
        char[] currentArray = (getZeroString(wheelCount - currentValue.toString().length()) + currentValue.toString()
        ).toCharArray();

        LogKit.e(this, "TriggerMin:" + isTriggerMin + ",TriggerMax:" + isTriggerMax);
        boolean isLastNumberMin = false;
        boolean isLastNumberMax = false;
        for (int i = 0; i < numberParam.getWheelList().size(); i++) {
            WheelVerticalView wheel = numberParam.getWheelList().get(i);

            int wheelMin = WHEEL_DATA_RANGE_MIN;
            int wheelMax = WHEEL_DATA_RANGE_MAX;
            int currentNumber = parseChar(currentArray[i]);
            LogKit.e(this, i + ">1 WheelMin:" + wheelMin + ",WheelMax:" + wheelMax);
            if (i == 0) {
                wheelMin = parseChar(minArray[i]);
                wheelMax = parseChar(maxArray[i]);
                LogKit.e(this, i + ">2 WheelMin:" + wheelMin + ",WheelMax:" + wheelMax);
                /*根据上一组滚轮的状态确定这一组滚轮首轮的状态*/
                if (isTriggerMin) {
                    wheelMin = parseChar(minTriggerArray[i]);
                }

                if (isTriggerMax) {
                    wheelMax = parseChar(maxTriggerArray[i]);
                }
                LogKit.e(this, i + ">3 WheelMin:" + wheelMin + ",WheelMax:" + wheelMax);

                  /*根据本滚轮最新的边界值，修正默认值*/
                if (currentNumber < wheelMin) {
                    currentNumber = wheelMin;
                    currentArray[i] = Character.forDigit(wheelMin, 10);
                    numberParam.setCurrentValue(Integer.parseInt(String.valueOf(currentArray)));
                }
                if (currentNumber > wheelMax) {
                    currentNumber = wheelMax;
                    currentArray[i] = Character.forDigit(wheelMax, 10);
                    numberParam.setCurrentValue(Integer.parseInt(String.valueOf(currentArray)));
                }

                  /*通知本组后面的滚轮*/
                if (currentNumber == wheelMin) {
                    isLastNumberMin = true;
                }
                if (currentNumber == wheelMax) {
                    isLastNumberMax = true;
                }

            }

            if (i > 0) {
                /*如果本组上一个滚轮已经处于极限值，则本滚轮修正自己的上下限*/
                if (isLastNumberMin) {
                    wheelMin = parseChar(minArray[i]);
                } else {
                    /*本组若有一位数没有处于极限状态，则立即中断组信号*/
                    isTriggerMin = false;
                }

                if (isLastNumberMax) {
                    wheelMax = parseChar(maxArray[i]);
                } else {
                    /*本组若有一位数没有处于极限状态，则立即中断组信号*/
                    isTriggerMax = false;
                }
                LogKit.e(this, i + ">2 WheelMin:" + wheelMin + ",WheelMax:" + wheelMax);

                /*如果上组滚轮已经处于极限值，则本滚轮根据触发值修正自己的上下限*/
                if (isTriggerMin) {
                    wheelMin = parseChar(minTriggerArray[i]);
                }

                if (isTriggerMax) {
                    wheelMax = parseChar(maxTriggerArray[i]);
                }
                LogKit.e(this, i + ">3 WheelMin:" + wheelMin + ",WheelMax:" + wheelMax);

                /*根据本滚轮最新的边界值，修正默认值*/
                if (currentNumber < wheelMin) {
                    currentNumber = wheelMin;
                    currentArray[i] = Character.forDigit(wheelMin, 10);
                    numberParam.setCurrentValue(Integer.parseInt(String.valueOf(currentArray)));
                }
                if (currentNumber > wheelMax) {
                    currentNumber = wheelMax;
                    currentArray[i] = Character.forDigit(wheelMax, 10);
                    numberParam.setCurrentValue(Integer.parseInt(String.valueOf(currentArray)));
                }

                /*根据本滚轮的默认值是否处于边界，通知下个滚轮*/
                if (currentNumber != wheelMin) {
                    isLastNumberMin = false;
                }
                if (currentNumber != wheelMax) {
                    isLastNumberMax = false;
                }
            }
            notifyWheel(wheel, wheelMin, wheelMax, currentNumber);
        }
        boolean[] result = {isLastNumberMin, isLastNumberMax};
        return result;
    }

    public void notifyWheel(WheelVerticalView wheel, int min, int max, int defaultValue) {

        if (min > max) {
            int i = max;
            max = min;
            min = i;
        }
        if (wheel.getViewAdapter() == null) {
            NumericWheelAdapter adapter = new NumericWheelAdapter(context, min, max);
            adapter.setTextSize(30);
            adapter.setTextTypeface(Typeface.DEFAULT);
            wheel.setViewAdapter(adapter);
            wheel.setCyclic(isWheelRecycle);
            wheel.addScrollingListener(this);
        }
        NumericWheelAdapter adapter = (NumericWheelAdapter) (wheel.getViewAdapter());
        adapter.setMaxValue(max);
        adapter.setMinValue(min);
        wheel.setCurrentItem(defaultValue - min);

    }

    public String getCurrentValue() {
        String currentValueString = "";
        for (Object param : paramList) {
            if (param instanceof NumberParam) {
                NumberParam temp = ((NumberParam) param);
                String value = Integer.toString(temp.getCurrentValue());
                if (isKeepZero()) {
                    value = getZeroString(temp.getWheelItemCount() - value.length()) + value;
                }
                currentValueString += value;
            }
            if (param instanceof String) {
                currentValueString += param;
            }
        }
        return currentValueString;
    }

    @Override
    public void onScrollingStarted(AbstractWheel wheel) {

    }

    @Override
    public void onScrollingFinished(AbstractWheel wheel) {
        refreshAllWheel();
    }

    public int parseChar(char c) {
        return Integer.parseInt("" + c);
    }

    public String getZeroString(int zeroCount) {
        String zero = "";
        for (int i = 0; i < zeroCount; i++) {
            zero += "0";
        }
        return zero;
    }

    public void setWheelRecycle(boolean wheelRecycle) {
        isWheelRecycle = wheelRecycle;
    }

    public List<Object> getParamList() {
        return paramList;
    }

    public void setDefaultValueString(String defaultValueString) {
        this.defaultValueString = defaultValueString;
    }

    public boolean isKeepZero() {
        return isKeepZero;
    }

    public void setKeepZero(boolean keepZero) {
        isKeepZero = keepZero;
    }
}

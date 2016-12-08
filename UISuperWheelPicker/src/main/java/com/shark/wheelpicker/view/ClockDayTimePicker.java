package com.shark.wheelpicker.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;

import com.shark.wheelpicker.R;
import com.shark.wheelpicker.core.AbstractWheel;
import com.shark.wheelpicker.core.adapter.ArrayWheelAdapter;
import com.shark.wheelpicker.core.callback.OnWheelScrollListener;

import java.text.DateFormatSymbols;
import java.util.List;

/**
 * Created by renyuxiang on 2016/3/23.
 */
public class ClockDayTimePicker {
    private Context context;
    private View rootLayout;
    private AbstractWheel firstDataPicker;
    private AbstractWheel secondDataPicker;
    private AbstractWheel thirdDataPicker;
    private boolean scrolling = false;

    /**
     * 设置上下午需要显示的文字，国际化需求
     */
    List<String> amPmLabelList;
    /**
     * 要显示的Label, 可选的
     */
    private String[] firstLabel;

    /**
     * 要显示的Label, 可选的
     */
    private String[] secondLabel;
    /**
     * 要显示的Label, 可选的
     */
    private String[] thirdLabelArray;

    /**
     * 与Label配套的值数组
     */
    private int[] firstValue;

    /**
     * 与Label配套的值数组
     */
    private int[] secondValue;
    /**
     * 与Label配套的值数组
     */
    private int[] thirdValueIndexArray;

    /**
     * 默认值
     */
    private int defaultValue;
    /**
     * 默认值
     */
    private int defaultValue2;
    /**
     * 默认值
     */
    private int defaultValue3;

    /**
     * 整数最小值
     */
    private int min1thValue = 0;

    /**
     * 整数最大值
     */
    private int max1thValue = 11;
    /**
     * 小数最小值
     */
    private int min2thValue = 0;

    /**
     * 小数最大值
     */
    private int max2thValue = 59;
    /**
     * 第一个拾取器的值索引
     */
    private int firstValIndex;

    /**
     * 第二个拾取器的值索引
     */
    private int secondValIndex;
    /**
     * 第三个拾取器的值索引
     */
    private int thirdValIndex;
    /**
     * 第一个拾取器的上一次值索引
     */
    private int lastFirstValIndex;

    /**
     * 第二个拾取器的上一次值索引
     */
    private int lastSecondValIndex;
    /**
     * 第三个拾取器的上一次值索引
     */
    private int lastThirdValIndex;

    /**
     * <b>构造方法。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 基本的构造函数
     *
     * @param context
     */
    public ClockDayTimePicker(Context context, int hour, int minute, boolean isAm) {
        this.context = context;
        this.defaultValue = hour;
        this.defaultValue2 = minute;
        this.defaultValue3 = isAm ? 0 : 1;
        thirdLabelArray = DateFormatSymbols.getInstance().getAmPmStrings();
        initData();
        findViews();
        initComponent();
    }

    private void initData() {
        int range1 = 0;
        int range2 = 0;
        if (min1thValue > max1thValue) {
            int i = max1thValue;
            max1thValue = min1thValue;
            min1thValue = i;
        }
        if (min2thValue > max2thValue) {
            int i = max2thValue;
            max2thValue = min2thValue;
            min2thValue = i;
        }
        range1 = max1thValue - min1thValue + 1;
        firstValIndex = 0;
        firstLabel = new String[range1];
        firstValue = new int[range1];
        for (int index = 0; index < range1; index++) {
            if (min1thValue + index < 10) {
                firstLabel[index] = "0" + String.valueOf(min1thValue + index);
            } else {
                firstLabel[index] = String.valueOf(min1thValue + index);
            }

            firstValue[index] = min1thValue + index;
            if (defaultValue == min1thValue + index)
                firstValIndex = index;
        }

        range2 = max2thValue - min2thValue + 1;
        secondValIndex = 0;
        secondLabel = new String[range2];
        secondValue = new int[range2];
        for (int index = 0; index < range2; index++) {
            if (min2thValue + index < 10) {
                secondLabel[index] = "0" + String.valueOf(min2thValue + index);
            } else {
                secondLabel[index] = String.valueOf(min2thValue + index);
            }
            secondValue[index] = min2thValue + index;
            if (defaultValue2 == min2thValue + index)
                secondValIndex = index;
        }

        thirdValueIndexArray = new int[2];
        for (int index = 0; index < thirdLabelArray.length; index++) {
            thirdValueIndexArray[index] = index;
            if (defaultValue3 == index)
                thirdValIndex = index;
        }
    }

    private void findViews() {
        rootLayout = View.inflate(context, R.layout.reminder_clock_picker, null);
        firstDataPicker = (AbstractWheel) rootLayout.findViewById(R.id.first_data_picker);
        secondDataPicker = (AbstractWheel) rootLayout.findViewById(R.id.second_data_picker);
        thirdDataPicker = (AbstractWheel) rootLayout.findViewById(R.id.third_data_picker);
    }

    private void initComponent() {
        initData();
        scrolling = false;
        initFirstComponent();
        initSecondComponent();
        initThirdComponent();
        lastFirstValIndex = firstValIndex;
        lastSecondValIndex = secondValIndex;
        lastThirdValIndex = thirdValIndex;
    }

    public long getCurrentPickerTime() {
        Log.e("Clock", "getCurrentPickerTime>firstValIndex:" + firstValIndex + ",secondValIndex:" + secondValIndex +
                ",thirdValIndex:" + thirdValIndex);
        long time = firstValIndex * 60 * 60 * 1000;
        time += secondValIndex * 60 * 1000;
        if (thirdValIndex == 1) {
            time += 12 * 60 * 60 * 1000;
        }
        return time;
    }

    public String currentPickerTimeToString() {
        Log.e("Clock", "getCurrentPickerTime>firstValIndex:" + firstValIndex + ",secondValIndex:" + secondValIndex +
                ",thirdValIndex:" + thirdValIndex);
        String time = "";
        if (firstValIndex < 10) {
            time += 0;
        }
        time += Integer.toString(firstValIndex);
        time += ":";
        if (secondValIndex < 10) {
            time += 0;
        }
        time += Integer.toString(secondValIndex);
        time += " ";
        time += thirdLabelArray[thirdValIndex];
        return time;
    }

    private void initFirstComponent() {
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(context, firstLabel);
        adapter.setTextSize(30);
        adapter.setTextTypeface(Typeface.DEFAULT);
        firstDataPicker.setViewAdapter(adapter);
        firstDataPicker.setCurrentItem(firstValIndex);

        firstDataPicker.addScrollingListener(new OnWheelScrollListener() {

            public void onScrollingStarted(AbstractWheel wheel) {
                scrolling = true;
            }

            public void onScrollingFinished(AbstractWheel wheel) {
                scrolling = false;
                firstValIndex = firstDataPicker.getCurrentItem();
            }
        });
    }

    private void initSecondComponent() {
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(context, secondLabel);
        adapter.setTextSize(30);
        adapter.setTextTypeface(Typeface.DEFAULT);
        secondDataPicker.setViewAdapter(adapter);
        secondDataPicker.setCurrentItem(secondValIndex);

        secondDataPicker.addScrollingListener(new OnWheelScrollListener() {

            public void onScrollingStarted(AbstractWheel wheel) {
                scrolling = true;
            }

            public void onScrollingFinished(AbstractWheel wheel) {

                scrolling = false;
                secondValIndex = secondDataPicker.getCurrentItem();
            }
        });
    }

    private void initThirdComponent() {
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<>(context, thirdLabelArray);
        adapter.setTextSize(30);
        adapter.setTextTypeface(Typeface.DEFAULT);
        thirdDataPicker.setViewAdapter(adapter);
        thirdDataPicker.setCurrentItem(thirdValIndex);

        thirdDataPicker.addScrollingListener(new OnWheelScrollListener() {

            public void onScrollingStarted(AbstractWheel wheel) {
                scrolling = true;
            }

            public void onScrollingFinished(AbstractWheel wheel) {
                scrolling = false;
                thirdValIndex = thirdDataPicker.getCurrentItem();
            }
        });
    }

    public View getView() {
        return rootLayout;
    }
}

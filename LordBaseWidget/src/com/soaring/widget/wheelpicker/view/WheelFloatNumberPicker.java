package com.soaring.widget.wheelpicker.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.soaring.widget.R;
import com.soaring.widget.wheelpicker.core.AbstractWheel;
import com.soaring.widget.wheelpicker.core.OnWheelChangedListener;
import com.soaring.widget.wheelpicker.core.OnWheelScrollListener;
import com.soaring.widget.wheelpicker.core.adapter.ArrayWheelAdapter;
import com.soaringcloud.kit.box.DisplayKit;
import com.soaringcloud.kit.box.LogKit;

import java.util.LinkedHashMap;

/**
 * Created by renyuxiang on 2016/3/23.
 */
public class WheelFloatNumberPicker {
    /**
     * 区间拾取器
     */
    public static final int DOUBLE_PICKER = 2;

    private OnFloatNumberPositiveButtonClick onPositiveButtonClick;
    private Context context;
    private View rootLayout;
    private PopupWindow window;
    private AbstractWheel firstDataPicker;
    private AbstractWheel secondDataPicker;
    private LinearLayout secondPickerLayout;
    private TextView pickerTitle;
    private TextView separatorLabel;
    private TextView unitLabel;
    private TextView btnOK;
    private TextView btnCancel;
    private boolean scrolling = false;
    private boolean outSideTouchble;
    /**
     * 弹出窗口标题
     */
    private String title;

    /**
     * 分隔符文本
     */
    private String separatorText;

    /**
     * 弹出窗口宽度， 默认为Match Parent
     */
    private int windowWidth = -1;

    /**
     * 弹出窗口高度
     */
    private int windowHeight;

    /**
     * 弹出窗口是否可获得焦点，默认为false
     */
    private boolean focusable = false;
    /**
     * 要显示的Label, 可选的
     */
    private String[] firstLabel;

    /**
     * 要显示的Label, 可选的
     */
    private String[] secondLabel;

    /**
     * 与Label配套的值数组
     */
    private int[] firstValue;

    /**
     * 与Label配套的值数组
     */
    private int[] secondValue;

    /**
     * 第一个拾取器的数据源
     */
    private LinkedHashMap<Integer, String> firstPickerData;

    /**
     * 第二个拾取器的数据源
     */
    private LinkedHashMap<Integer, String> secondPickerData;

    /**
     * 默认值
     */
    private int defaultValue;
    /**
     * 默认值
     */
    private int defaultValue2;

    /**
     * 第一默认值
     */
    private Integer firstDefaultDataKey;

    /**
     * 第二默认值
     */
    private Integer secondDefaultDataKey;

    /**
     * 是否设置了默认值
     */
    private boolean hasSetDefaultValue;

    /**
     * 整数最小值
     */
    private int min1thValue = 0;

    /**
     * 整数最大值
     */
    private int max1thValue = 10;
    /**
     * 小数最小值
     */
    private int min2thValue = 0;

    /**
     * 小数最大值
     */
    private int max2thValue = 10;
    /**
     * 第一个拾取器的值索引
     */
    private int firstValIndex;

    /**
     * 第二个拾取器的值索引
     */
    private int secondValIndex;

    /**
     * 第一个拾取器的上一次值索引
     */
    private int lastFirstValIndex;

    /**
     * 第二个拾取器的上一次值索引
     */
    private int lastSecondValIndex;

    /**
     * <b>构造方法。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 基本的构造函数
     *
     * @param context
     * @param pickerTitle
     */
    private WheelFloatNumberPicker(Context context, String pickerTitle) {
        this.context = context;
        this.title = pickerTitle;
        this.hasSetDefaultValue = false;
        windowHeight = DisplayKit.getScaleValue((Activity) context, 210);
        findViews();
    }

    public WheelFloatNumberPicker(Context context, String pickerTitle, int minValue1, int maxValue1, int defaultValue1, int minValue2, int maxValue2, int defaultValue2) {
        this(context, pickerTitle);
        this.min1thValue = minValue1;
        this.max1thValue = maxValue1;
        this.min2thValue = minValue2;
        this.max2thValue = maxValue2;
        this.defaultValue = defaultValue1;
        this.defaultValue2 = defaultValue2;
        this.hasSetDefaultValue = true;
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
            firstLabel[index] = String.valueOf(min1thValue + index);
            firstValue[index] = min1thValue + index;
            if (defaultValue == min1thValue + index)
                firstValIndex = index;
        }

        range2 = max2thValue - min2thValue + 1;
        secondValIndex = 0;
        secondLabel = new String[range2];
        secondValue = new int[range2];
        for (int index = 0; index < range2; index++) {
            secondLabel[index] = String.valueOf(min2thValue + index);
            secondValue[index] = min2thValue + index;
            if (defaultValue2 == min2thValue + index)
                secondValIndex = index;
        }
        LogKit.e(this, "firstValIndex:" + firstValIndex + ",secondValIndex:" + secondValIndex);
    }

    private void findViews() {
        rootLayout = View.inflate(context, R.layout.wheel_float_number_picker, null);
        secondPickerLayout = (LinearLayout) rootLayout.findViewById(R.id.second_data_picker_layout);
        pickerTitle = (TextView) rootLayout.findViewById(R.id.picker_title);
        separatorLabel = (TextView) rootLayout.findViewById(R.id.picker_min_label_title);
        unitLabel = (TextView) rootLayout.findViewById(R.id.picker_max_label_title);
    }

    private void initComponent() {
        initData();
        scrolling = false;
        pickerTitle.setText(title);

        initFirstComponent();
        initSecondComponent();

        lastFirstValIndex = firstValIndex;
        lastSecondValIndex = secondValIndex;

        btnOK = (TextView) rootLayout.findViewById(R.id.btn_ok);
        btnCancel = (TextView) rootLayout.findViewById(R.id.btn_cancel);

        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != window) {
                    if (null != onPositiveButtonClick) {
                        onPositiveButtonClick.onReceivedDoubleResult(firstLabel[firstValIndex], secondLabel[secondValIndex], firstValue[firstValIndex], secondValue[secondValIndex]);
                        lastFirstValIndex = firstValIndex;
                        lastSecondValIndex = secondValIndex;
                    }
                    window.dismiss();
                }
            }

        });

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != window) {
                    window.dismiss();
                    if (null != firstDataPicker)
                        firstDataPicker.setCurrentItem(lastFirstValIndex);
                    if (null != secondDataPicker)
                        secondDataPicker.setCurrentItem(lastSecondValIndex);
                }
            }

        });
    }

    private void initFirstComponent() {
        firstDataPicker = (AbstractWheel) rootLayout.findViewById(R.id.first_data_picker);
        firstDataPicker.setVisibleItems(5);
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(context, firstLabel);
        adapter.setTextSize(DisplayKit.getScaleValue((Activity) context, 10));
        adapter.setTextTypeface(Typeface.DEFAULT);
        firstDataPicker.setViewAdapter(adapter);
        firstDataPicker.setCurrentItem(firstValIndex);

        firstDataPicker.addChangingListener(new OnWheelChangedListener() {

            public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
                if (!scrolling) {
                }
            }
        });

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
        secondDataPicker = (AbstractWheel) rootLayout.findViewById(R.id.second_data_picker);
        secondDataPicker.setVisibility(View.VISIBLE);
        secondDataPicker.setVisibleItems(5);
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(context, secondLabel);
        adapter.setTextSize(DisplayKit.getScaleValue((Activity) context, 10));
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


    /**
     * <b>generatePickerWindow。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 生成拾取器窗口
     *
     * @return
     */
    public PopupWindow generatePickerWindow() {
        initComponent();
        window = new PopupWindow(rootLayout, windowWidth, windowHeight, false);
        window.setOutsideTouchable(outSideTouchble);
        window.setBackgroundDrawable(context.getResources().getDrawable(R.color.white));
        window.setAnimationStyle(R.style.WheelPickerPopupAnimation);
        window.setFocusable(focusable);
        return window;
    }

    /**
     * <b>generatePicker。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 生成拾取器
     */
    public void generatePicker() {
        initComponent();
        window = new PopupWindow(rootLayout, windowWidth, windowHeight, false);
        window.setOutsideTouchable(outSideTouchble);
        window.setFocusable(focusable);
        window.setBackgroundDrawable(context.getResources().getDrawable(R.color.white));
        window.setAnimationStyle(R.style.WheelPickerPopupAnimation);
    }

    /**
     * <b>showPicker。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 显示拾取器
     */
    public void showPicker(View anchor) {
        if (null != window && !window.isShowing()) {
            window.showAtLocation(anchor, Gravity.BOTTOM, 0, 0);
        } else {
            Log.d("DateTimePicker", "DateTimePicker ui component does not inited!");
        }
    }

    /**
     * <b>setPickerWidth。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 设置Picker的窗体宽度
     *
     * @param width
     */
    public void setPickerWidth(int width) {
        this.windowWidth = width;
        if (null != window) {
            window.setWidth(windowWidth);
        }
    }

    /**
     * <b>setPickerHeight。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 设置Picker的窗体高度
     *
     * @param height
     */
    public void setPickerHeight(int height) {
        this.windowHeight = height;
        if (null != window) {
            window.setHeight(windowHeight);
        }
    }

    /**
     * <b>isFocusable。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 判断拾取器是否可获得焦点
     *
     * @return
     */
    public boolean isFocusable() {
        return focusable;
    }

    /**
     * <b>setFocusable。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 设置拾取器是否可获得焦点
     *
     * @param focusable
     */
    public void setFocusable(boolean focusable) {
        this.focusable = focusable;
    }

    /**
     * <b>isShowing。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 拾取器是否已显示
     *
     * @return
     */
    public boolean isShowing() {
        if (null != window) {
            return window.isShowing();
        } else {
            return false;
        }
    }


    public void setShowedData(int firstNumber, int secondNumber) {
        for (int index = 0; index < firstValue.length; index++) {
            if (firstValue[index] == firstNumber) {
                firstValIndex = index;
                break;
            }
        }
        firstDataPicker.setCurrentItem(firstValIndex);

        for (int index = 0; index < secondValue.length; index++) {
            if (secondValue[index] == secondNumber) {
                secondValIndex = index;
                break;
            }
        }
        secondDataPicker.setCurrentItem(secondValIndex);
    }

    /**
     * <b>setOnPositiveButtonClick。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 设置点击确认后的回调函数
     *
     * @param onPositiveButtonClick
     */
    public void setOnPositiveButtonClick(OnFloatNumberPositiveButtonClick onPositiveButtonClick) {
        this.onPositiveButtonClick = onPositiveButtonClick;
    }

    public boolean isOutSideTouchble() {
        return outSideTouchble;
    }

    public void setOutSideTouchble(boolean outSideTouchble) {
        this.outSideTouchble = outSideTouchble;
    }

    public interface OnFloatNumberPositiveButtonClick {
        void onReceivedDoubleResult(String firstLabel, String secondLabel, int firstValue, int secondValue);
    }
}

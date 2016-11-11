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

import java.util.Iterator;
import java.util.LinkedHashMap;

public class WheelNumberPicker {

	/**
	 * 单项拾取器
	 */
	public static final int SINGLE_PICKER = 1;

	/**
	 * 区间拾取器
	 */
	public static final int DOUBLE_PICKER = 2;

	private OnPositiveButtonClick onPositiveButtonClick;
	private Context context;
	private View rootLayout;
	private PopupWindow window;
	private AbstractWheel firstDataPicker;
	private AbstractWheel secondDataPicker;
	private LinearLayout secondPickerLayout;
	private TextView pickerTitle;
	private TextView minLabel;
	private TextView maxLabel;
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
//	private String separatorText;

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
	 * 拾取器类型
	 */
	private int pickerType;

	/**
	 * 区间拾取器，最大值和最小值是否可以相等
	 */
	private boolean canEquals = false;

	/**
	 * 区间拾取器，最小值是否能大于最大值
	 */
	private boolean canGreaterThan = false;


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
	 * 最小值
	 */
	private int minValue = 0;

	/**
	 * 最大值
	 */
	private int maxValue = 10;

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
	 * 
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 基本的构造函数
	 * @param context
	 * @param pickerType
	 * @param pickerTitle
	 */
	private WheelNumberPicker(Context context, int pickerType, String pickerTitle) {
		this.context = context;
		this.pickerType = pickerType;
		this.title = pickerTitle;
		this.hasSetDefaultValue = false;
		windowHeight = DisplayKit.getScaleValue((Activity) context, 210);
		findViews();
	}

	/**
	 * 
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 用于只限于上下限的数值拾取器
	 * @param context
	 * @param pickerType
	 * @param pickerTitle
	 * @param minValue
	 * @param maxValue
	 */
	public WheelNumberPicker(Context context, int pickerType, String pickerTitle, int minValue, int maxValue, int defaultValue) {
		this(context, pickerType, pickerTitle);
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.defaultValue = defaultValue;
		this.hasSetDefaultValue = true;
	}

	/**
	 * 
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 用于只限于上下限的数值拾取器
	 * @param context
	 * @param pickerType
	 * @param pickerTitle
	 * @param minValue
	 * @param maxValue
	 */
	public WheelNumberPicker(Context context, int pickerType, String pickerTitle, boolean canEquals, boolean canGreaterThan, int minValue, int maxValue, int defaultValue) {
		this(context, pickerType, pickerTitle);
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.defaultValue = defaultValue;
		this.hasSetDefaultValue = true;
		this.canEquals = canEquals;
		this.canGreaterThan = canGreaterThan;
	}

	/**
	 * 
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 用于需要显示Label的单选拾取器
	 * @param context
	 * @param pickerTitle
	 * @param pickerData
	 */
	public WheelNumberPicker(Context context, String pickerTitle, LinkedHashMap<Integer, String> pickerData) {
		this(context, SINGLE_PICKER, pickerTitle);
		this.firstPickerData = pickerData;
	}
	
	/**
	 * 
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 用于需要显示Label的单选拾取器（可设置默认值）
	 * @param context
	 * @param pickerTitle
	 * @param pickerData
	 * @param defaultDataKey
	 */
	public WheelNumberPicker(Context context, String pickerTitle, LinkedHashMap<Integer, String> pickerData, Integer defaultDataKey) {
		this(context, SINGLE_PICKER, pickerTitle);
		this.firstPickerData = pickerData;
		this.firstDefaultDataKey = defaultDataKey;
		this.hasSetDefaultValue = true;
	}

	/**
	 * 
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 用于需要显示Label的区间拾取器
	 * @param context
	 * @param pickerTitle
	 * @param firstPickerData
	 * @param secondPickerData
	 */
	public WheelNumberPicker(Context context, String pickerTitle, LinkedHashMap<Integer, String> firstPickerData, LinkedHashMap<Integer, String> secondPickerData) {
		this(context, DOUBLE_PICKER, pickerTitle);
		this.firstPickerData = firstPickerData;
		this.secondPickerData = secondPickerData;
		this.canEquals = true;
		this.canGreaterThan = true;
	}
	
	/**
	 * 
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 用于需要显示Label的区间拾取器（可设置默认值）
	 * @param context
	 * @param pickerTitle
	 * @param firstPickerData
	 * @param firstDefaultDataKey
	 * @param secondPickerData
	 * @param secondDefaultDataKey
	 */
	public WheelNumberPicker(Context context, String pickerTitle, LinkedHashMap<Integer, String> firstPickerData, Integer firstDefaultDataKey, LinkedHashMap<Integer, String> secondPickerData, Integer secondDefaultDataKey) {
		this(context, DOUBLE_PICKER, pickerTitle);
		this.firstPickerData = firstPickerData;
		this.secondPickerData = secondPickerData;
		this.canEquals = true;
		this.canGreaterThan = true;
		this.firstDefaultDataKey = firstDefaultDataKey;
		this.secondDefaultDataKey = secondDefaultDataKey;
		this.hasSetDefaultValue = true;
	}
	
	/**
	 * 
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param context
	 * @param pickerTitle
	 * @param canEquals
	 * @param canGreaterThan
	 * @param firstPickerData
	 * @param secondPickerData
	 */
	public WheelNumberPicker(Context context, String pickerTitle, boolean canEquals, boolean canGreaterThan, LinkedHashMap<Integer, String> firstPickerData, LinkedHashMap<Integer, String> secondPickerData) {
		this(context, DOUBLE_PICKER, pickerTitle);
		this.firstPickerData = firstPickerData;
		this.secondPickerData = secondPickerData;
		this.canEquals = canEquals;
		this.canGreaterThan = canGreaterThan;
	}
	
	/**
	 * 
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param context
	 * @param pickerTitle
	 * @param canEquals
	 * @param canGreaterThan
	 * @param firstPickerData
	 * @param firstDefaultDataKey
	 * @param secondPickerData
	 * @param secondDefaultDataKey
	 */
	public WheelNumberPicker(Context context, String pickerTitle, boolean canEquals, boolean canGreaterThan, LinkedHashMap<Integer, String> firstPickerData, Integer firstDefaultDataKey, LinkedHashMap<Integer, String> secondPickerData, Integer secondDefaultDataKey) {
		this(context, DOUBLE_PICKER, pickerTitle);
		this.firstPickerData = firstPickerData;
		this.secondPickerData = secondPickerData;
		this.canEquals = canEquals;
		this.canGreaterThan = canGreaterThan;
		this.firstDefaultDataKey = firstDefaultDataKey;
		this.secondDefaultDataKey = secondDefaultDataKey;
		this.hasSetDefaultValue = true;
	}

	private void initData() {
		int range = 0;
		if (minValue > maxValue) {
			int i = maxValue;
			maxValue = minValue;
			minValue = i;
		}
		range = maxValue - minValue + 1;
		switch (pickerType) {
			case DOUBLE_PICKER:
				secondValIndex = 0;
				if (null != secondPickerData && secondPickerData.size() > 0) {
					range = secondPickerData.size();
					secondLabel = new String[range];
					secondValue = new int[range];
					int index = 0;
					for (Iterator<Integer> iterator = secondPickerData.keySet().iterator(); iterator.hasNext(); index++) {
						Integer key = iterator.next();
						secondLabel[index] = secondPickerData.get(key);
						secondValue[index] = key;
						if (hasSetDefaultValue && secondDefaultDataKey.intValue() == key.intValue()) secondValIndex = index;
					}
				} else {
					secondLabel = new String[range];
					secondValue = new int[range];
					for (int index = 0; index < range; index++) {
						secondLabel[index] = String.valueOf(minValue + index);
						secondValue[index] = minValue + index;
						if (hasSetDefaultValue && defaultValue == minValue + index) secondValIndex = index;
					}
				}
			case SINGLE_PICKER:
				firstValIndex = 0;
				if (null != firstPickerData && firstPickerData.size() > 0) {
					range = firstPickerData.size();
					firstLabel = new String[range];
					firstValue = new int[range];
					int index = 0;
					for (Iterator<Integer> iterator = firstPickerData.keySet().iterator(); iterator.hasNext(); index++) {
						Integer key = iterator.next();
						firstLabel[index] = firstPickerData.get(key);
						firstValue[index] = key;
						if (hasSetDefaultValue && firstDefaultDataKey.intValue() == key.intValue()) firstValIndex = index;
					}
				} else {
					firstLabel = new String[range];
					firstValue = new int[range];
					for (int index = 0; index < range; index++) {
						firstLabel[index] = String.valueOf(minValue + index);
						firstValue[index] = minValue + index;
						if (hasSetDefaultValue && defaultValue == minValue + index) firstValIndex = index;
					}
				}
			default:
		}
	}

	private void findViews() {
		rootLayout = View.inflate(context, R.layout.wheel_number_picker, null);
		secondPickerLayout = (LinearLayout) rootLayout.findViewById(R.id.second_data_picker_layout);
		pickerTitle = (TextView) rootLayout.findViewById(R.id.picker_title);
		minLabel = (TextView) rootLayout.findViewById(R.id.picker_min_label_title);
		maxLabel = (TextView) rootLayout.findViewById(R.id.picker_max_label_title);
	}

	private void initComponent() {
		initData();
		scrolling = false;
		pickerTitle.setText(title);
		switch (pickerType) {
			case DOUBLE_PICKER:
				secondPickerLayout.setVisibility(View.VISIBLE);
				initSecondComponent();
			case SINGLE_PICKER:
				initFirstComponent();
			default:
				if (pickerType == DOUBLE_PICKER) {
					if (!canGreaterThan) {
						if (firstValIndex >= secondValIndex) {
							if (canEquals) {
								secondValIndex = firstValIndex;
							} else {
								if (firstValIndex >= secondValue.length - 1) {
									secondValIndex = secondValue.length - 1;
									if (secondValue.length > 1) {
										firstValIndex = secondValIndex - 1;
									} else {
										secondValIndex = firstValIndex;
									}
								} else {
									secondValIndex = firstValIndex + 1;
								}
							}
						}
						if (null != firstDataPicker) firstDataPicker.setCurrentItem(firstValIndex);
						if (null != secondDataPicker) secondDataPicker.setCurrentItem(secondValIndex);
					}
				}
		}

		lastFirstValIndex = firstValIndex;
		lastSecondValIndex = secondValIndex;
		
		btnOK = (TextView) rootLayout.findViewById(R.id.btn_ok);
		btnCancel = (TextView) rootLayout.findViewById(R.id.btn_cancel);

		btnOK.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != window) {
					if (null != onPositiveButtonClick) {
						switch (pickerType) {
							case DOUBLE_PICKER:
								onPositiveButtonClick.onReceivedDoubleResult(firstLabel[firstValIndex], secondLabel[secondValIndex], firstValue[firstValIndex], secondValue[secondValIndex]);
								lastFirstValIndex = firstValIndex;
								lastSecondValIndex = secondValIndex;
								break;
							case SINGLE_PICKER:
								onPositiveButtonClick.onReceivedSingleResult(firstLabel[firstValIndex], firstValue[firstValIndex]);
								lastFirstValIndex = firstValIndex;
								break;
						}
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
					if (null != firstDataPicker) firstDataPicker.setCurrentItem(lastFirstValIndex);
					if (null != secondDataPicker) secondDataPicker.setCurrentItem(lastSecondValIndex);
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
//				onPositiveButtonClick.onReceivedSingleResult(firstLabel[firstValIndex], firstValue[firstValIndex]);
				checkDataLegal();
			}
		});
	}

	private void initSecondComponent() {
		secondDataPicker = (AbstractWheel) rootLayout.findViewById(R.id.second_data_picker);
		secondDataPicker.setVisibility(View.VISIBLE);
		secondDataPicker.setVisibleItems(5);
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(context, secondLabel);
		adapter.setTextSize(DisplayKit.getScaleValue((Activity) context, 8));
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
				checkDataLegal();
			}
		});
	}

	/**
	 * 
	 * <b>checkDataLegal。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 检查数据合法性
	 */
	private void checkDataLegal() {
		if (pickerType == DOUBLE_PICKER) {
			if (!canGreaterThan) {
				if (firstValIndex >= secondValIndex) {
					if (canEquals) {
						secondValIndex = firstValIndex;
					} else {
						if (firstValIndex >= secondValue.length - 1) {
							secondValIndex = secondValue.length - 1;
							if (secondValue.length > 1) {
								firstValIndex = secondValIndex - 1;
							} else {
								secondValIndex = firstValIndex;
							}
						} else {
							secondValIndex = firstValIndex + 1;
						}
					}
				}
				firstDataPicker.setCurrentItem(firstValIndex, true);
				secondDataPicker.setCurrentItem(secondValIndex, true);
			}
		}
	}

	public void showLabelTitle(String minLabelTitle, String maxLabelTitle) {
		if (null != minLabel) {
			minLabel.setText(minLabelTitle);
			minLabel.setVisibility(View.VISIBLE);
		}
		if (null != maxLabel) {
			maxLabel.setText(maxLabelTitle);
			maxLabel.setVisibility(View.VISIBLE);
		}
	}

	public void hideLabelTitle() {
		if (null != minLabel) minLabel.setVisibility(View.GONE);
		if (null != maxLabel) maxLabel.setVisibility(View.GONE);
	}

	/**
	 * 
	 * <b>generatePickerWindow。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 生成拾取器窗口
	 * @return
	 */
	public PopupWindow generatePickerWindow() {
		initComponent();
		window = new PopupWindow(rootLayout, windowWidth, windowHeight, false);
		window.setOutsideTouchable(true);
		window.setBackgroundDrawable(context.getResources().getDrawable(R.color.white));
		window.setAnimationStyle(R.style.WheelPickerPopupAnimation);
		return window;
	}

	/**
	 * 
	 * <b>generatePicker。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 生成拾取器
	 */
	public void generatePicker() {
		initComponent();
		window = new PopupWindow(rootLayout, windowWidth, windowHeight, false);
		window.setOutsideTouchable(true);
		window.setBackgroundDrawable(context.getResources().getDrawable(R.color.white));
		window.setAnimationStyle(R.style.WheelPickerPopupAnimation);
	}

	/**
	 * 
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
	 * 
	 * <b>setPickerWidth。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 设置Picker的窗体宽度
	 * @param width
	 */
	public void setPickerWidth(int width) {
		this.windowWidth = width;
		if (null != window) {
			window.setWidth(windowWidth);
		}
	}

	/**
	 * 
	 * <b>setPickerHeight。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 设置Picker的窗体高度
	 * @param height
	 */
	public void setPickerHeight(int height) {
		this.windowHeight = height;
		if (null != window) {
			window.setHeight(windowHeight);
		}
	}

	/**
	 * 
	 * <b>isFocusable。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 判断拾取器是否可获得焦点
	 * @return
	 */
	public boolean isFocusable() {
		return focusable;
	}

	/**
	 * 
	 * <b>setFocusable。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 设置拾取器是否可获得焦点
	 * @param focusable
	 */
	public void setFocusable(boolean focusable) {
		this.focusable = focusable;
	}

	/**
	 * 
	 * <b>isShowing。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 拾取器是否已显示
	 * @return
	 */
	public boolean isShowing() {
		if (null != window) {
			return window.isShowing();
		} else {
			return false;
		}
	}
	
	public void setShowedData(int value){
		for(int index = 0; index < firstValue.length; index++){
			if(value == firstValue[index]) firstValIndex = index; 
		}
		firstDataPicker.setCurrentItem(firstValIndex);
	}
	
	public void setShowedData(Integer valueKey){
		int index = 0;
		for (Iterator<Integer> iterator = firstPickerData.keySet().iterator(); iterator.hasNext(); index++) {
			Integer key = iterator.next();
			if (valueKey.intValue() == key.intValue()) firstValIndex = index;
		}
		firstDataPicker.setCurrentItem(firstValIndex);
	}
	
	public void setShowedData(Integer firstValueKey, Integer secondValueKey){
		int index = 0;
		for (Iterator<Integer> iterator = firstPickerData.keySet().iterator(); iterator.hasNext(); index++) {
			Integer key = iterator.next();
			if (firstValueKey.intValue() == key.intValue()) firstValIndex = index;
		}
		firstDataPicker.setCurrentItem(firstValIndex);
		index = 0;
		for (Iterator<Integer> iterator = secondPickerData.keySet().iterator(); iterator.hasNext(); index++) {
			Integer key = iterator.next();
			if (secondValueKey.intValue() == key.intValue()) secondValIndex = index;
		}
		secondDataPicker.setCurrentItem(secondValIndex);
	}

	/**
	 * 
	 * <b>setOnPositiveButtonClick。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 设置点击确认后的回调函数
	 * @param onPositiveButtonClick
	 */
	public void setOnPositiveButtonClick(OnPositiveButtonClick onPositiveButtonClick) {
		this.onPositiveButtonClick = onPositiveButtonClick;
	}

	public boolean isOutSideTouchble() {
		return outSideTouchble;
	}

	public void setOutSideTouchble(boolean outSideTouchble) {
		this.outSideTouchble = outSideTouchble;
		window.setOutsideTouchable(false);
		btnCancel.setVisibility(View.GONE);
	}

	public interface OnPositiveButtonClick {

		void onReceivedSingleResult(String label, int value);

		void onReceivedDoubleResult(String firstLabel, String secondLabel, int firstValue, int secondValue);
	}
}

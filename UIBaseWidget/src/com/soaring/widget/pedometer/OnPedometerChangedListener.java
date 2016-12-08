package com.soaring.widget.pedometer;

/**
 * <b>OnPedometerChangedListener。</b>
 * <p>
 * <b>详细说明：</b>
 * </p>
 * <!-- 在此添加详细说明 --> 无。
 * <p>
 * <b>修改列表：</b>
 * </p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF">
 * <td>序号</td>
 * <td>作者</td>
 * <td>修改日期</td>
 * <td>修改内容</td>
 * </tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr>
 * <td>1</td>
 * <td>Renyuxiang</td>
 * <td>2015-1-21 下午3:55:23</td>
 * <td>建立类型</td>
 * </tr>
 * 
 * </table>
 * 
 * @version 1.0
 * @author Renyuxiang
 * @since 1.0
 */
public interface OnPedometerChangedListener {

	void onStepChanged(int stepValue);

	void onPeaceChanged(int peaceValue);

	void onDistanceChanged(float distanceValue);

	void onSpeedChanged(float speedValue);

	void onCaloriesChanged(float caloriesValue);
}

/**
 * RotateTextView.java 2015-1-17
 * 
 * 天津云翔联动科技有限公司(c) 1995 - 2015 。
 * http://www.soaring-cloud.com.cn
 *
 */
package com.soaring.widget.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * <b>RotateTextView。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-1-17 下午4:20:55</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Renyuxiang
 * @since 1.0
 */

public class RotateTextView
		extends TextView {

	private static final String NAME_SPACE = "http://www.baidu.com/apk/res/custom";

	private static final String ATTR_ROTATE = "rotate";
	private static final int DEFAULT_VALUE_ROTATE = 0;

	private static final String ATTR_TRANSLATE_X = "translateX";
	private static final String ATTR_TRANSLATE_Y = "translateY";
	private static final float DEFAULT_VALUE_TRANSLATE_X = 0f;
	private static final float DEFAULT_VALUE_TRANSLATE_Y = 0f;

	private int rotate = DEFAULT_VALUE_ROTATE;

	private float translateX = DEFAULT_VALUE_TRANSLATE_X;
	private float translateY = DEFAULT_VALUE_TRANSLATE_Y;

	public RotateTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		rotate = attrs.getAttributeIntValue(NAME_SPACE, ATTR_ROTATE, DEFAULT_VALUE_ROTATE);// 旋转度数

		translateX = attrs.getAttributeFloatValue(NAME_SPACE, ATTR_TRANSLATE_X, DEFAULT_VALUE_TRANSLATE_X);// 获取在布局中的x轴偏移百分比
		translateY = attrs.getAttributeFloatValue(NAME_SPACE, ATTR_TRANSLATE_Y, DEFAULT_VALUE_TRANSLATE_Y);// 获取在布局中的y轴偏移百分比
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.translate(getMeasuredWidth() * translateX, getMeasuredHeight() * translateY);
		// 首先偏移在旋转，是因为，如果先旋转，本身xy坐标系也会跟着旋转，之后在偏移会不方便我们的控制，也不直观
		canvas.rotate(rotate);
		super.onDraw(canvas);
	}

}
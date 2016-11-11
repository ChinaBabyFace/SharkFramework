/**
 * NoScrollListView.java 2015-6-4
 * 云翔联动(c) 2015 - 2015 。
 */
package com.soaring.widget.abslistview;

import android.content.Context;
import android.widget.ListView;

/**
 * <b>NoScrollListView。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-6-4 下午6:09:06</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Xunxiaojing
 * @since 1.0
 */
public class NoScrollListView
		extends ListView {

	public NoScrollListView(Context context, android.util.AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	* Integer.MAX_VALUE >> 2,如果不设置，系统默认设置是显示两条
	*/
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}

}

/**
 * FrameworkSettings.java 2015-1-5
 * 
 * 天津云翔联动科技有限公司(c) 1995 - 2015 。
 * http://www.soaring-cloud.com.cn
 *
 */
package com.soaringcloud.kit.box;

import android.util.Log;

import com.soaringcloud.kit.KitSettings;

/**
 * <b>LogTools。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-1-5 下午8:05:12</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Renyuxiang
 * @since 1.0
 */
public class LogKit {

	public static final boolean IS_DEBUG = KitSettings.IS_DEBUG_MODE;
	public static final String GLOBAL_TAG = KitSettings.APP_LOG_TAG;

	public static String getObjectName(Object object) {
		String temp = object.getClass().getName();
		return temp.substring(temp.lastIndexOf(".") + 1, temp.length());
	}

	public static void v(Object obj, String msg) {
		if (IS_DEBUG) {
			Log.v(getObjectName(obj), GLOBAL_TAG + msg);
		}
	}

	public static void e(Object obj, String msg) {
		if (IS_DEBUG) {
			Log.e(getObjectName(obj), GLOBAL_TAG + msg);
		}
	}

	public static void i(Object obj, String msg) {
		if (IS_DEBUG) {
			Log.i(getObjectName(obj), GLOBAL_TAG + msg);
		}
	}

	public static void d(Object obj, String msg) {
		if (IS_DEBUG) {
			Log.d(getObjectName(obj), GLOBAL_TAG + msg);
		}
	}
}

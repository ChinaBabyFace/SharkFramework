/**
 * PreferenceTools.java 2015-1-6
 * 
 * 天津云翔联动科技有限公司(c) 1995 - 2015 。
 * http://www.soaring-cloud.com.cn
 *
 */
package com.soaringcloud.kit.box;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * <b>PreferenceTools。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-1-6 下午1:51:19</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Renyuxiang
 * @since 1.0
 */
public class PreferenceKit {

	public final static String APPLICATION_PREFERENCE_NAME = "www.soaring-cloud.com.cn";

	private static SharedPreferences getApplicationPreference(Context context) {
		return context.getSharedPreferences(APPLICATION_PREFERENCE_NAME, Context.MODE_PRIVATE);
	}

	public static boolean getSharedPreferenceAsBoolean(Context context, String name, boolean defaultValue) {
		return getApplicationPreference(context).getBoolean(name, defaultValue);
	}

	public static void setSharedPreferenceAsBoolean(Context context, String name, boolean value) {
		SharedPreferences.Editor editor = getApplicationPreference(context).edit();
		editor.putBoolean(name, value);
		editor.commit();
	}

	public static float getSharedPreferenceAsFloat(Context context, String name, float defaultValue) {
		return getApplicationPreference(context).getFloat(name, defaultValue);
	}

	public static void setSharedPreferenceAsFloat(Context context, String name, float value) {
		SharedPreferences.Editor editor = getApplicationPreference(context).edit();
		editor.putFloat(name, value);
		editor.commit();
	}

	public static int getSharedPreferenceAsInt(Context context, String name, int defaultValue) {
		return getApplicationPreference(context).getInt(name, defaultValue);
	}

	public static void setSharedPreferenceAsInt(Context context, String name, int value) {
		SharedPreferences.Editor editor = getApplicationPreference(context).edit();
		editor.putInt(name, value);
		editor.commit();
	}

	public static long getSharedPreferenceAsLong(Context context, String name, long defaultValue) {
		return getApplicationPreference(context).getLong(name, defaultValue);
	}

	public static void setSharedPreferenceAsLong(Context context, String name, long value) {
		SharedPreferences.Editor editor = getApplicationPreference(context).edit();
		editor.putLong(name, value);
		editor.commit();
	}

	public static String getSharedPreference(Context context, String name, String defaultValue) {
		return getApplicationPreference(context).getString(name, defaultValue);
	}

	public static void setSharedPreference(Context context, String name, String value) {
		SharedPreferences.Editor editor = getApplicationPreference(context).edit();
		editor.putString(name, value);
		editor.commit();
	}
}

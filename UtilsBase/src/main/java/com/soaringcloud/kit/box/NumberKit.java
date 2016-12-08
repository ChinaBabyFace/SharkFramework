/**
 * NumberKit.java 2015-1-6
 * 
 * 天津云翔联动科技有限公司(c) 1995 - 2015 。
 * http://www.soaring-cloud.com.cn
 *
 */
package com.soaringcloud.kit.box;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b>NumberKit。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-1-6 下午2:51:30</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Renyuxiang
 * @since 1.0
 */
public class NumberKit {

	public static int praseStringToInteger(String valueString, int defaultValue) {
		if (valueString == null)
			return defaultValue;
		int result = defaultValue;
		try {
			result = Integer.parseInt(valueString);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static long praseStringToLong(String valueString, long defaultValue) {
		if (valueString == null)
			return defaultValue;
		long result = defaultValue;
		try {
			result = Long.parseLong(valueString);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		return result;
	}
	public static float praseStringToFloat(String valueString, float defaultValue) {
		if (valueString == null)
			return defaultValue;
		float result = defaultValue;
		try {
			result = Float.parseFloat(valueString);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		return result;
	}
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		return isNum.matches();
	}
}

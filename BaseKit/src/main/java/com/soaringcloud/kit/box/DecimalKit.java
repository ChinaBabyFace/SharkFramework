/**
 * DecimalTools.java 2014年8月4日
 * 
 * 天津云翔联动科技有限公司(c) 1995 - 2014 。
 * http://www.soaring-cloud.com.cn
 *
 */
package com.soaringcloud.kit.box;

import java.math.BigDecimal;

/**
 * <b>DecimalTools。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>renyuxiang</td><td>2014年8月4日 下午2:21:25</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author renyuxiang
 * @since 1.0
 */
public class DecimalKit {

	/**
	 * <b>decimalToInteger。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * BigDecimal转化为integer。
	 * @param decimal
	 * @return
	 */
	public static int decimalToInteger(BigDecimal decimal) {
		try {
			if (decimal != null) {
				return decimal.setScale(0, BigDecimal.ROUND_UP).intValue();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	/**
	 * <b>integerToBigDecimal。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 将int型转化为BigDecimal。
	 * @param value
	 * @return
	 */
	public static BigDecimal integerToBigDecimal(int value) {
		try {
			return new BigDecimal(value);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new BigDecimal(0);
	}
	
	/**
	 * <b>stringToBigDecimalToInt。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 将字符串型的BigDecimal转化为int型。
	 * @param number
	 * @return
	 */
	public static int stringToBigDecimalToInteger(String number) {
		if (number == null || number.trim().equals("")) {
			return 0;
		}
		BigDecimal decimal = new BigDecimal(number);
		return decimal.intValue();
	}
}

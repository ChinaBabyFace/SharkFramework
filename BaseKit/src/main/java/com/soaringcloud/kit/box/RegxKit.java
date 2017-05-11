package com.soaringcloud.kit.box;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegxKit {

	public static String rgex_match_four_number = "(?<![0-9])([0-9]{ 4 })(?![0-9])";

	/**
	 * 从字符串中截取连续4位数字组合 ([0-9]{" + 4 + "})截取六位数字 进行前后断言不能出现数字 用于从短信中获取动态密码
	 * 
	 * @param str
	 *            短信内容
	 * @return 截取得到的4位动态密码
	 */
	public static String getNumberFromString(String str, int numberLength) {
		Pattern continuousNumberPattern = Pattern.compile("(?<![0-9])([0-9]{" + numberLength + "})(?![0-9])");
		Matcher m = continuousNumberPattern.matcher(str);
		String dynamicPassword = "";
		while (m.find()) {
			dynamicPassword = m.group();
		}

		return dynamicPassword;
	}
}

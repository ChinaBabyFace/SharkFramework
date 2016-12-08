/**
 * JavaKit.java 2015-1-6
 * 
 * 天津云翔联动科技有限公司(c) 1995 - 2015 。
 * http://www.soaring-cloud.com.cn
 *
 */
package com.soaringcloud.kit.box;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b>JavaKit。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-1-6 下午3:17:40</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Renyuxiang
 * @since 1.0
 */
public class JavaKit {

	/**
	 * <b>getOne。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param list
	 * @return
	 */
	public static Object getListFirstItem(List<Object> list) {
		if ((list != null) && !list.isEmpty()) {
			if (list.get(0) != null) {
				return list.get(0);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * <b>getListItemById。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 返回list集合中指定id的对象。
	 * @param list
	 * @param id
	 * @return
	 */
	public static Object getListItemById(List<Object> list, int id) {
		if ((list != null) && !list.isEmpty() && id < list.size()) {
			if (list.get(id) != null) {
				return list.get(id);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * <b>isListEmpty。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * list是否为空。
	 * @param l
	 * @return
	 */
	public static boolean isListEmpty(List<?> l) {
		return (l == null || l.isEmpty());
	}

	public static boolean isStringEmpty(String context) {
		return (context == null || context.trim().equals(""));
	}

	public static JSONObject mapToJson(Map<String, Object> map) {
		JSONObject json = new JSONObject();
		try {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				json.put(entry.getKey(), entry.getValue().toString());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * <b>splitStringToList。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 将String按正则表达式给定的规则，拆成一个List。
	 * @param target
	 * @param regx
	 * @return
	 */
	public static List<String> splitStringToList(String target, String regx) {
		List<String> stringList = new ArrayList<String>();
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(target);
		int cursor = 0;
		while (matcher.find()) {
			if (cursor < matcher.start()) {
				stringList.add((String) target.subSequence(cursor, matcher.start()));
			}
			stringList.add(matcher.group());
			cursor = matcher.end() ;
		}
		if (cursor < target.length()) {
			stringList.add((String) target.subSequence(cursor, target.length()));
		}
		return stringList;
	}
}

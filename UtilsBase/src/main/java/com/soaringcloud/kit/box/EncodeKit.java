package com.soaringcloud.kit.box;

import android.os.Bundle;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * <b>EncodeTools。</b>
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
 * <td>2015-1-5 下午8:13:54</td>
 * <td>建立类型</td>
 * </tr>
 * 
 * </table>
 * 
 * @version 1.0
 * @author Renyuxiang
 * @since 1.0
 */
public class EncodeKit {

	public final static String CHARSET_UTF8 = "utf-8";
	private static char[] encodes = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
			.toCharArray();
	private static byte[] decodes = new byte[256];

	/**
	 * <b>parseStringUrlToBundle。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> String URl 转成bundle。
	 * 
	 * @param url
	 * @return
	 */
	public static Bundle parseStringUrlToBundle(String url) {
		try {
			URL u = new URL(url);
			Bundle b = decodeStringUrlPartToBundle(u.getQuery());
			b.putAll(decodeStringUrlPartToBundle(u.getRef()));
			return b;
		} catch (MalformedURLException e) {
		}
		return new Bundle();
	}

	/**
	 * <b>decodeStringUrlPartToBundle。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> 将url string 的一部分转为bundle。
	 * 
	 * @param s
	 * @return
	 */
	public static Bundle decodeStringUrlPartToBundle(String s) {
		Bundle params = new Bundle();
		try {
			if (s != null) {
				String[] array = s.split("&");
				for (String parameter : array) {
					String[] v = parameter.split("=");
					params.putString(URLDecoder.decode(v[0], CHARSET_UTF8),
							URLDecoder.decode(v[1], CHARSET_UTF8));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	/**
	 * <b>encodeBase62。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> base62加密。
	 * 
	 * @param data
	 * @return
	 */
	public static String encodeBase62(byte[] data) {
		StringBuffer sb = new StringBuffer(data.length * 2);
		int pos = 0;
		int val = 0;
		for (int i = 0; i < data.length; i++) {
			val = val << 8 | data[i] & 0xFF;
			pos += 8;
			while (pos > 5) {
				pos -= 6;
				char c = encodes[(val >> pos)];
				sb.append(c == '/' ? "ic" : c == '+' ? "ib" : c == 'i' ? "ia"
						: Character.valueOf(c));
				val &= (1 << pos) - 1;
			}
		}
		if (pos > 0) {
			char c = encodes[(val << 6 - pos)];
			sb.append(

			c == '/' ? "ic" : c == '+' ? "ib" : c == 'i' ? "ia" : Character
					.valueOf(c));
		}
		return sb.toString();
	}

	/**
	 * <b>decodeBase62。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> base62 解密。
	 * 
	 * @param string
	 * @return
	 */
	public static byte[] decodeBase62(String string) {
		if (string == null) {
			return null;
		}
		char[] data = string.toCharArray();
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				string.toCharArray().length);
		int pos = 0;
		int val = 0;
		for (int i = 0; i < data.length; i++) {
			char c = data[i];
			if (c == 'i') {
				c = data[(++i)];
				c = c == 'c' ? '/' : c == 'b' ? '+' : c == 'a' ? 'i'
						: data[(--i)];
			}
			val = val << 6 | decodes[c];
			pos += 6;
			while (pos > 7) {
				pos -= 8;
				baos.write(val >> pos);
				val &= (1 << pos) - 1;
			}
		}
		return baos.toByteArray();
	}

	public static String encodeHtmlForAndroid(String html)
	{
		// Guess a bit bigger for encoded form
		StringBuilder buf = new StringBuilder(html.length() + 16);
		for (int i = 0; i < html.length(); i++) {
			char ch = html.charAt(i);
			if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
					|| (ch >= '0' && ch <= '9') || ".-*_".indexOf(ch) > -1) { //$NON-NLS-1$   
				buf.append(ch);
			} else {
				byte[] bytes = new String(new char[] { ch }).getBytes();
				for (int j = 0; j < bytes.length; j++) {
					buf.append('%');
					buf.append(html.charAt((bytes[j] & 0xf0) >> 4));
					buf.append(html.charAt(bytes[j] & 0xf));
				}
			}
		}
		return buf.toString();
	}
}

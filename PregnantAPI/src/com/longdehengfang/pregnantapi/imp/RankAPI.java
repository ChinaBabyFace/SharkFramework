/**
 * RankAPI.java 2015-4-29
 * 云翔联动(c) 2015 - 2015 。
 */
package com.longdehengfang.pregnantapi.imp;

import android.content.Context;

import com.longdehengfang.pregnantapi.base.BaseAPI;
import com.soaring.io.http.auth.IUserAgent;
import com.soaring.io.http.net.RequestListener;
import com.soaring.io.http.net.SoaringParam;

/**
 * <b>RankAPI。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-4-29 上午9:37:12</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Xunxiaojing
 * @since 1.0
 */
public class RankAPI
		extends BaseAPI {

	private static final String GET_RANK = API_SERVER + "/RankLists";

	public RankAPI(Context context, IUserAgent userAgent) {
		super(context, userAgent);
		// TODO Auto-generated constructor stub
	}

	public void getRankList(SoaringParam param, RequestListener listener) {
		// TODO Auto-generated method stub
		requestAsync(GET_RANK, param, HTTPMETHOD_GET, listener);
	}

}

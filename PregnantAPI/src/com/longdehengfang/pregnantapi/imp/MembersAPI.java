/**
 * MembersAPI.java 2015-4-25
 * 龙德恒方科技发展有限公司(c) 2015 - 2015 。
 */
package com.longdehengfang.pregnantapi.imp;

import android.content.Context;

import com.longdehengfang.pregnantapi.base.BaseAPI;
import com.soaring.io.http.auth.IUserAgent;
import com.soaring.io.http.net.RequestListener;
import com.soaring.io.http.net.SoaringParam;

/**
 * <b>MembersAPI。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-4-25 下午5:19:15</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author huangfuxin
 * @since 1.0
 */
public class MembersAPI
		extends BaseAPI {

	private static final String GET_MEMBERS_URL = API_SERVER + "/Members";
	private static final String PUT_MEMBERS_URL = API_SERVER + "/Members";
	private static final String GET_PREGNANCIES_URL = API_SERVER + "/Pregnancies";
	private static final String GET_POINTS_URL = API_SERVER + "/Points";
	private static final String GET_POINTS_USE_URL = API_SERVER + "/PointDetails";
	private static final String GET_MYCOMMENT = API_SERVER + "/MyComment";

	public MembersAPI(Context context, IUserAgent userAgent) {
		super(context, userAgent);
	}

	// 获取个人信息 备孕
	public void getMembersDetail(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "memberId" };
		requestAsync(GET_MEMBERS_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
	}

	// 提交修改后的备孕个人信息
	public void putMembersDetail(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "memberId", "NickName", "Age", "Height", "Email", "Mobile", "CityId", "Nation", "HasAvoidCertainFood",
				"AvoidCertainFood", "HasAnaphylactogen", "Anaphylactogen", "PortraitUrl", "InfoIntegrity" ,"PregnantDate","PrePregnancyWeight"};
		requestAsync(PUT_MEMBERS_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_PUT, requestListener);
	}

	// 获取个人信息 孕期
	public void getPregnanciesDetail(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "memberId" };
		requestAsync(GET_PREGNANCIES_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
	}

	// 获取积分
	public void getPointsDetail(SoaringParam param, RequestListener requestListener) {
		// String[] keyArray = { "memberId" };
		requestAsync(GET_POINTS_URL, param, HTTPMETHOD_GET, requestListener);
	}

	// 获取及使用记录
	public void getPointsUseDetail(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "PageIndex", "PageSize" };
		requestAsync(GET_POINTS_USE_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
	}

	// 获取评论
	public void getDiscussDetail(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "PageIndex", "PageSize" };
		requestAsync(GET_MYCOMMENT, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
	}
}

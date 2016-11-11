/**
 * SelfAPI.java 2015-4-24
 * 龙德恒方科技发展有限公司(c) 2015 - 2015 。
 */
package com.longdehengfang.pregnantapi.imp;

import android.content.Context;

import com.longdehengfang.pregnantapi.base.BaseAPI;
import com.soaring.io.http.auth.IUserAgent;
import com.soaring.io.http.net.RequestListener;
import com.soaring.io.http.net.SoaringParam;

/**
 * <b>SelfAPI。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-4-24 上午10:15:15</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Renyuxiang
 * @since 1.0
 */
public class SelfAPI
		extends BaseAPI {

	/**GET_FAVORITES。显示收藏列表*/
	private static final String GET_FAVORITES = API_SERVER + "/Favorites";
	/**POST_FAVORITES。添加收藏*/
	private static final String POST_FAVORITES =GET_FAVORITES ;
	/**POST_FAVORITESDELETE。取消收藏*/
	private static final String POST_FAVORITESDELETE=API_SERVER+"/FavoritesDelete";

	/**
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param context
	 * @param userAgent
	 */
	public SelfAPI(Context context, IUserAgent userAgent) {
		super(context, userAgent);
		// TODO Auto-generated constructor stub
	}
	public void getFavorites(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "Type", "ShowType","PageIndex","PageSize" };
		requestAsync(GET_FAVORITES, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
	}
	public void addFavorites(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "Type", "SourceId","CategoryId" };
		requestAsync(POST_FAVORITES, removeInvalidParam(param, keyArray), HTTPMETHOD_POST, requestListener);
	}
	public void favoritesDelete(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "FavoritesList"};
		requestAsync(POST_FAVORITESDELETE, removeInvalidParam(param, keyArray), HTTPMETHOD_POST, requestListener);
	}
}

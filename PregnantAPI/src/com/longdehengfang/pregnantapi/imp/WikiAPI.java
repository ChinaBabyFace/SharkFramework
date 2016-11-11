/**
 * WikiAPI.java 2015-4-24
 * 龙德恒方科技发展有限公司(c) 2015 - 2015 。
 */
package com.longdehengfang.pregnantapi.imp;

import android.content.Context;

import com.longdehengfang.pregnantapi.base.BaseAPI;
import com.soaring.io.http.auth.IUserAgent;
import com.soaring.io.http.net.RequestListener;
import com.soaring.io.http.net.SoaringParam;

/**
 * <b>WikiAPI。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-4-24 上午10:13:58</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Renyuxiang
 * @since 1.0
 */
public class WikiAPI
		extends BaseAPI {
	
	/**GET_LOGIN。百科栏目分类*/
	private static final String GET_WIKICATEGORIES_URL = API_SERVER + "/WikiCategories";
	/**GET_HOTWIKIS_URL百科热点。*/
	private static final String GET_HOTWIKIS_URL = API_SERVER + "/HotWikis";
	/**GET_WIKIS_URL。百科标题*/
	private static final String GET_WIKIS_URL = API_SERVER + "/Wikis";
	/**GET_WIKIDETAILS_URL。百科详情*/
	private static final String GET_WIKIDETAILS_URL = API_SERVER + "/WikiDetails";
	/**POST_COMMENT_URL。提交评论*/
	private static final String POST_COMMENT_URL = API_SERVER + "/Comment";
	/**GET_COMMENT_URL。评论列表*/
	private static final String GET_COMMENT_URL = API_SERVER + "/Comment";
	/**GET_NUTRITIONINFO_URL。营养课堂*/
	private static final String GET_NUTRITIONINFO_URL = API_SERVER + "/NutritionInfo";

	/**
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param context
	 * @param userAgent
	 */
	public WikiAPI(Context context,IUserAgent userAgent) {
		super(context, userAgent);
	}

	public void wikiCategories(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "CategoryType" };
		requestAsync(GET_WIKICATEGORIES_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
	}
	
	public void hotWikis(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "PageSize","CategoryType" };
		requestAsync(GET_HOTWIKIS_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
	}
	public void Wikis(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "CategoryId" ,"PageIndex","PageSize"};
		requestAsync(GET_WIKIS_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
	}
	public void wikiDetails(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "ArticleId" ,"CategoryId"};
		requestAsync(GET_WIKIDETAILS_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
	}
	public void wikiComment(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "ArticleId" ,"CategoryId","Content"};
		requestAsync(POST_COMMENT_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_POST, requestListener);
	}
	public void wikiCommentList(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "ArticleId" ,"PageIndex","PageSize","CategoryId"};
		requestAsync(GET_COMMENT_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
	}
	public void nutritionSchool(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = {""};
		requestAsync(GET_NUTRITIONINFO_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
	}
}

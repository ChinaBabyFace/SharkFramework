/**
 * HomeAPI.java 2015-4-24
 * 龙德恒方科技发展有限公司(c) 2015 - 2015 。
 */
package com.longdehengfang.pregnantapi.imp;

import android.content.Context;

import com.longdehengfang.pregnantapi.base.BaseAPI;
import com.soaring.io.http.auth.IUserAgent;
import com.soaring.io.http.net.RequestListener;
import com.soaring.io.http.net.SoaringParam;

/**
 * <b>HomeAPI。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-4-24 上午10:13:29</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Renyuxiang
 * @since 1.0
 */
public class HomeAPI
		extends BaseAPI {
	
	/**GET_LOGIN。孕周获取胎儿状态*/
	private static final String GET_EMBRYOS_URL = API_SERVER + "/Embryos"; 
	/**GET_BANNERS_URL获取公告。*/
	private static final String GET_BANNERS_URL = API_SERVER + "/Banners"; 
	/**GET_BANNERDETAILS_URL。获取公告详情*/
	private static final String GET_BANNERDETAILS_URL = API_SERVER + "/BannerDetails";
	/**GET_HOME_COLUMN_URL。首页热点的接口*/
	private static final String GET_HOME_COLUMN_URL = API_SERVER + "/HomeColumn";
	/**GET_QUESTIONNAIRERESULTHISTORIES_URL显示日历已答问卷。*/
	private static final String GET_QUESTIONNAIRERESULTHISTORIES_URL = API_SERVER + "/QuestionnaireResultHistories";
    /**GET_VOICE_URL首页语音接口 */
    private static final String GET_VOICE_URL = API_SERVER + "/Voice";
    /**GET_HOMEMODULE_URL首页模块接口 */
    private static final String GET_HOMEMODULE_URL = API_SERVER + "/HomeModule";
    /**GET_HOMEMODULE_URL首页宝宝说活动接口 */
    private static final String GET_HOMENOTICE_URL = API_SERVER + "/HomeNotice";
	/*聊天信息未读数*/
	private static final String GET_UNREAD_COUNT=API_SERVER+"/UnReadMessage";

	/**
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param context
	 * @param userAgent
	 */
	public HomeAPI(Context context,IUserAgent userAgent) {
		super(context, userAgent);
	}
	
	public void embryos(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "NewWeek" };
		requestAsync(GET_EMBRYOS_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
	}
	public void banners(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "AdaptStatus" };
		requestAsync(GET_BANNERS_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
	}
	public void bannersDetail(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "ArticleId" };
		requestAsync(GET_BANNERDETAILS_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
	}
	public void getHomeColumn(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "ArticleCount","QuestionCount" };
		requestAsync(GET_HOME_COLUMN_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
	}
	public void questionnaireResultHistories(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "" };
		requestAsync(GET_QUESTIONNAIRERESULTHISTORIES_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
	}
    public void getVoice(SoaringParam param, RequestListener requestListener) {
        String[] keyArray = { "Type" };
        requestAsync(GET_VOICE_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
    }
    public void getHomNotice(SoaringParam param, RequestListener requestListener) {
        String[] keyArray = { "Type" };
        requestAsync(GET_HOMENOTICE_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
    }
    public void getHomeModule(SoaringParam param, RequestListener requestListener) {
        String[] keyArray = { "" };
        requestAsync(GET_HOMEMODULE_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
    }
	public void getUnreadCount(RequestListener requestListener){
		requestAsync(GET_UNREAD_COUNT, new SoaringParam(), HTTPMETHOD_GET, requestListener);
	}

}

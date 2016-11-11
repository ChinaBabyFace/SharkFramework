/**
 * IssueAPI.java 2015-4-24
 * 龙德恒方科技发展有限公司(c) 2015 - 2015 。
 */
package com.longdehengfang.pregnantapi.imp;

import android.content.Context;

import com.longdehengfang.pregnantapi.base.BaseAPI;
import com.soaring.io.http.auth.IUserAgent;
import com.soaring.io.http.net.RequestListener;
import com.soaring.io.http.net.SoaringParam;

/**
 * <b>IssueAPI。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-4-24 上午10:15:51</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Renyuxiang
 * @since 1.0
 */
public class IssueAPI
		extends BaseAPI {

	private static final String GET_ISSUE = API_SERVER+"/Search";
	/**我要提问*/
	private static final String POST_ISSUE = API_SERVER + "/Questions";
	/**追问*/
	private static final String POST_ISSUE_PUMP = API_SERVER + "/PumpQuestions";
	/**我的问题列表*/
	private static final String GET_SELF_ISSUE = API_SERVER + "/Questions";
	/**问题点赞*/
	private static final String POST_PRAISE_ISSUE =API_SERVER+"/Praise";
	/**我的问题详情。*/
	private static final String GET_SELF_ISSUE_DETAIL=API_SERVER+"/QuestionDetails";
	/**采纳为最佳。*/
	private static final String POST_SELF_ISSUE_DETAIL_BESTANSWER=API_SERVER+"/BestAnswers";
	/**设置争议问题。*/
	private static final String POST_SELF_ISSUE_DETAIL_APPEALS=API_SERVER+"/Appeals";
	/**搜索标签。*/
	private static final String GET_ISSUE_SEARCH_TAGS=API_SERVER+"/Tags";
	/**。未关闭问题的数量*/
	private static final String GET_OPENEDQUESTIONS=API_SERVER+"/OpenedQuestions";
	/**问题收藏。*/
	private static final String POST_ISSUE_FAVORITE=API_SERVER+"/Favorites";
	/**取消问题收藏。*/
	private static final String POST_ISSUE_FAVORITE_DELETE=API_SERVER+"/FavoritesDelete";
	/**
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param context
	 * @param userAgent
	 */
	public IssueAPI(Context context,IUserAgent userAgent) {
		super(context, userAgent);
		// TODO Auto-generated constructor stub
	}

	public void postIssue(SoaringParam param, RequestListener listener) {
		String[] keysArray = { "Title", "Content", "PregnancyStatus", "IsPublic" };
		requestAsync(POST_ISSUE, removeInvalidParam(param, keysArray), HTTPMETHOD_POST, listener);
	}

	public void postPump(SoaringParam param, RequestListener listener) {
		String[] keyArray = { "answerId", "content", "PregnancyStatus" };
		requestAsync(POST_ISSUE_PUMP, removeInvalidParam(param, keyArray), HTTPMETHOD_POST, listener);
	}

	public void getSelfIssue(SoaringParam param, RequestListener listener) {
		String[] keyArray = { "Status", "KeyWord", "PageIndex", "PageSize" };
		requestAsync(GET_SELF_ISSUE, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, listener);
	}
	public void postPraiseIssue(SoaringParam param,RequestListener listener){
		String[]keyArray={"questionId"};
		requestAsync(POST_PRAISE_ISSUE, removeInvalidParam(param, keyArray), HTTPMETHOD_POST, listener);
	}
	public void getSelfIssueDetail(SoaringParam param,RequestListener listener){
		String[]keyArray={"QuestionId"};
		requestAsync(GET_SELF_ISSUE_DETAIL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, listener);
	}
	public void postBestAnswer(SoaringParam param,RequestListener listener){
		String[]keyArray={"AnswerId"};
		requestAsync(POST_SELF_ISSUE_DETAIL_BESTANSWER, removeInvalidParam(param, keyArray), HTTPMETHOD_POST, listener);
	}
	public void postAppeals(SoaringParam param,RequestListener listener){
		String[]keyArray={"QuestionId"};
		requestAsync(POST_SELF_ISSUE_DETAIL_APPEALS, removeInvalidParam(param, keyArray), HTTPMETHOD_POST, listener);
	}
	public void getIssue(SoaringParam param,RequestListener listener){
		String[]keyArray={"SearchType","KeyWord","PageIndex","PageSize","DietitianId"};
		requestAsync(GET_ISSUE, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, listener);
	}
	public void getIssueTags(SoaringParam param,RequestListener listener){
		requestAsync(GET_ISSUE_SEARCH_TAGS, param, HTTPMETHOD_GET, listener);
	}
	public void getIssueCount(SoaringParam param,RequestListener listener){
		String[]keyArray={""};
		requestAsync(GET_OPENEDQUESTIONS, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, listener);
	}
	public void postIssueFavorite(SoaringParam param,RequestListener listener){
		String[]keyArray={"Type","SourceId"};
		requestAsync(POST_ISSUE_FAVORITE, removeInvalidParam(param, keyArray),HTTPMETHOD_POST, listener);
	}
	public void postIssueFavoriteDelete(SoaringParam param,RequestListener listener){
		String[]keyArray={"FavoritesList"};
		requestAsync(POST_ISSUE_FAVORITE_DELETE, removeInvalidParam(param, keyArray), HTTPMETHOD_POST, listener);
		
	}
}

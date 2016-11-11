/**
 * ExamAPI.java 2015-4-28
 * 云翔联动(c) 2015 - 2015 。
 */
package com.longdehengfang.pregnantapi.imp;

import android.content.Context;

import com.longdehengfang.pregnantapi.base.BaseAPI;
import com.soaring.io.http.auth.IUserAgent;
import com.soaring.io.http.net.RequestListener;
import com.soaring.io.http.net.SoaringParam;

/**
 * <b>ExamAPI。</b>
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
 * <td>2015-4-28 下午1:25:51</td>
 * <td>建立类型</td>
 * </tr>
 * 
 * </table>
 * 
 * @version 1.0
 * @author Xunxiaojing
 * @since 1.0
 */
public class ExamAPI extends BaseAPI {

	/** GET_EXAM得到自测题。 */
	private static final String GET_EXAM = API_SERVER + "/TestLibraries";
	/** GET_STATISTIC统计数据。 */
	private static final String GET_STATISTIC = API_SERVER + "/TestStatistics";
	/** PUT_STARTTEST。自测开始答题 */
	private static final String PUT_STARTTEST = API_SERVER + "/StartTest";
	/** GET_EXAM_ANSWER。提交自测题 */
	private static final String GET_EXAM_ANSWER = API_SERVER + "/TestResults";

	public ExamAPI(Context context, IUserAgent userAgent) {
		super(context, userAgent);
		// TODO Auto-generated constructor stub
	}

	public void getExamList(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "DataVersion" };
		requestAsync(GET_EXAM, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
	}

	public void getStatistic(SoaringParam param, RequestListener listener) {
		String[] keyArray = { "RankListId" };
		requestAsync(GET_STATISTIC, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, listener);
	}

	public void examStart(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "" };
		requestAsync(PUT_STARTTEST, removeInvalidParam(param, keyArray), HTTPMETHOD_PUT, requestListener);
	}

	public void postExam(SoaringParam param, RequestListener listener) {
		String[] keyArray = { "TestCount", "TestList" };
		requestAsync(GET_EXAM_ANSWER, removeInvalidParam(param, keyArray), HTTPMETHOD_POST, listener);
	}

}

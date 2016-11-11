/**
 * SurveyAPI.java 2015-4-24
 * 龙德恒方科技发展有限公司(c) 2015 - 2015 。
 */
package com.longdehengfang.pregnantapi.imp;

import android.content.Context;

import com.longdehengfang.pregnantapi.base.BaseAPI;
import com.soaring.io.http.auth.IUserAgent;
import com.soaring.io.http.net.RequestListener;
import com.soaring.io.http.net.SoaringParam;


/**
 * <b>SurveyAPI。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-4-24 上午10:14:27</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Renyuxiang
 * @since 1.0
 */
public class SurveyAPI
		extends BaseAPI {
	
	
	/**POST_WEIGHTS。添加孕期体重*/
	private static final String POST_WEIGHTS=API_SERVER+"/Weights";
	private static final String GET_QUESTIONNAIRERESULTS=API_SERVER+"/QuestionnaireResults";
	/**孕前BMI+当前体重。*/
	private static final String GET_WEIGHT_BMI=API_SERVER+"/BMICharts";
	/**问卷列表。*/
	private static final String GET_SURVEY=API_SERVER+"/Questionnaires"; 
	private static final String POST_SURVEY=API_SERVER+"/Questionnaires";

	/**
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param context
	 * @param userAgent
	 */
	public SurveyAPI(Context context,IUserAgent userAgent) {
		super(context, userAgent);
	}
	
	public void addWeight(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = {"Weight","IsRemoved" };
		requestAsync(POST_WEIGHTS, removeInvalidParam(param, keyArray), HTTPMETHOD_POST, requestListener);
	}
	public void surveyResult(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "ResultId"};
		requestAsync(GET_QUESTIONNAIRERESULTS, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
	}
	public void getBMIWeight(SoaringParam param,RequestListener requestListener){
		requestAsync(GET_WEIGHT_BMI, param, HTTPMETHOD_GET, requestListener);
	}
	public void getSurveyList(SoaringParam param,RequestListener requestListener){
		requestAsync(GET_SURVEY, param, HTTPMETHOD_GET, requestListener);
	}
	public void postSurvey(SoaringParam param,RequestListener requestListener){
		String[] keys={"Height","Weight","QuestionnaireId","TotalScore","Score","BMI","BMIScore","BMITotalScore","LifeStyleScore"
				,"LifeStyleTotalScore","PhysiologyScore","PhysiologyTotalScore","BodyScore","BodyTotalScore","ResultCount","ResultList"};
		requestAsync(POST_SURVEY, removeInvalidParam(param, keys), HTTPMETHOD_POST, requestListener);
	}
	
}

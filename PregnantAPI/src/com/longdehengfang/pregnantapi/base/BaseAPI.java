package com.longdehengfang.pregnantapi.base;

import android.content.Context;
import android.text.TextUtils;

import com.soaring.io.http.auth.IUserAgent;
import com.soaring.io.http.auth.SoaringOauthToken;
import com.soaring.io.http.exception.SoaringException;
import com.soaring.io.http.net.AsyncSoaringRunner;
import com.soaring.io.http.net.RequestListener;
import com.soaring.io.http.net.SoaringParam;
import com.soaringcloud.kit.box.JavaKit;
import com.soaringcloud.kit.box.LogKit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <b>AbsOpenAPI。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2014-12-26 下午2:19:04</td><td>建立类型</td></tr>
 * <p/>
 * </table>
 *
 * @author Renyuxiang
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseAPI {
    /**
     * 访问接口的服务器根地址
     */
    //  开发接口
    //	protected static final String API_SERVER = "http://192.168.18.202:8080/PregnantAPI/v1.0";
    //	protected static final String API_SERVER = "http://121.42.155.39:8888/PregnantAPI/v1.0";
    //	protected static final String API_SERVER = "http://121.42.155.39:8889/PregnantAPI/v1.2";
    //	protected static final String API_SERVER = "http://192.168.10.15:8890/PregnantAPI/v2.0";
//      protected static final String API_SERVER = "http://121.42.155.39:8890/PregnantAPI/v2.0";
    //	protected static final String API_SERVER = "http://192.168.10.57:8083/PregnantAPI/v2.0";
    //==========================================================================================
    //	V1.0版本接口
    //	protected static final String API_SERVER = "http://121.42.155.39:8080/PregnantAPI/v1.0";
    //	V1.2版本接口
    //	protected static final String API_SERVER = "http://121.42.155.39:8081/PregnantAPI/v1.2";
    //	V1.3版正式库
    //	protected static final String API_SERVER = "http://121.42.155.39:8082/PregnantAPI/v1.3";
    //  V2.0版正式接口
    //	protected static final String API_SERVER = "http://121.42.155.39:8083/PregnantAPI/v2.0";
    //  V2.1版正式接口
    protected static final String API_SERVER = "http://121.42.155.39:8084/PregnantAPI/v2.1";

    /**
     * POST 请求方式
     */
    protected static final String HTTPMETHOD_POST = "POST";
    /**
     * GET 请求方式
     */
    protected static final String HTTPMETHOD_GET = "GET";
    /**
     * PUT 请求方式
     */
    protected static final String HTTPMETHOD_PUT = "PUT";
    /**
     * DELETE 请求方式
     */
    protected static final String HTTPMETHOD_DELETE = "DELETE";
    /**
     * HTTP 参数
     */
    protected static final String KEY_ACCESS_TOKEN = "access_token";

    /**
     * 当前的 Token
     */
    protected SoaringOauthToken mAccessToken;
    protected Context mContext;
    protected IUserAgent userAgent;
    protected String mAppKey;

    /**
     * 构造函数，使用各个 API 接口提供的服务前必须先获取 Token。
     *
     * @param userAgent 访问令牌
     */
    public BaseAPI(Context context, IUserAgent userAgent) {
        this.mContext = context;
        this.userAgent = userAgent;
    }

    /**
     * 构造函数，使用各个 API 接口提供的服务前必须先获取 Token。
     *
     * @param accesssToken 访问令牌
     */
    // public BaseAPI(Context context, String appKey, SoaringOauthToken accessToken) {
    // mContext = context;
    // mAppKey = appKey;
    // mAccessToken = accessToken;
    // }

    /**
     * HTTP 异步请求。
     *
     * @param url        请求的地址
     * @param params     请求的参数
     * @param httpMethod 请求方法
     * @param listener   请求后的回调接口
     */
    protected void requestAsync(String url, SoaringParam params, String httpMethod, RequestListener listener) {
        if (userAgent == null || JavaKit.isStringEmpty(userAgent.generateUA()) || JavaKit.isStringEmpty(url) || null == params
                || JavaKit.isStringEmpty(httpMethod) || null == listener) {
            LogKit.e(this, "Argument error!");
            return;
        }
        new AsyncSoaringRunner(mContext).requestAsync(url, params, httpMethod, userAgent, listener);
    }

    /**
     * HTTP 同步请求。
     *
     * @param url        请求的地址
     * @param params     请求的参数
     * @param httpMethod 请求方法
     * @return 同步请求后，服务器返回的字符串。
     * @throws SoaringException
     */
    protected String requestSync(String url, SoaringParam params, String httpMethod) throws SoaringException {
        if (userAgent == null || JavaKit.isStringEmpty(userAgent.generateUA()) || TextUtils.isEmpty(url) || null == params
                || TextUtils.isEmpty(httpMethod)) {
            LogKit.e(this, "Argument error!");
            return "";
        }

        return new AsyncSoaringRunner(mContext).request(url, params, httpMethod, userAgent);
    }

    /**
     * <b>removeUnusedParam。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 将实际接口需要的参数剔除掉。
     *
     * @param param    待提出的参数集合
     * @param keyArray 需要保留的参数Key值数组，如该数组为空则不剔除任何参数
     */
    public SoaringParam removeInvalidParam(SoaringParam param, String[] keyArray) {
        if (keyArray != null && param != null && param.size() > 0 && keyArray.length > 0) {
            Iterator<String> iterator = param.keySet();
            List<String> keyList = new ArrayList<String>();
            while (iterator.hasNext()) {
                boolean flag = false;
                String key = iterator.next();
                for (int j = 0; j < keyArray.length; j++) {
                    if (keyArray[j].toLowerCase().equals(key.toLowerCase())) {
                        flag = true;
                    }
                }
                if (!flag) {
                    keyList.add(key);
                }
            }
            for (int i = 0; i < keyList.size(); i++) {
                param.remove(keyList.get(i));
            }
        }
        return param;
    }
}

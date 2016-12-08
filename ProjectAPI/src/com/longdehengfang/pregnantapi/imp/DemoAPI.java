/*
 * Copyright (c) 2016.  任宇翔创建
 */

package com.longdehengfang.pregnantapi.imp;

import android.content.Context;

import com.longdehengfang.pregnantapi.base.BaseAPI;
import com.soaring.io.http.auth.IUserAgent;
import com.soaring.io.http.exception.SoaringException;
import com.soaring.io.http.net.RequestListener;
import com.soaring.io.http.net.SoaringParam;

/**
 * Created by renyuxiang on 2016/12/8.
 * 这个类仅仅用于展示如何封装底层网络请求接口
 */
public class DemoAPI extends BaseAPI {
    public static final String API_GET = API_SERVER + "/apiGet";
    public static final String API_POST = API_SERVER + "/apiPost";
    public static final String API_PUT = API_SERVER + "/apiPut";

    /**
     * 构造函数，使用各个 API 接口提供的服务前必须先获取 Token。
     *
     * @param context
     * @param userAgent 访问令牌
     */
    public DemoAPI(Context context, IUserAgent userAgent) {
        super(context, userAgent);
    }

    /*以下三个为异步调用*/
    public void apiGet(SoaringParam param, RequestListener requestListener) {
        requestAsync(API_GET, param, HTTPMETHOD_GET, requestListener);
    }

    public void apiPost(SoaringParam param, RequestListener requestListener) {
        requestAsync(API_POST, param, HTTPMETHOD_POST, requestListener);
    }

    public void apiPut(SoaringParam param, RequestListener requestListener) {
        requestAsync(API_PUT, param, HTTPMETHOD_PUT, requestListener);
    }

    /*以下三个为同步调用*/
    public String apiGet(SoaringParam param) throws SoaringException {
        return requestSync(API_GET, param, HTTPMETHOD_GET);
    }

    public String apiPost(SoaringParam param) throws SoaringException {
        return requestSync(API_POST, param, HTTPMETHOD_POST);
    }

    public String apiPut(SoaringParam param) throws SoaringException {
        return requestSync(API_PUT, param, HTTPMETHOD_PUT);
    }
}

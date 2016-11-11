/*
 * Copyright (c) 2016.  任宇翔创建
 */
package com.soaring.io.http;

import android.content.Context;

import com.soaring.io.http.auth.IUserAgent;
import com.soaring.io.http.exception.SoaringException;
import com.soaring.io.http.interceptor.HeaderInterceptor;
import com.soaring.io.http.interceptor.LogInterceptor;
import com.soaring.io.http.net.SoaringParam;
import com.soaring.io.http.net.SoaringStatus;
import com.soaringcloud.kit.box.LogKit;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by renyuxiang on 2015/12/3.
 */
public class HttpManager {
    public static final MediaType jsonReq = MediaType.parse("application/json; charset=utf-8");
    public static final String HTTPMETHOD_GET = "GET";
    public static final String HTTPMETHOD_PUT = "PUT";
    public static final String HTTPMETHOD_DELETE = "DELETE";
    private static final String HTTPMETHOD_POST = "POST";
    private LogInterceptor logInterceptor = null;
    private HeaderInterceptor headerInterceptor = null;
    private OkHttpClient httpClient = null;

    public HttpManager(IUserAgent userAgent) {
        this.headerInterceptor = new HeaderInterceptor(userAgent.generateUA());
        this.logInterceptor = new LogInterceptor();
        this.initHttpClient();
    }

    public String openUrl(Context context, String url, String method, SoaringParam params, IUserAgent userAgent,
                          String file)
            throws SoaringException {
        try {
            Call call = null;
            Response response = null;
            if (method.equals(HTTPMETHOD_GET)) {
                url = url + "?" + params.encodeUrl();
                Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .build();
                call = httpClient.newCall(request);
                response = call.execute();
            } else if (method.equals(HTTPMETHOD_POST)) {
                Request request = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create(jsonReq, params.toJsonString()))
                        .build();
                call = httpClient.newCall(request);
                response = call.execute();
            } else if (method.equals(HTTPMETHOD_PUT)) {
                Request request = new Request.Builder()
                        .url(url)
                        .put(RequestBody.create(jsonReq, params.toJsonString()))
                        .build();
                call = httpClient.newCall(request);
                response = call.execute();
            } else if (method.equals(HTTPMETHOD_DELETE)) {
                Request request = new Request.Builder()
                        .url(url)
                        .delete(RequestBody.create(jsonReq, params.toJsonString()))
                        .build();
                call = httpClient.newCall(request);
                response = call.execute();
            }
            String result = response.body().string();
            LogKit.e(this, "Http code:" + response.code());
            if (!isExecuteSuccess(response.code())) {
                LogKit.e("", "ERROR:" + result);
                throw new SoaringException(result);
            }
            return result;
        } catch (SoaringException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SoaringException(e.getMessage());
        }
    }

    public boolean isExecuteSuccess(int statusCode) {
        switch (statusCode) {
            case SoaringStatus.ACCEPTED:
                return true;
            case SoaringStatus.CREATED:
                return true;
            case SoaringStatus.FORBIDDEN:
                return false;
            case SoaringStatus.GONE:
                return false;
            case SoaringStatus.INTERNAL_SERVER_ERROR:
                return false;
            case SoaringStatus.INVALID_REQUEST:
                return false;
            case SoaringStatus.NO_CONTENT:
                return true;
            case SoaringStatus.NOT_ACCEPTABLE:
                return false;
            case SoaringStatus.NOT_FOUND:
                return false;
            case SoaringStatus.OK:
                return true;
            case SoaringStatus.UNAUTHORIZED:
                return false;
            case SoaringStatus.UNPROCESABLE_ENTITY:
                return false;
            default:
                break;
        }
        return false;
    }

    private void initHttpClient() {
        //日志拦截器一定放在最后，否则输出可能不全
        httpClient = new OkHttpClient();
        //        httpClient.setProtocols(new Pr)
        //        Protocol protocol=new Protocol();
        httpClient.interceptors().add(headerInterceptor);
        //        httpClient.interceptors().add(logInterceptor);
    }

    public void setSSL(SSLSocketFactory factory, final String hostName) {
        httpClient.setSslSocketFactory(factory);
        httpClient.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return hostName.equals(hostname);
            }
        });
    }
}

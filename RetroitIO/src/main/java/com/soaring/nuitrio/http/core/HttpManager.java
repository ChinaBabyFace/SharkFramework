package com.soaring.nuitrio.http.core;

import com.soaring.nuitrio.http.header.IUserAgent;
import com.soaring.nuitrio.http.interceptor.HeaderInterceptor;
import com.soaring.nuitrio.http.interceptor.LogInterceptor;
import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by renyuxiang on 2015/12/3.
 */
public class HttpManager {
    private OkHttpClient httpClient;
    private LogInterceptor logInterceptor;
    private HeaderInterceptor headerInterceptor;
    private String apiBaseUrl;
    private Retrofit retrofit;

    public HttpManager(String apiBaseUrl, IUserAgent userAgent) {
        this.apiBaseUrl = apiBaseUrl;
        this.httpClient = new OkHttpClient();
        this.headerInterceptor = new HeaderInterceptor(userAgent.generateUA());
        this.logInterceptor = new LogInterceptor();
        this.initHttpClient();
        this.initRetrofit();
    }

    private void initHttpClient() {
        //日志拦截器一定放在最后，否则输出可能不全
        httpClient.interceptors().add(headerInterceptor);
        httpClient.interceptors().add(logInterceptor);
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(apiBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

    }

    public <S> S createAPI(Class<S> apiClass) {
        return retrofit.create(apiClass);
    }

    public Retrofit getCore() {
        return retrofit;
    }
}

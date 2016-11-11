package com.soaring.io.http.interceptor;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by renyuxiang on 2015/12/3.
 */
public class HeaderInterceptor implements Interceptor {
    private String userAgent;

    public HeaderInterceptor() {
    }

    public HeaderInterceptor(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                .header("User-Agent", userAgent)
                .header("Content-Type", "application/json")
                .method(original.method(), original.body())
                .build();
        return chain.proceed(request);
    }
}

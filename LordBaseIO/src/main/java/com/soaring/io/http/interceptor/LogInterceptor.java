package com.soaring.io.http.interceptor;

import com.soaringcloud.kit.box.LogKit;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okio.Buffer;
import okio.BufferedSource;

/**
 * <b>LogInterceptor。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015/12/3</td><td>建立类型,实现功能</td></tr>
 * <p/>
 * </table>
 *
 * @author Renyuxiang
 * @version 1.0
 * @since 1.0
 */
public class LogInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        LogKit.e(this, "=========================================================");
        LogKit.e(this, "REQUEST URL:" + request.urlString());
        LogKit.e(this, "HTTP METHOD:" + request.method());
        LogKit.e(this, "USER—AGENT:" + request.header("User-Agent"));
        LogKit.e(this, "=========================================================");

        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        ResponseBody responseBody = response.body();

        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }
        if (response != null) {
            LogKit.e(this, "HTTP COAST TM:" + tookMs);
            LogKit.e(this, "HTTP PROTOCOL:" + response.protocol());
            LogKit.e(this, "HTTP     CODE:" + response.code());
            LogKit.e(this, "HTTP   RESULT:" + (response.isSuccessful() ? "Request successful!" : "Request fail!"));
        }
        if (responseBody.contentLength() != 0) {
            LogKit.e(this, "HTTP RESPONSE:" + buffer.clone().readString(charset));
        } else {
            LogKit.e(this, "HTTP RESPONSE:NO CONTENT");
        }
        LogKit.e(this, "=========================================================");
        return response;
    }
}

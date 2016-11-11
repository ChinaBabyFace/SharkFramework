package com.longdehengfang.pregnantapi.imp;

import android.content.Context;

import com.longdehengfang.pregnantapi.base.BaseAPI;
import com.soaring.io.http.auth.IUserAgent;
import com.soaring.io.http.net.RequestListener;
import com.soaring.io.http.net.SoaringParam;

public class QiniuAPI
		extends BaseAPI {

	/**GET_MEMEBER_HEADER*/
	private static final String GET_MEMEBER_HEADER_URL = API_SERVER + "/Images";
	private static final String POST_MEMEBER_HEADER_URL = API_SERVER + "/Images";

	public QiniuAPI(Context context,IUserAgent userAgent) {
		super(context, userAgent);
	}

	public void getToken(SoaringParam param, RequestListener listener) {
		String[] keyArray = { "sourceType" };
		requestAsync(GET_MEMEBER_HEADER_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, listener);
	}
	public void postToken(SoaringParam param, RequestListener listener) {
		String[] keyArray = { "sourceType" };
		requestAsync(POST_MEMEBER_HEADER_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_POST, listener);
	}
}

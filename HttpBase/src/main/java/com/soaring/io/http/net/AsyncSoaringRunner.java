/**
 * TestAPI.java 2015-1-5
 * <p>
 * 天津云翔联动科技有限公司(c) 1995 - 2015 。
 * http://www.soaring-cloud.com.cn
 */
package com.soaring.io.http.net;

import android.content.Context;
import android.os.AsyncTask;

import com.soaring.io.http.HttpManager;
import com.soaring.io.http.auth.IUserAgent;
import com.soaring.io.http.exception.SoaringException;
import com.soaringcloud.kit.box.LogKit;


/**
 * <b>AsyncSoaringRunner。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-1-8 上午11:11:20</td><td>建立类型</td></tr>
 * <p>
 * </table>
 *
 * @author Renyuxiang
 * @version 1.0
 * @since 1.0
 */
public class AsyncSoaringRunner {

	private Context mContext;

	public AsyncSoaringRunner(Context context) {
		this.mContext = context;
	}

	public String request(String url, SoaringParam params, String httpMethod, IUserAgent userAgent) throws SoaringException {
		LogKit.e(this, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		LogKit.e(this, "Request UserAgent:" + userAgent.generateUA());
		LogKit.e(this, "Request Method:" + httpMethod);
		LogKit.e(this, "Request URL:" + url);
		LogKit.e(this, "Request Param:" + params.toJsonString());
		LogKit.e(this, "===================================");
		HttpManager httpManager=new HttpManager(userAgent);
		String result = httpManager.openUrl(this.mContext, url, httpMethod, params, userAgent, (String) params.get(SoaringParam.FILE_NAME_TAG));
		LogKit.e(this, "Request Result:" + result);
		LogKit.e(this, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return result;
	}

	public void requestAsync(String url, SoaringParam params, String httpMethod, IUserAgent userAgent, RequestListener listener) {
		new RequestRunner(this.mContext, url, params, httpMethod, userAgent, listener).execute(new Void[1]);
	}

	private static class AsyncTaskResult<T> {

		private T result;
		private SoaringException error;

		public AsyncTaskResult(T result) {
			this.result = result;
		}

		public AsyncTaskResult(SoaringException error) {
			this.error = error;
		}

		public T getResult() {
			return this.result;
		}

		public SoaringException getError() {
			return this.error;
		}
	}

	private class RequestRunner
			extends AsyncTask<Void, Void, AsyncTaskResult<String>> {

		private Context mContext;
		private String mUrl;
		private SoaringParam mParams;
		private String mHttpMethod;
		private IUserAgent userAgent;
		private RequestListener mListener;
		private HttpManager httpManager;

		public RequestRunner(Context context, String url, SoaringParam params, String httpMethod, IUserAgent userAgent, RequestListener listener) {
			this.mContext = context;
			this.mUrl = url;
			this.mParams = params;
			this.mHttpMethod = httpMethod;
			this.mListener = listener;
			this.userAgent = userAgent;
		}

		protected AsyncTaskResult<String> doInBackground(Void... params) {
			try {
				LogKit.e(this, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				LogKit.e(this, "Request UserAgent:" + userAgent.generateUA());
				LogKit.e(this, "Request Method:" + mHttpMethod);
				LogKit.e(this, "Request URL:" + mUrl);
				LogKit.e(this, "Request Param:" + mParams.toJsonString());
				LogKit.e(this, "===================================");
				HttpManager httpManager=new HttpManager(userAgent);
				String result = httpManager.openUrl(this.mContext, this.mUrl, this.mHttpMethod, this.mParams, this.userAgent,
						(String) this.mParams.get(SoaringParam.FILE_NAME_TAG));
				LogKit.e(this, "Request Result:" + result);
				LogKit.e(this, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				return new AsyncTaskResult<String>(result);
			} catch (SoaringException e) {
				LogKit.e(this, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				return new AsyncTaskResult<String>(e);
			}
		}

		protected void onPreExecute() {
		}

		protected void onPostExecute(AsyncTaskResult<String> result) {
			SoaringException exception = result.getError();
			if (mListener != null) {
				if (exception != null) {
					this.mListener.onSoaringException(exception);
				} else {
					this.mListener.onComplete(result.getResult());
				}
			}
		}
	}
}

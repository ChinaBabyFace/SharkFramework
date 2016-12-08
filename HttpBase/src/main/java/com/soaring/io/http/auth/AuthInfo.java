package com.soaring.io.http.auth;

import android.content.Context;
import android.os.Bundle;

import com.soaringcloud.kit.box.AndroidKit;


public class AuthInfo {

	private String mAppKey = "";
	private String mRedirectUrl = "";
	private String mScope = "";
	private String mPackageName = "";
	private String mKeyHash = "";

	public AuthInfo(Context context, String appKey, String redirectUrl, String scope) {
		this.mAppKey = appKey;
		this.mRedirectUrl = redirectUrl;
		this.mScope = scope;
		this.mPackageName = context.getPackageName();
		this.mKeyHash = AndroidKit.getApkSignature(context, this.mPackageName);
	}

	public String getAppKey() {
		return this.mAppKey;
	}

	public String getRedirectUrl() {
		return this.mRedirectUrl;
	}

	public String getScope() {
		return this.mScope;
	}

	public String getPackageName() {
		return this.mPackageName;
	}

	public String getKeyHash() {
		return this.mKeyHash;
	}

	public Bundle getAuthBundle() {
		Bundle mBundle = new Bundle();
		mBundle.putString("appKey", this.mAppKey);
		mBundle.putString("redirectUri", this.mRedirectUrl);
		mBundle.putString("scope", this.mScope);
		mBundle.putString("packagename", this.mPackageName);
		mBundle.putString("key_hash", this.mKeyHash);
		return mBundle;
	}

	public static AuthInfo parseBundleData(Context context, Bundle data) {
		String appKey = data.getString("appKey");
		String redirectUrl = data.getString("redirectUri");
		String scope = data.getString("scope");
		return new AuthInfo(context, appKey, redirectUrl, scope);
	}
}

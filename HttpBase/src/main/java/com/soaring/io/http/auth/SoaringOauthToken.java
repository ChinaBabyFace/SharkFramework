package com.soaring.io.http.auth;

import android.os.Bundle;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class SoaringOauthToken {

//	private static final String KEY_UID = "uid";
//	private static final String KEY_ACCESS_TOKEN = "access_token";
//	private static final String KEY_EXPIRES_IN = "expires_in";
//	private static final String KEY_REFRESH_TOKEN = "refresh_token";
	private String mUid = "";
	private String mAccessToken = "";
	private String mRefreshToken = "";
	private long mExpiresTime = 0L;

	public SoaringOauthToken() {
	}

	@Deprecated
	public SoaringOauthToken(String responseText) {
		if ((responseText != null) && (responseText.indexOf("{") >= 0)) {
			try {
				JSONObject json = new JSONObject(responseText);
				setUid(json.optString("uid"));
				setToken(json.optString("access_token"));
				setExpiresIn(json.optString("expires_in"));
				setRefreshToken(json.optString("refresh_token"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public SoaringOauthToken(String accessToken, String expiresIn) {
		this.mAccessToken = accessToken;
		this.mExpiresTime = System.currentTimeMillis();
		if (expiresIn != null) {
			this.mExpiresTime += Long.parseLong(expiresIn) * 1000L;
		}
	}

	public static SoaringOauthToken parseAccessToken(String responseJsonText) {
		if ((!TextUtils.isEmpty(responseJsonText)) && (responseJsonText.indexOf("{") >= 0)) {
			try {
				JSONObject json = new JSONObject(responseJsonText);
				SoaringOauthToken token = new SoaringOauthToken();
				token.setUid(json.optString("uid"));
				token.setToken(json.optString("access_token"));
				token.setExpiresIn(json.optString("expires_in"));
				token.setRefreshToken(json.optString("refresh_token"));

				return token;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static SoaringOauthToken parseAccessToken(Bundle bundle) {
		if (bundle != null) {
			SoaringOauthToken accessToken = new SoaringOauthToken();
			accessToken.setUid(getString(bundle, "uid", ""));
			accessToken.setToken(getString(bundle, "access_token", ""));
			accessToken.setExpiresIn(getString(bundle, "expires_in", ""));
			accessToken.setRefreshToken(getString(bundle, "refresh_token", ""));

			return accessToken;
		}
		return null;
	}

	public boolean isSessionValid() {
		return !TextUtils.isEmpty(this.mAccessToken);
	}

	public Bundle toBundle() {
		Bundle bundle = new Bundle();
		bundle.putString("uid", this.mUid);
		bundle.putString("access_token", this.mAccessToken);
		bundle.putString("refresh_token", this.mRefreshToken);
		bundle.putString("expires_in", Long.toString(this.mExpiresTime));
		return bundle;
	}

	public String toString() {
		return

		"uid: " + this.mUid + ", " + "access_token" + ": " + this.mAccessToken + ", " + "refresh_token" + ": " + this.mRefreshToken + ", " + "expires_in" + ": " + Long.toString(this.mExpiresTime);
	}

	public String getUid() {
		return this.mUid;
	}

	public void setUid(String uid) {
		this.mUid = uid;
	}

	public String getToken() {
		return this.mAccessToken;
	}

	public void setToken(String mToken) {
		this.mAccessToken = mToken;
	}

	public String getRefreshToken() {
		return this.mRefreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.mRefreshToken = refreshToken;
	}

	public long getExpiresTime() {
		return this.mExpiresTime;
	}

	public void setExpiresTime(long mExpiresTime) {
		this.mExpiresTime = mExpiresTime;
	}

	public void setExpiresIn(String expiresIn) {
		if ((!TextUtils.isEmpty(expiresIn)) && (!expiresIn.equals("0"))) {
			setExpiresTime(System.currentTimeMillis() + Long.parseLong(expiresIn) * 1000L);
		}
	}

	private static String getString(Bundle bundle, String key, String defaultValue) {
		if (bundle != null) {
			String value = bundle.getString(key);
			return value != null ? value : defaultValue;
		}
		return defaultValue;
	}
}

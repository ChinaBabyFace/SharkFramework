package com.soaring.io.http.auth;

import android.os.Bundle;

import com.soaring.io.http.exception.SoaringException;

public interface SoaringAuthListener {
	void onComplete(Bundle paramBundle);

	void onWeiboException(SoaringException paramWeiboException);

	void onCancel();
}

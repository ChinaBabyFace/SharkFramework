package com.soaring.io.http.net;

import com.soaring.io.http.exception.SoaringException;

public interface RequestListener {

	void onComplete(String paramString);

	void onSoaringException(SoaringException paramException);
}
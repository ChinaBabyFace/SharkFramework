package com.soaring.io.http.exception;

public class SoaringException extends Exception {

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = 6880391973425758689L;

	public SoaringException() {
	}

	public SoaringException(String message) {
		super(message);
	}

	public SoaringException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public SoaringException(Throwable throwable) {
		super(throwable);
	}
}

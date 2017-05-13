package com.ginny.auth.service.api;

public class GinnyRuntimeException extends RuntimeException {

	public static final String ERROR_CODE_AUTH_MANAGEMENT_SERVICE_ERROR = "AUTH_MANAGEMENT_SERVICE_ERROR";
	public static final String ERROR_CODE_AUTH_MANAGEMENT_SERVICE_ERROR_DESC = "AunthenticationF management service encountered error";

	String errorCode;
	String userErrorDescription;

	public GinnyRuntimeException(Exception e, String errorCode,
			String userErrorDescription) {
		super(e);
		this.userErrorDescription = userErrorDescription;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getUserErrorDescription() {
		return userErrorDescription;
	}

	public void setUserErrorDescription(String errorDescription) {
		this.userErrorDescription = errorDescription;
	}

}

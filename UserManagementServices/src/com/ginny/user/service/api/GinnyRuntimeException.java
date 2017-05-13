package com.ginny.user.service.api;

public class GinnyRuntimeException extends RuntimeException {

	public static final String ERROR_CODE_USER_MANAGEMENT_SERVICE_ERROR = "USER_MANAGEMENT_SERVICE_ERROR";
	public static final String ERROR_CODE_USER_MANAGEMENT_SERVICE_ERROR_DESC = "User management service encountered error";

	public static final String ERROR_CODE_EMAIL_ID_DUPLICATE = "EMAIL_ID_DUPLICATE";
	public static final String ERROR_CODE_EMAIL_ID_DUPLICATE_DESC = "There is already a user with this email. Please try another";

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

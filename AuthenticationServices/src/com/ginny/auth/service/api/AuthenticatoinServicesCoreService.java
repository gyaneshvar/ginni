package com.ginny.auth.service.api;

public interface AuthenticatoinServicesCoreService {

	public boolean isValidAccessKey(String accessKey);

	public String findAccessKeyJSON(String userId);

	public void createAccessKey(String userAuthInfoJSON);

}
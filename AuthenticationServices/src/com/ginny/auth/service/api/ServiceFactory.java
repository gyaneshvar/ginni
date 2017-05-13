package com.ginny.auth.service.api;

import com.ginny.auth.service.impl.AuthenticatoinServicesCoreServiceImpl;

public class ServiceFactory {
	public static AuthenticatoinServicesCoreService createAuthenticatoinServicesCoreService() {
		return new AuthenticatoinServicesCoreServiceImpl();
	}
}

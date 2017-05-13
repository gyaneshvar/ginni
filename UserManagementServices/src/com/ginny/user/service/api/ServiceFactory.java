package com.ginny.user.service.api;

import com.ginny.user.service.impl.UserManagementServicesCoreServiceImpl;

public class ServiceFactory {
	public static UserManagementServicesCoreService createUserManagementServicesCoreService() {
		return new UserManagementServicesCoreServiceImpl();
	}
}

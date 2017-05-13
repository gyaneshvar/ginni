package com.ginny.servicemanager.service.api;

import com.ginny.servicemanager.service.impl.ServiceManagerCoreServiceImpl;

public class ServiceFactory {
	public static ServiceManagerCoreService createServiceManagerCoreService() {
		return new ServiceManagerCoreServiceImpl();
	}
}

package com.ginny.reminder.service.api;

import com.ginny.reminder.service.impl.ReminderManagementServicesCoreServiceImpl;


public class ServiceFactory {
	public static ReminderManagementServicesCoreService createReminderManagementServicesCoreService() {
		return new ReminderManagementServicesCoreServiceImpl();
	}
}

package com.ginny.reminder.service.api;

public interface ReminderManagementServicesCoreService {

	public void addReminder(String userId, String reminderJSON);

	public void deleteReminder(String remId);

	public String findReminderInfoJSON(String userId,
			Long startTimeInMillisSinceEpoch, Long endTimeInMillisSinceEpoch);

}
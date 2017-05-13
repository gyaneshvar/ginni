package com.ginny.reminder.service.impl;

import java.util.List;
import java.util.logging.Logger;

import com.ginny.reminder.db.mongo.ReminderDBVO;
import com.ginny.reminder.db.mongo.ReminderManagementDAO;
import com.ginny.reminder.service.api.GinnyRuntimeException;
import com.ginny.reminder.service.api.ReminderManagementServicesCoreService;
import com.google.gson.Gson;

public class ReminderManagementServicesCoreServiceImpl implements
		ReminderManagementServicesCoreService {
	Logger logger = Logger
			.getLogger(ReminderManagementServicesCoreServiceImpl.class
					.getName());

	public void addReminder(String userJSON) {
		try {
			ReminderManagementDAO dao = new ReminderManagementDAO();
			ReminderDBVO dbvo = new Gson().fromJson(userJSON,
					ReminderDBVO.class);
			// check input...
			dao.addReminder(dbvo);

		} catch (GinnyRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new GinnyRuntimeException(
					e,
					GinnyRuntimeException.ERROR_CODE_REMINDER_MANAGEMENT_SERVICE_ERROR,
					GinnyRuntimeException.ERROR_CODE_REMINDER_MANAGEMENT_SERVICE_ERROR_DESC);
		}

	}

	public void deleteReminder(String remId) {
		new ReminderManagementDAO().deleteReminder(remId);

	}

	public String findReminderInfoJSON(String userId,
			Long startTimeInMillisSinceEpoch, Long endTimeInMillisSinceEpoch) {
		List<ReminderDBVO> dbvoList = new ReminderManagementDAO()
				.findReminders(userId, startTimeInMillisSinceEpoch,
						endTimeInMillisSinceEpoch);

		return dbvoList != null ? new Gson().toJson(dbvoList) : null;
	}

}

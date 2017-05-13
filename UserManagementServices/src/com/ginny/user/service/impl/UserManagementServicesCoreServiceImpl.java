package com.ginny.user.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import com.ginny.user.db.mongo.UserDBVO;
import com.ginny.user.db.mongo.UserManagementDAO;
import com.ginny.user.service.api.GinnyRuntimeException;
import com.ginny.user.service.api.UserManagementServicesCoreService;
import com.google.gson.Gson;

public class UserManagementServicesCoreServiceImpl implements
		UserManagementServicesCoreService {
	Logger logger = Logger
			.getLogger(UserManagementServicesCoreServiceImpl.class.getName());

	public void createUser(String userJSON) {
		try {
			UserManagementDAO dao = new UserManagementDAO();
			UserDBVO dbvo = new Gson().fromJson(userJSON, UserDBVO.class);
			UserDBVO existingUser = dao.finfUserInfoByEmail(dbvo.getEmail());
			if (existingUser == null) {
				dao.createUser(dbvo);
				existingUser = dao.finfUserInfo(dbvo.getEmail());
				if (existingUser != null) {
					dao.createUserAuthInfo(existingUser.getId(),
							hashPassword(dbvo.getPassword()));
				}
			} else {
				throw new GinnyRuntimeException(
						null,
						GinnyRuntimeException.ERROR_CODE_EMAIL_ID_DUPLICATE,
						GinnyRuntimeException.ERROR_CODE_EMAIL_ID_DUPLICATE_DESC);
			}
		} catch (GinnyRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new GinnyRuntimeException(
					e,
					GinnyRuntimeException.ERROR_CODE_USER_MANAGEMENT_SERVICE_ERROR,
					GinnyRuntimeException.ERROR_CODE_USER_MANAGEMENT_SERVICE_ERROR_DESC);
		}

	}

	public void updateUser(String userId, String userJSON) {
		new UserManagementDAO().updateUser(userId,
				((new Gson().fromJson(userJSON, UserDBVO.class))));

	}

	public void deleteUser(String userId) {
		// move to be deleted user to deleted user list..
		new UserManagementDAO().deleteUser(userId);
		new UserManagementDAO().deleteUserAuthInfo(userId);

	}

	public String findUserInfoJSON(String userId) {
		UserDBVO dbvo = new UserManagementDAO().finfUserInfo(userId);

		return dbvo != null ? new Gson().toJson(dbvo) : null;
	}

	public String findUserInfoByEmailJSON(String email) {
		UserDBVO dbvo = new UserManagementDAO().finfUserInfoByEmail(email);

		return dbvo != null ? new Gson().toJson(dbvo) : null;
	}

	private String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			md.update(password.getBytes(), 0, password.length());

			byte[] mdbytes = md.digest();
			return new String(mdbytes);
		} catch (NoSuchAlgorithmException e) {
			throw new GinnyRuntimeException(
					e,
					GinnyRuntimeException.ERROR_CODE_USER_MANAGEMENT_SERVICE_ERROR,
					GinnyRuntimeException.ERROR_CODE_USER_MANAGEMENT_SERVICE_ERROR_DESC);
		}
	}
}

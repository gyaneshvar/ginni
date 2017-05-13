package com.ginny.auth.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.ginny.auth.db.mongo.AuthSessionAccessKeyDAO;
import com.ginny.auth.db.mongo.UserAuthInfoDBVO;
import com.ginny.auth.service.api.AuthenticatoinServicesCoreService;
import com.google.gson.Gson;

public class AuthenticatoinServicesCoreServiceImpl implements
		AuthenticatoinServicesCoreService {
	Logger logger = Logger
			.getLogger(AuthenticatoinServicesCoreServiceImpl.class.getName());

	public boolean isValidAccessKey(String accessKey) {
		return true;
	}

	public String findAccessKeyJSON(String userId) {

		Map<String, String> resultAttribMap = new HashMap();
		String accessKey = null;
		String status = "failure";
		AuthSessionAccessKeyDAO dao = new AuthSessionAccessKeyDAO();

		accessKey = dao.findAuthSessionAccessKeyForUser(userId);
		if (accessKey != null) {
			status = "success";
		}

		resultAttribMap.put("status", status);
		resultAttribMap.put("accessKey", accessKey);
		return new Gson().toJson(resultAttribMap);

	}

	public void createAccessKey(String userAuthInfoJSON) {

		Map<String, String> resultAttribMap = new HashMap();
		String accessKey = null;
		String status = "failure";

		UserAuthInfoDBVO authInfo = new Gson().fromJson(userAuthInfoJSON,
				UserAuthInfoDBVO.class);
		AuthSessionAccessKeyDAO dao = new AuthSessionAccessKeyDAO();

		if (dao.isValidUserAuthInfo(authInfo)) {
			accessKey = dao.findAuthSessionAccessKeyForUser(authInfo
					.getUserId());
			if (accessKey == null) {
				dao.createAuthSessionAccessKey(authInfo.getUserId());
			}
		}

	}
}

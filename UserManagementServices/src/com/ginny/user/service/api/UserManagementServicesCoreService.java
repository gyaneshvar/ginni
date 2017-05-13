package com.ginny.user.service.api;

public interface UserManagementServicesCoreService {

	public void createUser(String userJSON);

	public void updateUser(String userId, String userJSON);

	public void deleteUser(String userID);

	public String findUserInfoJSON(String userId);

	public String findUserInfoByEmailJSON(String email);

}
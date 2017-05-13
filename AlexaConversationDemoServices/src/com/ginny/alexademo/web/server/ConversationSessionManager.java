package com.ginny.alexademo.web.server;

public interface ConversationSessionManager {
	public static final String NONE = "NONE";

	public String findResponse(String deviceId, String queryMessageRaw);

	public String findLastCommandJson(String deviceId);

	public boolean isStartOfNewSession(String queryMessage);

	public void reset(String deviceId);

	public default String getUser(){return ConversationSessionManagerFactory.demoUser;};
}
package com.ginny.alexademo.web.server;

public class ConversationSessionManagerFactory {
	static String demoUser = "Doug";
	static ConversationSessionManager conversationSessionManager;

	public static ConversationSessionManager createSession(String deviceId,
			String queryMessageRaw) {
		queryMessageRaw = queryMessageRaw == null ? "" : queryMessageRaw;

		if (ConversationSessionManagerMyCalendarImpl
				.getConversationSessionManager().isStartOfNewSession(
						queryMessageRaw)) {
			conversationSessionManager = ConversationSessionManagerMyCalendarImpl
					.getConversationSessionManager();
		} else if (ConversationSessionManagerCommunityEventFinderImpl
				.getConversationSessionManager().isStartOfNewSession(
						queryMessageRaw)) {
			conversationSessionManager = ConversationSessionManagerCommunityEventFinderImpl
					.getConversationSessionManager();
		} else if (ConversationSessionManagerGoing2BedImpl
				.getConversationSessionManager().isStartOfNewSession(
						queryMessageRaw)) {
			conversationSessionManager = ConversationSessionManagerGoing2BedImpl
					.getConversationSessionManager();
		}else if (ConversationSessionManagerImageFinderImpl
				.getConversationSessionManager().isStartOfNewSession(
						queryMessageRaw)) {
			conversationSessionManager = ConversationSessionManagerImageFinderImpl
					.getConversationSessionManager();
		}else if (conversationSessionManager == null) {
			conversationSessionManager = ConversationSessionManagerCommunityEventFinderImpl
					.getConversationSessionManager();
		}

		return conversationSessionManager;
	}

	public static String resetAllSession(String deviceId, String queryMessage) {
		queryMessage = queryMessage == null ? "" : queryMessage;

		if (checkUser(queryMessage)
				|| (queryMessage.contains("reset") && queryMessage
						.contains("demo"))) {
			ConversationSessionManagerMyCalendarImpl
					.getConversationSessionManager().reset(deviceId);
			ConversationSessionManagerGoing2BedImpl
					.getConversationSessionManager().reset(deviceId);
			ConversationSessionManagerCommunityEventFinderImpl
					.getConversationSessionManager().reset(deviceId);
			demoUser = "Doug";
			String response = "{_Q_response_Q_: { _Q_shouldEndSession_Q_: false, _Q_outputSpeech_Q_: {_Q_type_Q_: _Q_SSML_Q_,"
					+ " _Q_ssml_Q_: _Q_<speak> Yes I am ? </speak>_Q_}} }";

			response = response.replace("_Q_", "\"");

			return response;

		}

		return null;
	}

	private static boolean checkUser(String queryMessage) {
		boolean isDoug = (queryMessage.contains("are")
				&& queryMessage.contains("you") && queryMessage
				.contains("ready"));
		boolean isSteve = (queryMessage.contains("are")
				&& queryMessage.contains("you") && queryMessage.contains("set"));
		if (isDoug)
			demoUser = "Doug";
		if (isSteve)
			demoUser = "Steve";
		return isDoug || isSteve;
	}
}

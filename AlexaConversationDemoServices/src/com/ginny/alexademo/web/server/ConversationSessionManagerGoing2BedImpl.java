package com.ginny.alexademo.web.server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gson.Gson;

public class ConversationSessionManagerGoing2BedImpl implements
		ConversationSessionManager {
	static ConversationSessionManagerGoing2BedImpl manager = new ConversationSessionManagerGoing2BedImpl();

	static enum VoceCommandID {
		GOING_TO_BED, GOOD_NIGHT_MSG
	}

	// device id session map
	Map<String, ConversationSession> sessionMap = new HashMap<>();

	public void reset(String deviceId) {
		ConversationSession conversationSession = sessionMap.get(deviceId);
		if (conversationSession != null) {
			conversationSession.reset();
		}
	}

	VoiceCommand findLastCommand(String deviceId) {
		VoiceCommand lastCommand = null;
		ConversationSession conversationSession = sessionMap.get(deviceId);
		if (conversationSession != null) {
			lastCommand = conversationSession.getLastCommand();
		}

		if (lastCommand == null) {
			lastCommand = new VoiceCommand(NONE, NONE, NONE);
		} else {
			if (System.currentTimeMillis() - lastCommand.ts > (3 * 60 * 1000)) {
				lastCommand = new VoiceCommand(NONE, NONE, NONE);
			}
		}
		lastCommand.user = ConversationSessionManagerFactory.demoUser;
		return lastCommand;
	}

	public String findLastCommandJson(String deviceId) {

		return new Gson().toJson(findLastCommand(deviceId));
	}

	public String findResponse(String deviceId, String queryMessageRaw) {

		String queryMessage = queryMessageRaw.toLowerCase();
		ConversationSession cnvSession = sessionMap.get(deviceId);
		if (cnvSession == null) {
			cnvSession = new ConversationSession();
			sessionMap.put(deviceId, cnvSession);
		}

		Logger.getAnonymousLogger().info(
				" deviceID=" + deviceId + " queryMessageRaw..."
						+ queryMessageRaw + " ...findLastCommand(deviceId)"
						+ findLastCommand(deviceId));
		Logger.getAnonymousLogger().info(
				" deviceID=" + deviceId + " ...findLastCommand(deviceId)"
						+ findLastCommand(deviceId));

		String GOING_TO_BED = "Did you take your medicine, you should be taking 30mg of Monopril and 30mg of Lipitor?";
		String GOOD_NIGHT_MSG = "Good night Doug";

		// User: What is in my calendar tomorrow?
		if (isStartOfNewSession(queryMessage)) {

			String response = "{_Q_response_Q_: { _Q_shouldEndSession_Q_: false, _Q_outputSpeech_Q_: {_Q_type_Q_: _Q_SSML_Q_,"
					+ " _Q_ssml_Q_: _Q_<speak> GOING_TO_BED </speak>_Q_}} }";

			response = response.replace("_Q_", "\"");
			response = response.replace("GOING_TO_BED", GOING_TO_BED);
			// save command
			cnvSession.addCommand(new VoiceCommand(VoceCommandID.GOING_TO_BED
					.toString(), VoceCommandID.GOING_TO_BED.toString(),
					GOING_TO_BED));
			return response;
		} else if (queryMessage.contains("yes")
				&& findLastCommand(deviceId) != null
				&& findLastCommand(deviceId).id
						.equals(VoceCommandID.GOING_TO_BED.toString())) {

			String response = "{_Q_response_Q_: {_Q_shouldEndSession_Q_: false, _Q_outputSpeech_Q_: {_Q_type_Q_: _Q_SSML_Q_,"
					+ " _Q_ssml_Q_: _Q_<speak> GOOD_NIGHT_MSG"
					+ "    </speak>_Q_}} }";

			response = response.replace("_Q_", "\"");
			response = response.replace("GOOD_NIGHT_MSG", GOOD_NIGHT_MSG);

			// save command
			cnvSession.addCommand(new VoiceCommand(VoceCommandID.GOOD_NIGHT_MSG
					.toString(), VoceCommandID.GOOD_NIGHT_MSG.toString(),
					GOOD_NIGHT_MSG));
			return response;
		} else {
			String response = "{_Q_response_Q_: {_Q_outputSpeech_Q_: {_Q_type_Q_: _Q_SSML_Q_,"
					+ " _Q_ssml_Q_: _Q_<speak>   </speak>_Q_}} }";

			response = response.replace("_Q_", "\"");
			return response;

		}
		/**
		 * User: I am going to bed
		Ginni: Did you take your medicine, you should be taking 30mg of Monopril and 30mg of Lipitor?
		User: Yes i did?
		Ginni: Good night Doug.

		 */

	}

	public boolean isStartOfNewSession(String queryMessage) {
		return queryMessage.contains("going") && (queryMessage.contains("bed"));
	}

	private ConversationSessionManagerGoing2BedImpl() {
	}

	public static ConversationSessionManager getConversationSessionManager() {
		return manager;
	}

	static class VoiceCommand {
		String user;
		String id;
		String request;
		String response;
		Long ts;

		public VoiceCommand(String id, String msg, String response) {
			super();
			this.id = id;
			this.request = msg;
			this.response = response;
			this.ts = System.currentTimeMillis();
		}

		public String toString() {
			return " id=" + id + " request=" + request;
		}

	}

	class ConversationSession {
		Long createTs = System.currentTimeMillis();
		LinkedList<VoiceCommand> cmds = new LinkedList<>();

		void addCommand(VoiceCommand cmd) {
			cmds.addLast(cmd);
			if (cmds.size() > 20) {
				cmds.removeFirst();
			}
		}

		public void reset() {
			cmds.clear();
		}

		public VoiceCommand getLastCommand() {
			if (cmds.size() > 0) {
				return cmds.getLast();
			} else {
				return new VoiceCommand(NONE, NONE, NONE);
			}

		}
	}

}

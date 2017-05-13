package com.ginny.alexademo.web.server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gson.Gson;

public class ConversationSessionManagerCommunityEventFinderImpl implements
		ConversationSessionManager {
	static ConversationSessionManagerCommunityEventFinderImpl manager = new ConversationSessionManagerCommunityEventFinderImpl();

	static enum VoceCommandID {
		UP_COMMING_EVENTS, SECOND_EVENT, LAST_EVENT, MOTHER_BRUNCH, ADDING_BRUNCH, BOOK_MY_RIDE, CONTACING_DAUGHTER, CONTACING_SON, CONTACING_UBER, DAUGHTER_CONFIRMED, SON_CONFIRMED, UBER_CONFIRMED, SHARE_PICTURE_YES, SHARE_PICTURE_NO
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

		String UP_COMMING_EVENTS = "These are the upcoming events in the community. ";
		String FIRST_EVENT = " First event is the movie night this Friday ?";
		String SECOND_EVENT = "Blossom trail hiking event ?";

		String LAST_EVENT = "Mother`s Day brunch";
		String MOTHER_BRUNCH = "Mothers day brunch will be in Holiday Touch dining room and free for "
				+ "community members. Brunch will be served from 12:00 to 1:00. No reservation required?";
		// User: What is in my calendar tomorrow?
		if (isStartOfNewSession(queryMessage)) {

			String response = "{_Q_response_Q_: { _Q_shouldEndSession_Q_: false, _Q_outputSpeech_Q_: {_Q_type_Q_: _Q_SSML_Q_,"
					+ " _Q_ssml_Q_: _Q_<speak> UP_COMMING_EVENTS"

					+ " FIRST_EVENT  </speak>_Q_}} }";

			response = response.replace("_Q_", "\"");
			response = response.replace("UP_COMMING_EVENTS", UP_COMMING_EVENTS);
			response = response.replace("FIRST_EVENT", FIRST_EVENT);
			// save command
			cnvSession.addCommand(new VoiceCommand(
					VoceCommandID.UP_COMMING_EVENTS.toString(),
					VoceCommandID.UP_COMMING_EVENTS.toString(),
					UP_COMMING_EVENTS));
			return response;
		} else if (queryMessage.contains("next")
				&& findLastCommand(deviceId) != null
				&& findLastCommand(deviceId).id
						.equals(VoceCommandID.UP_COMMING_EVENTS.toString())) {

			String response = "{_Q_response_Q_: {_Q_shouldEndSession_Q_: false, _Q_outputSpeech_Q_: {_Q_type_Q_: _Q_SSML_Q_,"
					+ " _Q_ssml_Q_: _Q_<speak> SECOND_EVENT"
					+ "    </speak>_Q_}} }";

			response = response.replace("_Q_", "\"");
			response = response.replace("SECOND_EVENT", SECOND_EVENT);

			// save command
			cnvSession.addCommand(new VoiceCommand(VoceCommandID.SECOND_EVENT
					.toString(), VoceCommandID.SECOND_EVENT.toString(),
					SECOND_EVENT));
			return response;
		} else if (queryMessage.contains("next")
				&& findLastCommand(deviceId) != null
				&& findLastCommand(deviceId).id
						.equals(VoceCommandID.SECOND_EVENT.toString())) {

			String response = "{_Q_response_Q_: {_Q_shouldEndSession_Q_: false, _Q_outputSpeech_Q_: {_Q_type_Q_: _Q_SSML_Q_,"
					+ " _Q_ssml_Q_: _Q_<speak> LAST_EVENT"
					+ "    </speak>_Q_}} }";

			response = response.replace("_Q_", "\"");
			response = response.replace("LAST_EVENT", LAST_EVENT);

			// save command
			cnvSession.addCommand(new VoiceCommand(VoceCommandID.LAST_EVENT
					.toString(), VoceCommandID.LAST_EVENT.toString(),
					LAST_EVENT));
			return response;
		} else if (queryMessage.contains("mother")
				&& queryMessage.contains("brunch")
				&& findLastCommand(deviceId) != null) {

			String response = "{_Q_response_Q_: {_Q_shouldEndSession_Q_: false, _Q_outputSpeech_Q_: {_Q_type_Q_: _Q_SSML_Q_,"
					+ " _Q_ssml_Q_: _Q_<speak> MOTHER_BRUNCH"
					+ "    </speak>_Q_}} }";

			response = response.replace("_Q_", "\"");
			response = response.replace("MOTHER_BRUNCH", MOTHER_BRUNCH);

			// save command
			cnvSession.addCommand(new VoiceCommand(VoceCommandID.MOTHER_BRUNCH
					.toString(), VoceCommandID.MOTHER_BRUNCH.toString(),
					MOTHER_BRUNCH));
			return response;
		} else if (queryMessage.contains("calendar")
				&& findLastCommand(deviceId) != null
				&& findLastCommand(deviceId).id
						.equals(VoceCommandID.MOTHER_BRUNCH.toString())) {

			String response = "{_Q_response_Q_: {_Q_shouldEndSession_Q_: false, _Q_outputSpeech_Q_: {_Q_type_Q_: _Q_SSML_Q_,"
					+ " _Q_ssml_Q_: _Q_<speak> adding to calendar"
					+ "    </speak>_Q_}} }";

			response = response.replace("_Q_", "\"");

			// save command
			cnvSession.addCommand(new VoiceCommand(VoceCommandID.ADDING_BRUNCH
					.toString(), VoceCommandID.ADDING_BRUNCH.toString(),
					"ADDING_BRUNCH"));
			return response;

		} else {
			String response = "{_Q_response_Q_: {_Q_outputSpeech_Q_: {_Q_type_Q_: _Q_SSML_Q_,"
					+ " _Q_ssml_Q_: _Q_<speak>   </speak>_Q_}} }";

			response = response.replace("_Q_", "\"");
			return response;

		}

		/**
		 
		 User: What is in my calendar tomorrow?
		Alexa: You have doctor`s appointment at 1:30p.m and then movie with your daughter at 4:00p.m.
		Alexa: You don't have a ride scheduled for doctor`s appointment , you want to do it right now ?
		User: Yes please.
		Alexa: Whom would you like me to contact ? Your son, daughter or uber ?
		User: My daughter
		Alexa: Contacting your daughter.
		(Pause for 3 seconds) 
		Alexa: Your daughter confirmed, she will pick you up at 1.
		(Pause for 3 seconds)
		Alexa: Your daughter shared a picture of Margaret at her Ballet performance, you want to see it ?
		User: Yes please.

		<speak>
		There is a three second pause here <break time="3s"/> 
		then the speech continues.
		</speak>
		 */
	}

	public boolean isStartOfNewSession(String queryMessage) {
		return queryMessage.contains("event")
				&& (queryMessage.contains("neighborhood")
						|| queryMessage.contains("community") || queryMessage
							.contains("nearby"));
	}

	private ConversationSessionManagerCommunityEventFinderImpl() {
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

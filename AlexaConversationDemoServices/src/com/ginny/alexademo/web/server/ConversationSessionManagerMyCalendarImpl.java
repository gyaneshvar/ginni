package com.ginny.alexademo.web.server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.google.gson.Gson;

public class ConversationSessionManagerMyCalendarImpl implements
		ConversationSessionManager {
	static ConversationSessionManagerMyCalendarImpl manager = new ConversationSessionManagerMyCalendarImpl();

	static enum VoceCommandID {
		TOMORROW_CALENDAR, BOOK_MY_RIDE, CONTACING_DAUGHTER, CONTACING_SON, CONTACING_UBER, DAUGHTER_CONFIRMED, SON_CONFIRMED, UBER_CONFIRMED, SHARE_PICTURE_YES, SHARE_PICTURE_NO
	}

	// device id session map
	Map<String, ConversationSession> sessionMap = new HashMap<>();

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

	public void reset(String deviceId) {
		ConversationSession conversationSession = sessionMap.get(deviceId);
		if (conversationSession != null) {
			conversationSession.reset();
		}
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

		String APPOINTMENT_AT_130 = "You have doctor's appointment at 1:30p.m and "
				+ "then movie with your daughter at 4:00p.m";
		String YOU_DONT_HAVE_RIDE = "You don't have a ride scheduled for doctor's appointment, do you want to do it right now?";
		String WHOME_WOULD_YOU_LIKE_TO_CONTACT = "Whom would you like me to contact ? Your son, daughter or uber?";

		// User: What is in my calendar tomorrow?
		if (isStartOfNewSession(queryMessage)) {

			String response = "{_Q_response_Q_: { _Q_shouldEndSession_Q_: false, _Q_outputSpeech_Q_: {_Q_type_Q_: _Q_SSML_Q_,"
					+ " _Q_ssml_Q_: _Q_<speak> APPOINTMENT_AT_130"
					+ " <break time=\\_Q_3s\\_Q_/>"
					+ " YOU_DONT_HAVE_RIDE  </speak>_Q_}} }";

			response = response.replace("_Q_", "\"");
			response = response.replace("APPOINTMENT_AT_130",
					APPOINTMENT_AT_130);
			response = response.replace("YOU_DONT_HAVE_RIDE",
					YOU_DONT_HAVE_RIDE);
			// save command
			cnvSession.addCommand(new VoiceCommand(
					VoceCommandID.TOMORROW_CALENDAR.toString(),
					VoceCommandID.TOMORROW_CALENDAR.toString(),
					APPOINTMENT_AT_130));
			return response;
		} else if (queryMessage.contains("yes")
				&& findLastCommand(deviceId) != null
				&& findLastCommand(deviceId).id
						.equals(VoceCommandID.TOMORROW_CALENDAR.toString())) {

			String response = "{_Q_response_Q_: {_Q_shouldEndSession_Q_: false, _Q_outputSpeech_Q_: {_Q_type_Q_: _Q_SSML_Q_,"
					+ " _Q_ssml_Q_: _Q_<speak> WHOME_WOULD_YOU_LIKE_TO_CONTACT"
					+ "    </speak>_Q_}} }";

			response = response.replace("_Q_", "\"");
			response = response.replace("WHOME_WOULD_YOU_LIKE_TO_CONTACT",
					WHOME_WOULD_YOU_LIKE_TO_CONTACT);

			// save command
			cnvSession.addCommand(new VoiceCommand(VoceCommandID.BOOK_MY_RIDE
					.toString(), VoceCommandID.BOOK_MY_RIDE.toString(),
					WHOME_WOULD_YOU_LIKE_TO_CONTACT));
			return response;
		} else if ((queryMessage.contains("son")
				|| queryMessage.contains("daughter") || queryMessage
					.contains("uber"))
				&& findLastCommand(deviceId) != null
				&& findLastCommand(deviceId).id
						.equals(VoceCommandID.BOOK_MY_RIDE.toString())) {

			String CONTACTING_MSG = "";
			String cmd = null;
			if (queryMessage.contains("son")) {
				CONTACTING_MSG = "Contacting your son. <break time=\\_Q_3s\\_Q_/>  Your son confirmed, he  will pick you up at 1.";
				cmd = VoceCommandID.CONTACING_SON.toString();
			} else if (queryMessage.contains("daughter")) {

				CONTACTING_MSG = "Contacting your daughter."
						+ " <break time=\\_Q_3s\\_Q_/>  "
						+ "Your daughter confirmed, she will pick you up at 1. "
						+ "<break time=\\_Q_3s\\_Q_/> "
						+ " Your daughter shared a picture of Margaret at her Ballet performance, do you want to see it ?";
				cmd = VoceCommandID.CONTACING_DAUGHTER.toString();
			} else if (queryMessage.contains("uber")) {
				CONTACTING_MSG = "Contacting uber. <break time=\\_Q_3s\\_Q_/>  Confirmation from uber to pick you up at 1. Confirmation ID 5001";

				cmd = VoceCommandID.CONTACING_UBER.toString();
			}

			String response = "{ _Q_response_Q_: {_Q_shouldEndSession_Q_: false,_Q_outputSpeech_Q_: {_Q_type_Q_: _Q_SSML_Q_,"
					+ " _Q_ssml_Q_: _Q_<speak> CONTACTING_MSG"
					+ "   <break time=\\_Q_3s\\_Q_/> </speak>_Q_}} }";

			response = response.replace("CONTACTING_MSG", CONTACTING_MSG);
			response = response.replace("_Q_", "\"");

			// save command
			cnvSession.addCommand(new VoiceCommand(cmd, cmd, CONTACTING_MSG));
			return response;
		} else if (queryMessage.contains("yes")
				&& findLastCommand(deviceId) != null
				&& findLastCommand(deviceId).id
						.equals(VoceCommandID.CONTACING_DAUGHTER.toString())) {
			String OPEN_TV_APP = "OK, Showing picture on TV.";
			String response = "{ _Q_response_Q_: {_Q_shouldEndSession_Q_: false,_Q_outputSpeech_Q_: {_Q_type_Q_: _Q_SSML_Q_,"
					+ " _Q_ssml_Q_: _Q_<speak> "
					+ "   OPEN_TV_APP <break time=\\_Q_3s\\_Q_/> </speak>_Q_}} }";

			response = response.replace("_Q_", "\"");
			response = response.replace("OPEN_TV_APP", OPEN_TV_APP);

			// save command
			cnvSession.addCommand(new VoiceCommand(
					VoceCommandID.SHARE_PICTURE_YES.toString(),
					VoceCommandID.SHARE_PICTURE_YES.toString(), OPEN_TV_APP));
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
		return queryMessage.contains("calendar")
				&& queryMessage.contains("tomorrow");
	}

	private ConversationSessionManagerMyCalendarImpl() {
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

		public VoiceCommand getLastCommand() {
			if (cmds.size() > 0) {
				return cmds.getLast();
			} else {
				return new VoiceCommand(NONE, NONE, NONE);
			}

		}

		public void reset() {
			cmds.clear();

		}
	}
}

package com.ginny.alexademo.web.server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gson.Gson;

public class ConversationSessionManagerImageFinderImpl implements
		ConversationSessionManager {
	static ConversationSessionManagerImageFinderImpl manager = new ConversationSessionManagerImageFinderImpl();

	static enum VoceCommandID {
		FAMILY_PICTURES,NATURE_PICTURES
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

		String FAMILY_PICTURES = "Switching to family pictures";
		String NATURE_PICTURES = "Switching to nature pictures";
		// User: What is in my calendar tomorrow?
		if (isStartOfNewSession(queryMessage) && queryMessageRaw.contains("family")) {

			String response = "{_Q_response_Q_: { _Q_shouldEndSession_Q_: false, _Q_outputSpeech_Q_: {_Q_type_Q_: _Q_SSML_Q_,"
					+ " _Q_ssml_Q_: _Q_<speak> FAMILY_PICTURES"

					+ " </speak>_Q_}} }";

			response = response.replace("_Q_", "\"");
			response = response.replace("FAMILY_PICTURES", FAMILY_PICTURES);
			// save command
			cnvSession.addCommand(new VoiceCommand(
					VoceCommandID.FAMILY_PICTURES.toString(),
					VoceCommandID.FAMILY_PICTURES.toString(),
					FAMILY_PICTURES));
			return response;
		}if (isStartOfNewSession(queryMessage) && queryMessageRaw.contains("nature")) {

			String response = "{_Q_response_Q_: { _Q_shouldEndSession_Q_: false, _Q_outputSpeech_Q_: {_Q_type_Q_: _Q_SSML_Q_,"
					+ " _Q_ssml_Q_: _Q_<speak> NATURE_PICTURES"

					+ "   </speak>_Q_}} }";

			response = response.replace("_Q_", "\"");
			response = response.replace("NATURE_PICTURES", NATURE_PICTURES);
			// save command
			cnvSession.addCommand(new VoiceCommand(
					VoceCommandID.NATURE_PICTURES.toString(),
					VoceCommandID.NATURE_PICTURES.toString(),
					NATURE_PICTURES));
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
		return  (queryMessage.contains("family")
						|| queryMessage.contains("nature") );
	}

	private ConversationSessionManagerImageFinderImpl() {
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

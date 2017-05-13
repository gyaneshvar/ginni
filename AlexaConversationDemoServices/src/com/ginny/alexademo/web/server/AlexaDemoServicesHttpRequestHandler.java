package com.ginny.alexademo.web.server;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class AlexaDemoServicesHttpRequestHandler {

	private static final String _DEVICE_ID_DEMO = "1234";
	final static int MAX_HTTP_REQUEST_CONTENT_LENGTH = 1024 * 2;

	/**
	 * 	 
	     /GET /alexademo/v1/services
	     /POST /alexademo/v1/services
		 Request- {"email": "XYZ12",  "password":,"firstName":, "lastName", "phone":,}
		 
		  /GET /alexademo/v1/services/commands/current
		   * 
		   { 
		       "command": {"id": "XYZ12",  "name":,"set-reminder", "category"="Reminders/Calendar/Social"},
		       "response": {"id": "XYZ12", "msg":,"Reminder for 7:30am tomorrow."}
		   }
		 
		  
		
	* */

	public void handle(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getContentLength() > MAX_HTTP_REQUEST_CONTENT_LENGTH) {
				// do nothing... bad guy trying to choke server..
				// read source of this request and profile as a spam
				return;
			}

			String uri = request.getRequestURI();

			while (uri.startsWith("/")) {
				uri = uri.substring(1);
			}

			String[] uriParts = uri.split("/");

			String httpMethod = request.getMethod();

			String contentStr = readJSONContent(request);

			System.out.println("contentStr..." + contentStr);
			Logger.getAnonymousLogger().info("contentStr..." + contentStr);

			Map<String, Object> contentMap = new HashMap();
			contentMap = new Gson().fromJson(contentStr, contentMap.getClass());
			Logger.getAnonymousLogger().info("contentMap=" + contentMap);

			String resultJSON = "Success";
			// Content type header must be set to "application/json"

			// map service to uri and get results
			if (uriParts[0].equals("alexademo") && uriParts[1].equals("v1")) {

				if (uriParts[2].equals("services")) {
					if ("GET".equals(httpMethod) && uriParts.length == 5
							&& uriParts[3].equals("commands")
							&& uriParts[4].equals("current")) {
						// /GET /alexademo/v1/services/commands/current
						resultJSON = ConversationSessionManagerFactory
								.createSession(_DEVICE_ID_DEMO, "")
								.findLastCommandJson(_DEVICE_ID_DEMO);
						Logger.getAnonymousLogger().info(
								" sending resultJSON=" + resultJSON
										+ ", for request..=" + contentStr);
					} else if ("POST".equals(httpMethod)
							&& uriParts.length == 5
							&& uriParts[3].equals("commands")
							&& uriParts[4].equals("reset")) {
						// /POST /alexademo/v1/services/commands/reset
						ConversationSessionManagerFactory.resetAllSession(
								_DEVICE_ID_DEMO, "reset demo");
						resultJSON = ConversationSessionManagerFactory
								.createSession(_DEVICE_ID_DEMO, "")
								.findLastCommandJson(_DEVICE_ID_DEMO);
						Logger.getAnonymousLogger().info(
								" sending resultJSON=" + resultJSON
										+ ", for request..reset demo=");
					} else if ("POST".equals(httpMethod)
							&& uriParts.length == 3) {
						resultJSON = "{\"response\": { \"outputSpeech\": {	\"type\": \"PlainText\",\"text\": \"OK, I can do- Reminders, Ride booking, find places- like restaurants etc.\"}}}";
						String notNullContentStr = (contentStr != null ? contentStr
								: "123");
						// check if it is reset command, if it is then response
						// should be not null
						resultJSON = ConversationSessionManagerFactory
								.resetAllSession(_DEVICE_ID_DEMO,
										notNullContentStr);

						if (resultJSON == null) {
							resultJSON = ConversationSessionManagerFactory
									.createSession(_DEVICE_ID_DEMO, contentStr)
									.findResponse(_DEVICE_ID_DEMO,
											notNullContentStr);
						}

					}

				}
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(resultJSON);
			response.setHeader("Access-Control-Allow-Origin", "*");

			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String readJSONContent(HttpServletRequest request) {

		StringBuffer content = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				content.append(line);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		return content.toString();
	}
}

package com.ginny.reminder.web.server;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class ReminderManagementServiceHttpRequestHandler {

	final static int MAX_HTTP_REQUEST_CONTENT_LENGTH = 1024 * 2;

	/**
	 * 	 
	     /GET /rmg/v1/reminders/[userId]
		 Request
		 {"startTimeInMillisSinceEpoch": "123344",  "endTimeInMillisSinceEpoch":"123455",
		  "category":"all|medicine|dinner|ride|visit|exercise|birthday|anniversary"}
		  		  
		 Response
		 [
		 {"id":"abc_345_120", "label":"Medicine Reminder", "timeInMillisSinceEpoch": "123344", "category":"medicine"},
		 {"id":"abc_345_120", "label":"Dinner at Friend's House", "timeInMillisSinceEpoch": "5623489", "category":"dinner"}
		 ]
		
		 
	     /POST /rmg/v1/reminders/[userId]
	      Request- 
	      {"label":"Medicine Reminder", "firstOccurrenceTimeInMillisSinceEpoch": "123344",
	      "lastOccurrenceTimeInMillisSinceEpoch": "1356793" ,
	      "occurrence"="once|daily|weekdays|weekends|weekly|monthly|yearly"
	      }
		 
		  /DELETE /rmg/v1/reminders/[userId]/[reminderId]		 	  
		
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

			String resultJSON = "Success";
			// Content type header must be set to "application/json"

			// map service to uri and get results
			if (uriParts[0].equals("rmg") && uriParts[1].equals("v1")) {

				if (uriParts[2].equals("users")) {
					if ("GET".equals(httpMethod) && uriParts.length == 4) {
						// /GET /umg/v1/users/[userId]
						String userId = uriParts[3];
						Map<String, String> params = new HashMap<>();
						params = new Gson().fromJson(contentStr,
								params.getClass());
						Long startTs = Long.parseLong(params
								.get("startTimeInMillisSinceEpoch"));
						Long endTs = Long.parseLong(params
								.get("endTimeInMillisSinceEpoch"));
						resultJSON = com.ginny.reminder.service.api.ServiceFactory
								.createReminderManagementServicesCoreService()
								.findReminderInfoJSON(userId, startTs, endTs);

					} else if ("POST".equals(httpMethod)
							&& uriParts.length == 4) {
						String userId = uriParts[3];
						com.ginny.reminder.service.api.ServiceFactory
								.createReminderManagementServicesCoreService()
								.addReminder(userId, contentStr);

					} else if ("DELETE".equals(httpMethod)
							&& uriParts.length == 4) {
						String remId = uriParts[3];
						com.ginny.reminder.service.api.ServiceFactory
								.createReminderManagementServicesCoreService()
								.deleteReminder(remId);
					}

				}
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(resultJSON);

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

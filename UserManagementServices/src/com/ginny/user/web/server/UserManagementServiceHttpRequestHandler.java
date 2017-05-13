package com.ginny.user.web.server;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserManagementServiceHttpRequestHandler {

	final static int MAX_HTTP_REQUEST_CONTENT_LENGTH = 1024 * 2;

	/**
	 * 	 
	     /GET /umg/v1/users/[userId]
	     /POST /umg/v1/users
		 Request- {"email": "XYZ12",  "password":,"firstName":, "lastName", "phone":,}
		 
		 /PUT /umg/v1/users/[userId]
		 /DELETE /umg/v1/users/[userId]	  
		
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
			if (uriParts[0].equals("umg") && uriParts[1].equals("v1")) {
				
				if (uriParts[2].equals("users")) {
					if ("GET".equals(httpMethod) && uriParts.length == 4) {
						// /GET /umg/v1/users/[userId]
						String userId = uriParts[3];
						resultJSON = com.ginny.user.service.api.ServiceFactory
								.createUserManagementServicesCoreService()
								.findUserInfoJSON(userId);

					} else if ("POST".equals(httpMethod)
							&& uriParts.length == 3) {
						// /POST /umg/v1/users
						// Request- {"email": "XYZ12", "password":,"firstName":,
						// "lastName", "phone":,}
						com.ginny.user.service.api.ServiceFactory
								.createUserManagementServicesCoreService()
								.createUser(contentStr);
					} else if ("PUT".equals(httpMethod) && uriParts.length == 4) {
						// /put /umg/v1/users/[usrId]
						// Request- {"email": "XYZ12", "password":,"firstName":,
						// "lastName", "phone":,}
						String userId = uriParts[3];
						com.ginny.user.service.api.ServiceFactory
								.createUserManagementServicesCoreService()
								.updateUser(userId, contentStr);
					} else if ("DELETE".equals(httpMethod)
							&& uriParts.length == 4) {
						// /DELETE /umg/v1/users/[userId]
						String userId = uriParts[3];
						com.ginny.user.service.api.ServiceFactory
								.createUserManagementServicesCoreService()
								.deleteUser(userId);
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

package com.ginny.auth.web.server;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ginny.auth.service.api.ServiceFactory;
import com.google.gson.Gson;

public class AuthenticationServiceHttpRequestHandler {

	final static int MAX_HTTP_REQUEST_CONTENT_LENGTH = 1024 * 2;

	/**
	 * 	 
	     /GET /auth/v1/accessKey
		 Request- {"userId": "XYZ12",  "password":"753@$%^^",
		  "externalAuthCrendentialAttributes": {"attrib1":, "attrib2":}}
		 Response-{"status":"success", "accessKey":"677_6788_975_123_abc"}
		 
				 
		 /GET /auth/v1/accessKey/[accessKey]/validate
		 Response-{"status":"success" or "failure"}
		 
		
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

			System.out
					.println(" invoking AuthenticationServicesHttpRequestHandler uri="
							+ uri + " m=" + httpMethod);

			System.out
					.println(" invoking ServiceMangagerHttpRequestHandler uriParts="
							+ Arrays.asList(uriParts) + " m=" + httpMethod);

			String resultJSON = "Success";
			// Content type header must be set to "application/json"

			// map service to uri and get results
			if (uriParts[0].equals("auth") && uriParts[1].equals("v1")) {
				if (uriParts[2].equals("accessKey")) {
					if ("GET".equals(httpMethod) && uriParts.length == 3) {
						// GET /auth/v1/accessKey
						String userAuthInfoJSON = contentStr;
						ServiceFactory
								.createAuthenticatoinServicesCoreService()
								.createAccessKey(userAuthInfoJSON);

						Map<String, String> attrib = new Gson().fromJson(
								contentStr,
								new HashMap<String, String>().getClass());

						resultJSON = ServiceFactory
								.createAuthenticatoinServicesCoreService()
								.findAccessKeyJSON(attrib.get("userId"));

					} else if ("GET".equals(httpMethod)
							&& "validate".equals(uriParts[4])
							&& uriParts.length == 5) {
						// GET /auth/v1/accessKey
						String accessKey = uriParts[4];
						boolean status = ServiceFactory
								.createAuthenticatoinServicesCoreService()
								.isValidAccessKey(accessKey);
						Map<String, String> attrib = new HashMap<String, String>();
						attrib.put("status", status ? "sucess" : "failure");

						resultJSON = new Gson().toJson(attrib);
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

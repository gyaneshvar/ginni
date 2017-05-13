/*
 * Copyright (c) 2004-2005 ResolveTech, Inc.
 *
 * All Rights Reserved.  Unpublished rights reserved under the copyright laws
 * of the United States.  The software contained on this media is proprietary
 * to and embodies the confidential technology of ResolveTech, Inc.  The
 * possession or receipt of this information does not convey any right to
 * disclose its contents, reproduce it, use it, or license its use, for
 * manufacture or sale.  The foregoing restriction applies to the information or
 * anything described therein.  Any use, disclosure, or reproduction without
 * ResolveTech's prior written permission is strictly prohibited.
 *
 */
package com.ginny.auth.web.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationServicesMainServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(AuthenticationServicesMainServelet.class
			.getName());
	AuthenticationServiceHttpRequestHandler requestHandler = new AuthenticationServiceHttpRequestHandler();

	public void doDelete(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			handle(request, response);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void doPut(HttpServletRequest request, HttpServletResponse response) {

		try {
			handle(request, response);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {

		try {
			handle(request, response);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

	private void handle(HttpServletRequest request, HttpServletResponse response) {

		try {
			String taskUri = request.getRequestURI();
			if (taskUri != null && taskUri.startsWith("/auth")) {
				requestHandler.handle(request, response);
			}

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}
	}

}

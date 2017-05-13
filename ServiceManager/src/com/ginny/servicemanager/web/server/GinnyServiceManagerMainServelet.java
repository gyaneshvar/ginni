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
package com.ginny.servicemanager.web.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GinnyServiceManagerMainServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(GinnyServiceManagerMainServelet.class
			.getName());
	ServiceManagerHttpRequestHandler smHandler = new ServiceManagerHttpRequestHandler();

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
		/***
		 * develop rest api ..use googles REST api model ..use following link or
		 * other google rest api doc link..
		 * https://developers.google.com/drive/v2/reference/ intent for google
		 * style is to make developer understand it better..easy communication
		 * .. not much documentation is needed if we follow google style..
		 * 
		 * delete DELETE /files/fileId/comments/commentId Deletes a comment. get
		 * GET /files/fileId/comments/commentId Gets a comment by ID. insert
		 * POST /files/fileId/comments Creates a new comment on the given file.
		 * list GET /files/fileId/comments Lists a file's comments. patch PATCH
		 * /files/fileId/comments/commentId Updates an existing comment. This
		 * method supports patch semantics. update PUT
		 * /files/fileId/comments/commentId Updates an existing comment.
		 */
		try {
			String taskUri = request.getRequestURI();
			if (taskUri != null && taskUri.startsWith("/smg")) {
				smHandler.handle(request, response);
			}

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}
	}

}

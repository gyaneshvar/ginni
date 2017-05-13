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
package com.ginny.tvdemo.web.server;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class TVDemoServicesApplicationContextListener implements
		ServletContextAttributeListener, ServletContextListener {

	// ----------------------------------------------------- Instance Variables

	/**
	 * The servlet context with which we are associated.
	 */
	private ServletContext context = null;

	// --------------------------------------------------------- Public Methods

	/**
	 * Record the fact that a servlet context attribute was added.
	 * 
	 * @param event
	 *            The servlet context attribute event
	 */
	// @Override
	public void attributeAdded(ServletContextAttributeEvent event) {

		log("attributeAdded('" + event.getName() + "', '" + event.getValue()
				+ "')");

	}

	/**
	 * Record the fact that a servlet context attribute was removed.
	 * 
	 * @param event
	 *            The servlet context attribute event
	 */
	// @Override
	public void attributeRemoved(ServletContextAttributeEvent event) {

		log("attributeRemoved('" + event.getName() + "', '" + event.getValue()
				+ "')");

	}

	/**
	 * Record the fact that a servlet context attribute was replaced.
	 * 
	 * @param event
	 *            The servlet context attribute event
	 */
	// @Override
	public void attributeReplaced(ServletContextAttributeEvent event) {

		log("attributeReplaced('" + event.getName() + "', '" + event.getValue()
				+ "')");

	}

	/**
	 * Record the fact that this web application has been destroyed.
	 * 
	 * @param event
	 *            The servlet context event
	 */
	// @Override
	public void contextDestroyed(ServletContextEvent event) {

		log("contextDestroyed()");
		this.context = null;

	}

	/**
	 * Record the fact that this web application has been initialized.
	 * 
	 * @param event
	 *            The servlet context event
	 */
	// @Override
	public void contextInitialized(ServletContextEvent event) {

		this.context = event.getServletContext();
		log("contextInitialized()");

	}

	// -------------------------------------------------------- Private Methods

	/**
	 * Log a message to the servlet context application log.
	 * 
	 * @param message
	 *            Message to be logged
	 */
	private void log(String message) {

		if (context != null)
			context.log("ContextListener: " + message);
		else
			System.out.println("ContextListener: " + message);

	}
}

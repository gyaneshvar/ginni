package com.ginny.servicemanager.web.server;

import java.io.BufferedReader;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ginny.servicemanager.service.api.ServiceFactory;

public class ServiceManagerHttpRequestHandler {

	final static int MAX_HTTP_REQUEST_CONTENT_LENGTH = 1024 * 2;

	/**
		/GET /smg/v1/services
		/POST /smg/v1/services
		/PUT /smg/v1/services/[serviceId]
		/DELETE /smg/v1/services/[serviceId]
		 
	    /GET /smg/v1/nodes
		/POST /smg/v1/nodes
		/PUT /smg/v1/nodes/[nodeId]
		/DELETE /smg/v1/nodes/[nodeId]
		 
		 ////
		/GET /smg/v1/services/deployed
		/POST /smg/v1/services/[serviceId]/deploy/[nodeId]
		/DELETE /smg/v1/services/[serviceId]/undeploy/[nodeId]
		 * 
		 * start/stop service
		/PUT /smg/v1/services/[deployedServiceId]/start
		/PUT /smg/v1/services/[deployedServiceId]/stop
		/GET /smg/v1/services/deployed/runningStatus
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
					.println(" invoking ServiceManaagerHttpRequestHandler uri="
							+ uri + " m=" + httpMethod);

			System.out
					.println(" invoking ServiceMangagerHttpRequestHandler uriParts="
							+ Arrays.asList(uriParts) + " m=" + httpMethod);

			String resultJSON = "Success";
			// Content type header must be set to "application/json"

			// map service to uri and get results
			if (uriParts[0].equals("smg") && uriParts[1].equals("v1")) {
				if (uriParts[2].equals("services")) {
					if ("GET".equals(httpMethod) && uriParts.length == 3) {
						// GET /smg/v1/services
						resultJSON = ServiceFactory
								.createServiceManagerCoreService()
								.findCloudServiceDefinitionsJSON();

					} else if ("POST".equals(httpMethod)
							&& uriParts.length == 3) {
						// POST /smg/v1/services
						// create new cloud service
						ServiceFactory.createServiceManagerCoreService()
								.createCloudServiceDefinition(contentStr);
					} else if ("PUT".equals(httpMethod) && uriParts.length == 4) {
						// PUT /smg/v1/services/[serviceid]
						// update all attribute of service(serviceId)
						String serviceId = uriParts[3];
						ServiceFactory.createServiceManagerCoreService()
								.updateCloudServiceDefinition(serviceId,
										contentStr);
					} else if ("DELETE".equals(httpMethod)
							&& uriParts.length == 4) {
						// DELETE /smg/v1/services/[serviceid]
						// Delete service(serviceId)
						String serviceId = uriParts[3];
						ServiceFactory.createServiceManagerCoreService()
								.deleteCloudServiceDefinition(serviceId);

					} else if ("GET".equals(httpMethod) && uriParts.length == 4
							&& "deployed".equals(uriParts[3])) {
						// /GET /smg/v1/services/deployed
						resultJSON = ServiceFactory
								.createServiceManagerCoreService()
								.findDeployedCloudServicesJSON();
					} else if ("POST".equals(httpMethod)
							&& uriParts.length == 6
							&& "deploy".equals(uriParts[4])) {
						// /POST /smg/v1/services/[serviceId]/deploy/[nodeId]
						String serviceId = uriParts[3];
						String nodeId = uriParts[5];
						String configurationJSON = contentStr;
						resultJSON = ServiceFactory
								.createServiceManagerCoreService()
								.deployCloudService(serviceId, nodeId,
										configurationJSON);
					} else if ("POST".equals(httpMethod)
							&& uriParts.length == 6
							&& "undeploy".equals(uriParts[4])) {
						// /POST /smg/v1/services/[serviceId]/undeploy/[nodeId]
						String serviceId = uriParts[3];
						String nodeId = uriParts[5];
						resultJSON = ServiceFactory
								.createServiceManagerCoreService()
								.unDeployCloudService(serviceId, nodeId);

						// /PUT /smg/v1/services/[deployedServiceId]/stop
						// /GET /smg/v1/services/deployed/status

					} else if ("PUT".equals(httpMethod) && uriParts.length == 5
							&& "start".equals(uriParts[3])) {
						// /PUT /smg/v1/services/[deployedServiceId]/start
						String deployedServiceId = uriParts[3];
						resultJSON = ServiceFactory
								.createServiceManagerCoreService()
								.startCloudService(deployedServiceId);
					} else if ("PUT".equals(httpMethod) && uriParts.length == 5
							&& "stop".equals(uriParts[3])) {
						// /PUT /smg/v1/services/[deployedServiceId]/stop
						String deployedServiceId = uriParts[3];
						resultJSON = ServiceFactory
								.createServiceManagerCoreService()
								.startCloudService(deployedServiceId);
					} else if ("GET".equals(httpMethod) && uriParts.length == 5
							&& "deployed".equals(uriParts[3])
							&& "runningStatus".equals(uriParts[4])) {
						// /GET /smg/v1/services/deployed/runningStatus
						String deployedServiceId = uriParts[3];
						resultJSON = ServiceFactory
								.createServiceManagerCoreService()
								.runningStatus(deployedServiceId);

					}
				} else if (uriParts[2].equals("nodes")) {
					/*
					/GET /smg/v1/nodes
					/POST /smg/v1/nodes
					/PUT /smg/v1/nodes/[nodeid]
					/DELETE /smg/v1/nodes/[nodeid]
					*/

					if ("GET".equals(httpMethod) && uriParts.length == 3) {
						// GET /smg/v1/nodes
						resultJSON = ServiceFactory
								.createServiceManagerCoreService()
								.findCloudNodesJSON();

					} else if ("POST".equals(httpMethod)
							&& uriParts.length == 3) {
						// POST /smg/v1/nodes
						// create new cloud node
						ServiceFactory.createServiceManagerCoreService()
								.createCloudNode(contentStr);
					} else if ("PUT".equals(httpMethod) && uriParts.length == 4) {
						// PUT /smg/v1/nodes/[nodeid]
						// update all attribute of node(nodeId)
						String nodeId = uriParts[3];
						ServiceFactory.createServiceManagerCoreService()
								.updateCloudNode(nodeId, contentStr);
					} else if ("DELETE".equals(httpMethod)
							&& uriParts.length == 4) {
						// DELETE /smg/v1/nodes/[nodeid]
						// Delete node(nodeId)
						String nodeId = uriParts[3];
						ServiceFactory.createServiceManagerCoreService()
								.deleteCloudNode(nodeId);

					}
				}

			}

			response.setContentType("application/json");
			response.setHeader("Access-Control-Allow-Origin", "*");
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

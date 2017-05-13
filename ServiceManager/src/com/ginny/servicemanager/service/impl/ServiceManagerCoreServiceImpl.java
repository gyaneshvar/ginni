package com.ginny.servicemanager.service.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ginny.servicemanager.db.mongo.CloudNodeDBVO;
import com.ginny.servicemanager.db.mongo.CloudServiceDefinitionDBVO;
import com.ginny.servicemanager.db.mongo.CloudServiceDeploymentDBVO;
import com.ginny.servicemanager.db.mongo.SmgCloudNodeDAO;
import com.ginny.servicemanager.db.mongo.SmgCloudServiceDefinitionDAO;
import com.ginny.servicemanager.db.mongo.SmgCloudServiceDeploymentDAO;
import com.ginny.servicemanager.service.api.ServiceManagerCoreService;
import com.google.gson.Gson;

public class ServiceManagerCoreServiceImpl implements ServiceManagerCoreService {
	Logger logger = Logger.getLogger(ServiceManagerCoreServiceImpl.class
			.getName());

	public String findCloudNodesJSON() {

		try {
			List<CloudNodeDBVO> dbvoList = new SmgCloudNodeDAO()
					.findCloudNode();
			return new Gson().toJson(dbvoList);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

		return null;
	}

	public String createCloudNode(String nodeJson) {

		try {
			new SmgCloudNodeDAO().createCloudNode((new Gson().fromJson(
					nodeJson, CloudNodeDBVO.class)));

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}
		return null;

	}

	public String updateCloudNode(String nodeId, String nodeJSON) {

		try {
			CloudNodeDBVO nodeDBVO = new Gson().fromJson(nodeJSON,
					CloudNodeDBVO.class);
			nodeDBVO.setId(nodeId);
			new SmgCloudNodeDAO().updateCloudNode(nodeDBVO);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

		return null;
	}

	public String deleteCloudNode(String nodeId) {

		try {

			new SmgCloudNodeDAO().deleteCloudNode(nodeId);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

		return null;
	}

	public String findCloudServiceDefinitionsJSON() {

		try {
			List<CloudServiceDefinitionDBVO> dbvoList = new SmgCloudServiceDefinitionDAO()
					.findServiceDefinitions();
			return new Gson().toJson(dbvoList);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

		return null;
	}

	public String createCloudServiceDefinition(String serviceDefJSON) {

		try {
			new SmgCloudServiceDefinitionDAO()
					.createServiceDefinition(new Gson().fromJson(
							serviceDefJSON, CloudServiceDefinitionDBVO.class));

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

		return null;
	}

	public String updateCloudServiceDefinition(String serviceId,
			String serviceDefJSON) {

		try {
			CloudServiceDefinitionDBVO serviceDef = new Gson().fromJson(
					serviceDefJSON, CloudServiceDefinitionDBVO.class);
			serviceDef.setId(serviceId);
			new SmgCloudServiceDefinitionDAO()
					.updateServiceDefinition(serviceDef);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

		return null;
	}

	public String deleteCloudServiceDefinition(String serviceId) {

		try {
			new SmgCloudServiceDefinitionDAO()
					.deleteServiceDefinition(serviceId);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

		return null;
	}

	public String findDeployedCloudServicesJSON() {

		try {
			List<CloudServiceDeploymentDBVO> dbvoList = new SmgCloudServiceDeploymentDAO()
					.findCloudServiceDeploymentDBVO();
			return new Gson().toJson(dbvoList);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

		return null;
	}

	public String deployCloudService(String serviceId, String nodeId,
			String configurationJSON) {

		try {
			new SmgCloudServiceDeploymentDAO().deployService(serviceId, nodeId,
					configurationJSON);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

		return null;
	}

	public String unDeployCloudService(String serviceId, String nodeId) {

		try {
			new SmgCloudServiceDeploymentDAO().undeployService(serviceId,
					nodeId);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

		return null;
	}

	public String startCloudService(String deployedServiceId) {

		try {
			return null;

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

		return null;
	}

	public String stopCloudService(String deployedServiceId) {

		try {
			return null;

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

		return null;
	}

	public String runningStatus(String deployedServiceId) {
		try {
			return null;

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

		return null;
	}
}

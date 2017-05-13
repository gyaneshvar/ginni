package com.ginny.servicemanager.db.mongo;

public class CloudServiceDeploymentDBVO {

	String id;
	String nodeId;
	String serviceId;
	String configurationJSON;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getConfigurationJSON() {
		return configurationJSON;
	}

	public void setConfigurationJSON(String configurationJSON) {
		this.configurationJSON = configurationJSON;
	}

}

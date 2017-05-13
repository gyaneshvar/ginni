package com.ginny.servicemanager.service.api;

public interface ServiceManagerCoreService {

	// services
	public String findCloudServiceDefinitionsJSON();

	public String createCloudServiceDefinition(String serviceDefJSON);

	public String updateCloudServiceDefinition(String serviceId,
			String serviceDefJSON);

	public String deleteCloudServiceDefinition(String serviceId);

	// nodes
	public String findCloudNodesJSON();

	public String createCloudNode(String nodeJson);

	public String updateCloudNode(String nodeId, String nodeJSON);

	public String deleteCloudNode(String nodeId);

	//

	public String findDeployedCloudServicesJSON();

	public String deployCloudService(String serviceId, String nodeId,
			String configurationJSON);

	public String unDeployCloudService(String serviceId, String nodeId);

	public String startCloudService(String deployedServiceId);

	public String stopCloudService(String deployedServiceId);

	public String runningStatus(String deployedServiceId);

}
package com.ginny.servicemanager.db.mongo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class SmgCloudServiceDeploymentDAO {
	Logger logger = Logger.getLogger(MongoSampleCollectionDAO.class.getName());
	private final static String COLLECTION_SMG_CLOUD_SERVICE_DEPLOYMENT = "smg_cloud_service_deployment";

	/**
	 * { _id:"1234-3747-3748", name: , desc: * }
	 */

	public List<CloudServiceDeploymentDBVO> findCloudServiceDeploymentDBVO() {
		List<CloudServiceDeploymentDBVO> results = new ArrayList();

		try {

			FindIterable<Document> res = getCollection(
					COLLECTION_SMG_CLOUD_SERVICE_DEPLOYMENT).find();

			for (Iterator iterator = res.iterator(); iterator.hasNext();) {
				Document doc = (Document) iterator.next();
				CloudServiceDeploymentDBVO vo = new CloudServiceDeploymentDBVO();
				vo.setId(doc.getString("_id"));
				vo.setServiceId(doc.getString("serviceId"));
				vo.setNodeId(doc.getString("nodeId"));
				vo.setConfigurationJSON(doc.getString("configurationJSON"));
				results.add(vo);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

		return results;
	}

	public CloudServiceDeploymentDBVO findCloudServiceDeploymentDBVO(
			String serviceId, String nodeId) {
		List<CloudServiceDeploymentDBVO> results = new ArrayList();

		try {
			Document query = new Document("nodeId", nodeId);
			query.append("serviceId", serviceId);

			FindIterable<Document> res = getCollection(
					COLLECTION_SMG_CLOUD_SERVICE_DEPLOYMENT).find(query);

			for (Iterator iterator = res.iterator(); iterator.hasNext();) {
				Document doc = (Document) iterator.next();
				CloudServiceDeploymentDBVO vo = new CloudServiceDeploymentDBVO();
				vo.setId(doc.getString("_id"));
				vo.setServiceId(doc.getString("serviceId"));
				vo.setNodeId(doc.getString("nodeId"));

				vo.setConfigurationJSON(doc.getString("configurationJSON"));
				return vo;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

		return null;
	}

	public void deployService(String serviceId, String nodeId,
			String configurationJSON) {

		try {
			if (findCloudServiceDeploymentDBVO(serviceId, nodeId) == null) {
				Document doc = new Document();
				doc.append("serviceId", serviceId);
				doc.append("nodeId", nodeId);
				doc.append("configurationJSON", configurationJSON);

				getCollection(COLLECTION_SMG_CLOUD_SERVICE_DEPLOYMENT)
						.insertOne(doc);

			}

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

	}

	public void undeployService(String serviceId, String nodeId) {

		try {
			Document query = new Document();
			query.append("serviceId", serviceId);
			query.append("nodeId", nodeId);

			getCollection(COLLECTION_SMG_CLOUD_SERVICE_DEPLOYMENT)
					.findOneAndDelete(query);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

	}

	private MongoCollection<Document> getCollection(String collName) {
		return MongoDbServiceFacade.getMongoDatabase().getCollection(collName);
	}
}

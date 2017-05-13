package com.ginny.servicemanager.db.mongo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class SmgCloudServiceDefinitionDAO {
	Logger logger = Logger.getLogger(MongoSampleCollectionDAO.class.getName());
	private final static String COLLECTION_SMG_CLOUD_SERVICE_DEFINITION = "smg_cloud_service_definition";

	/**
	 * { _id:"1234-3747-3748", name: , desc:, baseServiceURL * }
	 */

	public List<CloudServiceDefinitionDBVO> findServiceDefinitions() {
		List<CloudServiceDefinitionDBVO> results = new ArrayList();

		try {
			FindIterable<Document> res = getCollection(
					COLLECTION_SMG_CLOUD_SERVICE_DEFINITION).find();

			for (Iterator iterator = res.iterator(); iterator.hasNext();) {
				Document doc = (Document) iterator.next();
				CloudServiceDefinitionDBVO vo = new CloudServiceDefinitionDBVO();
				vo.setId(doc.getObjectId("_id").toString());
				vo.setName(doc.getString("name"));
				vo.setName(doc.getString("desc"));
				vo.setBaseServiceURL(doc.getString("baseServiceURL"));
				results.add(vo);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}
		return results;
	}

	public void createServiceDefinition(CloudServiceDefinitionDBVO dbvo) {

		try {
			Document doc = new Document();
			doc.append("name", dbvo.getName());
			doc.append("baseServiceURL", dbvo.getBaseServiceURL());
			doc.append("desc", dbvo.getDesc());
			getCollection(COLLECTION_SMG_CLOUD_SERVICE_DEFINITION).insertOne(
					doc);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

	}

	public void updateServiceDefinition(CloudServiceDefinitionDBVO dbvo) {

		try {
			Document doc = new Document();
			doc.append("name", dbvo.getName());
			doc.append("baseServiceURL", dbvo.getBaseServiceURL());
			doc.append("desc", dbvo.getDesc());

			Document query = new Document("_id", dbvo.getId());

			getCollection(COLLECTION_SMG_CLOUD_SERVICE_DEFINITION)
					.findOneAndUpdate(query, doc);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

	}

	public void deleteServiceDefinition(String serviceId) {

		try {
			Document query = new Document("_id", serviceId);

			getCollection(COLLECTION_SMG_CLOUD_SERVICE_DEFINITION)
					.findOneAndDelete(query);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

	}

	private MongoCollection<Document> getCollection(String collName) {
		return MongoDbServiceFacade.getMongoDatabase().getCollection(collName);
	}
}

package com.ginny.servicemanager.db.mongo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class SmgCloudNodeDAO {
	Logger logger = Logger.getLogger(MongoSampleCollectionDAO.class.getName());
	private final static String COLLECTION_SMG_CLOUD_NODE = "smg_cloud_node";

	/**
	 * { _id:"1234-3747-3748", name: , desc: * }
	 */

	public List<CloudNodeDBVO> findCloudNode() {
		List<CloudNodeDBVO> results = new ArrayList();

		try {
			FindIterable<Document> res = getCollection(
					COLLECTION_SMG_CLOUD_NODE).find();

			for (Iterator iterator = res.iterator(); iterator.hasNext();) {
				Document doc = (Document) iterator.next();
				CloudNodeDBVO vo = new CloudNodeDBVO();
				vo.setId(doc.getString("_id"));
				vo.setName(doc.getString("name"));
				vo.setName(doc.getString("desc"));
				vo.setIpAddress(doc.getString("ipAddress"));
				results.add(vo);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

		return results;
	}

	public void createCloudNode(CloudNodeDBVO dbvo) {

		try {
			Document doc = new Document();
			doc.append("name", dbvo.getName());
			doc.append("desc", dbvo.getDesc());
			doc.append("ipAddress", dbvo.getIpAddress());
			getCollection(COLLECTION_SMG_CLOUD_NODE).insertOne(doc);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

	}

	public void updateCloudNode(CloudNodeDBVO dbvo) {
		try {
			Document doc = new Document();
			doc.append("name", dbvo.getName());
			doc.append("ipAddress", dbvo.getIpAddress());
			doc.append("desc", dbvo.getDesc());

			Document query = new Document("_id", dbvo.getId());

			getCollection(COLLECTION_SMG_CLOUD_NODE).findOneAndUpdate(query,
					doc);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

	}

	public void deleteCloudNode(String nodeId) {

		try {
			Document query = new Document("_id", nodeId);

			getCollection(COLLECTION_SMG_CLOUD_NODE).findOneAndDelete(query);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

	}

	private MongoCollection<Document> getCollection(String collName) {
		return MongoDbServiceFacade.getMongoDatabase().getCollection(collName);
	}

}

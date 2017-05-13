package com.ginny.servicemanager.db.mongo;

import static com.mongodb.client.model.Filters.ne;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class MongoSampleCollectionDAO {
	Logger logger = Logger.getLogger(MongoSampleCollectionDAO.class.getName());
	private final static String ALERT_SUBSCRIPTION_COLLECTION_NAME = "alert_subscription";
	private final static String ALERT_CONFIG_PROP_COLLECTION_NAME = "alert_config_prop";
	private final static String ALERT_COLLECTION_NAME = "alert";

	private MongoCollection<Document> getCollection(String collName) {
		return MongoDbServiceFacade.getMongoDatabase().getCollection(collName);
	}

	public void insertAlert(Long guid, String eventRef, String alertBriefMsg,
			Long createDate) {
		/**
		 * String INSERT_ALERT_QUERY = "INSERT INTO alert VALUES (?,?,?,?)";
		 */
		try {

			Document insertDoc = new Document("_id", guid)
					.append("event_ref", eventRef)
					.append("alert_brief_msg", alertBriefMsg)
					.append("create_date", new Date(createDate));

			getCollection(ALERT_COLLECTION_NAME).insertOne(insertDoc);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

	}

	public void deleteAlertSubcriptionByGuid(Long subscriptionGuidId) {
		try {
			getCollection(ALERT_SUBSCRIPTION_COLLECTION_NAME).updateOne(
					new Document("_id", subscriptionGuidId),
					new Document("$set", new Document("deleted", 1l)));
		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);

		}
	}

	public void insertConfigProps(Long guid, String name, String value) {
		try {
			getCollection(ALERT_CONFIG_PROP_COLLECTION_NAME).insertOne(
					new Document("_id", guid).append("name", name).append(
							"value", value));
		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}
	}

	public void deleteAlertConfiguration() {
		try {
			// "DELETE FROM alert_config_prop where name<>'SUBSCRIPTION_HANDLE'");
			getCollection(ALERT_CONFIG_PROP_COLLECTION_NAME).deleteMany(
					ne("name", "SUBSCRIPTION_HANDLE"));
		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}
	}

	public List findAlertConfiguration(String propName) {
		List<DBVO2> results = new ArrayList();

		try {
			FindIterable<Document> res = null;
			if (propName != null) {
				res = getCollection(ALERT_CONFIG_PROP_COLLECTION_NAME).find(
						new Document("name", propName));
			} else {
				res = getCollection(ALERT_CONFIG_PROP_COLLECTION_NAME).find();
			}

			for (Iterator iterator = res.iterator(); iterator.hasNext();) {
				Document doc = (Document) iterator.next();
				DBVO2 vo = new DBVO2();
				vo.setName(doc.getString("name"));
				vo.setValue(doc.getString("value"));
				results.add(vo);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

		return results;
	}

	public List findAlertConfiguration() {
		try {
			return findAlertConfiguration(null);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
			return new ArrayList();
		}
	}

}
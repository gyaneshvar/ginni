package com.ginny.reminder.db.mongo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class ReminderManagementDAO {
	Logger logger = Logger.getLogger(ReminderManagementDAO.class.getName());

	private final static String COLLECTION_RMG_REMINDER_INFO = "rmg_reminder_info";

	public void addReminder(ReminderDBVO dbvo) {

		try {
			Document doc = new Document();
			doc.append("userId", dbvo.getUserId());
			doc.append("label", dbvo.getLabel());
			doc.append("firstOccurrenceTimeInMillisSinceEpoch",
					dbvo.getFirstOccurrenceTimeInMillisSinceEpoch());
			doc.append("lastOccurrenceTimeInMillisSinceEpoch",
					dbvo.getLastOccurrenceTimeInMillisSinceEpoch());
			doc.append("nextOccurrenceTimeInMillisSinceEpoch",
					dbvo.getNextOccurrenceTimeInMillisSinceEpoch());
			doc.append("occurance", dbvo.getOccurrence());

			getCollection(COLLECTION_RMG_REMINDER_INFO).insertOne(doc);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

	}

	public void deleteReminder(String remId) {

		try {
			getCollection(COLLECTION_RMG_REMINDER_INFO).findOneAndDelete(
					new Document("_id", remId));

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

	}

	public List<ReminderDBVO> findReminders(String userId, Long startTs,
			Long endTs) {
		try {
			Document query = new Document("userId", userId);
			query.append("nextOccurrenceTimeInMillisSinceEpoch", new Document(
					"$gte", startTs));
			query.append("nextOccurrenceTimeInMillisSinceEpoch", new Document(
					"$lte", endTs));

			FindIterable<Document> res = getCollection(
					COLLECTION_RMG_REMINDER_INFO).find(query);

			List<ReminderDBVO> dbvoList = new ArrayList<>();

			for (Iterator iterator = res.iterator(); iterator.hasNext();) {
				Document doc = (Document) iterator.next();
				dbvoList.add(xform(doc));
			}

			return dbvoList;

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}
		return null;
	}

	private ReminderDBVO xform(Document doc) {
		ReminderDBVO dbvo = new ReminderDBVO();
		dbvo.setFirstOccurrenceTimeInMillisSinceEpoch(doc
				.getLong("firstOccurrenceTimeInMillisSinceEpoch"));
		dbvo.setId(doc.getString("_id"));
		dbvo.setUserId(doc.getString("userId"));
		dbvo.setLabel(doc.getString("label"));
		dbvo.setLastOccurrenceTimeInMillisSinceEpoch(doc
				.getLong("lastOccurrenceTimeInMillisSinceEpoch"));
		dbvo.setNextOccurrenceTimeInMillisSinceEpoch(doc
				.getLong("nextOccurrenceTimeInMillisSinceEpoch"));
		dbvo.setOccurrence(Occurrence.valueOf(doc.getString("occurrence")));
		dbvo.setCreateTs(doc.getDate("createTs").getTime());
		return dbvo;
	}

	private MongoCollection<Document> getCollection(String collName) {
		return MongoDbServiceFacade.getMongoDatabase().getCollection(collName);
	}
}

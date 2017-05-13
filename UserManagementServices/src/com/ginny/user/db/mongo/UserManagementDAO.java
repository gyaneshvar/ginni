package com.ginny.user.db.mongo;

import java.sql.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class UserManagementDAO {
	Logger logger = Logger.getLogger(UserManagementDAO.class.getName());

	private final static String COLLECTION_UMG_USER_AUTH_INFO = "umg_user_auth_info";
	private final static String COLLECTION_UMG_USER_INFO = "umg_user_info";

	public void createUserAuthInfo(String userId, String password) {

		try {
			Document doc = new Document();
			doc.append("userId", userId);
			doc.append("password", password);
			getCollection(COLLECTION_UMG_USER_AUTH_INFO).insertOne(doc);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

	}

	public void updateUserAuthInfo(String userId, String password) {

		try {
			getCollection(COLLECTION_UMG_USER_AUTH_INFO).findOneAndUpdate(
					new Document("userId", userId),
					new Document("password", password));

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

	}

	public void deleteUserAuthInfo(String userId) {

		try {
			getCollection(COLLECTION_UMG_USER_AUTH_INFO).findOneAndDelete(
					new Document("userId", userId));

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

	}

	public void createUser(UserDBVO dbvo) {

		try {
			Document doc = new Document();
			doc.append("email", dbvo.getEmail());
			doc.append("firstName", dbvo.getFirstName());
			doc.append("lastName", dbvo.getLastName());
			doc.append("phone", dbvo.getPhone());
			doc.append("createTs", new Date(System.currentTimeMillis()));
			getCollection(COLLECTION_UMG_USER_INFO).insertOne(doc);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

	}

	public void updateUser(String userId, UserDBVO dbvo) {

		try {
			Document doc = new Document();
			doc.append("email", dbvo.getEmail());
			doc.append("firstName", dbvo.getFirstName());
			doc.append("lastName", dbvo.getLastName());
			doc.append("phone", dbvo.getPhone());

			getCollection(COLLECTION_UMG_USER_INFO).findOneAndUpdate(
					new Document("_id", dbvo.getId()), doc);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

	}

	public void deleteUser(String userId) {

		try {

			getCollection(COLLECTION_UMG_USER_INFO).findOneAndDelete(
					new Document("_id", userId));

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

	}

	public UserDBVO finfUserInfo(String userId) {
		try {
			Document query = new Document("_id", userId);

			FindIterable<Document> res = getCollection(
					COLLECTION_UMG_USER_AUTH_INFO).find(query);

			for (Iterator iterator = res.iterator(); iterator.hasNext();) {
				Document doc = (Document) iterator.next();
				return xform(doc);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}
		return null;
	}

	public UserDBVO finfUserInfoByEmail(String email) {
		try {
			Document query = new Document("email", email);

			FindIterable<Document> res = getCollection(
					COLLECTION_UMG_USER_AUTH_INFO).find(query);

			for (Iterator iterator = res.iterator(); iterator.hasNext();) {
				Document doc = (Document) iterator.next();
				return xform(doc);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}
		return null;
	}

	private UserDBVO xform(Document doc) {
		UserDBVO dbvo = new UserDBVO();
		dbvo.setEmail(doc.getString("email"));
		dbvo.setFirstName(doc.getString("firstName"));
		dbvo.setLastName(doc.getString("lastName"));
		dbvo.setPhone(doc.getString("phone"));
		dbvo.setCreateTs(doc.getDate("createTs").getTime());
		return dbvo;
	}

	private MongoCollection<Document> getCollection(String collName) {
		return MongoDbServiceFacade.getMongoDatabase().getCollection(collName);
	}
}

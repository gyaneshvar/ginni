package com.ginny.auth.db.mongo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class AuthSessionAccessKeyDAO {
	Logger logger = Logger.getLogger(AuthSessionAccessKeyDAO.class.getName());
	private final static String COLLECTION_SESSION_AUTH_ACCESS_KEY = "auth_session_access_key";

	private final static String COLLECTION_UMG_USER_AUTH_INFO = "umg_user_auth_info";

	/**
	 * { _id:"1234-3747-3748", userId: , createTs: }
	 */

	public void createAuthSessionAccessKey(String userId) {

		try {
			Document doc = new Document();
			doc.append("userId", userId);
			doc.append("createTs", new Date(System.currentTimeMillis()));
			getCollection(COLLECTION_SESSION_AUTH_ACCESS_KEY).insertOne(doc);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

	}

	public boolean isValidUserAuthInfo(UserAuthInfoDBVO dbvo) {
		try {
			Document query = new Document("userId", dbvo.getUserId());
			query.append("password", hashPassword(dbvo.getPassword()));

			FindIterable<Document> res = getCollection(
					COLLECTION_UMG_USER_AUTH_INFO).find(query);

			for (Iterator iterator = res.iterator(); iterator.hasNext();) {
				Document doc = (Document) iterator.next();
				return true;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}
		return false;
	}

	private String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			md.update(password.getBytes(), 0, password.length());

			byte[] mdbytes = md.digest();
			return new String(mdbytes);
		} catch (NoSuchAlgorithmException e) {
			throw new com.ginny.auth.service.api.GinnyRuntimeException(
					e,
					com.ginny.auth.service.api.GinnyRuntimeException.ERROR_CODE_AUTH_MANAGEMENT_SERVICE_ERROR,
					com.ginny.auth.service.api.GinnyRuntimeException.ERROR_CODE_AUTH_MANAGEMENT_SERVICE_ERROR_DESC);
		}
	}

	public String findAuthSessionAccessKeyForUser(String userId) {

		try {
			FindIterable<Document> res = getCollection(
					COLLECTION_SESSION_AUTH_ACCESS_KEY).find(
					new Document("userId", userId));

			for (Iterator iterator = res.iterator(); iterator.hasNext();) {
				Document doc = (Document) iterator.next();
				return doc.getString("_id");
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}

		return null;
	}

	private MongoCollection<Document> getCollection(String collName) {
		return MongoDbServiceFacade.getMongoDatabase().getCollection(collName);
	}
}

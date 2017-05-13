package com.ginny.servicemanager.db.mongo;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

public class MongoDbServiceFacade {
	final static Logger logger = Logger.getLogger(MongoDbServiceFacade.class
			.getName());
	private static MongoClient mongoClient;

	public static MongoDatabase getMongoDatabase() {
		MongoDatabase db = null;
		try {
			if (mongoClient == null) {
				createMongoClient();
			}

			db = mongoClient.getDatabase("ginny");
			return db;
		} catch (Exception e) {
			logger.log(Level.SEVERE,
					"stdm/Method: getMongoDatabase(), Exception", e);
			if (mongoClient != null) {
				mongoClient.close();
				mongoClient = null;
			}
		}

		return null;

	}

	private static synchronized void createMongoClient() {
		// connect to single set
		// mongoClient = new MongoClient("localhost", 27017);

		// connecting to replicaset
		mongoClient = new MongoClient(Arrays.asList(new ServerAddress(
				"localhost", 27017)));
		// do authenticate..
		/**
		 * MongoCredential credential = MongoCredential.createCredential(userName, database, password);
		MongoClient mongoClient = new MongoClient(new ServerAddress(), Arrays.asList(credential));
		new MongoClient(Arrays.asList(new ServerAddress(
				"localhost", 27017), new ServerAddress("localhost", 27018)));
		 */
		/**
		 * start mogod client
		 * mongod --port 27017 --logpath /var/log/mongodb.log --dbpath /srv/mongodb/
		 */

	}
}

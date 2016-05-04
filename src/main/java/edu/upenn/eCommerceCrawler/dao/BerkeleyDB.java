package edu.upenn.eCommerceCrawler.dao;

import java.io.File;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

public class BerkeleyDB {
	private static BerkeleyDB instance;
	private static File file = new File("C:/Users/Ting/Desktop");
	private Environment env;
	private EntityStore store;

	private BerkeleyDB() {
	}

	public static BerkeleyDB getInstance() {
		if (instance == null) {
			synchronized (BerkeleyDB.class) {
				if (instance == null) {
					instance = new BerkeleyDB();
				}
			}
		}

		return instance;
	}
	
	public <T> PrimaryIndex<Long, T> getPrimaryIndex(Class<T> type) throws DatabaseException {
		return store.getPrimaryIndex(Long.class, type);
	}

	public void setup() throws DatabaseException {
		if(!file.exists()) file.mkdir();
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);
		envConfig.setTransactional(true);
		env = new Environment(file, envConfig);

		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setAllowCreate(true);
		store = new EntityStore(env, "EntityStore", storeConfig);
	}

	public void close() throws DatabaseException {
		store.close();
		env.close();
	}

}

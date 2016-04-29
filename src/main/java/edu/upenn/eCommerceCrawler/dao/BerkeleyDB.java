package edu.upenn.eCommerceCrawler.dao;

import java.io.File;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

/**
 * This is a BerkeleyDB class that saves data in <key, value> pairs 
 *
 */
public class BerkeleyDB {
	private static BerkeleyDB instance;
	/**
	 * directory location in which database environment resides 
	 */
	//private static File file = new File("/Users/bondwong/Desktop/JEDB");
	private static File file = new File("C:\\Users\\Ting\\Desktop\\JEDB");
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
		
		/**
		 * open database environment - JE database
		 */
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);
		envConfig.setTransactional(true); //supports transactions
		env = new Environment(file, envConfig); 

		/**
		 * create entity store (basic entity: ECommerceEntity) 
		 */
		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setAllowCreate(true);
		store = new EntityStore(env, "EntityStore", storeConfig);
	}

	/**
	 * Close database environment
	 */
	public void close()  {
		if (env != null) {
			try {
				store.close();
				env.cleanLog();
				env.close();
			} catch (DatabaseException dbe) {
				System.err.println("Error closing environment" +
						dbe.toString());
			}
		}
	}
	
	public Environment getEnv() {
		return env;
	}
}

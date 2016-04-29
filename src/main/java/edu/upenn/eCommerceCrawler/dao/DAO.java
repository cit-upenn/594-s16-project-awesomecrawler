package edu.upenn.eCommerceCrawler.dao;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.PrimaryIndex;

/**
 * This is a specialized data accessor class  
 * @param <T>
 */

public class DAO<T> {
	private PrimaryIndex<Long, T> primaryIndex;

	public DAO(Class<T> type) throws DatabaseException {
		BerkeleyDB bdb = BerkeleyDB.getInstance(); 
		this.primaryIndex = bdb.getPrimaryIndex(type); 
	}
	
	/**
	 * This method saves a new entity into the database
	 */
	public T save(T entity, Class<T> type) throws DatabaseException {
		return primaryIndex.put(entity);
	}

	/**
	 * This method counts the number of duplicates 
	 */
	public long count() throws DatabaseException {
		return primaryIndex.count();
	}
}

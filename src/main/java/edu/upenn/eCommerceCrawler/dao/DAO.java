package edu.upenn.eCommerceCrawler.dao;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.PrimaryIndex;

public class DAO<T> {
	private PrimaryIndex<Long, T> primaryIndex;

	public DAO(Class<T> type) throws DatabaseException {
		BerkeleyDB bdb = BerkeleyDB.getInstance();
		this.primaryIndex = bdb.getPrimaryIndex(type);
	}

	public T save(T entity, Class<T> type) throws DatabaseException {
		return primaryIndex.put(entity);
	}

	public long count() throws DatabaseException {
		return primaryIndex.count();
	}
}

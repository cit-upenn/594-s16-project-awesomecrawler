package edu.upenn.eCommerceCrawler.dao;

import java.util.ArrayList;
import java.util.List;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.PrimaryIndex;

public class DAO<T> {
	private PrimaryIndex<Long, T> primaryIndex;

	public DAO(Class<T> type) throws DatabaseException {
		BerkeleyDB bdb = BerkeleyDB.getInstance();
		this.primaryIndex = bdb.getPrimaryIndex(type);
	}

	public T save(T entity) throws DatabaseException {
		return primaryIndex.put(entity);
	}

	public long count() throws DatabaseException {
		return primaryIndex.count();
	}

	public List<T> fetch(long start, long size) throws DatabaseException {
		EntityCursor<T> cursor = primaryIndex.entities(start, true, start + size, false);
		List<T> result = new ArrayList<T>();
		try {
			for (T entity : cursor)
				result.add(entity);
		} finally {
			cursor.close();
		}
		return result;
	}
}

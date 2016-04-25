package edu.upenn.eCommerceCrawler.dbtest;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

import com.sleepycat.je.DatabaseException;

import edu.upenn.eCommerceCrawler.dao.BerkeleyDB;
import edu.upenn.eCommerceCrawler.domain.ECommerceEntity;

public class BerkeleyDBTest {
	@AfterClass
	public static void close() throws DatabaseException {
		BerkeleyDB.getInstance().close();
	}

	@Test
	public void testDBSetup() {
		try {
			BerkeleyDB.getInstance().setup();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		assertTrue(true);
	}

	@Test
	public void testDBClose() {
		try {
			BerkeleyDB.getInstance().setup();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			BerkeleyDB.getInstance().close();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		assertTrue(true);
	}

	@Test
	public void testGetPrimaryKey() {
		try {
			BerkeleyDB.getInstance().setup();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertNotNull(BerkeleyDB.getInstance().getPrimaryIndex(ECommerceEntity.class));
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
		assertTrue(true);

	}

}

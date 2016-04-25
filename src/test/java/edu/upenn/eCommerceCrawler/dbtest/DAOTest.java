package edu.upenn.eCommerceCrawler.dbtest;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sleepycat.je.DatabaseException;

import edu.upenn.eCommerceCrawler.dao.BerkeleyDB;
import edu.upenn.eCommerceCrawler.dao.DAO;
import edu.upenn.eCommerceCrawler.domain.ECommerceEntity;

public class DAOTest {
	@BeforeClass
	public static void setup() throws DatabaseException {
		BerkeleyDB.getInstance().setup();
	}

	@AfterClass
	public static void close() throws DatabaseException {
		BerkeleyDB.getInstance().close();
	}

	@Test
	public void testCreateDAO() {
		try {
			new DAO<ECommerceEntity>(ECommerceEntity.class);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		assertTrue(true);
	}

	@Test
	public void testSave() {
		DAO<ECommerceEntity> dao = null;
		try {
			dao = new DAO<ECommerceEntity>(ECommerceEntity.class);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ECommerceEntity entity = new ECommerceEntity();
			entity = dao.save(new ECommerceEntity(), ECommerceEntity.class);
			assertNull(entity);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		assertTrue(true);
	}

}

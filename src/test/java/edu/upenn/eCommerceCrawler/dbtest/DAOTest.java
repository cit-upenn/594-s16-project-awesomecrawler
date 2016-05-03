package edu.upenn.eCommerceCrawler.dbtest;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sleepycat.je.DatabaseException;

import edu.upenn.eCommerceCrawler.dao.BerkeleyDB;
import edu.upenn.eCommerceCrawler.dao.DAO;
import edu.upenn.eCommerceCrawler.domain.ECommerceEntity;
import edu.upenn.eCommerceCrawler.web.helper.CollectionResult;

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
			entity = dao.save(new ECommerceEntity());
			assertNull(entity);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		assertTrue(true);
	}

	@Test
	public void testRead() {
		DAO<ECommerceEntity> dao = null;
		try {
			dao = new DAO<ECommerceEntity>(ECommerceEntity.class);
			for (int i = 0; i < 10; i++) {
				dao.save(new ECommerceEntity());
			}
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		try {
			List<ECommerceEntity> elements = dao.fetch(1, 10);
			long count = dao.count();
			CollectionResult<ECommerceEntity> result = new CollectionResult<ECommerceEntity>(elements, count, 1, 10);
			assertEquals(10, result.getElements().size());
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}

}

package edu.upenn.eCommerceCrawler;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.sleepycat.je.DatabaseException;

import edu.upenn.eCommerceCrawler.crawler.ECommerceCrawler;
import edu.upenn.eCommerceCrawler.crawler.Task;
import edu.upenn.eCommerceCrawler.dao.BerkeleyDB;

/**
 * Application Lifecycle Listener implementation class StartupListener
 *
 */
@WebListener
public class StartupListener implements ServletContextListener {
	private static final String POOL = "POOL";

	/**
	 * Default constructor.
	 */
	public StartupListener() {
		// TODO Auto-generated constructor stub
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		// TODO Auto-generated method stub
		try {
			ExecutorService pool = (ExecutorService) servletContextEvent.getServletContext().getAttribute(POOL);
			pool.shutdownNow();
			BerkeleyDB.getInstance().close();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		// TODO Auto-generated method stub
		try {
			BerkeleyDB.getInstance().setup();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// if fail, never fire up crawlers
			return;
		}

		BlockingQueue<Task> tasks = new LinkedBlockingQueue<Task>();
		ExecutorService pool = Executors.newCachedThreadPool();
		pool.execute(new ECommerceCrawler(tasks,"Ebay"));
		pool.execute(new ECommerceCrawler(tasks, "Amazon"));
		servletContextEvent.getServletContext().setAttribute(POOL, pool);

	}

}

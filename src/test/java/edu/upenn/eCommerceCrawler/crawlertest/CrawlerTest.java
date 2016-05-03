package edu.upenn.eCommerceCrawler.crawlertest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.sleepycat.je.DatabaseException;

import edu.upenn.eCommerceCrawler.crawler.ECommerceCrawler;
import edu.upenn.eCommerceCrawler.crawler.Task;
import edu.upenn.eCommerceCrawler.dao.BerkeleyDB;

public class CrawlerTest {

	public static void main(String[] args) {
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
		pool.execute(new ECommerceCrawler(tasks, "Amazon"));
		pool.execute(new ECommerceCrawler(tasks, "Ebay"));
	}
}

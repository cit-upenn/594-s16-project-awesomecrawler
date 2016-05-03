package edu.upenn.eCommerceCrawler.crawler;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import com.sleepycat.je.DatabaseException;

import edu.upenn.eCommerceCrawler.dao.DAO;
import edu.upenn.eCommerceCrawler.domain.ECommerceEntity;

/**
 * This class implements a crawler that store web information into local
 * database, following producer-consumer pattern - the producer puts tasks into
 * the blocking queue for the consumer to process
 * 
 * @author TingWang
 *
 */
public class ECommerceCrawler implements Runnable {
	private Config config;
	private static boolean isInitiated = false;
	private BlockingQueue<Task> tasks;
	private String siteId; 

	public ECommerceCrawler(BlockingQueue<Task> tasks, String site) {
		this.tasks = tasks;
		ConfigFactory cf = new ConfigFactory();
		this.config = cf.createConfigFile(site);
		this.siteId = config.getSiteID();
	}

	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (!isInitiated) {
				synchronized (ECommerceCrawler.class) {
					if (!isInitiated) {
						isInitiated = true;
						try {
							initiate();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}

			Task task;
			try {
				task = tasks.take();
				switch (task.getType()) {
				case MAIN:
					handleMain(task);
					break;
				case CATEGORY:
					handleCategory(task);
					break;
				case LIST:
					handleList(task);
					break;
				case DATA:
					handleData(task);
					break;
				default:
					break;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// handle silently
				e.printStackTrace();
			}

		}
	}

	/**
	 * Put site URL into the queue
	 */
	public void initiate() throws InterruptedException {
		String url = config.getURL();
		tasks.put(new Task(url, TaskType.MAIN));
		//System.out.println("Main url: " + url);
	}

	/**
	 * Handle main task: Generate category tasks and put them into the queue
	 */
	public void handleMain(Task task) throws InterruptedException {
		String selector = config.getCategorySelector();
		ArrayList<Task> taskList = 
				SaveURL.extractLinks(task.url, selector, siteId, TaskType.CATEGORY);
		for (Task t : taskList) {
			System.out.println("Categories:" + t.url);
			tasks.put(t);
		}
	}

	/**
	 * Handle category task: Generate list tasks and put them into the queue
	 */
	private void handleCategory(Task task) throws InterruptedException {
		/**
		 * get link to first page
		 */
		Task curt = new Task(task.url, TaskType.LIST);
		tasks.put(curt);
	}

	/**
	 * Handle list task: Generate data tasks and a list task of next page and
	 * put them into the queue
	 */
	private void handleList(Task task) throws InterruptedException {
		/**
		 * get link to data of each item
		 */
		System.out.println("Parsing page: " + task.url);
		String selector = config.getItemSelector();
		ArrayList<Task> taskList = 
				SaveURL.extractLinks(task.url, selector, siteId, TaskType.DATA);
		
		for (Task t : taskList) {
			System.out.println("insert item URL: " + t.url);
			tasks.put(t);
		}

		/**
		 * get link to next page
		 */
		Task next = SaveURL.extractNextPage(task.url, config.getNextSelector(), siteId, TaskType.LIST);
		tasks.put(next);
		System.out.println("next URL: " + next.url);
		System.out.println();
	}

	/**
	 * Handle data task: 1. get link to sales, price, date, and image from html
	 * page 2. create corresponding ECommerceEntity 3. create a DAO and call its
	 * save method to store it into the Berkeley DB (check out the unit test to
	 * see how to use that)
	 */
	private void handleData(Task task) {
		/** 
		 * Process data 
		 */
		DataProcessor processor = new DataProcessor(task, siteId, config); 
		
		/**
		 * generate entity 
		 */
		ECommerceEntity entity = processor.getEntity(); 
		
		/**
		 * save data into Berkeley DB
		 */
		try {
			DAO<ECommerceEntity> dao = new DAO<ECommerceEntity>(ECommerceEntity.class);
			dao.save(entity);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
}

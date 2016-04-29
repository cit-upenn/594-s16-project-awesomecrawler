package edu.upenn.eCommerceCrawler.crawler;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import javax.swing.text.html.parser.Element;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.sleepycat.je.DatabaseException;

import edu.upenn.eCommerceCrawler.dao.DAO;
import edu.upenn.eCommerceCrawler.domain.ECommerceEntity;

/**
 * This class implements a crawler that store web information 
 * into local database, following producer-consumer pattern - 
 * the producer puts tasks into the blocking queue for the consumer 
 * to process 
 * 
 * @author TingWang
 *
 */
public class ECommerceCrawler implements Runnable {
	private ConfigFile config; 
	private static boolean isInitiated = false;
	private BlockingQueue<Task> tasks;

	public ECommerceCrawler(BlockingQueue<Task> tasks) {
		this.tasks = tasks;
		this.config = new ConfigFile(); 
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
	}
	
	/**
	 * Handle main task: 
	 * Generate category tasks and put them into the queue
	 */
	public void handleMain(Task task) throws InterruptedException {
		String selector = config.getCategorySelector(); 
		ArrayList<Task> taskList = SaveURL.extractLinks(task.url, selector, TaskType.CATEGORY);
		for (Task t: taskList) {
//			System.out.println(t.url);
			tasks.put(t);
		}
	}
	
	/**
	 * Handle category task: 
	 * Generate list tasks and put them into the queue
	 */
	private void handleCategory(Task task) throws InterruptedException {
		/**
		 * get link to first page 
		 */ 
		Task curt = new Task(task.url, TaskType.LIST);
		tasks.put(curt);
		//System.out.println("curt URL: " + curt.url);
		
		/**
		 * get link to next page 
		 */
//		Task next = SaveURL.extractNextPage(task.url, config.getNextSelector(), TaskType.LIST);
//		tasks.put(next);
		//System.out.println("next URL: " + next.url);
		//System.out.println(); 
	}
	
	/**
	 * Handle list task: 
	 * Generate data tasks and a list task of next page and put them into the queue
	 */
	private void handleList(Task task) throws InterruptedException {
		/**
		 * get link to data of each item 
		 */
		System.out.println("Parsing page: " + task.url);
		String selector = config.getItemSelector();
		ArrayList<Task> taskList = SaveURL.extractLinks(task.url, selector, TaskType.DATA);
		//System.out.println("List: " + task.url); 
		for (Task t: taskList) {
			System.out.println("insert item URL: " + t.url);
			tasks.put(t);
		}
		
		/**
		 * get link to next page 
		 */
//		Task next = SaveURL.extractNextPage(task.url, config.getNextSelector(), TaskType.LIST);
//		tasks.put(next);
		//System.out.println("next URL: " + next.url);
		//System.out.println();
	}

	/**
	 * Handle data task: 
	 * 1. get link to sales, price, date, and image from html page 
	 * 2. create corresponding ECommerceEntity 
	 * 3. create a DAO and call its save method to store it into the Berkeley DB
	 * (check out the unit test to see how to use that)
	 */
	private void handleData(Task task) {
		Document doc = null; 
		try {
			doc = Jsoup.connect(task.url).get();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		System.out.println("Processing item: " + task.url);
		
		// sales 
		String sales_str = doc.select(config.getItemSold()).text(); 
		int sales = -1;  //Not Available
		if (!sales_str.replaceAll("[^\\d]", "").equals("")) {
			sales = Integer.parseInt(sales_str.replaceAll("[^\\d]", ""));
		}
		
		// price
		Elements p = doc.select(config.getItemPrice());
		String price_str = "";
		if (p.size()>1) {
			price_str = p.get(p.size()-1).text(); 
		} else if (p.size() == 1){
			price_str = p.get(0).text(); 
		}
		double price = -1; 
		if (!price_str.isEmpty()){
			price = (Double.parseDouble(price_str.replaceAll("[^0-9.]", "")));
		}
		
		System.out.println("Sales = " + sales + " Price = " + price);
		
		// image 
		Elements imageUrl = doc.select(config.getItemImage());
		String image = imageUrl.attr("src");
		// date
		String date = doc.select(config.getItemDate()).text();
		// type 
		String type = doc.select(config.getItemType()).text();  
		
		System.out.println("Date= " + date + " ImageURL= " + image + " Type= " + type);
		
		/**
		 * create corresponding ECommerceEntity
		 */
		ECommerceEntity entity = new ECommerceEntity(sales, price, date, image, type);
		
		/**
		 * save data into Berkeley DB
		 */
		DAO<ECommerceEntity> dao = null;
		try {
			dao = new DAO<ECommerceEntity>(ECommerceEntity.class);
			entity = dao.save(new ECommerceEntity(), ECommerceEntity.class);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	} 
}

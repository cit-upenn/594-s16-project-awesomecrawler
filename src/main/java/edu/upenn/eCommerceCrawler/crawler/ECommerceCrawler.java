package edu.upenn.eCommerceCrawler.crawler;

import java.util.concurrent.BlockingQueue;

public class ECommerceCrawler implements Runnable {
	private static boolean isInitiated = false;
	private BlockingQueue<Task> tasks;

	public ECommerceCrawler(BlockingQueue<Task> tasks) {
		this.tasks = tasks;
	}

	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (!isInitiated) {
				synchronized (ECommerceCrawler.class) {
					if (!isInitiated) {
						isInitiated = true;
						initiate();
					}
				}
			}

			Task task;
			try {
				task = tasks.take();
				switch (task.getType()) {
				case CATEGORY:
					handleCategory();
					break;
				case LIST:
					handleList();
					break;
				case DATA:
					handleData();
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

	private void initiate() {
		// put category tasks into queue
	}

	private void handleCategory() {
		/*
		 * handle category task generate data task and list tasks and put them
		 * into queue
		 */
	}

	private void handleList() {
		/*
		 * handle list task generate data tasks and put them into queue
		 */
	}

	private void handleData() {
		/*
		 * handle data task 1. get image link, sold number, date from html 2.
		 * create am ECommerceEntity 3. create a DAO and call its save method to
		 * store it into the berkeley db (check out the unit test to see how to
		 * use that)
		 */
	}

}

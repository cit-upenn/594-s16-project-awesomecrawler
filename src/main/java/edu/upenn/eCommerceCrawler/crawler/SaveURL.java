package edu.upenn.eCommerceCrawler.crawler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Class with static methods that can save URLs and extract links
 */

public class SaveURL {
	
	public SaveURL() throws FileNotFoundException{
	}
	
	/**
     * Extract links from a HTML page
     */
    public static ArrayList<Task> extractLinks(String pageURL, String selector, TaskType type) {
    	ArrayList<Task> tasks = new ArrayList<Task>();
		Document page = null;
		try {
			page = Jsoup.connect(pageURL).get();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		Elements links = page.select(selector);
		for (Element link: links) {
			tasks.add(new Task(link.attr("href"), type));
		}
		
		return tasks; 
    }
    
    /**
	 * Extract "next" page link 
	 */
	public static Task extractNextPage(String pageURL, String selector, TaskType type) {
		Document page = null;
		try {
			page = Jsoup.connect(pageURL).get();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		String nextLink = page.select(selector).attr("href"); 
		return new Task(nextLink, type);
	}
}


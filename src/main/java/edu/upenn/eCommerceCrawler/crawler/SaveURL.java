package edu.upenn.eCommerceCrawler.crawler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
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
    public static ArrayList<Task> extractLinks(String pageURL, String selector, String siteID, TaskType type) {
    	ArrayList<Task> tasks = new ArrayList<Task>();
    	Document doc = null;
		while (true) {
			try {
				Connection.Response res = 
						Jsoup.connect(pageURL).
						timeout(10000).
						execute(); 
				int statusCode = res.statusCode(); 
				if (statusCode == 200) { 			
					doc = Jsoup.connect(pageURL).get(); 
					break; 
				} else if (statusCode == 503) {
					System.out.println("Reconnecting...");
				}
			} catch (IOException e) {
				System.out.println("Reconnecting ...");
			}
		}
		
		Elements links = doc.select(selector);
		for (Element link: links) {
			String href = link.attr("href");
			if (!href.contains(siteID)) {
				href = siteID + href; 
			}
			tasks.add(new Task(href, type));
		}
		
		return tasks; 
    }
    
    /**
	 * Extract "next" page link 
	 */
	public static Task extractNextPage(String pageURL, String selector, String siteID, TaskType type) {
		Document doc = null;
		while (true) {
			try {
				Connection.Response res = 
						Jsoup.connect(pageURL).
						timeout(10000).
						execute(); 
				int statusCode = res.statusCode(); 
				if (statusCode == 200) { 			
					doc = Jsoup.connect(pageURL).get(); 
					break; 
				} else if (statusCode == 503) {
					System.out.println("Reconnecting...");
				}
			} catch (IOException e) {
				System.out.println("Reconnecting ...");
			}
		}
		
		String nextLink = doc.select(selector).attr("href"); 
		if (!nextLink.contains(siteID)) {
			nextLink = siteID + nextLink; 
		}
		return new Task(nextLink, type);
	}
}


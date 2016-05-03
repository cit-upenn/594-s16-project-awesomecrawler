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
    	
    	Document doc = getHtmlPage(pageURL); 
    	
    	ArrayList<Task> tasks = new ArrayList<Task>();
		Elements links = doc.select(selector);
		for (Element link: links) {
			String href = link.attr("href");
			if (href.isEmpty()) continue; 
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
		
		Document doc = getHtmlPage(pageURL); 
		
		String nextLink = doc.select(selector).attr("href"); 
		if (nextLink.isEmpty()) return null; 
		if (!nextLink.contains(siteID)) {
			nextLink = siteID + nextLink; 
		}
		return new Task(nextLink, type);
	}
	
	/**
	 * Connect to HTML page 
	 */
	public static Document getHtmlPage(String url) {
		Document doc = null;
		int count = 0; 
		while (true) {
			try {
				Connection.Response res = 
						Jsoup.connect(url).
						timeout(10000).
						execute(); 
				int statusCode = res.statusCode(); 
				if (statusCode == 200) { 			
					doc = Jsoup.connect(url).get(); 
					break; 
				} else if (statusCode == 503) {
					System.out.println("Reconnecting...");
					count++; 
				} 
				if (count > 50) 
					break; 
			} catch (IOException e) {
				System.out.println("Reconnecting ...");
				count++; 
			}
		}
		return doc; 
	}
}


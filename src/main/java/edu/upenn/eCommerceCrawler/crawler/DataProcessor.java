package edu.upenn.eCommerceCrawler.crawler;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import edu.upenn.eCommerceCrawler.domain.ECommerceEntity;

/** 
 * This class downloads data and generate a database entity 
 *
 */
public class DataProcessor {
	Task task; 
	String site; 
	Config config; 
	String sales, price, date, image, type; 
	Document doc = null;
	
	public DataProcessor(Task task, String siteID, Config config) {
		System.out.println("Processing item: " + task.url);
		this.task = task; 
		this.site = siteID;  
		this.config = config;
		
		this.doc = SaveURL.getHtmlPage(task.url);
		
		if (site.contains("amazon")) {
			processAmazonData(); 
		} else if (site.contains("ebay")) {
			processEbayData(); 
		}
	}
	
	public void processEbayData() {
		// sales
		String sales_str = doc.select(config.getItemSold()).text();
		int salesNum = -1; // Not Available
		if (!sales_str.replaceAll("[^\\d]", "").equals("")) {
			salesNum = Integer.parseInt(sales_str.replaceAll("[^\\d]", ""));
		}
		sales = String.valueOf(salesNum);
		
		// price
		Elements p = doc.select(config.getItemPrice());
		String price_str = "";
		if (p.size() > 1) {
			price_str = p.get(p.size() - 1).text();
		} else if (p.size() == 1) {
			price_str = p.get(0).text();
		}
		double priceNum = -1;
		if (!price_str.isEmpty()) {
			priceNum = (Double.parseDouble(price_str.replaceAll("[^0-9.]", "")));
		}
		price = String.valueOf(priceNum);

		// image
		Elements imageUrl = doc.select(config.getItemImage());
		image = imageUrl.attr("src");
		// date
		date = doc.select(config.getItemDate()).text();
		// type
		type = doc.select(config.getItemType()).text();

	}
	
	public void processAmazonData() {
		// sales (in terms of sales rank) 
		sales = doc.select(config.getItemSold()).text();
		sales = getSalesRank(sales);
		
		// price 
		price = doc.select(config.getItemPrice()).text(); 
		
		// date  (first available at Amazon) 
		date = doc.select(config.getItemDate()).text(); 	
		
		// image 
		image = doc.select(config.getItemImage()).attr("src"); 
		
		// type 
		type = "TBD";
	}
	
	public String getSalesRank(String sales)  {
		/* input: Amazon Best Sellers Rank: #1,087,145 in Clothing */
		int i=0; 
		while (sales.charAt(i) != '#') i++; 
		int j = i + 1; 
		while ((sales.charAt(j) >= '0' && sales.charAt(j) <= '9') || (sales.charAt(j) == ',')) {
			j++; 
		}
		sales = sales.substring(i+1, j);
		return sales; 
	}
	
	/**
	 * create corresponding ECommerceEntity
	 */
	public ECommerceEntity getEntity() {
		
		System.out.println("sales = " + sales + " price= " + price + " date= " + date + 
				" type= " + type); 
		System.out.println("imageURL: " + image);
		
		ECommerceEntity entity = new ECommerceEntity(sales, price, date, image, type);
		return entity; 
	}
}

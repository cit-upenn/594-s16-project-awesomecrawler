package edu.upenn.eCommerceCrawler.crawler;

/**
 * This class reads in configuration file 
 * that contains the following info:
 * 1) site URL
 * 2) group XPath / CSS selector
 * 3) imageURL XPath / CSS selector
 * 4) itemURL XPath / CSS selector
 *
 */
public class ConfigFile {
	String url; 
	String categorySelector; 
	String itemSelector;
	String nextSelector; 
	
	// data information 
	String itemSoldSelector;		//sales 
	String itemPriceSelector;		//price 
	String itemImageSelector; 		//image
	String itemDateSelector; 		//date it was recorded
	String itemTypeSelector; 		//item type
	
	
	//TODO read a .txt or .xml file and parse info
	
	public ConfigFile() {
		url = "http://www.ebay.com/rpp/womens-clothing";
		categorySelector = "li[data-node-id=0]>ul[class=navigation-list]>li>a[href]";
		itemSelector = "ul[id=GalleryViewInner] h3>a[href]";
		nextSelector = "td[class=pagn-next]>a";
		
		// handle data task (item information) 
		//itemSoldSelector = "span[class=vi-qtyS-hot-red]>a:empty"; 
		itemSoldSelector = "div#why2buy div div:first-child span"; 
		itemPriceSelector = "span[itemprop=price]";
		itemImageSelector = "div[id=mainImgHldr] img[id=icImg]";
		itemDateSelector = "div.vi-desc-revHistory";
		itemTypeSelector = "h2>ul>li:nth-last-of-type(1)>a>span";
		
	}
	
	public String getURL() {
		return url; 
	}
	
	public String getCategorySelector() {
		return categorySelector; 
	}
	
	public String getItemSelector() {
		return itemSelector; 
	}
	
	public String getNextSelector() {
		return nextSelector; 
	}
	
	public String getItemSold() {
		return itemSoldSelector; 
	}
	
	public String getItemPrice() {
		return itemPriceSelector; 
	}
	
	public String getItemImage() {
		return itemImageSelector; 
	}
	
	public String getItemDate() {
		return itemDateSelector; 
	}
	
	public String getItemType() {
		return itemTypeSelector; 
	}
	
	
}

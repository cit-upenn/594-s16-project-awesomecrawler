package edu.upenn.eCommerceCrawler.crawler.config;

public abstract class Config {
	String siteID; // website entrance url
	String url;
	String categorySelector;
	String itemSelector;
	String nextSelector;

	// data information
	String itemSoldSelector; // sales
	String itemPriceSelector; // price
	String itemImageSelector; // image
	String itemDateSelector; // date it was recorded
	String itemTypeSelector; // item type

	public Config() {
	};

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

	public String getSiteID() {
		return siteID;
	}
}

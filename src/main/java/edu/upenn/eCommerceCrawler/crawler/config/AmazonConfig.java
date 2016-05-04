package edu.upenn.eCommerceCrawler.crawler.config;

public class AmazonConfig extends Config {
	// TODO fix CSS selectors
	public AmazonConfig() {
		siteID = "http://www.amazon.com";
		url = "http://www.amazon.com/s/ref=lp_7147440011_ex_n_3?rh=n%3A7141123011%2Cn%3A10445813011%2Cn%3A7147440011%2Cn%3A1040660&bbn=10445813011&ie=UTF8&qid=1462202549";
		categorySelector = "ul.root li:nth-child(2) ul li ul > li > a[href]";
		itemSelector = "div#mainResults ul > li div div:nth-child(3) div:nth-child(1) a[href]";
		nextSelector = "a#pagnNextLink[href]";

		// handle data task (item information)
		itemSoldSelector = "li#SalesRank";
		itemPriceSelector = "span#priceblock_ourprice";
		itemImageSelector = "div.imgTagWrapper img";
		itemDateSelector = "div#detailBullets_feature_div ul li:last-child span.a-list-item span:nth-child(2)";
		itemTypeSelector = "";
	}
}

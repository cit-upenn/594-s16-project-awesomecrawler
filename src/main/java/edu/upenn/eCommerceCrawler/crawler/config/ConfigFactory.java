package edu.upenn.eCommerceCrawler.crawler.config;

public class ConfigFactory {

	public Config createConfigFile(String site) {
		Config file = null;
		if (site.equals("Amazon")) {
			file = new AmazonConfig();
		} else if (site.equals("Ebay")) {
			file = new EbayConfig();
		} else {
			System.out.println("invalid site");
		}
		return file;
	}

}

package edu.upenn.eCommerceCrawler.crawlertest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.upenn.eCommerceCrawler.crawler.Config;
import edu.upenn.eCommerceCrawler.crawler.ConfigFactory;
import edu.upenn.eCommerceCrawler.crawler.SaveURL;
import edu.upenn.eCommerceCrawler.crawler.Task;
import edu.upenn.eCommerceCrawler.crawler.TaskType;

/** 
 * This class tests if the CSS selector obtains desirable URLs
 *
 */
public class SelectorTest {
	Config config; 
	String siteID; 
	
	public SelectorTest(String site) {
		ConfigFactory cf = new ConfigFactory();
		this.config = cf.createConfigFile(site);
		this.siteID = config.getSiteID();
	}
	
	public void getLinks(String url) {
		System.out.println("url=" + url);
		String selector = "li[id^=result_] div div:nth-child(3) div:nth-child(1) a[href]";
		
		//String selector = config.getCategorySelector();
		//String selector = config.getNextSelector();
		//String selector = config.getItemSelector();
		Document page = null;
		try {
			//page = Jsoup.connect(url).userAgent("Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; fr) Presto/2.9.168 Version/11.52").get();
			page = Jsoup.connect(url).userAgent("Mozilla").get();
			//System.out.println(page);
			File file = new File("html_page");
			PrintWriter out = new PrintWriter(file);
			out.print(page);
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		Elements links = page.select(selector);
		for (Element link: links) {
			//System.out.println(link);
			String href = link.attr("href");
			if (!href.contains(siteID)) {
				href = siteID + href; 
			}
			System.out.println(href);
		}
	}

	public void testData(String url) {
		Document doc = null;
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
				}
			} catch (IOException e) {
				System.out.println("Reconnecting ...");
			}
		}
		
		//sales 
		String sales_str = doc.select("li#SalesRank").text();
		System.out.println(sales_str);
		
		// price 
		String price_str = doc.select("span#priceblock_ourprice").text(); 
		System.out.println(price_str);
		
		// date 
		String date = doc.select("div#detailBullets_feature_div ul li:last-child span.a-list-item span:nth-child(2)").text(); 
		System.out.println(date);
		
		// image 
		String image = doc.select("div.imgTagWrapper img").attr("src");
		System.out.println(image);

	}
	
	public static void main(String[] args) {
		String site = "Amazon";
		SelectorTest test = new SelectorTest(site);
		//test.getLinks("http://www.amazon.com/s?ie=UTF8&page=2&rh=n%3A1045024"); 
		//test.getLinks("http://www.amazon.com/s/ref=lp_7147440011_ex_n_3?rh=n%3A7141123011%2Cn%3A10445813011%2Cn%3A7147440011%2Cn%3A1040660&bbn=10445813011&ie=UTF8&qid=1462202549");
		//test.getLinks("http://www.amazon.com/Dresses/b?ie=UTF8&node=1045024");
		//test.getLinks("http://www.amazon.com/s/ref=lp_1045024_pg_2/181-7641371-2331761?rh=n%3A7141123011%2Cn%3A7147440011%2Cn%3A1040660%2Cn%3A1045024&page=2&ie=UTF8&qid=1462227252");
		test.testData("http://www.amazon.com/VELVET-GRAHAM-SPENCER-Longsleeve-Sweater/dp/B01BT8DJW2");
		
//		String site = "Ebay";
//		SelectorTest test = new SelectorTest(site);
//		test.getLinks("http://www.ebay.com/sch/Womens-Dresses/63861/bn_661850/i.html?LH_ItemCondition=1000&LH_PrefLoc=1&&LH_FS=1");
	}
}

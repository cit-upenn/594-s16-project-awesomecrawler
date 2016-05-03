package edu.upenn.eCommerceCrawler.crawlertest;

public class DataTest {
	public static void main(String[] args) {
		String sales = "Amazon Best Sellers Rank: #1,087,145 in Clothing (See Top 100 in Clothing) #4851 in?Clothing > Women > Fashion Hoodies & Sweatshirts";
		System.out.println(sales);
		int i=0; 
		while (sales.charAt(i) != '#') i++; 
		int j = i + 1; 
		while ((sales.charAt(j) >= '0' && sales.charAt(j) <= '9') || (sales.charAt(j) == ',')) {
			j++; 
		}
		sales = sales.substring(i+1, j);
		System.out.println(sales);
	}
}

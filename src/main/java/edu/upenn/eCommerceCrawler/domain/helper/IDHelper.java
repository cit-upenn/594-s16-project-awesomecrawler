package edu.upenn.eCommerceCrawler.domain.helper;

public class IDHelper {
	private static long id = 0l;

	public static synchronized long getId() {
		return id++;
	}
}

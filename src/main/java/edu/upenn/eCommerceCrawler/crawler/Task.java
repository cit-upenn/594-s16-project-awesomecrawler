package edu.upenn.eCommerceCrawler.crawler;

public class Task {
	private String url;
	private TaskType type;

	public Task(String url, TaskType type) {
		this.url = url;
		this.type = type;
	}

	public String getUrl() {
		return this.url;
	}

	public TaskType getType() {
		return this.type;
	}

}

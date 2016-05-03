package edu.upenn.eCommerceCrawler.web.helper;

import java.util.List;

public final class CollectionResult<T> {
	private List<T> elements;
	private long total;
	private long start;
	private long end;

	public CollectionResult(List<T> elements, long total, long start, long end) {
		this.elements = elements;
		this.total = total;
		this.start = start;
		this.end = end;
	}

	public List<T> getElements() {
		return elements;
	}

	public long getTotal() {
		return total;
	}

	public long getStart() {
		return start;
	}

	public long getEnd() {
		return end;
	}

}

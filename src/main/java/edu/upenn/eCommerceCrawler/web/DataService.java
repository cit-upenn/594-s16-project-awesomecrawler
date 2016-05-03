package edu.upenn.eCommerceCrawler.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sleepycat.je.DatabaseException;

import edu.upenn.eCommerceCrawler.dao.DAO;
import edu.upenn.eCommerceCrawler.domain.ECommerceEntity;
import edu.upenn.eCommerceCrawler.web.helper.CollectionResult;

@RestController
@RequestMapping("/v1/")
public class DataService {
	@RequestMapping(value = "data/{start:\\d{1,}}/{size:\\d{1,}}", produces = MediaType.APPLICATION_JSON_VALUE, method = GET)
	public ResponseEntity<CollectionResult<ECommerceEntity>> getByTitle(@PathVariable("start") long start,
			@PathVariable("size") long size) throws DatabaseException {
		DAO<ECommerceEntity> dao;
		dao = new DAO<ECommerceEntity>(ECommerceEntity.class);
		List<ECommerceEntity> elements = dao.fetch(start, size);
		long total = dao.count();
		CollectionResult<ECommerceEntity> result = new CollectionResult<ECommerceEntity>(elements, total, start,
				start + size);
		return ResponseEntity.ok(result);
	}
}

package edu.upenn.eCommerceCrawler.domain;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

import edu.upenn.eCommerceCrawler.domain.helper.IDHelper;

@Entity
public class ECommerceEntity {
	@PrimaryKey
	private Long id;
	private String sales;   
	private String price;
	private String date;
	private String imageLink;
	private String type;

	public ECommerceEntity(String sales, String price, String date, String imageLink, String type) {
		this.id = IDHelper.getId();
		this.setSales(sales);
		this.setPrice(price);
		this.setDate(date);
		this.setImageLink(imageLink);
		this.setType(type);
	}

	public ECommerceEntity() {
		this.id = IDHelper.getId();
	}

	public Long getId() {
		return id;
	}
	
	public String getSales() {
		return sales; 
	}
	
	public String getPrice() {
		return price;
	}

	public void setSales(String sales2) {
		this.sales = sales2; 
	}
	
	public void setPrice(String price) {
		this.price = price;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof ECommerceEntity))
			return false;
		ECommerceEntity other = (ECommerceEntity) obj;
		if (other.getId() == null)
			return false;
		if (this.id == null)
			return false;
		return this.id == other.getId();
	}

	public int hashCode() {
		return this.id == null ? 0 : this.id.hashCode();
	}

}

package edu.upenn.eCommerceCrawler.domain;

import java.util.Date;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class ECommerceEntity {
	@PrimaryKey(sequence = "id")
	private Long id;
	private double salary;
	private Date date;
	private String imageLink;
	private String type;

	public ECommerceEntity(double salary, Date date, String imageLink, String type) {
		this.setSalary(salary);
		this.setDate(date);
		this.setImageLink(imageLink);
		this.setType(type);
	}

	public ECommerceEntity() {

	}

	public Long getId() {
		return id;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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

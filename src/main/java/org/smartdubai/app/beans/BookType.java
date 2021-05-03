package org.smartdubai.app.beans;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BookType {
	@Id
	private String type;
	private double discount;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public BookType() {
		super();
	}
	
	public BookType(String type, double discount) {
		this.type=type;
		this.discount=discount;				
	}
}

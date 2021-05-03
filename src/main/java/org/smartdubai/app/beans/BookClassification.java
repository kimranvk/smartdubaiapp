package org.smartdubai.app.beans;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BookClassification {
	@Id
	private String classification;
	private double discount;
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public BookClassification() {
		super();
	}
	
	public BookClassification(String classification, double discount) {
		this.classification=classification;
		this.discount=discount;				
	}
}

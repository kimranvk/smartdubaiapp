package org.smartdubai.app.beans;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BookPromoCode {
	@Id
	private String promoCode;
	private double discount;
	public String getPromoCode() {
		return promoCode;
	}
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public BookPromoCode() {
		super();
	}
	
	public BookPromoCode(String promoCode, double discount) {
		this.promoCode=promoCode;
		this.discount=discount;				
	}
}

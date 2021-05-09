package org.smartdubai.app.beans;

import java.util.ArrayList;
import java.util.List;

public class Order {
	private List<Long> bookIds = new ArrayList<>();
	private String promoCode;

	public List<Long> getBookIds() {
		return bookIds;
	}

	public void setBookIds(List<Long> bookIds) {
		this.bookIds = bookIds;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
}

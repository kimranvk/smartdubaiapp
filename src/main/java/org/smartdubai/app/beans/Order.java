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

	@Override
	public String toString() {
		return "Order [bookIds=" + bookIds + ", promoCode=" + promoCode + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookIds == null) ? 0 : bookIds.hashCode());
		result = prime * result + ((promoCode == null) ? 0 : promoCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (bookIds == null) {
			if (other.bookIds != null)
				return false;
		} else if (!bookIds.equals(other.bookIds))
			return false;
		if (promoCode == null) {
			if (other.promoCode != null)
				return false;
		} else if (!promoCode.equals(other.promoCode))
			return false;
		return true;
	}
}

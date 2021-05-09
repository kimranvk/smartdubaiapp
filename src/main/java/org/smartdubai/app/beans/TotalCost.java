package org.smartdubai.app.beans;

public class TotalCost {
	private double totalCost = 0.0;

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public TotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	@Override
	public String toString() {
		return "TotalCost [totalCost=" + totalCost + "]";
	}
}

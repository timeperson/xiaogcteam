package com.coding.sales.customer.customers;

import com.coding.sales.customer.cards.Card;

public class Customer {
	private String CustomerId;
	private String CustomerName;
	private Card card; 
	private String sumIntegral;
	
	public String getCustomerId() {
		return CustomerId;
	}
	public void setCustomerId(String customerId) {
		CustomerId = customerId;
	}
	public String getCustomerName() {
		return CustomerName;
	}
	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
	public String getSumIntegral() {
		return sumIntegral;
	}
	public void setSumIntegral(String sumIntegral) {
		this.sumIntegral = sumIntegral;
	}
}

package com.coding.sales.customer.customers;

import com.coding.sales.customer.cards.Card;
import com.coding.sales.customer.integral.SumIntegral;

public class Customer {
	private String CustomerId;
	private String CustomerName;
	private Card card; 
	private SumIntegral sumIntegral;
	
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
	public SumIntegral getSumIntegral() {
		return sumIntegral;
	}
	public void setSumIntegral(SumIntegral sumIntegral) {
		this.sumIntegral = sumIntegral;
	}
}

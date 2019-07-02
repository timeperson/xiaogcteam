package com.coding.sales.customer.cards;

import com.coding.sales.customer.integral.BaseIntegral;
import com.coding.sales.customer.integral.SumIntegral;

public abstract class Card {
	private BaseIntegral baseIntegral;
	private SumIntegral sumIntegral;
	public BaseIntegral getBaseIntegral() {
		return baseIntegral;
	}
	public void setBaseIntegral(BaseIntegral baseIntegral) {
		this.baseIntegral = baseIntegral;
	}
	public SumIntegral getSumIntegral() {
		return sumIntegral;
	}
	public void setSumIntegral(SumIntegral sumIntegral) {
		this.sumIntegral = sumIntegral;
	}
}

package com.coding.sales.customer.cards;

import com.coding.sales.customer.integral.BaseIntegral;
import com.coding.sales.customer.integral.SumIntegral;

public class DiamondCard implements Card{
	public Double bBaseIntegral = BaseIntegral.one;
	public String maxIntegral;
	public String minIntegral; 
	@Override
	public void setBaseIntegral() {
		this.bBaseIntegral = BaseIntegral.one;
	}
	
	@Override
	public void setSumIntegral() {
		this.maxIntegral= SumIntegral.Integral_10000;
		this.minIntegral= SumIntegral.Integral_0;
	}

	@Override
	public Double getBaseIntegral() {
		return bBaseIntegral;
	}

	@Override
	public String getSumIntegral() {
		return "";
	}
}

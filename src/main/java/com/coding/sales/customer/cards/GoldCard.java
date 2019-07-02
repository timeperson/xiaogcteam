package com.coding.sales.customer.cards;

import com.coding.sales.customer.integral.BaseIntegral;
import com.coding.sales.customer.integral.SumIntegral;

public class GoldCard implements Card{
	public Double bBaseIntegral = BaseIntegral.oneAndHalf;
	public String maxIntegral;
	public String minIntegral; 
	@Override
	public void setBaseIntegral() {
		this.bBaseIntegral = BaseIntegral.oneAndHalf;
	}
	
	@Override
	public void setSumIntegral() {
		this.maxIntegral= SumIntegral.Integral_50000;
		this.minIntegral= SumIntegral.Integral_10000;
	}

	@Override
	public Double getBaseIntegral() {
		// TODO Auto-generated method stub
		return bBaseIntegral;
	}

	@Override
	public String getSumIntegral() {
		// TODO Auto-generated method stub
		return null;
	}
}

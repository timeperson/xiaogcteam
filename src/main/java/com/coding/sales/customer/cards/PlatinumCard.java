package com.coding.sales.customer.cards;

import com.coding.sales.customer.integral.BaseIntegral;
import com.coding.sales.customer.integral.SumIntegral;

public class PlatinumCard implements Card{
	public Double bBaseIntegral= BaseIntegral.two;
	public String maxIntegral;
	public String minIntegral; 
	@Override
	public void setBaseIntegral() {
		this.bBaseIntegral = BaseIntegral.two;
	}
	
	@Override
	public void setSumIntegral() {
		this.minIntegral= SumIntegral.Integral_100000;
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

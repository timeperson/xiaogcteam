package com.coding.sales.goods;

import com.coding.sales.goods.discount.Events;


public class Goods {
	private String GoodsId;
	private String GoodsName;
	private String unit;
	private String price;
	private Events events;
	public String getGoodsId() {
		return GoodsId;
	}
	public void setGoodsId(String goodsId) {
		GoodsId = goodsId;
	}
	public String getGoodsName() {
		return GoodsName;
	}
	public void setGoodsName(String goodsName) {
		GoodsName = goodsName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Events getEvents() {
		return events;
	}
	public void setEvents(Events events) {
		this.events = events;
	}
}

package com.coding.sales.init;

import java.util.ArrayList;

import com.coding.sales.customer.cards.Card;
import com.coding.sales.customer.cards.DiamondCard;
import com.coding.sales.customer.cards.GoldCard;
import com.coding.sales.customer.cards.PlatinumCard;
import com.coding.sales.customer.customers.Customer;
import com.coding.sales.goods.Goods;
import com.coding.sales.goods.Unit;
import com.coding.sales.goods.discount.Discount;
import com.coding.sales.goods.discount.Events;
import com.coding.sales.goods.discount.FullReduction;

public class InitArgs {
	private ArrayList<Customer> cuslist = new ArrayList<Customer>();
	private ArrayList<Goods> goodsList = new ArrayList<Goods>();
	
	private Object[][] cusObject= new Object[][]{
			{"6236609999","马丁",new DiamondCard(),"9860"},
			{"6630009999","王立",new GoldCard(),"48860"},
			{"8230009999","李想",new PlatinumCard(),"98860"},
			{"9230009999","张三",new DiamondCard(),"198860"},
	};
	
	private Object[][] goodsObject= new Object[][]{
			
			{"001001","世园会五十国钱币册", Unit.manual,"998.00",setEvent("","")},
			{"001002","2019北京世园会纪念银章大全40g",Unit.box,"1380.00",setEvent(Discount.discount9,"")},
			{"003001","招财进宝", Unit.slip,"1580.00",setEvent(Discount.discount95,"")},
			{"003002","水晶之恋", Unit.slip,"980.00",setEvent("",FullReduction.fullThreeGiveone+"@"+FullReduction.thirdHalfPrice)},
			{"002002","中国经典钱币套装", Unit.cover,"998.00",setEvent("",FullReduction.full2000cut30+"@"+FullReduction.full1000cut10)},
			{"002001","守扩之羽比翼双飞4.8g", Unit.slip,"998.00",setEvent(Discount.discount95,"")},
			{"002003","中国银象棋12g", Unit.cover,"698.00",setEvent(Discount.discount9,FullReduction.full3000cut350+"@"+FullReduction.full2000cut30+"@"+FullReduction.full1000cut10)},
	}; 
	
	public Events setEvent(String discount,String fullReduction){
		Events event = new Events();
		event.setDiscount(discount);
		event.setFullReduction(fullReduction);
		return event;
	}
	
	public void init(){
		initCustomer();
		initGoods();
	}
	
	public void initCustomer(){
		for(Object[] obj:cusObject){
			Customer customer = createCustomer(obj);
			cuslist.add(customer);
		}
	}
	
	public void initGoods(){
		for(Object[] obj:goodsObject){
			Goods goods = createGoods(obj);
			goodsList.add(goods);
		}
	}
	
	public Customer createCustomer(Object[] object){
		Customer customer = new Customer();
		customer.setCustomerId((String)object[0]);
		customer.setCustomerName((String)object[1]);
		customer.setCard((Card)object[2]);
		customer.setSumIntegral((String)object[3]);
		return customer;
	}
	
	public Goods createGoods(Object[] object){
		Goods goods = new Goods();
		goods.setGoodsId((String)object[0]);
		goods.setGoodsName((String)object[1]);
		goods.setUnit((String)object[2]);
		goods.setPrice((String)object[3]);
		goods.setEvents((String)object[3]);
		return goods;
	}
	
}
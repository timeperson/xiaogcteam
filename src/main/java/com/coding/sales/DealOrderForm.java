package com.coding.sales;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.coding.sales.customer.cards.Card;
import com.coding.sales.customer.customers.Customer;
import com.coding.sales.customer.integral.BaseIntegral;
import com.coding.sales.goods.Goods;
import com.coding.sales.goods.discount.Discount;
import com.coding.sales.goods.discount.FullReduction;
import com.coding.sales.init.InitArgs;
import com.coding.sales.input.OrderCommand;
import com.coding.sales.input.OrderItemCommand;
import com.coding.sales.input.PaymentCommand;
import com.coding.sales.output.DiscountItemRepresentation;
import com.coding.sales.output.OrderItemRepresentation;
import com.coding.sales.output.OrderRepresentation;
import com.coding.sales.output.PaymentRepresentation;

public class DealOrderForm {
	
    private String orderId;
    private String createTime;//创建时间
    private String memberId;//会员编号
    private String memberName;//会员名称
    private String oldMemberType;//原会员等级
    private String newMemberType;//新会员等级
    private String memberPointsIncreased="";//本次消费会员新增的积分
    private String newIntegral;//会员最新的积分
    
    private List<OrderItemCommand> items;//订单信息
    private List<PaymentCommand> payments;//支付信息
    List<PaymentRepresentation> paymentlist;
    List<DiscountItemRepresentation> discountslist = new ArrayList<DiscountItemRepresentation>();
    List<OrderItemRepresentation> orderItems;
    private List<String> discounts;//优惠信息
    
    private String oldIntegral;
    
    private String totalPrice;//订单总金额
    private BigDecimal totalDiscountPrice;//优惠总金额
    
    private BigDecimal PretotalDiscountPrice;//订单总金额
    
    private String discount95 = "0.95";
    private String discount90 = "0.9";
    
    
    
    private ArrayList<Customer> cuslist = new ArrayList<Customer>();
	private ArrayList<Goods> goodsList = new ArrayList<Goods>();
    
	public OrderRepresentation dealOrder(OrderCommand command){
		
		orderId = command.getOrderId();
		createTime = command.getCreateTime();
		memberId = command.getMemberId();
		items = command.getItems();
		payments = command.getPayments();
		discounts = command.getDiscounts();
		
		InitArgs init = new InitArgs();
		init.init();
		cuslist = init.getCuslist();
		goodsList = init.getGoodsList();		
		
		totalPrice = calculatePaySum().toString();
		dealIntegral(totalPrice);
		addlist();
		Date createDate = null;
		try {
			SimpleDateFormat sfd = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
			 createDate = sfd.parse(createTime);
		} catch (Exception e) {
			e.fillInStackTrace();
		}
		
		memberPointsIncreased = new BigDecimal(BigDecimal.valueOf(Double.parseDouble(memberPointsIncreased)).stripTrailingZeros().toPlainString()).toString();
		newIntegral = new BigDecimal(BigDecimal.valueOf(Double.parseDouble(newIntegral)).stripTrailingZeros().toPlainString()).toString();
		OrderRepresentation result = new OrderRepresentation(orderId,createDate,memberId,memberName
				,oldMemberType,newMemberType,Integer.parseInt(memberPointsIncreased),Integer.parseInt(newIntegral)
				,orderItems,PretotalDiscountPrice,discountslist,totalDiscountPrice,calculatePaySum(),paymentlist,discounts);
		
		return result;
	}
	
	//计算支付金额
	public BigDecimal calculatePaySum(){
		BigDecimal PaySum = null;
		BigDecimal bPretotalDiscountPrice = null;
		for(OrderItemCommand payment:items){
			String product = payment.getProduct();
			BigDecimal amount = payment.getAmount();
			
			//匹配商品对应的活动信息
			for(Goods goods:goodsList){
				if(goods.getGoodsId().equals(product)){
					String price = goods.getPrice();//商品金额
					String productName = goods.getGoodsName();
					String discount = goods.getEvents().getDiscount();//打折券
					
					bPretotalDiscountPrice = amount.multiply(new BigDecimal(price));
					
					String fullReduction = goods.getEvents().getFullReduction();//满减活动
					BigDecimal preferentialPricebyfullReduction = calculateSinglePriceforfullReduction(product,productName,price,amount,fullReduction);
					BigDecimal preferentialPricebyDiscount = calculateSinglePriceforDiscount(price,amount,discount);
					if(preferentialPricebyfullReduction.compareTo(preferentialPricebyDiscount)>0){
						PaySum = preferentialPricebyfullReduction;
					}else{
						PaySum = preferentialPricebyDiscount;
					}
					break;
				}
			}
			bPretotalDiscountPrice = bPretotalDiscountPrice.add(bPretotalDiscountPrice);
			PaySum = bPretotalDiscountPrice.add(PaySum);
		}
		PretotalDiscountPrice = bPretotalDiscountPrice;
		totalDiscountPrice = bPretotalDiscountPrice.subtract(PaySum);
		return PaySum;
	}
	
	//根据满减计算单个产品价格
	public BigDecimal calculateSinglePriceforfullReduction(String product,String productName,String price,BigDecimal amount,String fullReduction){
		BigDecimal bprice=new BigDecimal(price);
		BigDecimal PriceAll = amount.multiply(bprice);
		BigDecimal SinglePriceforfullReduction = PriceAll;
		if(fullReduction == null || "".equals(fullReduction))return PriceAll;
		String[] fullReductionArray = fullReduction.split("@", -1);
		for(int i = 0;i<fullReductionArray.length;i++){
			BigDecimal preferentialPrice = SinglePriceforfullReduction ;
			String fullRed = fullReductionArray[0];
			if(fullRed.equals(FullReduction.full1000cut10)){
				if(PriceAll.compareTo(new BigDecimal("1000"))>0){
					preferentialPrice = PriceAll.subtract(new BigDecimal("10"));
				}
			}else if(fullRed.equals(FullReduction.full2000cut30)){
				if(PriceAll.compareTo(new BigDecimal("2000"))>0){
					preferentialPrice = PriceAll.subtract(new BigDecimal("30"));
					
				}
			}else if(fullRed.equals(FullReduction.full3000cut350)){
				if(PriceAll.compareTo(new BigDecimal("3000"))>0){
					preferentialPrice = PriceAll.subtract(new BigDecimal("350"));
				}
			}else if(fullRed.equals(FullReduction.fullThreeGiveone)){
				if(amount.compareTo(new BigDecimal("4"))>=0){
					preferentialPrice = PriceAll.subtract(bprice);
				}
			}else if(fullRed.equals(FullReduction.thirdHalfPrice)){
				if(amount.compareTo(new BigDecimal("3"))>=0){
					preferentialPrice = PriceAll.subtract(bprice.divide(new BigDecimal("2")));
				}
			}
			DiscountItemRepresentation dis = new DiscountItemRepresentation(product,productName,new BigDecimal("212"));
			discountslist.add(dis);
			SinglePriceforfullReduction = setSinglePriceforfullReduction(SinglePriceforfullReduction,preferentialPrice);
		}	
		return SinglePriceforfullReduction;
	}
	
	private BigDecimal setSinglePriceforfullReduction(BigDecimal SinglePriceforfullReduction,BigDecimal preferentialPrice){
		if(preferentialPrice.compareTo(SinglePriceforfullReduction)<0)SinglePriceforfullReduction = preferentialPrice;
		return SinglePriceforfullReduction;
	}
	
	//根据打折券计算产品金额
	public BigDecimal calculateSinglePriceforDiscount(String price,BigDecimal amount,String discount){
		BigDecimal bprice=new BigDecimal(price);
		BigDecimal PriceAll = amount.multiply(bprice);
		if((discount == null || "".equals(discount)) || (discounts == null || "".equals(discounts)))return PriceAll;
		if(discount.equals(discounts)){
			if(Discount.discount95.equals(discounts))PriceAll = PriceAll.multiply(new BigDecimal(discount95));
			if(Discount.discount9.equals(discounts))PriceAll = PriceAll.multiply(new BigDecimal(discount90));
		}
		return PriceAll;
	}
	
	//处理积分信息
	public void dealIntegral(String totalPrice){
		DecimalFormat decimalFormat = new DecimalFormat(BigDecimal.valueOf(Double.parseDouble(totalPrice))
				.stripTrailingZeros().toPlainString());
		
		//匹配商品对应的活动信息
		for(Customer cus:cuslist){
			if(cus.getCustomerId().equals(memberId)){
				memberName = cus.getCustomerName();//
				Card card = cus.getCard();
				Double cc = card.getBaseIntegral();
				oldIntegral = cus.getSumIntegral();
				System.out.println("原卡种"+cc);
				newIntegral = new BigDecimal(oldIntegral).add(new BigDecimal(totalPrice).multiply(new BigDecimal(cc))).toString();
				if(cc == BaseIntegral.one){
					oldMemberType = "普卡";
				}else if(cc == BaseIntegral.oneAndHalf){
					oldMemberType = "金卡";
				}else if(cc == BaseIntegral.oneAndeight){
					oldMemberType = "白金卡";
				}else{
					oldMemberType = "钻石卡";
				}
				double doldIntegral = Double.parseDouble(oldIntegral);
				double dnewIntegral = Double.parseDouble(newIntegral);
				System.out.println("新卡种"+dnewIntegral);
				memberPointsIncreased = dnewIntegral-doldIntegral +"";
				if(0<dnewIntegral && dnewIntegral<10000){
					newMemberType = "普卡";
				}else if(10000<dnewIntegral && 50000<dnewIntegral){
					newMemberType = "金卡";
				}else if(50000<dnewIntegral && 100000<dnewIntegral){
					newMemberType = "白金卡";
				}else{
					newMemberType = "钻石卡";
				}
			}
		}
	}
	
	public void addlist(){
		
	    orderItems = new ArrayList<OrderItemRepresentation>();
	    paymentlist = new ArrayList<PaymentRepresentation>();
	    PaymentRepresentation pp = new PaymentRepresentation("余额支付",new BigDecimal("2"));
	    paymentlist.add(pp);
	    
	    OrderItemRepresentation or = new OrderItemRepresentation("111","222",new BigDecimal("2"),new BigDecimal("2"),new BigDecimal("2"));
	    
	    orderItems.add(or);
	}
}

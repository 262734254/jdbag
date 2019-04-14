package com.xiu.jd.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class WebUtils {
	/**生成6位的随机字符串**/
	public static String randomString(){
		Random random =new Random();
		StringBuilder sb=new StringBuilder();
		while(true){
			sb.append(random.nextInt(10));
			if(sb.length()>=6){
				break;
			}
		}
		return sb.toString();
	}
	
	public static String getFullYearString(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
	
	/**
	 * 计算最后的京东价格(单位为元)
	 * @param prdOfferPriceBig
	 * @return
	 */
/*	public static int  computePrice(BigDecimal prdOfferPriceBig){
		String ratio=ParseProperties.RATIO;
		int lastNumber=ParseProperties.LASTNUMBER;
		double ratioDouble=1;
		int finalPrice=0;
		if(ratio!=null && !ratio.isEmpty()){
			ratioDouble=Double.parseDouble(ratio);
		}
		if(ratioDouble>=1){
			double num=prdOfferPriceBig.doubleValue();
			int intFinal=(int)num;
			return intFinal;
		}
		if(lastNumber!=0){
			double num=prdOfferPriceBig.doubleValue()/ratioDouble;
			int intFinal=(int)num;
			String stringFinal=intFinal+"";
			int len=stringFinal.length();
			 //截取最后一个数字
		     int lastNum=Integer.parseInt(stringFinal.substring(len-1, len));
		     if(lastNum<lastNumber){
		    	 finalPrice=intFinal+lastNumber-lastNum;
			 }
		     
		}else{
			//lastNumber为0时，取整数
			double num=prdOfferPriceBig.doubleValue()/ratioDouble;
			int intFinal=(int)num;
			finalPrice=intFinal;
		}
		return finalPrice;
	}
	*/
	
	public static int  computePrice(BigDecimal prdOfferPriceBig){
		int finalPrice=0;
		double num=prdOfferPriceBig.doubleValue()+prdOfferPriceBig.doubleValue()*0.1;
		int intFinal=(int)Math.rint(num);
		finalPrice=intFinal;
		return finalPrice;
	}
	
	/**
	 * 对于有些商品没有市场价的，计算商品的市场价 (单位为元)
	 * 由于京东的接口需要
	 * @param xiuPrice
	 * @return
	 */
	public static int  buildMarketPrice(int xiuPrice){
    	int xiuMarketPrice=xiuPrice;
		   while(true){
			   if(xiuMarketPrice>xiuPrice){
				   break;
			   }
				 int tempPrice= (int) (xiuPrice*0.4);
				  xiuMarketPrice=xiuPrice+tempPrice;
		   }
		   return xiuMarketPrice;
	}
	
	/**
	 * 将分转换为元
	 * @param xiuPrice
	 * @return
	 */
	public static int longParseInt(long xiuPrice){
		if(xiuPrice<=0){
			return 0;
		}
		return (int)xiuPrice/100;
	}
	
	public static int doubleToInt(Double productCenterXiuPrice){
		int price=0;
		if(productCenterXiuPrice==null || productCenterXiuPrice<=0){
			return price;
		}
		long xiuPrice=Long.parseLong(new DecimalFormat("#0").format(productCenterXiuPrice));
		price=Integer.valueOf(xiuPrice+"");
		return price;
	}
	public static void main(String[] args) {
	/*	Long realCustomsTaxTotal=0L; //订单总关税  （订单下行邮商品关税之和，单位：分）
		
		System.out.println(realCustomsTaxTotal+1);*/
	/*	String xiucode="aaaa";
		 long xiuPrice=766000L;
		int tempJdPrice=WebUtils.longParseInt(xiuPrice);//分转换为元
		System.out.println(tempJdPrice);
		if(tempJdPrice<=0){
		System.out.println("走秀码:"+xiucode+"解析价格"+xiuPrice+"失败</br>");
		}
		int jdPrice=WebUtils.computePrice(new BigDecimal(tempJdPrice)); //单位为元
		System.out.println(jdPrice);*/
		String buyQuantitys="0.92";
		Double price=Double.valueOf(buyQuantitys)*10;
		Double aa=Double.parseDouble(String.valueOf(price).substring(0,3));
		System.out.println(aa);
		//Double price =(new DecimalFormat("#0.00").format(Double.parseDouble(buyQuantitys)));
		//System.out.println(	price*10);
	}
	
	public static long toSharePrice(Double cur, Double total, Double coupon) {
		String rs = ((cur - (cur / total * coupon)) * 100) + "";
		if (rs.indexOf(".") > -1) {
			return Long.parseLong(rs.substring(0, rs.indexOf(".")));
		}
		// String rs = (((cur-(cur / total * coupon)) * 100) +
		// "").replaceAll("\\.00", "")
		// .replaceAll("\\.0", "");
		return Long.parseLong(rs);
	}
}

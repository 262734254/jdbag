package com.xiu.jd.vo;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.solr.client.solrj.beans.Field;

import com.xiu.jd.utils.ParseProperties;



public class Goods {
	// 走秀码
	@Field("partNumber")
	private String goodsSn;

	// 商品名称
	@Field("itemName")
	private String goodsName;

	//上下架状态 1:上架，下架
	@Field("stateOnsale")
	private int stateOnsale;
	
	// 市场价
	@Field("priceMkt")
	private double price;

	// 走秀价(参加活动的)
	@Field("priceFinal")
	private double zsPrice;
	
	// 走秀价(没有参加活动的)
	@Field("priceXiu")
	private double priceXiu;
	

	// 商品图片
	@Field("imgUrl")
	private String imgUrl;

	// 折扣
	@Field("discount")
	private float discount;

	// 品牌
	@Field("brandName")
	private String brandname;
	
	/**商品ID**/
	@Field("itemId")
	private String itemId;
	
	/**供应商编号**/
	@Field("providerCode")
	private String providerCode;
	
	//上架时间
	@Field("onsaleTime")
	private Date onsaleTime;

	
	public String getOnsaleTime() {
		return this.onsaleTime == null ? "" :
			DateFormatUtils.format(this.onsaleTime, "yyyy-MM-dd HH:mm:ss");
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public String getGoodsName() {
		String prefix = (StringUtils.isNotBlank(this.brandname) ? (this.brandname) : "");
		return prefix + this.goodsName;
	}

	public String getPrice() {
		if (price == 0.0) {
			return "null";
		}
		return "" + price;
	}

	public double getZsPrice() {
		return zsPrice;
	}
	
	

	public double getPriceXiu() {
		return priceXiu;
	}

	/**
	 * 取得商品图片
	 * 
	 * @return
	*/
	public String getGoodsImg() {
		return ParseProperties.getPropertiesValue("IMG_PREFIX") + "/upload" + this.imgUrl;
	}
 
	/**
	 * 折扣
	 * 
	 * @return
	 */
	public String getDiscount() {
		if (price == 0.0) {
			return "";
		}
		if (zsPrice >= price) {
			return "";
		}
		int d = Math.round(discount * 10);
		if (d % 10 == 0) {
			return "" + (d / 10) + "折";
		}
		return "" + (d / 10.0) + "折";
	
	}

	/**
	 * 为了兼容老版本的活动商品 活动商品类表接口用到了这个字段
	 * 
	 * @return
	 */
	public int getIsActivityGoods() {
		return 0;
	}

	public int getStateOnsale() {
		return stateOnsale;
	}


	public String getItemId() {
		if(itemId!=null && !itemId.trim().isEmpty()){
			//不够7为前面补零 5
			int len=itemId.length();
			if(len<7){
				StringBuilder str=new StringBuilder(itemId);
				for(;len<7;len++){
					str.insert(0, '0');
				}
				 String productId=str.toString();
				 return productId;
			}
			
			
		}
		return itemId;
	}
	

	public String getProviderCode() {
		return providerCode;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("[");
		sb.append("goodsSn: ");
		sb.append(getGoodsSn());
		sb.append(", ");
		sb.append("goodsName: ");
		sb.append(getGoodsName());

		sb.append(", ");
		sb.append("stateOnsale: ");
		sb.append(getStateOnsale());
		
		
		sb.append(", ");
		sb.append("mktprice: ");
		sb.append(getPrice());
		sb.append(", ");
		sb.append("xiuPrice: ");
		sb.append(getZsPrice());
		sb.append(", ");
		sb.append("goodsImg: ");
		sb.append(getGoodsImg());
		sb.append(", ");
		sb.append("discount: ");
		sb.append(getDiscount());
		
		sb.append(", ");
		sb.append("itemId: ");
		sb.append(getItemId());
		sb.append(", ");
		sb.append("providerCode: ");
		sb.append(getProviderCode());
		
		sb.append("]");
		return sb.toString();
	}
}
package com.xiu.jd.bean.ware;

import java.io.Serializable;

public class SettlementProductInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3358526935495206284L;

	
    private String goodsSn; //走秀码
    
    private long xiuPrice; //原始的走秀价(单位为分)
    
    private long finnalXiuPrice; //调用结算系统后最终的走秀价(原始的走秀价+国际关税+国际运费)(单位为分)
    
    /**
     * 行邮商品成交价 单位：分
     * 对应结算接口中
     * 商品未加入关税与运费的价格 basePrice
     */
    private Long dealPrice;

    
    
	/**
	是否是行邮
	**/
	private boolean isCustoms;
    
	/**
	 * 海关编码
	 */
	private String hsCode;

	/**
	 * 税率编码
	 */
	private String customsCode;
	
	
	/**
	 * 真实关税
	 */
	private long realCustomsTax;
	
	/**
	 * 国际物流费
	 */
	private long transportCost;
	
	

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public long getXiuPrice() {
		return xiuPrice;
	}

	public void setXiuPrice(long xiuPrice) {
		this.xiuPrice = xiuPrice;
	}

	public long getFinnalXiuPrice() {
		return finnalXiuPrice;
	}

	public void setFinnalXiuPrice(long finnalXiuPrice) {
		this.finnalXiuPrice = finnalXiuPrice;
	}

	public boolean isCustoms() {
		return isCustoms;
	}

	public void setCustoms(boolean isCustoms) {
		this.isCustoms = isCustoms;
	}

	public String getHsCode() {
		return hsCode;
	}

	public void setHsCode(String hsCode) {
		this.hsCode = hsCode;
	}

	public String getCustomsCode() {
		return customsCode;
	}

	public void setCustomsCode(String customsCode) {
		this.customsCode = customsCode;
	}

	public long getRealCustomsTax() {
		return realCustomsTax;
	}

	public void setRealCustomsTax(long realCustomsTax) {
		this.realCustomsTax = realCustomsTax;
	}

	public long getTransportCost() {
		return transportCost;
	}

	public void setTransportCost(long transportCost) {
		this.transportCost = transportCost;
	}

	public Long getDealPrice() {
		return dealPrice;
	}

	public void setDealPrice(Long dealPrice) {
		this.dealPrice = dealPrice;
	}

	@Override
	public String toString() {
		return "SettlementProductInfo [goodsSn=" + goodsSn + ", xiuPrice="
				+ xiuPrice + ", finnalXiuPrice=" + finnalXiuPrice
				+ ", dealPrice=" + dealPrice + ", isCustoms=" + isCustoms
				+ ", hsCode=" + hsCode + ", customsCode=" + customsCode
				+ ", realCustomsTax=" + realCustomsTax + ", transportCost="
				+ transportCost + "]";
	}

	
	
	
    
	
	

}

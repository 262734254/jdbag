package com.xiu.jd.bean.ware;

/**
 * @author Vivian.Lu
 *
 *订单规格
 */
public class JdOrderMode {
	
	  private String promoId ;  //促销编号
	  private String favorMode;//满减（1），每满减（2）
	  private String quota   ;  //订单额度
	  private String rate  ;     //优惠力度
	  private String plus ;      
	  private String jdMinus ;  
	  private String link  ;     
	  private String jdDesc   ;
	  
	public String getPromoId() {
		return promoId;
	}
	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}
	public String getFavorMode() {
		return favorMode;
	}
	public void setFavorMode(String favorMode) {
		this.favorMode = favorMode;
	}
	public String getQuota() {
		return quota;
	}
	public void setQuota(String quota) {
		this.quota = quota;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getPlus() {
		return plus;
	}
	public void setPlus(String plus) {
		this.plus = plus;
	}
	public String getJdMinus() {
		return jdMinus;
	}
	public void setJdMinus(String jdMinus) {
		this.jdMinus = jdMinus;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getJdDesc() {
		return jdDesc;
	}
	public void setJdDesc(String jdDesc) {
		this.jdDesc = jdDesc;
	} 
	  
	  

}

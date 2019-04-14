package com.xiu.jd.bean.ware;

import java.util.Date;

public class JdPromo {
	  private String id;        
	  private String promoId;   //促销ID
	  private String name;      //促销名称
	  private Integer type;     //促销类型
	  private String beginTime;   //促销开始时间
	  private String endTime;     //促销结束时间
	  private String createTime;  //创建时间
	  private String bound; //部分商品参加（1）、）、部分商品不参加（3）
	  private String favorMode; //满减（1），每满减（2）
	  private String member;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPromoId() {
		return promoId;
	}
	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getBound() {
		return bound;
	}
	public void setBound(String bound) {
		this.bound = bound;
	}
	public String getFavorMode() {
		return favorMode;
	}
	public void setFavorMode(String favorMode) {
		this.favorMode = favorMode;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
	
	  
	  
	  
	
	  

}

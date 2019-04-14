package com.xiu.jd.vo;

import java.io.Serializable;

public class WareStockVo implements Serializable{
	private String wareId;		
	private Integer stockNum;
	public WareStockVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public WareStockVo(String wareId, Integer stockNum) {
		super();
		this.wareId = wareId;
		this.stockNum = stockNum;
	}

	public String getWareId() {
		return wareId;
	}
	public void setWareId(String wareId) {
		this.wareId = wareId;
	}
	public Integer getStockNum() {
		return stockNum;
	}
	public void setStockNum(Integer stockNum) {
		this.stockNum = stockNum;
	}
	
}

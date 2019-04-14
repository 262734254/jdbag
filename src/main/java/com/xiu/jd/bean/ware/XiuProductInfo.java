package com.xiu.jd.bean.ware;

import java.io.Serializable;

/**
 * 封装商品基本信息 
 * @author Administrator
 *
 */
public class XiuProductInfo implements Serializable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -2131916723369896955L;

	//商品ID
    private long itemId;
	
     //商品所属品牌编码
	 private String brandCode;
	 //商品所属品牌名称
	 private String brandName;
	 
	 //商品四级分类编号
     private int catCode;
   //商品四级分类名称
     private String childName;
     //商品走秀码
     private String xiuCode;
    
     //商品名称
    private String  itemName;
    //商品图片路径   p.SIZEIMGURL picturePath ,是错误的
    private String picturePath;
     //颜色
     private String  itemColor;
     //尺码
     private String  itemSize;
     
     //供应商编号,标识属于哪个供应商，来源EBS
     private String supplierCode; 
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public int getCatCode() {
		return catCode;
	}
	public void setCatCode(int catCode) {
		this.catCode = catCode;
	}
	public String getChildName() {
		return childName;
	}
	public void setChildName(String childName) {
		this.childName = childName;
	}
	
	public String getXiuCode() {
		return xiuCode;
	}
	public void setXiuCode(String xiuCode) {
		this.xiuCode = xiuCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public String getItemColor() {
		return itemColor;
	}
	public void setItemColor(String itemColor) {
		this.itemColor = itemColor;
	}
	public String getItemSize() {
		return itemSize;
	}
	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}
	
	
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	@Override
	public String toString() {
		return "XiuProductInfo [itemId=" + itemId + ", brandCode=" + brandCode
				+ ", brandName=" + brandName + ", catCode=" + catCode
				+ ", childName=" + childName + ", xiuCode=" + xiuCode
				+ ", itemName=" + itemName + ", itemColor=" + itemColor
				+ ", itemSize=" + itemSize + ", supplierCode=" + supplierCode
				+ "]";
	}
	
	
	
    
}

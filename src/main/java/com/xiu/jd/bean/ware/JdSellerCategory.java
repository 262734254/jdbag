package com.xiu.jd.bean.ware;

import java.io.Serializable;

/**
 * 商家自定义店内分类(类目)实体类
 * @author user
 *
 */
public class JdSellerCategory  implements Serializable{

private static final long serialVersionUID = 5451178058640790698L;
/**类目ID**/
private Integer cid;

/**父类目编号***/
private int parentId;

/**分类名称**/
private String name;

/**是否在首页展示商品  0:前台不展示，1:前台展示**/
private int isHomeShow;


/**类目是否为父类目 0：非,1:是**/
private int isParent;


/**是否展开子分类  0:不展开；1:展开**/
private int isOpen;

/**排序号**/
private int orderNo;


/**店铺ID**/
private String shopId;

public JdSellerCategory(){}



public JdSellerCategory(Integer cid, int parentId, String name, int orderNo,
		String shopId) {
	super();
	this.cid = cid;
	this.parentId = parentId;
	this.name = name;
	this.orderNo = orderNo;
	this.shopId = shopId;
}



public Integer getCid() {
	return cid;
}


public void setCid(Integer cid) {
	this.cid = cid;
}


public int getParentId() {
	return parentId;
}


public void setParentId(int parentId) {
	this.parentId = parentId;
}


public String getName() {
	return name;
}


public void setName(String name) {
	this.name = name;
}


public int getIsHomeShow() {
	return isHomeShow;
}


public void setIsHomeShow(int isHomeShow) {
	this.isHomeShow = isHomeShow;
}


public int getIsParent() {
	return isParent;
}


public void setIsParent(int isParent) {
	this.isParent = isParent;
}


public int getIsOpen() {
	return isOpen;
}


public void setIsOpen(int isOpen) {
	this.isOpen = isOpen;
}


public int getOrderNo() {
	return orderNo;
}


public void setOrderNo(int orderNo) {
	this.orderNo = orderNo;
}


public String getShopId() {
	return shopId;
}


public void setShopId(String shopId) {
	this.shopId = shopId;
}



@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((cid == null) ? 0 : cid.hashCode());
	return result;
}


@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	JdSellerCategory other = (JdSellerCategory) obj;
	if (cid == null) {
		if (other.cid != null)
			return false;
	} else if (!cid.equals(other.cid))
		return false;
	return true;
}


@Override
public String toString() {
	return "JdSellerCategory [cid=" + cid + ", parentId=" + parentId
			+ ", name=" + name + ", isHomeShow=" + isHomeShow + ", isParent="
			+ isParent + ", isOpen=" + isOpen + ", orderNo=" + orderNo
			+ ", shopId=" + shopId + "]";
}



/*System.out.println("类目ID："+c.getCid()+",是否在首页展示商品："+c.getHomeShow()
		+",名称："+c.getName()+",是否展开子分类:"+c.getOpen()+",排序号:"+c.getOrderNo()
		+",类目是否为父类目:"+c.getParent()+",父类目编号:"+c.getParentId()+",店铺ID:"+c.getShopId());*/
//类目ID：636307,是否在首页展示商品：false,名称：女装,是否展开子分类:false,排序号:1
//,类目是否为父类目:true,父类目编号:0,店铺ID:1001021



}

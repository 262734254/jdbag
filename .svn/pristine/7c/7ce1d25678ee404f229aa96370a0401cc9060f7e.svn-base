package com.xiu.jd.bean.ware;

import java.io.Serializable;

public class SkuInventoryQtyChangeJms implements Serializable {

 private static final long serialVersionUID = 2738555083273652162L;
 
/**商品sku码**/
  private String skuCode;
  /**商品sku码对应的库存量**/
  private String qty;
public String getSkuCode() {
	return skuCode;
}
public void setSkuCode(String skuCode) {
	this.skuCode = skuCode;
}
public String getQty() {
	return qty;
}
public void setQty(String qty) {
	this.qty = qty;
}



@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((skuCode == null) ? 0 : skuCode.hashCode());
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
	SkuInventoryQtyChangeJms other = (SkuInventoryQtyChangeJms) obj;
	if (skuCode == null) {
		if (other.skuCode != null)
			return false;
	} else if (!skuCode.equals(other.skuCode))
		return false;
	return true;
}
@Override
public String toString() {
	return "SkuInventoryQtyChangeJms [skuCode=" + skuCode + ", qty=" + qty
			+ "]";
}

  
  
}

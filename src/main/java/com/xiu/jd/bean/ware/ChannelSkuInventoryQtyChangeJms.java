package com.xiu.jd.bean.ware;

import java.io.Serializable;
import java.util.List;

/**
 * 商品sku库存变化
 * @author user
 *
 */
public class ChannelSkuInventoryQtyChangeJms implements Serializable {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = -2661080331078800032L;

	private String channelCode;
	
	private List<SkuInventoryQtyChangeJms> skuInventoryQtyChanges;

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public List<SkuInventoryQtyChangeJms> getSkuInventoryQtyChanges() {
		return skuInventoryQtyChanges;
	}

	public void setSkuInventoryQtyChanges(
			List<SkuInventoryQtyChangeJms> skuInventoryQtyChanges) {
		this.skuInventoryQtyChanges = skuInventoryQtyChanges;
	}
	
	
}

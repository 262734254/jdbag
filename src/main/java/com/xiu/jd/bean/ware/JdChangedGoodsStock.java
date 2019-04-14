package com.xiu.jd.bean.ware;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品库存MQ
 * @author liweibiao
 *
 */
public class JdChangedGoodsStock implements Serializable{

	private static final long serialVersionUID = 1L;
	/**表主键*/
	private long recordId; 
	/**商品走秀skuCode*/
	private String skuCode;
	/**走秀码*/
	private String xiuCode;
	/**商品sku走秀库存*/
	private String xiuStock;
	/**商品sku京东库存*/
	private String jdStock;
	/**数据创建时间*/
	private Date createdate;
	/**京东商品ID*/
	private String wareId;
	/**京东商品skuId*/
	private String skuId;
	/**0:未同步到京东,1:同步到京东成功,2:失败**/
	private int updateStatus;
	
	public long getRecordId() {
		return recordId;
	}
	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getXiuCode() {
		return xiuCode;
	}
	public void setXiuCode(String xiuCode) {
		this.xiuCode = xiuCode;
	}
	public String getXiuStock() {
		return xiuStock;
	}
	public void setXiuStock(String xiuStock) {
		this.xiuStock = xiuStock;
	}
	public String getJdStock() {
		return jdStock;
	}
	public void setJdStock(String jdStock) {
		this.jdStock = jdStock;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public String getWareId() {
		return wareId;
	}
	public void setWareId(String wareId) {
		this.wareId = wareId;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public int getUpdateStatus() {
		return updateStatus;
	}
	public void setUpdateStatus(int updateStatus) {
		this.updateStatus = updateStatus;
	}
	
}

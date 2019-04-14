package com.xiu.jd.bean.order;

import java.io.Serializable;

/**
 * 京东商品订单项实体
 * @author user
 *
 */
public class JDOrderItemInfo implements Serializable {

	private static final long serialVersionUID = 8456827679041989303L;
	/**京东订单编号**/
	private String jdOrderId;
	
	/**走秀订单号 ***/
	private String localOrderId;
	/**走秀订单编码***/
	private String orderCode;
	
	
	/**京东skuId**/
	private String skuId;
	/**SKU外部ID，即走秀SKU编码**/
	private String outerSkuId;
	/**商品名称+SKU规格**/
	private String skuName;
	/**SKU的京东价(为单个商品的价格)**/
	private String jdPrice;
	/**赠送积分
	private String giftPoint;**/
	/**京东内部商品ID**/
	private String wareId;
	/**单个商品总的数量**/
	private String itemTotal;
	
	
	
	/**收货人名称**/
	private String fullName;
	/**收货人手机号**/
	private String mobile;
	/**收货人固定电话**/
	private String telePhone;
	/**收货人地址详情**/
	private String fullAddress;
	
	/**下单时间**/
	private String placeTime;
	
	/**用户的订单是否被走秀客户人员处理过了，默认为:0未处理,1:已处理**/
	private String isProcess;
	
	//京东订单推送给走秀osc结果:0：未推送(默认),1:推送成功,2:推送失败,3:扣减库存失败  
	private String placeResult;
	
	//是否已进将走秀订单包裹信息推送给京东0:默认,1:推送包裹给京东成功,2:推送包裹给京东失败 
	private String isSyncPlace;	
	
	/**处理时间**/
	private String processTime;
	
	/***订单推送失败描述**/
	private String failDescri;
	
	/***包裹失败信息描述***/
	private String packageFailDesci;
	
 
	
	public JDOrderItemInfo(){
		
	}
	
	
	public JDOrderItemInfo(String jdOrderId, String skuId, String outerSkuId,
			String skuName, String jdPrice, String wareId, String itemTotal) {
		super();
		this.jdOrderId = jdOrderId;
		this.skuId = skuId;
		this.outerSkuId = outerSkuId;
		this.skuName = skuName;
		this.jdPrice = jdPrice;
		this.wareId = wareId;
		this.itemTotal = itemTotal;
	}


	public String getJdOrderId() {
		return jdOrderId;
	}
	public void setJdOrderId(String jdOrderId) {
		this.jdOrderId = jdOrderId;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getOuterSkuId() {
		return outerSkuId;
	}
	public void setOuterSkuId(String outerSkuId) {
		this.outerSkuId = outerSkuId;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public String getJdPrice() {
		return jdPrice;
	}
	public void setJdPrice(String jdPrice) {
		this.jdPrice = jdPrice;
	}

	public String getWareId() {
		return wareId;
	}
	public void setWareId(String wareId) {
		this.wareId = wareId;
	}
	public String getItemTotal() {
		return itemTotal;
	}
	public void setItemTotal(String itemTotal) {
		this.itemTotal = itemTotal;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getTelePhone() {
		return telePhone;
	}


	public void setTelePhone(String telePhone) {
		this.telePhone = telePhone;
	}


	public String getFullAddress() {
		return fullAddress;
	}


	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}





	public String getPlaceTime() {
		return placeTime;
	}


	public void setPlaceTime(String placeTime) {
		this.placeTime = placeTime;
	}


	public String getIsProcess() {
		return isProcess;
	}


	public void setIsProcess(String isProcess) {
		this.isProcess = isProcess;
	}


	public String getProcessTime() {
		return processTime;
	}


	public void setProcessTime(String processTime) {
		this.processTime = processTime;
	}


	public String getLocalOrderId() {
		return localOrderId;
	}


	public void setLocalOrderId(String localOrderId) {
		this.localOrderId = localOrderId;
	}


	public String getOrderCode() {
		return orderCode;
	}


	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}


	public String getPlaceResult() {
		return placeResult;
	}


	public void setPlaceResult(String placeResult) {
		this.placeResult = placeResult;
	}


	public String getIsSyncPlace() {
		return isSyncPlace;
	}


	public void setIsSyncPlace(String isSyncPlace) {
		this.isSyncPlace = isSyncPlace;
	}


	public String getFailDescri() {
		return failDescri;
	}


	public void setFailDescri(String failDescri) {
		this.failDescri = failDescri;
	}


	public String getPackageFailDesci() {
		return packageFailDesci;
	}


	public void setPackageFailDesci(String packageFailDesci) {
		this.packageFailDesci = packageFailDesci;
	}
	
	
	
	
	
}

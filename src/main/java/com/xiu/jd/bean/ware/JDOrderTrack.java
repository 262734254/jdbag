package com.xiu.jd.bean.ware;

import java.io.Serializable;
import java.util.Date;

/**
 * 封装京东订单与本地同步跟踪
 */
public class JDOrderTrack implements Serializable{
	private static final long serialVersionUID = -855969757053304157L;
	private String jdOrderId;
	/**走秀订单号**/
	private String localOrderId;
	/**下单时间**/
	private String placeTime;
	
	//京东订单推送给走秀osc结果:0：未推送(默认),1:推送成功,2:推送失败,3:扣减库存失败
	private String placeResult;
	
	//是否已进将走秀订单包裹信息推送给京东0:默认,1:推送包裹给京东成功,2:推送包裹给京东失败 
	private String isSyncPlace;		
	
	/**走秀订单号,是前台用户看到的那个**/
	private String orderCode;
	
	
	private Date createdate;
	
	/***订单推送失败描述**/
	private String failDescri;
	
	/***包裹失败信息描述***/
	private String packageFailDesci;
	/***包裹推送失败次数***/
	
	private Integer packageFailCount;
	
	/**处理时间**/
	private Date processtime;
	
	/***用户的订单是否被走秀客户人员处理过了，默认为:0未处理,1:已处理**/
	private Integer isprocess;
	
	private String mobile;

	public String getJdOrderId() {
		return jdOrderId;
	}
	public void setJdOrderId(String jdOrderId) {
		this.jdOrderId = jdOrderId;
	}
	public String getLocalOrderId() {
		return localOrderId;
	}
	public void setLocalOrderId(String localOrderId) {
		this.localOrderId = localOrderId;
	}
	public String getPlaceTime() {
		return placeTime;
	}
	public void setPlaceTime(String placeTime) {
		this.placeTime = placeTime;
	}
	public String getPlaceResult() {
		return placeResult;
	}
	public void setPlaceResult(String placeResult) {
		this.placeResult = placeResult;
	}
	public JDOrderTrack() {
		super();
	}
	public String getIsSyncPlace() {
		return isSyncPlace;
	}
	public void setIsSyncPlace(String isSyncPlace) {
		this.isSyncPlace = isSyncPlace;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
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
	public Date getProcesstime() {
		return processtime;
	}
	public void setProcesstime(Date processtime) {
		this.processtime = processtime;
	}
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public Integer getPackageFailCount() {
		return packageFailCount;
	}
	public void setPackageFailCount(Integer packageFailCount) {
		this.packageFailCount = packageFailCount;
	}
	
	public Integer getIsprocess() {
		return isprocess;
	}
	public void setIsprocess(Integer isprocess) {
		this.isprocess = isprocess;
	}
	@Override
	public String toString() {
		return "JDOrderTrack [jdOrderId=" + jdOrderId + ", localOrderId="
				+ localOrderId + ", placeTime=" + placeTime + ", placeResult="
				+ placeResult + ", isSyncPlace=" + isSyncPlace + ", orderCode="
				+ orderCode + ", createdate=" + createdate + ", failDescri="
				+ failDescri + ", packageFailDesci=" + packageFailDesci
				+ ", packageFailCount=" + packageFailCount + ", processtime="
				+ processtime + ", isprocess=" + isprocess + ", mobile="
				+ mobile + "]";
	}
	
	
}

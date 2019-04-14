package com.xiu.jd.web.form;

import java.util.LinkedHashMap;
import java.util.Map;

import com.xiu.common.lang.StringUtil;

public class JdOrderTrackForm {
	

	//**订单包裹信息推送给京东结果**//*
	private String issyncplace;
	
	/**京东订单编号**/
	private String jdOrderId;
	
	/**收货人名称**/
	private String fullName;
	/**收货人手机号**/
	private String mobile;
	/**收货人固定电话**/
	private String telePhone;
	/**收货人地址详情**/
	private String fullAddress;
	
	//京东订单推送给走秀osc结果:0：未推送(默认),1:推送成功,2:推送失败,3:扣减库存失败
	private String placeResult;
	
	/**用户的订单是否被走秀客户人员处理过了，默认为:0未处理,1:已处理**/
	private String isProcess;
	
	
	/**下单开始时间**/
	private String startTime;
	/**下单结束时间**/
	private String endTime;
	
	/**下单开始时间**/
	private String startTimeProcess;
	/**下单结束时间**/
	private String endTimeProcess;
	
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
	
	
	public String getIssyncplace() {
		return issyncplace;
	}
	public void setIssyncplace(String issyncplace) {
		this.issyncplace = issyncplace;
	}
	public String getStartTimeProcess() {
		return startTimeProcess;
	}
	public void setStartTimeProcess(String startTimeProcess) {
		this.startTimeProcess = startTimeProcess;
	}
	public String getEndTimeProcess() {
		return endTimeProcess;
	}
	public void setEndTimeProcess(String endTimeProcess) {
		this.endTimeProcess = endTimeProcess;
	}
	public String getJdOrderId() {
		return jdOrderId;
	}
	public void setJdOrderId(String jdOrderId) {
		this.jdOrderId = jdOrderId;
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
	
	
	public String getPlaceResult() {
		return placeResult;
	}
	public void setPlaceResult(String placeResult) {
		this.placeResult = placeResult;
	}
	
	
	
	public String getIsProcess() {
		return isProcess;
	}
	public void setIsProcess(String isProcess) {
		this.isProcess = isProcess;
	}
	public Map<String, Object> getJdProductInfoForm(){
		 Map<String, Object> parames=new LinkedHashMap<String, Object>();
		 
		 if(!StringUtil.isEmpty(this.jdOrderId)){
			 parames.put("jdOrderId", this.jdOrderId.trim());
		 }
		 if(!StringUtil.isEmpty(this.fullName)){
			 parames.put("fullName", this.fullName.trim());
		 }
		 
		 if(!StringUtil.isEmpty(this.mobile)){
			 parames.put("mobile", this.mobile.trim());
		 }
		 
		 if(!StringUtil.isEmpty(this.telePhone)){
			 parames.put("telePhone", this.telePhone.trim());
		 }
		 
		 if(!StringUtil.isEmpty(this.fullAddress)){
			 parames.put("fullAddress", this.fullAddress.trim());
		 }
		 
		 if(!StringUtil.isEmpty(this.isProcess) && !"-1".equals(this.isProcess)){
			 parames.put("isProcess", this.isProcess.trim());
		 }
		 
		 if(!StringUtil.isEmpty(this.placeResult)&& !"-1".equals(this.placeResult)){
			 parames.put("placeResult", this.placeResult.trim());
		 }
		 
		 if(!StringUtil.isEmpty(this.issyncplace)&& !"-1".equals(this.issyncplace)){
			 parames.put("isSyncPlace", this.issyncplace.trim());
		 }
		 
		 if(!StringUtil.isEmpty(this.startTime)){
			 parames.put("startTime",  this.startTime.trim());
		 }
		
		 if(!StringUtil.isEmpty(this.endTime)){
			 parames.put("endTime",  this.endTime.trim());
		 }
		 
		 if(!StringUtil.isEmpty(this.startTimeProcess)){
			 parames.put("startTimeProcess",  this.startTimeProcess.trim());
		 }
		
		 if(!StringUtil.isEmpty(this.endTimeProcess)){
			 parames.put("endTimeProcess",  this.endTimeProcess.trim());
		 }
		 
		 return parames;
	}
	@Override
	public String toString() {
		return "JdOrderTrackForm [jdOrderId=" + jdOrderId + ", fullName="
				+ fullName + ", mobile=" + mobile + ", telePhone=" + telePhone
				+ ", fullAddress=" + fullAddress + ", startTime=" + startTime
				+ ", endTime=" + endTime + "]";
	}
	
	
}

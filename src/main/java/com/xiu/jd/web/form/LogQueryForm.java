package com.xiu.jd.web.form;

import java.util.LinkedHashMap;
import java.util.Map;

import com.xiu.common.lang.StringUtil;
import com.xiu.jd.utils.DateHelper;

/**
 * 封装前台查询参数
 * Top商品选品查询页面
 * @author liweibiao
 *
 */
public class LogQueryForm {
	
	/**商品sku**/
	private String skuCode;
	
	/**日志类型**/
	private String logType;
	/**京东商品ID**/
	private String jdWareId;

	/**创建开始时间**/
	private String startTime;
	
	/**创建结束时间**/
	private String endTime;
	
	/**更新开始时间**/
	private String updateStartTime;
	
	/**更新结束时间**/
	private String updateEndTime;
	/**同步状态**/
	private String status; 
	
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public String getJdWareId() {
		return jdWareId;
	}
	public void setJdWareId(String jdWareId) {
		this.jdWareId = jdWareId;
	}
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

	public String getUpdateStartTime() {
		return updateStartTime;
	}
	public void setUpdateStartTime(String updateStartTime) {
		this.updateStartTime = updateStartTime;
	}
	public String getUpdateEndTime() {
		return updateEndTime;
	}
	public void setUpdateEndTime(String updateEndTime) {
		this.updateEndTime = updateEndTime;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Map<String, Object> getLogQueryForm(){
		 Map<String, Object> parames=new LinkedHashMap<String, Object>();
		 
		 if(!StringUtil.isEmpty(this.skuCode)){
			 parames.put("skuCode",  this.skuCode.trim());
		 }
		 if(!StringUtil.isEmpty(this.logType)){
			 parames.put("logType",  this.logType.trim());
		 }
		 if(!StringUtil.isEmpty(this.startTime)){
			 parames.put("startTime",  this.startTime.trim());
		 }
		
		 if(!StringUtil.isEmpty(this.endTime)){
			 parames.put("endTime",  this.endTime.trim());
		 }
		 if(!StringUtil.isEmpty(this.updateStartTime)){
			 parames.put("updateStartTime",  this.updateStartTime.trim());
		 }
		
		 if(!StringUtil.isEmpty(this.updateEndTime)){
			 parames.put("updateEndTime",  this.updateEndTime.trim());
		 }
		 if(!StringUtil.isEmpty(this.status)){
			 parames.put("status",  this.status.trim());
		 }
		 if(!StringUtil.isEmpty(this.jdWareId)){
			 parames.put("jdWareId",  this.jdWareId.trim());
		 }
		
		 /**筛选条件
		 if(!StringUtil.isEmpty(this.brandName)){
			 parames.put("brandName",  this.brandName);
		 }
        ***/
		 
		 return parames;
		 
	}
	@Override
	public String toString() {
		return "LogQueryForm [skuCode=" + skuCode +" , logType=" + logType +", startTime=" + startTime + ", endTime=" + endTime
				+ ", updateStartTime=" + updateStartTime + ", updateEndTime=" + updateEndTime+ ", status="+status+",jdWareId="+jdWareId+"]";
	}
	



	


	
	
}

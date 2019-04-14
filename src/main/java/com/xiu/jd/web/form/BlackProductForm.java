package com.xiu.jd.web.form;

import java.util.HashMap;
import java.util.Map;

/**
 * 上下架不对接的黑名单商品
 * 
 * 封装前台页面查询参数
 * @author root
 *
 */
public class BlackProductForm {
	private String xiuCode; //多个走秀码

	private String title; //商品名称
	
	private Integer timeType=0; //时间类型,0:请选择时间,1:上次确认时间,2:创建时间
	
	private String  confirmUserName; //(操作人)确认人
	
	private Integer onLineStatus=-1 ;//京东上下架状态：-1:京东上下架状态,0:未上架,1:在售,2:下架
	
	private Integer isButtJoint=0; //上下架对接状态: 0:上下架对接状态,1:未对接,2:已对接(走秀商品上下架是否与京东对接)
	 
	private Integer  confirmStatus=0; //确认状态: 0:确认状态 ,1:已确认,2:待确认,3:已过期(第一次导入确认状态为:1已确认是对未对接的确认)
	 
	private String startDate; //页面查询开始时间
		 
	private String endDate;//页面查询结束时间
	
	
	public Map<String, Object> getBlackProductForm(){
		Map<String, Object> parames=new HashMap<String, Object>();
		if(this.title!=null && !"".equals(this.title.trim())){
			parames.put("title", this.title.trim());
		}
		if(this.timeType!=null && this.timeType>0){
			if(this.timeType.equals(1)){
				if(this.startDate!=null && !"".equals(this.startDate.trim())){
					parames.put("confirmStartDate", this.startDate.trim());
				}
				if(this.endDate!=null && !"".equals(this.endDate.trim())){
					parames.put("confirmEndDate", this.endDate.trim());
				}
				
			}else if(this.timeType.equals(2)){
				if(this.startDate!=null && !"".equals(this.startDate.trim())){
					parames.put("createStartDate", this.startDate.trim());
				}
				if(this.endDate!=null && !"".equals(this.endDate.trim())){
					parames.put("createEndDate", this.endDate.trim());
				}
			}
		}
		if(this.confirmUserName!=null && !"".equals(this.confirmUserName.trim())){
			parames.put("confirmUserName", this.confirmUserName.trim());
		}
		if(this.onLineStatus!=null && this.onLineStatus>=0){
			parames.put("onLineStatus", this.onLineStatus);
		}
		
		if(this.isButtJoint!=null && this.isButtJoint>0){
			parames.put("isButtJoint", this.isButtJoint);
		}
		if(this.confirmStatus!=null && this.confirmStatus>0){
			parames.put("confirmStatus", this.confirmStatus);
		}
		 return parames;
		
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Integer getTimeType() {
		return timeType;
	}


	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}



	public String getConfirmUserName() {
		return confirmUserName;
	}


	public void setConfirmUserName(String confirmUserName) {
		this.confirmUserName = confirmUserName;
	}


	public Integer getOnLineStatus() {
		return onLineStatus;
	}


	public void setOnLineStatus(Integer onLineStatus) {
		this.onLineStatus = onLineStatus;
	}


	public Integer getIsButtJoint() {
		return isButtJoint;
	}


	public void setIsButtJoint(Integer isButtJoint) {
		this.isButtJoint = isButtJoint;
	}


	public Integer getConfirmStatus() {
		return confirmStatus;
	}


	public void setConfirmStatus(Integer confirmStatus) {
		this.confirmStatus = confirmStatus;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getXiuCode() {
		return xiuCode;
	}

	public void setXiuCode(String xiuCode) {
		this.xiuCode = xiuCode;
	}



	
}

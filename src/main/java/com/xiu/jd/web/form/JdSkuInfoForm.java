package com.xiu.jd.web.form;

import java.util.LinkedHashMap;
import java.util.Map;

import com.xiu.common.lang.StringUtil;

/**
 * 封装前台页面查询参数
 * @author Administrator
 *
 */
public class JdSkuInfoForm {
	/**京东分类ID**/
	private String cid;
	/**走秀码**/
	private String xiuCode;
	/**开始时间**/
	private String startTime;
	/**结束时间**/
	private String endTime;
	/**商品ID**/
	private String jdWareId;
	/**商品sku编码**/
	private String skuSn;
	/**图片推送状态**/
	private String status;
	/**图片状态更新为**/
	private String statu;
	
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getXiuCode() {
		return xiuCode;
	}

	public void setXiuCode(String xiuCode) {
		this.xiuCode = xiuCode;
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

	public String getJdWareId() {
		return jdWareId;
	}

	public void setJdWareId(String jdWareId) {
		this.jdWareId = jdWareId;
	}

	public String getSkuSn() {
		return skuSn;
	}

	public void setSkuSn(String skuSn) {
		this.skuSn = skuSn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	public Map<String, Object> getJdSkuInfoForm(){
		 Map<String, Object> parames=new LinkedHashMap<String, Object>();
		 
		 if(!StringUtil.isEmpty(this.cid)){
			 parames.put("cid",  this.cid.trim());
		 }
		 if(!StringUtil.isEmpty(this.xiuCode)){
			 parames.put("xiuCode",  this.xiuCode.trim());
		 }
		 if(!StringUtil.isEmpty(this.skuSn)){
			 parames.put("skuSn",  this.skuSn.trim());
		 }
		 if(!StringUtil.isEmpty(this.startTime)){
			 parames.put("startTime",  this.startTime.trim());
		 }
		 if(!StringUtil.isEmpty(this.endTime)){
			 parames.put("endTime",  this.endTime.trim());
		 }
		 if(!StringUtil.isEmpty(this.jdWareId)){
			 parames.put("jdWareId",  this.jdWareId.trim());
		 }
		 if(!StringUtil.isEmpty(this.status)){
			 parames.put("status",  this.status.trim());
		 }
		 if(!StringUtil.isEmpty(this.statu)){
			 parames.put("statu",  this.statu.trim());
		 }
		 return parames;
	}
	
	@Override
	public String toString() {
		return "JdProductInfoForm [cid=" + cid +" , xiuCode=" + xiuCode +", startTime=" + startTime 
		+ ", endTime=" + endTime+",jdWareId="+jdWareId+ ",skuSn=" + skuSn + ",status=" + status +"]";
	}
	
}

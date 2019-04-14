package com.xiu.jd.web.form;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.xiu.common.lang.StringUtil;

/**
 * 封装前台页面查询参数
 * @author Administrator
 *
 */
public class JdProductInfoForm {
	/**京东分类ID**/
	private String cid;
	/**走秀码**/
	private String xiuCode;
	/**开始时间**/
	private String startTime;
	/**结束时间**/
	private String endTime;
	/**最低商品京东价**/
	private String minJdPrice;
	/**最高商品京东价**/
	private String maxJdPrice;
	/**最低商品市场价**/
	private String minMarketPrice;
	/**最高商品市场价**/
	private String maxMarketPrice;
	/**京东商品编号**/
	private String jdWareId;
	/**批次号**/
	private String num;
	/**推送状态**/
	private String status;
	/**商品在售状态**/
	private String onLineStatus;
	/**商品标题**/
	private String title;
	/**商品库存**/
	private String wareStock;
	/**多个商品走秀码**/
	private List<String> xiuCodes;
	
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
	
	public String getMinJdPrice() {
		return minJdPrice;
	}
	public void setMinJdPrice(String minJdPrice) {
		this.minJdPrice = minJdPrice;
	}
	public String getMaxJdPrice() {
		return maxJdPrice;
	}
	public void setMaxJdPrice(String maxJdPrice) {
		this.maxJdPrice = maxJdPrice;
	}
	public String getMinMarketPrice() {
		return minMarketPrice;
	}
	public void setMinMarketPrice(String minMarketPrice) {
		this.minMarketPrice = minMarketPrice;
	}
	public String getMaxMarketPrice() {
		return maxMarketPrice;
	}
	public void setMaxMarketPrice(String maxMarketPrice) {
		this.maxMarketPrice = maxMarketPrice;
	}
	
	public String getJdWareId() {
		return jdWareId;
	}
	public void setJdWareId(String jdWareId) {
		this.jdWareId = jdWareId;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getOnLineStatus() {
		return onLineStatus;
	}
	public void setOnLineStatus(String onLineStatus) {
		this.onLineStatus = onLineStatus;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWareStock() {
		return wareStock;
	}
	public void setWareStock(String wareStock) {
		this.wareStock = wareStock;
	}
	public List<String> getXiuCodes() {
		return xiuCodes;
	}
	public void setXiuCodes(List<String> xiuCodes) {
		this.xiuCodes = xiuCodes;
	}
	public Map<String, Object> getJdProductInfoForm(){
		 Map<String, Object> parames=new LinkedHashMap<String, Object>();
		 
		/* if(!StringUtil.isEmpty(this.cid)){
			 parames.put("cid",  this.cid.trim());
		 }*/
		 if(!StringUtil.isEmpty(this.xiuCode)){
			 parames.put("xiuCode",  this.xiuCode.trim());
		 }
		 if(!StringUtil.isEmpty(this.startTime)){
			 parames.put("startTime",  this.startTime.trim());
		 }
		
		 if(!StringUtil.isEmpty(this.endTime)){
			 parames.put("endTime",  this.endTime.trim());
		 }
		 if(!StringUtil.isEmpty(this.minJdPrice)){
			 parames.put("minJdPrice",  this.minJdPrice.trim());
		 }
		 if(!StringUtil.isEmpty(this.maxJdPrice)){
			 parames.put("maxJdPrice",  this.maxJdPrice.trim());
		 }
		 if(!StringUtil.isEmpty(this.minMarketPrice)){
			 parames.put("minMarketPrice",  this.minMarketPrice.trim());
		 }
		 if(!StringUtil.isEmpty(this.maxMarketPrice)){
			 parames.put("maxMarketPrice",  this.maxMarketPrice.trim());
		 }
		 if(!StringUtil.isEmpty(this.jdWareId)){
			 parames.put("jdWareId",  this.jdWareId.trim());
		 }
		 if(!StringUtil.isEmpty(this.num)){
			 parames.put("num",  this.num.trim());
		 }
		 if(!StringUtil.isEmpty(this.status)){
			 parames.put("status",  this.status.trim());
		 }
		 if(!StringUtil.isEmpty(this.onLineStatus)){
			 parames.put("onLineStatus",  this.onLineStatus.trim());
		 }
		 if(!StringUtil.isEmpty(this.title)){
			 parames.put("title",  this.title.trim());
		 }
		 if(!StringUtil.isEmpty(this.wareStock)){
			 parames.put("wareStock", this.wareStock.trim());
		 }
		 
		 return parames;
	}
	
	@Override
	public String toString() {
		return "JdProductInfoForm [cid=" + cid +" , xiuCode=" + xiuCode +", startTime=" + startTime 
		+ ", endTime=" + endTime+", minJdPrice=" + minJdPrice + ", maxJdPrice=" + maxJdPrice 
		+ ", minMarketPrice=" + minMarketPrice + ", maxMarketPrice=" + maxMarketPrice + ",jdWareId="+jdWareId
		+ ",num=" + num + ",status=" + status +",onLineStatus=" + onLineStatus +",title="+title+",wareStock="+wareStock+"]";
	}
	
}
